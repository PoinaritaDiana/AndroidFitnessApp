package com.example.fitnessapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class FormPage1 extends AppCompatActivity {

    boolean radioButtonChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formpage1);

        User user = UserProfile.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Form Page 1");

        EditText ageEditText = findViewById(R.id.age);
        EditText heightEditText = findViewById(R.id.height);
        EditText weightEditText = findViewById(R.id.weight);
        EditText goalWeightEditText = findViewById(R.id.goal_weight);

        Button mSaveBtn = findViewById(R.id.submitForm1);
        mSaveBtn.setOnClickListener(view -> {
            String ageString = ageEditText.getText().toString();
            Integer age = Integer.parseInt(ageString);
            String heightString = heightEditText.getText().toString();
            Float height = Float.parseFloat(heightString);
            String weightString = weightEditText.getText().toString();
            Float weight = Float.parseFloat(weightString);
            String goalWeightString = goalWeightEditText.getText().toString();
            Float goalWeight = Float.parseFloat(goalWeightString);

            user.setAge(age);
            user.setHeight(height);
            user.setWeight(weight);
            user.setGoalWeight(goalWeight);

            Intent intent = new Intent(FormPage1.this, FormPage2.class);
            startActivity(intent);
        });

    }

    public void radioButtonhandler(View view) {

        User user = UserProfile.getInstance();

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.male:
                if (checked)
                    radioButtonChecked = true;
                user.setGender("male");
                break;
            case R.id.female:
                if (checked)
                    radioButtonChecked = true;
                user.setGender("female");
                break;
            case R.id.other:
                if (checked)
                    radioButtonChecked = true;
                user.setGender("other");
                break;
        }
    }

}