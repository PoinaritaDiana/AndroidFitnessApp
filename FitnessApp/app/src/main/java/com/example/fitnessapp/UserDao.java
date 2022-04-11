package com.example.fitnessapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Insert
    void register(User user);

    //check if user exists in db
    @Query("SELECT * FROM User WHERE email = (:email) AND password =(:password)")
    User checkUserExists(String email, String password);

    @Query("SELECT * FROM user WHERE  email = (:userGoogleId) AND password =(:userGoogleId)")
    User getUserByGoogleId(String userGoogleId);

    @Query("UPDATE User SET beforePhoto = :beforePhoto WHERE id = :userId")
    void addBeforePhoto(int userId, String beforePhoto);

    @Query("UPDATE User SET afterPhoto= :afterPhoto WHERE id = :userId")
    void addAfterPhoto(int userId, String afterPhoto);


    @Query("UPDATE User SET " +
            "typicalDay = :typicalDay, " +
            "bodyType = :bodyType, " +
            "weight = :weight, " +
            "goalWeight = :goalWeight, " +
            "gender = :gender, " +
            "height = :height, " +
            "age = :age, " +
            "goal = :goal, " +
            "dailyCalories = :dailyCalories " +
            "WHERE id = :userId")
    void updateUserQuizAnswer(int userId, String typicalDay, String bodyType, Float weight, Float goalWeight, String gender, Float height, Integer age, String goal, Integer dailyCalories);

    @Query("UPDATE User SET day1Done= :status WHERE id = :userId")
    void updateDay1Done(int userId, Boolean status);

    @Query("UPDATE User SET day2Done= :status WHERE id = :userId")
    void updateDay2Done(int userId, Boolean status);

    @Query("UPDATE User SET day3Done= :status WHERE id = :userId")
    void updateDay3Done(int userId, Boolean status);

    @Query("UPDATE User SET day4Done= :status WHERE id = :userId")
    void updateDay4Done(int userId, Boolean status);

    @Query("UPDATE User SET day5Done= :status WHERE id = :userId")
    void updateDay5Done(int userId, Boolean status);

    @Query("UPDATE User SET day6Done= :status WHERE id = :userId")
    void updateDay6Done(int userId, Boolean status);

    @Query("UPDATE User SET day7Done= :status WHERE id = :userId")
    void updateDay7Done(int userId, Boolean status);

    @Query("UPDATE User SET day8Done= :status WHERE id = :userId")
    void updateDay8Done(int userId, Boolean status);

    @Query("UPDATE User SET day9Done= :status WHERE id = :userId")
    void updateDay9Done(int userId, Boolean status);

    @Query("UPDATE User SET day10Done= :status WHERE id = :userId")
    void updateDay10Done(int userId, Boolean status);
}
