package com.example.fitnesscalendar.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesscalendar.model.Event;
import com.example.fitnesscalendar.model.User;
import com.example.fitnesscalendar.network.FireBaseFetcher;

import java.util.List;

public class EventViewModel extends ViewModel {
    //TODO 1. an instantiation of fetcher
    //TODO 2. a livedata of list of event
    //TODO 3. methods related to CURD

    // an instantiation of fetcher
    // a livedata of list of events
    //a single event
    private final FireBaseFetcher fr = FireBaseFetcher.getFireBaseFetcherInstance();
    public LiveData<List<Event>> events;
    public LiveData<Event> event;

    // This class uses implicit constructor

    /*
    get all users
     */
    public LiveData<List<Event>> getAllEvents(){
        events = fr.getEvents();
        return events;
    }

    public LiveData<Event> getOneEvent(){
        event = fr.getEvent();
        return event;
    }

    public void createEvent(Event event){
        fr.addEvent(event.getUserName(), event.getEventRmDuration(), event.getEventDate(),
                event.getTrain().getTrainingName(), event.getTrain().isTrainingIsAn(),
                event.getTrain().isTrainingIsCa(), event.getTrain().getTrainingCalories());
    }

    public void deleteEvent(String userName, String date){
        //TODO: implement
        fr.deleteEvent();
    }

    public void updateEvent(Event event){
        fr.updateUserEvent(event.getUserName(), event.getEventRmDuration(), event.getEventDate(),
                event.getTrain().getTrainingName(), event.getTrain().isTrainingIsAn(),
                event.getTrain().isTrainingIsCa(), event.getTrain().getTrainingCalories());
    }
}
