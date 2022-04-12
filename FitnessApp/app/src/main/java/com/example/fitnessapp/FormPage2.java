package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

public class FormPage2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formpage2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Form Page 2");

        Button mSaveBtn = findViewById(R.id.submitForm2);
        mSaveBtn.setEnabled(false);
//        mSaveBtn.setOnClickListener(view -> {
//            Intent intent = new Intent(FormPage2.this, FormPage3.class);
//            startActivity(intent);
//        });
    }

    public void onClick2(View v) {
        ((MotionLayout)v.getParent()).transitionToEnd();
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FormPage2.this, FormPage3.class);
                startActivity(intent);
            }
        }, 500);


    }

    public void onRadioButtonClicked(View view) {
        Button mSaveBtn = findViewById(R.id.submitForm2);
        boolean checked = ((RadioButton) view).isChecked();
        User user = UserProfile.getInstance();
        System.out.println(user.getGender());
        System.out.println(user.getHeight());

        switch(view.getId()) {
            case R.id.lose_weight:
                if (checked)
                    mSaveBtn.setEnabled(true);
                user.setGoal("lose_weight");
                break;
            case R.id.maintain_weight:
                if (checked)
                    mSaveBtn.setEnabled(true);
                user.setGoal("maintain_weight");
                break;
            case R.id.gain_muscle:
                if (checked)
                    mSaveBtn.setEnabled(true);
                user.setGoal("gain_muscle");
                break;
        }
    }
}