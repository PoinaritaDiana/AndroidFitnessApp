package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment {
    public PlanFragment() {
        super(R.layout.fragment_plan);
    }

    private Integer state;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList<String> days = new ArrayList<String>();
        for (int i = 1; i <= 10; ++i) {
            days.add("DAY " + i);
        }

        ListView daysListView = (ListView) view.findViewById(R.id.plan_days_list);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(
                getActivity(),
                R.layout.my_plan_day_card_item,
                days);
        daysListView.setAdapter(arr);


        daysListView.setOnItemClickListener((parent, view1, position, id) -> {
            switch (position) {
                case 0 :
                    state = 0;
                    break;
                case 1 :
                    state = 1;
                    break;
                case 2:
                    state = 2;
                    break;
                case 3 :
                    state = 3;
                    break;
                case 4:
                    state = 4;
                    break;
                case 5 :
                    state = 5;
                    break;
                case 6:
                    state = 6;
                    break;
                case 7 :
                    state = 7;
                    break;
                case 8:
                    state = 8;
                    break;
                case 9 :
                    state = 9;
                    break;
                case 10:
                    state = 10;
                    break;
            }

            Intent intent = new Intent(getActivity(), DayPlanActivity.class);
            intent.putExtra("state", state);
            startActivity(intent);
        });
    }
}
