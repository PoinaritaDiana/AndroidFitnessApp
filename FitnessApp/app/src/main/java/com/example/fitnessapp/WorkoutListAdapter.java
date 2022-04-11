package com.example.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.ViewHolder> implements Filterable {

    private List<WorkoutItem> filteredWorkoutItemArrayList;
    private List<WorkoutItem> fullWorkoutItemArrayList;
    private Context context;

    public WorkoutListAdapter(List<WorkoutItem> workoutItemArrayList, Context context) {
        this.fullWorkoutItemArrayList = new ArrayList<>(workoutItemArrayList);
        this.filteredWorkoutItemArrayList = workoutItemArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.workout_item, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        WorkoutItem modal = filteredWorkoutItemArrayList.get(position);
        viewHolder.workoutName.setText(modal.getName());
        viewHolder.workoutCategory.setText(modal.getCategory());
        viewHolder.workoutType.setText(modal.getType());
        viewHolder.workoutDuration.setText(modal.getDuration().toString());
        viewHolder.goToWorkoutButton.setOnClickListener(view -> {
            Uri uri = Uri.parse(modal.getYoutubeUrl());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        });

        if(modal.getType().equals("cardio")) {
            viewHolder.workoutImageIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_cardio));
        }
        else{
            viewHolder.workoutImageIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_powerlifting));
        }
    }

    @Override
    public int getItemCount() {
        return filteredWorkoutItemArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                ArrayList<WorkoutItem> filteredList = new ArrayList<WorkoutItem>();

                if (charString == null || charString.length() == 0 || charString.isEmpty()) {
                    filteredList.addAll(fullWorkoutItemArrayList);
                } else {
                    String filterPattern = charString.toLowerCase().trim();
                    for (WorkoutItem item : fullWorkoutItemArrayList) {
                        if (item.getName().toLowerCase().contains(filterPattern)
                                || item.getType().toLowerCase().contains(filterPattern)
                                || item.getCategory().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredWorkoutItemArrayList.clear();
                filteredWorkoutItemArrayList.addAll((ArrayList) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView workoutName, workoutCategory, workoutType, workoutDuration;
        private final Button goToWorkoutButton;
        private final ImageView workoutImageIcon;

        public ViewHolder(View view) {
            super(view);
            workoutName = itemView.findViewById(R.id.workout_name);
            workoutCategory = itemView.findViewById(R.id.workout_category);
            workoutType = itemView.findViewById(R.id.workout_type);
            workoutDuration = itemView.findViewById(R.id.workout_duration);
            goToWorkoutButton = itemView.findViewById(R.id.open_workout_url);
            workoutImageIcon = itemView.findViewById(R.id.workout_image);
        }
    }
}
