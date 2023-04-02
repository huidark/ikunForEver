package com.example.fitnesscalendar.api;

import androidx.lifecycle.LiveData;

import com.example.fitnesscalendar.model.User;

import java.util.HashMap;
import java.util.Map;

/*
This is the denormalized structure of the database schema user, which means all the user data will be put into
one table. A user will be created when he/she registered.
 */
public class FireBaseUserData {
    //Map<String, Object> result;
    private String userName;
    private String userGender;
    private double userHeight;
    private int userAge;
    //String denotes date, Integer denotes user's height of that day
    //TODO: there is a probability that you cannot use Map easily. It may be registered as a document
    private Map<String, Double> userWeight;

    private boolean registered;
    //a empty constructor required by FireBase
    public FireBaseUserData(){}

    //a standard constructor
    public FireBaseUserData(String userName, String userGender, double userHeight, Map<String, Double> userWeight,
                            int userAge, boolean registered){
        this.userName = userName;
        this.userGender = userGender;
        this.userAge = userAge;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.registered = registered;
        /*
        result = new HashMap<>();
        result.put("userName", userName);
        result.put("userGender", userGender);
        result.put("userAge", userAge);
        result.put("userHeight", userHeight);
        result.put("userWeight", userWeight);

         */
    }

    public FireBaseUserData(User u){
        this.userName = u.getUserName();
        this.userGender = u.getUserGender();
        this.userAge = u.getUserAge();
        this.userHeight = u.getUserHeight();
        this.userWeight = u.getUserWeight();
        this.registered = u.getRegistered();
    }
    /*
    public Map<String, Object> getMap(){
        return this.result;
    }*/



    /*
    below are all getters for our private variables
     */
    public String getUserName(){
        return userName;
    }

    public String getUserGender(){
        return userGender;
    }

    public int getUserAge(){
        return userAge;
    }

    public double getUserHeight(){
        return userHeight;
    }

    public Map<String, Double> getUserWeight(){
        return this.userWeight;
    }

    public boolean getRegistered(){ return this.registered; }


}
