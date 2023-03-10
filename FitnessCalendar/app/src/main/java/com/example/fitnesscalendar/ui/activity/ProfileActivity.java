package com.example.fitnesscalendar.ui.activity;

import static com.example.fitnesscalendar.R.id.fl_container;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.ui.fragment.ShowPfFragment;

public class ProfileActivity extends AppCompatActivity {

    //instantiation of ShowPfFragment
    ShowPfFragment showPfFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //create fragment manager and transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //create an instantiation of show profile fragment
        showPfFragment = new ShowPfFragment();

        //add the fragment into frame layout container
        fragmentTransaction.add(fl_container, showPfFragment).commitAllowingStateLoss();
    }
}