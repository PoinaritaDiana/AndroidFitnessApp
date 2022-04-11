package com.example.fitnessapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutListFragment extends Fragment {
    private List<WorkoutItem> workoutItemArrayList;
    private final static String[] allCsvFiles = {"workouts.csv"};

    public WorkoutListFragment() {
        super(R.layout.fragment_workout_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWorkoutList();

        WorkoutListAdapter adapter = new WorkoutListAdapter(workoutItemArrayList, getContext());
        RecyclerView workoutRecyclerView = view.findViewById(R.id.workouts_list);
        workoutRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        workoutRecyclerView.setAdapter(adapter);

        SearchView searchBar = view.findViewById(R.id.workout_search_bar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String textSearched) {
                adapter.getFilter().filter(textSearched);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String textSearched) {
                adapter.getFilter().filter(textSearched);
                return true;
            }
        });
    }


    private void initWorkoutList(){
        workoutItemArrayList = new ArrayList<>();
        for(String csvFileName: allCsvFiles) {
            CSVReader csvReader = new CSVReader(this.getContext(), csvFileName);
            List<String[]> rows = csvReader.readCSV();
            for (int i = 0; i < rows.size(); i++) {
                workoutItemArrayList.add(new WorkoutItem(rows.get(i)[0], rows.get(i)[1], rows.get(i)[2],
                        Float.parseFloat(rows.get(i)[3]), rows.get(i)[4], rows.get(i)[5]));
            }
        }
    }

}