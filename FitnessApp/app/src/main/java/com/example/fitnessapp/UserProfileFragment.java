package com.example.fitnessapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class UserProfileFragment extends Fragment implements UserOperations  {
    private UserRepository userRepository;
    private SharedPreferences sharedPreferences;

    private static final int CAMERA_REQUEST_BEFORE = 0;
    private static final int CAMERA_REQUEST_AFTER = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private User user;
    private String pictureFilePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        userRepository = new UserRepository();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        user = getCurrentUser();

        Button beforePhotoBtn = getView().findViewById(R.id.beforePhotoBtn);
        Button afterPhotoBtn = getView().findViewById(R.id.afterPhotoBtn);
        Button logoutBtn = getView().findViewById(R.id.logoutButton);

        logoutBtn.setOnClickListener(v -> {
            sharedPreferences.edit().remove("currentUserId").apply();
            sharedPreferences.edit().remove("currentUserDetails").apply();
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        });

        beforePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    File photoFile = null;
                    try {
                        String imageFileName = "before_photo_" + user.getId();
                        photoFile = createImageFile(imageFileName);
                    } catch (IOException ex) {
                        Toast.makeText(getContext(), "Photo file can't be created, please try again", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getContext(),
                                "com.fitnessapp.android.fileprovider",
                                photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_BEFORE);
                    }
                }
            }
        });

        afterPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    File photoFile = null;
                    try {
                        String imageFileName = "after_photo_" + user.getId();
                        photoFile = createImageFile(imageFileName);
                    } catch (IOException ex) {
                        Toast.makeText(getContext(), "Photo file can't be created, please try again", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getContext(),
                                "com.fitnessapp.android.fileprovider",
                                photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_AFTER);
                    }
                }
            }
        });

        TextView currentWeight = getView().findViewById(R.id.currentWeight);
        currentWeight.setText(user.getWeight() + " kg");

        TextView height = getView().findViewById(R.id.height);
        height.setText(user.getHeight() + " cm");

        TextView goalWeight = getView().findViewById(R.id.goal_weight);
        goalWeight.setText(user.getGoalWeight() + " kg");

        TextView calories = getView().findViewById(R.id.dailyCalories);
        calories.setText(String.valueOf(user.getDailyCalories()));

        TextView age = getView().findViewById(R.id.age);
        age.setText(String.valueOf(user.getAge()));

        TextView gender = getView().findViewById(R.id.gender);
        gender.setText(user.getGender());

        showAfterPhoto();
        showBeforePhoto();
    }

    private User getCurrentUser() {
        String userJson = sharedPreferences.getString("currentUserDetails", null);
        User user = new Gson().fromJson(userJson, User.class);
        return user;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Camera permission granted", Toast.LENGTH_SHORT).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_BEFORE);
            }
            else {
                Toast.makeText(getActivity(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST_BEFORE && resultCode == Activity.RESULT_OK)
        {
            saveBeforePhoto();
        }
        if (requestCode == CAMERA_REQUEST_AFTER && resultCode == Activity.RESULT_OK)
        {
            saveAfterPhoto();
        }
    }

    private void saveBeforePhoto(){
        int userId = sharedPreferences.getInt("currentUserId",-1);
        userRepository.addBeforePhoto(userId, pictureFilePath, this);
    }

    private void saveAfterPhoto(){
        int userId = sharedPreferences.getInt("currentUserId",-1);
        userRepository.addAfterPhoto(userId, pictureFilePath, this);
    }

    private void updateCurrentUser(){
        String userJson = new Gson().toJson(user);
        sharedPreferences.edit().putString("currentUserDetails", userJson).apply();
    }

    private void showBeforePhoto(){
        String beforePhotoPath = user.getBeforePhoto();
        if(beforePhotoPath!=null) {
            File imgFile = new File(beforePhotoPath);
            ImageView img = getView().findViewById(R.id.beforePhoto);
            if (imgFile.exists()) {
                img.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }

    private void showAfterPhoto(){
        String afterPhotoPath = user.getAfterPhoto();
        if(afterPhotoPath!=null) {
            File imgFile = new File(afterPhotoPath);
            ImageView img = getView().findViewById(R.id.afterPhoto);
            if (imgFile.exists()) {
                img.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }

    @Override
    public void addAfterPhoto(String result) {
        if(result.equals("success")) {
            user.setAfterPhoto(pictureFilePath);
            updateCurrentUser();
            showAfterPhoto();
            Toast.makeText(this.getActivity(), "Photo added sucessfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this.getActivity(), "Action failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addBeforePhoto(String result) {
        if(result.equals("success")) {
            user.setBeforePhoto(pictureFilePath);
            updateCurrentUser();
            showBeforePhoto();
            Toast.makeText(this.getActivity(), "Photo added sucessfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this.getActivity(), "Action failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateDayDoneStatus(String result) {

    }

    @Override
    public void getUserById(User user) {

    }


    @Override
    public void registerUser(String result) { }

    @Override
    public void loginUser(User user) { }

    @Override
    public void updateUserQuizAnswer(String result) { }

    private File createImageFile(String imageFileName) throws IOException {
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        pictureFilePath = imageFile.getAbsolutePath();
        return imageFile;
    }

}