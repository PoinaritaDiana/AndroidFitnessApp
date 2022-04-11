package com.example.fitnessapp;

import android.animation.ObjectAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.text.util.Linkify;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DayPlanActivity extends AppCompatActivity implements UserOperations {
    private UserRepository userRepository = new UserRepository();
    private SharedPreferences sharedPreferences;
    private final ResourcesWorkoutsAndMeals resources = new ResourcesWorkoutsAndMeals();
    private User user;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_plan);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Bundle extras = getIntent().getExtras();
        Integer state = extras.getInt("state");

        user =  getCurrentUser();

        ImageView currentDay = findViewById(R.id.day);
        Resources res = getResources();

        String mDrawableName = "d" + (state + 1);
        int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
        currentDay.setImageResource(resID);

        TextView workoutLink = findViewById(R.id.workout);
        String url = findWorkoutAndSetMealPlan(user, state);
        workoutLink.setText(url);
        Linkify.addLinks(workoutLink, Linkify.ALL);

        int dayNumber = state+1;
        CheckBox checkBox = findViewById(R.id.done);
        Boolean status = getDoneDayStatus(dayNumber);
        checkBox.setChecked(status);
        checkBox.setOnClickListener(v -> {
            ImageView img = findViewById(R.id.day);
            ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(img,
                    "rotation", 0f, 360f);
            imageViewObjectAnimator.setDuration(1000);
            imageViewObjectAnimator.start();
        });
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateDoneDayStatus(dayNumber, isChecked));
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setDoneDayStatus(dayNumber, isChecked);
            updateDoneDayStatus(dayNumber, isChecked);
        });

        Button shareBtn = findViewById(R.id.shareWorkout);
        shareBtn.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "My workout for day " + (state + 1) + " is " + url);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

        createNotification(state);
    }


    private void updateDoneDayStatus(int dayNumber, Boolean status){
        int userId = sharedPreferences.getInt("currentUserId",-1);
        userRepository.updateDayDoneStatus(userId, dayNumber, status, this);
    }

    private void updateCurrentUser(){
        String userJson = new Gson().toJson(user);
        sharedPreferences.edit().putString("currentUserDetails", userJson).apply();
    }

    @Override
    public void updateDayDoneStatus(String result) {
        if(result.equals("success")) {
            updateCurrentUser();
            Toast.makeText(this, "Day status modified sucessfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Action failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getUserById(User user) {

    }

    private Boolean getDoneDayStatus(int dayNumber){
        Boolean isDone = false;
        switch (dayNumber) {
            case 1:
                isDone = user.getDay1Done();
                break;
            case 2:
                isDone = user.getDay2Done();
                break;
            case 3:
                isDone = user.getDay3Done();
                break;
            case 4:
                isDone = user.getDay4Done();
                break;
            case 5:
                isDone = user.getDay5Done();
                break;
            case 6:
                isDone = user.getDay6Done();
                break;
            case 7:
                isDone = user.getDay7Done();
                break;
            case 8:
                isDone = user.getDay8Done();
                break;
            case 9:
                isDone = user.getDay9Done();
                break;
            case 10:
                isDone = user.getDay10Done();
                break;
        }
        return isDone;
    }

    private void setDoneDayStatus(int dayNumber, Boolean status){
        switch (dayNumber) {
            case 1:
                user.setDay1Done(status);
                break;
            case 2:
                user.setDay2Done(status);
                break;
            case 3:
                user.setDay3Done(status);
                break;
            case 4:
                user.setDay4Done(status);
                break;
            case 5:
                user.setDay5Done(status);
                break;
            case 6:
                user.setDay6Done(status);
                break;
            case 7:
                user.setDay7Done(status);
                break;
            case 8:
                user.setDay8Done(status);
                break;
            case 9:
                user.setDay9Done(status);
                break;
            case 10:
                user.setDay10Done(status);
                break;
        }
    }

    private User getCurrentUser() {
        String userJson = sharedPreferences.getString("currentUserDetails", null);
        User user = new Gson().fromJson(userJson, User.class);
        return user;
    }

    private String findWorkoutAndSetMealPlan(User user, Integer state) {
        List<String> workoutPlan = new ArrayList<>();
        String goal = "l";

        if (user.getGender().equalsIgnoreCase("Female")) {
            if (user.getGoal().equalsIgnoreCase("Lose_Weight")) {
                workoutPlan = resources.getLoseWeightFemale();
                goal = "l";
            } else if (user.getGoal().equalsIgnoreCase("Gain_muscle")) {
                workoutPlan = resources.getGainMuscleFemale();
                goal = "g";
            } else if (user.getGoal().equalsIgnoreCase("Maintain_weight")) {
                workoutPlan = resources.getMaintainWeightFemale();
                goal = "g";
            }
        } else if (user.getGender().equalsIgnoreCase("Male")) {
            if (user.getGoal().equalsIgnoreCase("Lose_Weight")) {
                workoutPlan = resources.getLoseWeightMale();
                goal = "l";
            } else if (user.getGoal().equalsIgnoreCase("Gain_muscle")) {
                workoutPlan = resources.getGainMuscleMale();
                goal = "g";
            } else if (user.getGoal().equalsIgnoreCase("Maintain_weight")) {
                workoutPlan = resources.getMaintainWeightMale();
                goal = "g";
            }
        }

        ImageView mealPlan = findViewById(R.id.mealPlan);
        Resources res = getResources();

        String mDrawableName = goal + (state + 1);
        int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
        mealPlan.setImageResource(resID);

        return workoutPlan.get(state);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotification(Integer state){
        Intent notifyIntent = new Intent(this, DayPlanActivity.class);
        notifyIntent.putExtra("state", state);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        NotificationManager notificationManager1 =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "workout";
        CharSequence channelName = "Channel";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager1.createNotificationChannel(notificationChannel);

        PendingIntent notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable.icon_powerlifting)
                .setContentTitle("It's time for your workout")
                .setContentText("Start now!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void registerUser(String result) { }

    @Override
    public void loginUser(User user) { }

    @Override
    public void updateUserQuizAnswer(String result) { }

    @Override
    public void addAfterPhoto(String result) { }

    @Override
    public void addBeforePhoto(String result) { }
}