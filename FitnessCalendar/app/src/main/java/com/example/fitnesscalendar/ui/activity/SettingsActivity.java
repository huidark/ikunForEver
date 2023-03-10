package com.example.fitnesscalendar.ui.activity;

import static com.example.fitnesscalendar.R.id.fl_container;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.ui.fragment.SettingFragment;
import com.example.fitnesscalendar.ui.fragment.ShowPfFragment;

public class SettingsActivity extends AppCompatActivity {

    //instantiation of Settings fragment
    SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //create fragment manager and transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //create an instantiation of show profile fragment
        settingFragment = new SettingFragment();

        //add the fragment into frame layout container
        fragmentTransaction.add(fl_container, settingFragment).commitAllowingStateLoss();
    }
}