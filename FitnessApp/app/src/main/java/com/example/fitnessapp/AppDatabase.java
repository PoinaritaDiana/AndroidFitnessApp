package com.example.fitnessapp;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}