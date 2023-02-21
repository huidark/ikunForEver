package com.example.fitnesscalendar.data;

import java.io.Serializable;

public class DataStructure implements Serializable {

    //TODO: this class works as data storage class: an object of this class should be passed
    //TODO: each time when data is transferred between Activity/Fragment
    private String username;
    //public constructor
    public DataStructure(){
        username = "";
    }

    //setter of username
    public void setUsername(String username){
        if(username == null){
            this.username = username;
        }
    }

    //getter of username
    public String getUsername(){
        if(username != null) {
            return this.username;
        }else{
            return "";
        }
    }

    //clear session
    public void clear_session(){
        this.username = null;
    }
}
