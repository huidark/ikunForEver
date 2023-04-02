package com.example.fitnesscalendar.model;

import com.example.fitnesscalendar.api.FireBaseEventData;

import java.io.Serializable;

/*
this is a standard line in the event database. YOU MUST USE pass the object of this class to the
methods in ViewModel! IMPLEMENT SERIALIZABLE IS FOR USING BUNDLE!
 */
public class DeEvent implements Serializable {
    //declare variables
    private String userName;
    private Event event;
    private Train train;

    public DeEvent(String userName, Event event, Train train) {
        this.userName = userName;
        this.event = event;
        this.train = train;
    }

    public DeEvent(FireBaseEventData fd){
        Event event = new Event(fd.getUserName(), fd.getEventRmDuration(), fd.getEventDate(), fd.getEventTime());
        Train train = new Train(fd.getTrainingName(), fd.getTrainingIsAn(), fd.getIsTrainingIsCa(), fd.getTrainingCalories());
        this.event = event;
        this.train = train;
        this.userName = fd.getUserName();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

}
