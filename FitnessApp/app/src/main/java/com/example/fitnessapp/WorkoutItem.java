package com.example.fitnessapp;

public class WorkoutItem {
    private String name;
    private String type;
    private String category;
    private Float duration;
    private String youtubeUrl;
    private String gender;

    public WorkoutItem(String name, String type, String category, Float duration,
                       String youtubeUrl, String gender) {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.category = category;
        this.youtubeUrl = youtubeUrl;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public Float getDuration() {
        return duration;
    }

    public String getYoutubeUrl() { return youtubeUrl; }

    public String getGender() { return gender; }
}
