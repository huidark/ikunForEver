package com.example.fitnesscalendar.api;

import com.example.fitnesscalendar.model.DeEvent;
import com.example.fitnesscalendar.model.Event;
import com.example.fitnesscalendar.model.Photo;
import com.example.fitnesscalendar.model.Train;

import java.util.HashMap;
import java.util.Map;

/*
This is the denormalized structure of the database schema, which means all the event data will be put into
one table. At the same time, this means that this table is totally event oriented: Only when user add
a event to his/her calender will a document be created.
 */
public class FireBaseEventData {

    private String userName;
    private int eventRmDuration;
    private String eventDate;

    private String eventTime;
    private String trainingName;
    private boolean trainingIsAn;
    private boolean trainingIsCa;
    private int trainingCalories;
    //private int eventCalories; TODO: can be calculated?
    //private ? photoData TODO: in what form?

    //a empty constructor required by FireBase
    public FireBaseEventData(){}



    //a standard constructor
    public FireBaseEventData(String userName, int eventRmDuration, String eventDate, String eventTime,
                             String trainingName, boolean trainingIsAn, boolean trainingIsCa, int trainingCalories){
        this.eventRmDuration = eventRmDuration;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.userName = userName;
        this.trainingName = trainingName;
        this.trainingIsAn = trainingIsAn;
        this.trainingIsCa = trainingIsCa;
        this.trainingCalories = trainingCalories;
    }

    public FireBaseEventData(DeEvent deEvent){
        this.userName = deEvent.getUserName();
        this.eventRmDuration = deEvent.getEvent().getEventRmDuration();
        this.eventDate = deEvent.getEvent().getEventDate();
        this.eventTime = deEvent.getEvent().getEventTime();
        this.trainingCalories = deEvent.getTrain().getTrainingCalories();
        this.trainingIsCa = deEvent.getTrain().getTrainingIsCa();
        this.trainingIsAn = deEvent.getTrain().getTrainingIsAn();
        this.trainingName = deEvent.getTrain().getTrainingName();
    }

    /*
    below are all getters for our private variables
     */
    public String getUserName(){
        return userName;
    }

    public int getEventRmDuration(){
        return eventRmDuration;
    }

    public String getEventDate(){
        return eventDate;
    }

    public String getTrainingName(){
        return this.trainingName;
    }

    public boolean getTrainingIsAn(){
        return this.trainingIsAn;
    }

    public boolean getIsTrainingIsCa(){
        return this.trainingIsCa;
    }

    public int getTrainingCalories(){
        return this.trainingCalories;
    }

    public String getEventTime(){ return this.eventTime; }


}
