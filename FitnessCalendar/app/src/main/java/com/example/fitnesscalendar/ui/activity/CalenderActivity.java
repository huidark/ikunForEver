package com.example.fitnesscalendar.ui.activity;

import static com.example.fitnesscalendar.R.id.fl_container;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.ui.fragment.CheckDateFragment;

public class CalenderActivity extends AppCompatActivity {

    private CheckDateFragment checkDateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        checkDateFragment = new CheckDateFragment();

        getSupportFragmentManager().beginTransaction()
                .add(fl_container, checkDateFragment).commitAllowingStateLoss();
    }
}