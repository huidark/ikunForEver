package com.example.fitnesscalendar.model;

import com.example.fitnesscalendar.api.FireBaseUserData;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

/*
Model class for user. This class works exactly the same as the FireBaseUserData, but one should use
this class at everywhere else than calling FireBaseFetcher. UserViewModel should render this class.
 */
public class User implements Serializable {

    private String userName;
    private String userGender;
    //TODO: try to optimize this by setting to Double so null can be accepted
    private double userHeight;
    private int userAge;
    //String denotes date, Integer denotes user's height of that day
    //TODO: try to optimize this by setting up a new class of data structure, since it has to be
    //TODO: shown in an certain order
    private Map<String, Double> userWeight;

    private boolean registered;

    //a standard constructor
    public User(String userName, String userGender, double userHeight, Map<String, Double> userWeight,
                int userAge, boolean registered) {
        this.userName = userName;
        this.userGender = userGender;
        this.userAge = userAge;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.registered = registered;
    }

    public User(FireBaseUserData fd) {
        this.userName = fd.getUserName();
        this.userGender = fd.getUserGender();
        this.userAge = fd.getUserAge();
        this.userHeight = fd.getUserHeight();
        this.userWeight = fd.getUserWeight();
        this.registered = fd.getRegistered();
    }

    /*
    below are all getters and setters for our private variables
     */
    public String getUserName() {
        return userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public int getUserAge() {
        return userAge;
    }

    public double getUserHeight() {
        return userHeight;
    }

    public Map<String, Double> getUserWeight() {
        return this.userWeight;
    }

    public boolean getRegistered() {
        return registered;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public void setUserHeight(double userHeight) {
        this.userHeight = userHeight;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public void setUserWeight(Map<String, Double> userWeight) {
        this.userWeight = userWeight;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public static double getLatestWeight(User user) {
        Map<String, Double> weights = user.getUserWeight();
        if (weights.size() == 0) {
            return 0;
        }
        String latestDate = "00000000";
        for (Map.Entry<String, Double> entry : weights.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            if (Integer.valueOf(latestDate) < Integer.valueOf(key)) {
                latestDate = key;
            }
        }
        return weights.get(latestDate);
    }

}
