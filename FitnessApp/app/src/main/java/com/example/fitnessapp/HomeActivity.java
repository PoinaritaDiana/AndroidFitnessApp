package com.example.fitnessapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity{
    private static final String SELECTED_FRAGMENT = "my_plan_menu_item";
    private String lastFragment;
    private BottomNavigationView bottomNavigation;
    private WorkoutListFragment workoutListFragment = new WorkoutListFragment();
    private PlanFragment myPlanFragment = new PlanFragment();
    private UserProfileFragment myProfileFragment = new UserProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(navigationItemSelectedListener);
        if(savedInstanceState==null) {
            bottomNavigation.setSelectedItemId(R.id.my_plan_menu_item);
        }
        if(lastFragment=="workout_list_menu_item"){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, workoutListFragment)
                    .commit();
        }
        if(lastFragment=="my_plan_menu_item"){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, myPlanFragment)
                    .commit();
        }
        if(lastFragment=="profile_menu_item"){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, myProfileFragment)
                    .commit();
        }
    }


    NavigationBarView.OnItemSelectedListener navigationItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.workout_list_menu_item:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, workoutListFragment)
                                .commit();
                        lastFragment="workout_list_menu_item";
                        return true;
                    case R.id.my_plan_menu_item:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, myPlanFragment)
                                .commit();
                        lastFragment="my_plan_menu_item";
                        return true;
                    case R.id.profile_menu_item:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, myProfileFragment)
                                .commit();
                        lastFragment="profile_menu_item";
                        return true;
                }
                return false;
            };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SELECTED_FRAGMENT, lastFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        lastFragment = inState.getString(SELECTED_FRAGMENT);
    }

}



