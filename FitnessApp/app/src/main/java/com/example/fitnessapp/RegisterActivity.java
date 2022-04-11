package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements UserOperations {
    private Button createAccountButton;
    private TextView goToLoginButton;
    private TextView nameInput;
    private TextView emailInput;
    private TextView passswordInput;
    private TextView confirmPasswordInput;
    private UserRepository userRepository = new UserRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        createAccountButton = (Button)findViewById(R.id.create_account_button);
        goToLoginButton = (TextView)findViewById(R.id.go_to_log_in_button);
        nameInput = (TextView)findViewById(R.id.sign_up_name_input);
        emailInput = (TextView)findViewById(R.id.sign_up_email_input);
        passswordInput = (TextView)findViewById(R.id.sign_up_password_input);
        confirmPasswordInput = (TextView)findViewById(R.id.sign_up_confirm_password_input);

        createAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                registerUser();
            }
        });

        goToLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToLoginPage();
            }
        });
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

    private void registerUser(){
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passswordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        if(name.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("")){
            Toast.makeText(getApplicationContext(), "Please fill out all the required fields", Toast.LENGTH_LONG).show();
        }else {
            if(password.equals(confirmPassword)){
                User newUser = new User(name,email,password);
                userRepository.registerUser(newUser, this );
            }else{
                Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void goToLoginPage(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void registerUser(String result) {
        if(result.equals("success")) {
            Toast.makeText(this, "User registered sucessfully", Toast.LENGTH_SHORT).show();
            goToLoginPage();
        }
        else {
            Toast.makeText(this, "User's registration failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loginUser(User user) { }
    @Override
    public void updateUserQuizAnswer(String result) { }
    @Override
    public void addAfterPhoto(String result) { }
    @Override
    public void addBeforePhoto(String result) { }

    @Override
    public void updateDayDoneStatus(String result) { }

    @Override
    public void getUserById(User user) {

    }
}
