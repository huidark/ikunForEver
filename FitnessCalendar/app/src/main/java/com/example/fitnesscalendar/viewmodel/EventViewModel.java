package com.example.fitnesscalendar.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesscalendar.model.DeEvent;
import com.example.fitnesscalendar.model.Event;
import com.example.fitnesscalendar.model.Photo;
import com.example.fitnesscalendar.model.Train;
import com.example.fitnesscalendar.model.User;
import com.example.fitnesscalendar.network.FireBaseFetcher;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class EventViewModel extends ViewModel implements FireBaseFetcher.onEventResult{

    // an instantiation of fetcher
    // a livedata of list of events
    //a single event
    private final FireBaseFetcher fr = new FireBaseFetcher();
    public LiveData<List<DeEvent>> events;

    // This class uses implicit constructor

    public LiveData<List<DeEvent>> getEventsOnce(String userName, String date){
        getEvents(userName, date);
        return this.events;
    }
    /*
    get events for one date and one user
     */
    public void getEvents(String userName, String date){
        CountDownLatch cdget = new CountDownLatch(1);
        synchronized (cdget) {
            cdget = new CountDownLatch(1);
            fr.getEvents(userName, date, EventViewModel.this, cdget);
            try {
                cdget.await();
            } catch (Exception e) {
                //none
            }
        }
    }


    /*
    create a event
     */
    public void createEvent(DeEvent deEvent){
        Log.d("create Event", "create Event");
        CountDownLatch cdadd = new CountDownLatch(1);
        synchronized (cdadd) {
            cdadd = new CountDownLatch(1);
            fr.addEvent(deEvent, EventViewModel.this, cdadd);
            try {
                cdadd.await();
            } catch (Exception e) {
                //none
            }
        }
    }

    /*
    delete a event
     */
    public void deleteEvent(DeEvent deEvent){
        CountDownLatch cddel = new CountDownLatch(1);
        synchronized (cddel) {
            cddel = new CountDownLatch(1);
            fr.deleteEvent(deEvent, EventViewModel.this, cddel);
            try {
                cddel.await();
            } catch (Exception e) {
                //none
            }
        }
    }

    /*
    update a event
     */
    public void updateEvent(DeEvent deEvent){
        CountDownLatch cdupdate = new CountDownLatch(1);
        synchronized (cdupdate) {
            cdupdate = new CountDownLatch(1);
            fr.updateEvent(deEvent, EventViewModel.this, cdupdate);
            try {
                cdupdate.await();
            } catch (Exception e) {
                //none
            }
        }
    }


    @Override
    public void onAddResult(String result, CountDownLatch cdadd) {
        cdadd.countDown();
    }


    @Override
    public void onUpdateResult(String result, CountDownLatch cdupdate) {
        cdupdate.countDown();
    }

    @Override
    public void onGetEventsResult(LiveData<List<DeEvent>> events, String result, CountDownLatch cdgets) {
        this.events = events;
        cdgets.countDown();
    }


    @Override
    public void onDeleteResult(String result, CountDownLatch cddel) {
        cddel.countDown();
    }
}
