package com.example.fitnessapp;

import android.app.Application;

import androidx.room.Room;


public class ApplicationController extends Application {
    private static ApplicationController mInstance;
    private static AppDatabase mAppDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mAppDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "better-me-fitness").fallbackToDestructiveMigration().build();
    }

    public static ApplicationController getInstance(){
        return mInstance;
    }

    public static AppDatabase getAppDatabase(){
        return mAppDatabase;
    }
}
