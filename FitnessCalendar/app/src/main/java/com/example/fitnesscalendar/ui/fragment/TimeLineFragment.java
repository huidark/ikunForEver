package com.example.fitnesscalendar.ui.fragment;

import static com.example.fitnesscalendar.R.id.fl_container;
import static com.example.fitnesscalendar.util.NetworkChecker.isNetworkConnected;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.data.AvailableTime;
import com.example.fitnesscalendar.model.DeEvent;
import com.example.fitnesscalendar.viewmodel.EventViewModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TimeLineFragment extends Fragment {


    EventViewModel em;
    Activity activity;
    List<DeEvent> events;
    AddEventFragment addEventFragment;
    ChooseEventFragment chooseEventFragment;
    Button adButton;

    Set<Integer> existed;
    private LinearLayout timelineLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        em = new ViewModelProvider(
                (ViewModelStoreOwner) activity).get(
                EventViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_time_line, container, false);
        timelineLayout = view.findViewById(R.id.timeline_layout);

        //TODO: 1. get the event data for that single day
        Bundle bundle = getArguments();
        String date = bundle.getString("date");
        Log.d("date in timeline", date);
        SharedPreferences sharedPreferences = getActivity().
                getSharedPreferences("Data", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", null);
        events = em.getEventsOnce(userName, date).getValue();
        Log.d("events size check", events.size()+"");
        Log.d("num of events in Time Line", events.size()+"");
        //TODO: 2. create a available time object
        AvailableTime at = new AvailableTime(events);
        createTimeline(events);

        //process add button
        adButton = view.findViewById(R.id.bt_ad);
        adButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isNetworkConnected(getActivity().getApplicationContext())){
                    Log.d("network", "no connection");
                    NoNetFragment noNetFragment = new NoNetFragment();
                    getFragmentManager().beginTransaction()
                            .replace(fl_container, noNetFragment).commitAllowingStateLoss();
                    return;
                }
                addEventFragment = new AddEventFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("time", at);
                bundle.putString("date", date);
                addEventFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container
                                ,addEventFragment)
                        .commitAllowingStateLoss();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!isNetworkConnected(getActivity().getApplicationContext())){
            Log.d("network", "no connection");
            NoNetFragment noNetFragment = new NoNetFragment();
            getFragmentManager().beginTransaction()
                    .replace(fl_container, noNetFragment).commitAllowingStateLoss();
            return;
        }


    }

    private void createTimeline(List<DeEvent> events) {
        //set up existed set
        existed = new HashSet<>();
        for(int k = 0; k < events.size(); k++){
            DeEvent event = events.get(k);
            String startTime = event.getEvent().getEventTime().split(":")[0];
            int startTimeI = Integer.valueOf(startTime);
            int durationTime = event.getEvent().getEventRmDuration()/60;
            for(int m = startTimeI+1; m < startTimeI + durationTime; m++){
                existed.add(m);
            }
        }
        for (int i = 0; i < 24; i++) {
            View timelineItem = LayoutInflater.from(getContext()).inflate(R.layout.timeline_item, null);
            TextView timeText = timelineItem.findViewById(R.id.time_text);
            View timelineLine = timelineItem.findViewById(R.id.timeline_line);

            timeText.setText(String.format("%02d:00", i));
            Log.d("button number check", events.size()+"");
            if(!existed.contains(i)) {
                timelineLayout.addView(timelineItem);
            }


            for(int j = 0; j < events.size(); j++) {
                DeEvent event = events.get(j);
                String startTime = event.getEvent().getEventTime().split(":")[0];
                int startTimeI = Integer.valueOf(startTime);
                int durationTime = event.getEvent().getEventRmDuration();
                Log.d("button check start", startTimeI + "");
                Log.d("button check end", (startTimeI + (durationTime / 60)) + "");
                Log.d("button check i", i + "");
                if (i == startTimeI) {
                    Button workout = new Button(getContext());
                    workout.setTag(event);
                    workout.setText(event.getTrain().getTrainingName()
                    + "  " + startTime + ":00  " + durationTime + "mins");
                    workout.setHeight(durationTime * 2);
                    workout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!isNetworkConnected(getActivity().getApplicationContext())){
                                Log.d("network", "no connection");
                                NoNetFragment noNetFragment = new NoNetFragment();
                                getFragmentManager().beginTransaction()
                                        .replace(fl_container, noNetFragment).commitAllowingStateLoss();
                                return;
                            }
                            chooseEventFragment = new ChooseEventFragment();
                            Bundle bundle = new Bundle();
                            DeEvent Bevent = (DeEvent) v.getTag();
                            Log.d("check null", (Bevent == null) + "");
                            bundle.putSerializable("event", Bevent);
                            chooseEventFragment.setArguments(bundle);
                            getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fl_container
                                            , chooseEventFragment)
                                    .commitAllowingStateLoss();
                        }
                    });
                    timelineLayout.addView(workout);
                }
            }
        }
    }


}