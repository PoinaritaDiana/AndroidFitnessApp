package com.example.fitnessapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

public class FormPage4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formpage4);

        Button mSaveBtn = findViewById(R.id.submitForm4);
        mSaveBtn.setEnabled(false);
//        mSaveBtn.setOnClickListener(view -> {
//            Intent intent = new Intent(FormPage4.this, FormResults.class);
//            startActivity(intent);
//        });
    }

    public void onClick4(View v) {
        ((MotionLayout)v.getParent()).transitionToEnd();

        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FormPage4.this, FormResults.class);
                startActivity(intent);
            }
        }, 500);

    }

    public void onRadioButtonClicked(View view) {
        Button mSaveBtn = findViewById(R.id.submitForm4);
        boolean checked = ((RadioButton) view).isChecked();

        User user = UserProfile.getInstance();

        switch(view.getId()) {
            case R.id.pear:
                if (checked)
                    mSaveBtn.setEnabled(true);
                user.setBodyType("pear");
                break;
            case R.id.round:
                if (checked)
                    mSaveBtn.setEnabled(true);
                user.setBodyType("round");
                break;
            case R.id.rectangle:
                if (checked)
                    mSaveBtn.setEnabled(true);
                user.setBodyType("rectangle");
                break;
            case R.id.hourglass:
                if (checked)
                    mSaveBtn.setEnabled(true);
                user.setBodyType("hourglass");
                break;
        }
    }
}