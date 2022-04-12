package com.example.fitnessapp;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class UserProfile {
    private static User instance;
    private static GoogleSignInClient mGoogleSignInClient;

    public static User getInstance() {
        if(instance == null) {
            instance = new User();
        }
        return instance;
    }

    public static GoogleSignInClient getGoogleClient(){
        return mGoogleSignInClient;
    }

    public static void setmGoogleSignInClient(GoogleSignInClient mGoogleSignInClient) {
        mGoogleSignInClient = mGoogleSignInClient;
    }
}
