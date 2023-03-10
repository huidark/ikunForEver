package com.example.fitnesscalendar.model;
/*
This is the model class of event. This class take care of information related to event, which would
contain userName, Date, Durations, and an instantiation of Photo and a instantiation of train. One
should use this class everywhere else than calling FireBaseFetcher. The EventViewModel class should
render this class.
 */
public class Event {
    private String userName;
    private int eventRmDuration;
    private String eventDate;
    //private Photo photo; TODO: change this
    private Train train;

    //public constructors
    public Event(String userName, int eventRmDuration, String eventDate, Train train) {
        this.userName = userName;
        this.eventRmDuration = eventRmDuration;
        this.eventDate = eventDate;
        this.train = train;
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

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

}
