package com.example.fitnessapp;

public interface UserOperations {
    void registerUser(String result);
    void loginUser(User user);
    void updateUserQuizAnswer(String result);
    void addAfterPhoto(String result);
    void addBeforePhoto(String result);
    void updateDayDoneStatus(String result);
    void getUserById(User user);
}
