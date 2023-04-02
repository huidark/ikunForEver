package com.example.fitnesscalendar.data;

import android.util.Log;

import com.example.fitnesscalendar.model.DeEvent;

import java.io.Serializable;
import java.util.List;

public class AvailableTime implements Serializable {

    private List<DeEvent> events;

    public AvailableTime(List<DeEvent> events) {
        this.events = events;
    }

    public List<DeEvent> getEvents() {
        return events;
    }

    public void setEvents(List<DeEvent> events) {
        this.events = events;
    }

    public static boolean checkTimeConflict(List<DeEvent> oldTimes, String newStartTime, int newDuration) {
        // Convert newStartTime to minutes
        int newStartTimeInMinutes = convertToMinutes(newStartTime);

        // Compute newEndTime in minutes by adding newDuration to newStartTime
        int newEndTimeInMinutes = newStartTimeInMinutes + newDuration;

        // Check if the new time period conflicts with any of the old time periods
        for (DeEvent oldTime : oldTimes) {
            int oldStartTimeInMinutes = convertToMinutes(oldTime.getEvent().getEventTime());
            int oldEndTimeInMinutes = oldStartTimeInMinutes + oldTime.getEvent().getEventRmDuration();

            Log.d("ATT new Start Time", newStartTimeInMinutes+"");
            Log.d("ATT old Start Time", oldStartTimeInMinutes+"");
            Log.d("ATT new End Time", newEndTimeInMinutes+"");
            Log.d("ATT old Start Time", oldEndTimeInMinutes+"");
            if ((newStartTimeInMinutes >= oldStartTimeInMinutes && newStartTimeInMinutes <= oldEndTimeInMinutes) ||
                    (newEndTimeInMinutes > oldStartTimeInMinutes && newEndTimeInMinutes <= oldEndTimeInMinutes) ||
                    (newStartTimeInMinutes <= oldStartTimeInMinutes && newEndTimeInMinutes >= oldEndTimeInMinutes)) {
                return true;
            }
        }

        // No conflict found
        return false;
    }


    private static int convertToMinutes(String time) {
        String[] timeParts = time.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        return hours * 60 + minutes;
    }
}
