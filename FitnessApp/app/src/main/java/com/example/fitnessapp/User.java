package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;

    @NonNull
    public String email;

    @NonNull
    public String password;

    public String typicalDay;
    public String bodyType;
    public Float weight;
    public Float goalWeight;
    public String gender;
    public Float height;
    public Integer age;
    public String goal;
    public Integer dailyCalories;

    public String beforePhoto;
    public String afterPhoto;

    public Boolean day1Done;
    public Boolean day2Done;
    public Boolean day3Done;
    public Boolean day4Done;
    public Boolean day5Done;
    public Boolean day6Done;
    public Boolean day7Done;
    public Boolean day8Done;
    public Boolean day9Done;
    public Boolean day10Done;

    public User(){}

    public User(@NonNull String name, @NonNull String email, @NonNull String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.day1Done = false;
        this.day2Done = false;
        this.day3Done = false;
        this.day4Done = false;
        this.day5Done = false;
        this.day6Done = false;
        this.day7Done = false;
        this.day8Done = false;
        this.day9Done = false;
        this.day10Done = false;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", typicalDay='" + typicalDay + '\'' +
                ", bodyType='" + bodyType + '\'' +
                ", weight=" + weight +
                ", goalWeight=" + goalWeight +
                ", gender='" + gender + '\'' +
                ", height=" + height +
                ", age=" + age +
                ", goal='" + goal + '\'' +
                ", dailyCalories=" + dailyCalories +
                ", beforePhoto='" + beforePhoto + '\'' +
                ", afterPhoto='" + afterPhoto + '\'' +
                ", day1Done=" + day1Done +
                ", day2Done=" + day2Done +
                ", day3Done=" + day3Done +
                ", day4Done=" + day4Done +
                ", day5Done=" + day5Done +
                ", day6Done=" + day6Done +
                ", day7Done=" + day7Done +
                ", day8Done=" + day8Done +
                ", day9Done=" + day9Done +
                ", day10Done=" + day10Done +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setTypicalDay(String typicalDay) {
        this.typicalDay = typicalDay;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setGoalWeight(Float goalWeight) {
        this.goalWeight = goalWeight;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setDailyCalories(Integer dailyCalories) {
        this.dailyCalories = dailyCalories;
    }

    public String getGoal() {
        return goal;
    }

    public Float getWeight() {
        return weight;
    }

    public Float getGoalWeight() {
        return goalWeight;
    }

    public String getGender() {
        return gender;
    }

    public Float getHeight() {
        return height;
    }

    public Integer getAge() {
        return age;
    }

    public void setBeforePhoto(String beforePhoto) {
        this.beforePhoto = beforePhoto;
    }

    public void setAfterPhoto(String afterPhoto) {
        this.afterPhoto = afterPhoto;
    }

    public Integer getDailyCalories() {
        return dailyCalories;
    }

    public String getTypicalDay() {
        return typicalDay;
    }

    public String getBodyType() {
        return bodyType;
    }

    public String getBeforePhoto() {
        return beforePhoto;
    }

    public String getAfterPhoto() {
        return afterPhoto;
    }

    public Boolean getDay1Done() {
        System.out.println(day1Done);
        return day1Done;
    }

    public void setDay1Done(Boolean day1Done) {
        this.day1Done = day1Done;
    }

    public Boolean getDay2Done() {
        return day2Done;
    }

    public void setDay2Done(Boolean day2Done) {
        this.day2Done = day2Done;
    }

    public Boolean getDay3Done() {
        return day3Done;
    }

    public void setDay3Done(Boolean day3Done) {
        this.day3Done = day3Done;
    }

    public Boolean getDay4Done() {
        return day4Done;
    }

    public void setDay4Done(Boolean day4Done) {
        this.day4Done = day4Done;
    }

    public Boolean getDay5Done() {
        return day5Done;
    }

    public void setDay5Done(Boolean day5Done) {
        this.day5Done = day5Done;
    }

    public Boolean getDay6Done() {
        return day6Done;
    }

    public void setDay6Done(Boolean day6Done) {
        this.day6Done = day6Done;
    }

    public Boolean getDay7Done() {
        return day7Done;
    }

    public void setDay7Done(Boolean day7Done) {
        this.day7Done = day7Done;
    }

    public Boolean getDay8Done() {
        return day8Done;
    }

    public void setDay8Done(Boolean day8Done) {
        this.day8Done = day8Done;
    }

    public Boolean getDay9Done() {
        return day9Done;
    }

    public void setDay9Done(Boolean day9Done) {
        this.day9Done = day9Done;
    }

    public Boolean getDay10Done() {
        return day10Done;
    }

    public void setDay10Done(Boolean day10Done) {
        this.day10Done = day10Done;
    }

    public int calculateMaintainCalories(){
        int g = 0;
        double bt = 0;
        double l = 0;

        if(gender.equalsIgnoreCase("female")){ g = 20; }
        if(gender.equalsIgnoreCase("male")) { g = 22; }
        if(gender.equalsIgnoreCase("other")) { g = 21; }

        if(bodyType.equalsIgnoreCase( "pear")) { bt = 1.5; }
        else if (bodyType.equalsIgnoreCase("round")) { bt = 1.3; }
        else if (bodyType.equalsIgnoreCase("rectangle")) { bt = 1.7; }
        else if (bodyType.equalsIgnoreCase("hourglass")) { bt = 1.5; }

        if(typicalDay.equalsIgnoreCase("sedentary")) { l = 0; }
        else if(typicalDay.equalsIgnoreCase("lightly_active")) { l = 0.2; }
        else if(typicalDay.equalsIgnoreCase( "active")) { l = 0.4; }
        else if(typicalDay.equalsIgnoreCase("very_active")) { l = 0.6; }

        double maintainCalories = weight * g * (bt + l);
        int result = (int) maintainCalories;
        setDailyCalories(result);
        return result;
    }

    public int calculateLoseWeightCalories(){
        double maintainCalories = calculateMaintainCalories();
        double loseCalories = 0.75 * maintainCalories;

        int result = (int) loseCalories;
        setDailyCalories(result);
        return result;

    }

    public int calculateGainMuscleCalories(){
        double maintainCalories = calculateMaintainCalories();
        double gainCalories = 1.15 * maintainCalories;
        int result = (int) gainCalories;
        setDailyCalories(result);
        return result;

    }

    public int calculateBMR(){
        double BMR = 0;

        if(gender.equalsIgnoreCase("female")){
            BMR = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
        if(gender.equalsIgnoreCase("male")){
            BMR = 88.362 + (13.397*weight) + (4.799*height) - (5.677*age);
        }
        System.out.println(BMR);

        int result = (int) BMR;
        return result;
    }
}