package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

public class FormPage3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formpage3);

        Button mSaveBtn = findViewById(R.id.submitForm3);
        mSaveBtn.setEnabled(false);
//        mSaveBtn.setOnClickListener(view -> {
//            Intent intent = new Intent(FormPage3.this, FormPage4.class);
//            startActivity(intent);
//        });
    }

    public void onClick3(View v) {
        ((MotionLayout)v.getParent()).transitionToEnd();

        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FormPage3.this, FormPage4.class);
                startActivity(intent);
            }
        }, 500);
    }

    public void onRadioButtonClicked(View view) {
        Button mSaveBtn = findViewById(R.id.submitForm3);
        boolean checked = ((RadioButton) view).isChecked();

        User user = UserProfile.getInstance();

        switch(view.getId()) {
            case R.id.sedentary:
                if (checked)
                    mSaveBtn.setEnabled(true);
                user.setTypicalDay("sedentary");
                break;
            case R.id.lightly_active:
                if (checked)
                    mSaveBtn.setEnabled(true);
                user.setTypicalDay("lightly_active");
                break;
            case R.id.active:
                if (checked)
                    mSaveBtn.setEnabled(true);
                user.setTypicalDay("active");
                break;
            case R.id.very_active:
                if (checked)
                    mSaveBtn.setEnabled(true);
                user.setTypicalDay("very_active");
                break;
        }
    }
}