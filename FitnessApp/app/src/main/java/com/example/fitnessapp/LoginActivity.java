package com.example.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, UserOperations {
    private static final int RC_SIGN_IN = 1;
    private Button loginButton;
    private SignInButton googleSignInButton;
    private TextView goToRegisterButton;
    private TextView emailInput;
    private TextView passswordInput;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private UserRepository userRepository = new UserRepository();
    private SharedPreferences sharedPreferences;
    private String userGoogleId;
    private String userGoogleEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        loginButton = (Button)findViewById(R.id.log_in_user_button);
        googleSignInButton = (SignInButton)findViewById(R.id.sign_in_with_google_button);
        goToRegisterButton = (TextView)findViewById(R.id.go_to_sign_up_button);
        emailInput = (TextView)findViewById(R.id.sign_in_email_input);
        passswordInput = (TextView)findViewById(R.id.sign_in_password_input);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("414339119690-b4f4lgpp2qprjrocb1e5jhccud01d09d.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        UserProfile.setmGoogleSignInClient(mGoogleSignInClient);

        loginButton.setOnClickListener(v -> logInUser());
        goToRegisterButton.setOnClickListener(v -> goToRegisterPage());
        googleSignInButton.setOnClickListener(v -> signInWithGoogle());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logInUser(){
        String email = emailInput.getText().toString();
        String password = passswordInput.getText().toString();

        if(email.equals("") || password.equals("")){
            Toast.makeText(getApplicationContext(), "Please fill out all the required fields", Toast.LENGTH_LONG).show();
        }else {
            userRepository.logInUser(email, password, this);
        }
    }

    private void redirectUser(User currentUser){
        if(currentUser.getBodyType()==null) {
            goToQuiz();
        }
        else{
            goToHomePage();
        }
    }

    private void goToRegisterPage(){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    private void goToHomePage(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    private void goToQuiz(){
        Intent intent = new Intent(getApplicationContext(), FormPage1.class);
        startActivity(intent);
    }

    private void signInWithGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            userGoogleId = account.getId();
            userGoogleEmail = account.getEmail();
            userRepository.getUserByGoogleId(userGoogleId, this);
        } catch (ApiException e) {
            System.out.println(e);
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void loginUser(User user) {
        if(user == null) {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "User logged in sucessfully", Toast.LENGTH_SHORT).show();
            sharedPreferences.edit().putInt("currentUserId", user.getId()).apply();
            String userJson = new Gson().toJson(user);
            sharedPreferences.edit().putString("currentUserDetails", userJson).apply();
            redirectUser(user);
        }
    }


    @Override
    public void updateUserQuizAnswer(String result) { }

    @Override
    public void addAfterPhoto(String result) { }

    @Override
    public void addBeforePhoto(String result) { }

    @Override
    public void updateDayDoneStatus(String result) {

    }

    @Override
    public void getUserById(User user) {
        if(user==null){
            User newUser = new User(userGoogleId, userGoogleId, userGoogleId );
            userRepository.registerUser(newUser, this );
        }
        else{
            userRepository.logInUser(userGoogleId, userGoogleId, this);
        }
    }

    @Override
    public void registerUser(String result) {
        if(result.equals("success")) {
            userRepository.logInUser(userGoogleId, userGoogleId, this);
        }
    }
}
