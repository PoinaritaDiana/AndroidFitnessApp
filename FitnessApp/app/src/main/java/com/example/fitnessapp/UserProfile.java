package com.example.fitnessapp;

public class UserProfile {
    private static User instance;

    public static User getInstance() {
        if(instance == null) {
            instance = new User();
        }
        return instance;
    }
}
