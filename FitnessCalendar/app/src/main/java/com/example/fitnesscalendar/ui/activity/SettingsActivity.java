package com.example.fitnesscalendar.ui.activity;

import static com.example.fitnesscalendar.R.id.fl_container;
import static com.example.fitnesscalendar.util.NetworkChecker.isNetworkConnected;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.ui.fragment.NoNetFragment;
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
        if(!isNetworkConnected(getApplicationContext())){
            Log.d("network", "no connection");
            NoNetFragment noNetFragment = new NoNetFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(fl_container, noNetFragment).commitAllowingStateLoss();
            return;
        }

        //add the fragment into frame layout container
        fragmentTransaction.add(fl_container, settingFragment).commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*
        //TODO: add auto login
        //case of no auto login
        SharedPreferences sp = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("userName");
        editor.apply();

         */
    }
}