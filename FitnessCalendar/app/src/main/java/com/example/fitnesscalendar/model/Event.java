package com.example.fitnesscalendar.model;

import java.io.Serializable;

/*
This is the model class of event. This class take care of information related to event, which would
contain userName, Date, Durations, and an instantiation of Photo and a instantiation of train. One
should use this class everywhere else than calling FireBaseFetcher. The EventViewModel class should
render this class.
 */
public class Event implements Serializable {
    private String userName;
    private int eventRmDuration;
    private String eventDate;
    private String eventTime;

    //public constructors
    public Event(String userName, int eventRmDuration, String eventDate, String eventTime) {
        this.userName = userName;
        this.eventRmDuration = eventRmDuration;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }

    /*
    belows are all getters and setters
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getEventRmDuration() {
        return eventRmDuration;
    }

    public void setEventRmDuration(int eventRmDuration) {
        this.eventRmDuration = eventRmDuration;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}
