package com.example.fitnessapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class UserRepository {
    private AppDatabase appDatabase;

    public UserRepository() {
        appDatabase = ApplicationController.getAppDatabase();
    }

    public void getUserByGoogleId(final String userGoogleId, final UserOperations listener){
        new GetUser(listener).execute(userGoogleId);
    }

    public void registerUser(final User user, final UserOperations listener) {
        new RegisterUser(listener).execute(user);
    }

    public void logInUser(final String email, final String password, final UserOperations listener){
        new LogInUser(listener).execute(new LoginCredentials(email, password));
    }

    public void updateUser(int userId,  String typicalDay, String bodyType,
                           Float weight, Float goalWeight, String gender,
                           Float height, Integer age, String goal,
                           Integer dailyCalories, UserOperations listener) {
        new UpdateUserQuizAnswer(listener).execute(new UserQuizAnswers(userId, typicalDay, bodyType, weight, goalWeight, gender, height, age, goal, dailyCalories));
    }

    public void addBeforePhoto(final int userId, final String image, final UserOperations listener) {
        new AddBeforePhoto(listener).execute(new IdAndImagePath(userId, image));

    } public void addAfterPhoto(final int userId, final String image, final UserOperations listener) {
        new AddAfterPhoto(listener).execute(new IdAndImagePath(userId, image));
    }

    public void updateDayDoneStatus(final int userId, final int dayNumber, final Boolean status, final UserOperations listener) {
        new UpdateDayDoneStatus(listener).execute(new DayStatus(userId, dayNumber, status));
    }


    private class DayStatus{
        int userId;
        int dayNumber;
        Boolean status;

        public DayStatus(int userId, int dayNumber, Boolean status) {
            this.userId = userId;
            this.dayNumber = dayNumber;
            this.status = status;
        }
    }
    private class UserQuizAnswers {
        int userId;
        String typicalDay;
        String bodyType;
        Float weight;
        Float goalWeight;
        String gender;
        Float height;
        Integer age;
        String goal;
        Integer dailyCalories;

        public UserQuizAnswers(int userId, String typicalDay, String bodyType, Float weight, Float goalWeight, String gender, Float height, Integer age, String goal, Integer dailyCalories) {
            this.userId = userId;
            this.typicalDay = typicalDay;
            this.bodyType = bodyType;
            this.weight = weight;
            this.goalWeight = goalWeight;
            this.gender = gender;
            this.height = height;
            this.age = age;
            this.goal = goal;
            this.dailyCalories = dailyCalories;
        }
    }

    private class IdAndImagePath {
        int userId;
        String imagePath;

        IdAndImagePath(int userId, String imagePath) {
            this.userId = userId;
            this.imagePath = imagePath;
        }
    }

    private class LoginCredentials {
        String email;
        String password;

        LoginCredentials(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }


    private class UpdateDayDoneStatus extends AsyncTask<DayStatus, Void, String> {
        UserOperations listener;

        UpdateDayDoneStatus(UserOperations listener){
            this.listener = listener;
        }

        @Override
        protected String doInBackground(DayStatus... dayStatus) {
            try {
                int userId = dayStatus[0].userId;
                int dayNumber = dayStatus[0].dayNumber;
                Boolean status = dayStatus[0].status;
                switch (dayNumber) {
                    case 1:
                        appDatabase.userDao().updateDay1Done(userId, status);
                        break;
                    case 2:
                        appDatabase.userDao().updateDay2Done(userId, status);
                        break;
                    case 3:
                        appDatabase.userDao().updateDay3Done(userId, status);
                        break;
                    case 4:
                        appDatabase.userDao().updateDay4Done(userId, status);
                        break;
                    case 5:
                        appDatabase.userDao().updateDay5Done(userId, status);
                        break;
                    case 6:
                        appDatabase.userDao().updateDay6Done(userId, status);
                        break;
                    case 7:
                        appDatabase.userDao().updateDay7Done(userId, status);
                        break;
                    case 8:
                        appDatabase.userDao().updateDay8Done(userId, status);
                        break;
                    case 9:
                        appDatabase.userDao().updateDay9Done(userId, status);
                        break;
                    case 10:
                        appDatabase.userDao().updateDay10Done(userId, status);
                        break;
                }
            } catch(Exception e){
                return "error";
            }
            return "success";
        }

        @Override
        protected void onPostExecute(String result) {
            listener.updateDayDoneStatus(result);
        }
    }

    private class RegisterUser extends AsyncTask<User, Void, String> {
        UserOperations listener;

        RegisterUser(UserOperations listener){
            this.listener = listener;
        }

        @Override
        protected String doInBackground(User... users) {
            try {
                appDatabase.userDao().register(users[0]);
            } catch(Exception e){
                return "error";
            }
            return "success";
        }

        @Override
        protected void onPostExecute(String result) {
            listener.registerUser(result);
        }
    }

    private class GetUser extends AsyncTask<String, Void, User> {
        UserOperations listener;

        GetUser(UserOperations listener){
            this.listener = listener;
        }

        @Override
        protected User doInBackground(String... ids) {
            User user = null;
            try {
                user = appDatabase.userDao().getUserByGoogleId(ids[0]);
            } catch(Exception e){
                return null;
            }
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            listener.getUserById(user);
        }
    }

    private class LogInUser extends AsyncTask<LoginCredentials, Integer, User> {
        UserOperations listener;

        LogInUser(UserOperations listener){
            this.listener = listener;
        }

        @Override
        protected User doInBackground(LoginCredentials... credentials) {
            User user = null;
            try {
                String email = credentials[0].email;
                String password = credentials[0].password;
                user = appDatabase.userDao().checkUserExists(email, password);
            } catch(Exception e){
                return null;
            }
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            listener.loginUser(user);
        }
    }

    private class UpdateUserQuizAnswer extends AsyncTask<UserQuizAnswers, Void, String> {
        UserOperations listener;
        UpdateUserQuizAnswer(UserOperations listener){
            this.listener = listener;
        }

        @Override
        protected String doInBackground(UserQuizAnswers... userQuizAnswers) {
            try {
                UserQuizAnswers userUpdate = userQuizAnswers[0];
                appDatabase.userDao().updateUserQuizAnswer(userUpdate.userId, userUpdate.typicalDay, userUpdate.bodyType, userUpdate.weight,
                        userUpdate.goalWeight, userUpdate.gender, userUpdate.height, userUpdate.age, userUpdate.goal, userUpdate.dailyCalories);
            } catch(Exception e){
                return "error";
            }
            return "success";
        }

        @Override
        protected void onPostExecute(String result){
            listener.updateUserQuizAnswer(result);
        }
    }

    private class AddBeforePhoto extends AsyncTask<IdAndImagePath, Void, String> {
        UserOperations listener;
        AddBeforePhoto(UserOperations listener){
            this.listener = listener;
        }

        @Override
        protected String doInBackground(IdAndImagePath... params) {
            try {
                int userId = params[0].userId;
                String image = params[0].imagePath;
                appDatabase.userDao().addBeforePhoto(userId, image);
            } catch(Exception e){
                return "error";
            }
            return "success";
        }

        @Override
        protected void onPostExecute(String result){
            listener.addBeforePhoto(result);
        }
    }

    private class AddAfterPhoto extends AsyncTask<IdAndImagePath, Void, String> {
        UserOperations listener;
        AddAfterPhoto(UserOperations listener){
            this.listener = listener;
        }

        @Override
        protected String doInBackground(IdAndImagePath... params) {
            try {
                int userId = params[0].userId;
                String image= params[0].imagePath;
                appDatabase.userDao().addAfterPhoto(userId, image);
            } catch(Exception e){
                return "error";
            }
            return "success";
        }

        @Override
        protected void onPostExecute(String result){
            listener.addAfterPhoto(result);
        }
    }


}


