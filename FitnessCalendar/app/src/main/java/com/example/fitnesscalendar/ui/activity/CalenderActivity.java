package com.example.fitnesscalendar.ui.activity;

import static com.example.fitnesscalendar.R.id.fl_container;
import static com.example.fitnesscalendar.util.NetworkChecker.isNetworkConnected;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.ui.fragment.CheckDateFragment;
import com.example.fitnesscalendar.ui.fragment.NoNetFragment;

public class CalenderActivity extends AppCompatActivity {

    private CheckDateFragment checkDateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        checkDateFragment = new CheckDateFragment();
        if(!isNetworkConnected(getApplicationContext())){
            Log.d("network", "no connection");
            NoNetFragment noNetFragment = new NoNetFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(fl_container, noNetFragment).commitAllowingStateLoss();
        }else {

            getSupportFragmentManager().beginTransaction()
                    .add(fl_container, checkDateFragment).commitAllowingStateLoss();
        }
    }
}