package com.example.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class FormResults extends AppCompatActivity  implements UserOperations {
    private UserRepository userRepository;
    private SharedPreferences sharedPreferences;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formresults);

        userRepository = new UserRepository();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        user = UserProfile.getInstance();

        saveUserDetailsToDb(user);

        TextView resultCalories = findViewById(R.id.calories);

        if(user.getGoal().equalsIgnoreCase("lose_weight")) {
            resultCalories.setText("To lose weight you need to consume " +
                    String.valueOf(user.calculateLoseWeightCalories()) + " calories per day." );
        }
        else if(user.getGoal().equalsIgnoreCase("maintain_weight")) {
            resultCalories.setText("To maintain your weight you need to consume " +
                    String.valueOf(user.calculateMaintainCalories()) + " calories per day." );
        }
        else if(user.getGoal().equalsIgnoreCase("gain_muscle")) {
            resultCalories.setText("To gain muscle mass you need to consume " +
                    String.valueOf(user.calculateGainMuscleCalories()) + " calories per day." );
        }

        Button goToProfile = findViewById(R.id.resultsBtn);
        goToProfile.setOnClickListener(view -> {
           goToHomePage();
        });
    }


    private void saveUserDetailsToDb(User user){
        int userId = sharedPreferences.getInt("currentUserId", -1);
        if(userId>0){
            userRepository.updateUser(userId, user.getTypicalDay(), user.getBodyType(), user.getWeight(),
                    user.getGoalWeight(), user.getGender(), user.getHeight(), user.getAge(),
                    user.getGoal(), user.getDailyCalories(), this);
        }
    }

    private void goToHomePage(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateUserQuizAnswer(String result) {
        if(result.equals("success")) {
            Toast.makeText(this, "Details added sucessfully", Toast.LENGTH_SHORT).show();
            updateCurrentUser();
        }
        else {
            Toast.makeText(this, "Action failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCurrentUser(){
        String userJson = new Gson().toJson(user);
        sharedPreferences.edit().putString("currentUserDetails", userJson).apply();
    }

    @Override
    public void registerUser(String result) { }

    @Override
    public void loginUser(User user) { }

    @Override
    public void addAfterPhoto(String result) { }

    @Override
    public void addBeforePhoto(String result) { }

    @Override
    public void updateDayDoneStatus(String result) {

    }

    @Override
    public void getUserById(User user) {

    }
}