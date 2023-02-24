package com.example.fitnesscalendar.activity;

import static com.example.fitnesscalendar.R.id.fl_container;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.data.DataStructure;
import com.example.fitnesscalendar.fragment.FirstPageFragment;
import com.example.fitnesscalendar.fragment.LoginFragment;

/*
This class serve as the activity class for the first page/login page/menu page
This class should use three fragments, namely first page/login page/menu page fragments flexibly
in different cases.
 */
public class GeneralContainerActivity extends AppCompatActivity {

    //declare fragments to be used.
    private FirstPageFragment firstPageFragment;

    //activity name
    private static final String activityTag = "GeneralContainerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_page_container);
        Log.d(activityTag, "onCreate() Activity!");

        //TODO: add switch method later for different usages.
        //TODO: initialize data structure

        {// 1. case of launcher, starting with start page then turn to login page after 1.5 seconds
            //initialization of first page fragment
            //first of all, initialize data structure
            DataStructure ds = new DataStructure();
            //initialization of fragment
            firstPageFragment = new FirstPageFragment();
            //add ds to bundle
            Bundle bundlef = new Bundle();
            bundlef.putSerializable("ds", ds);
            firstPageFragment.setArguments(bundlef);
            //add the fragment into frame layout container
            getFragmentManager().beginTransaction().add(fl_container, firstPageFragment).commitAllowingStateLoss();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(activityTag, "onStart() Activity!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(activityTag, "onResume() Activity!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(activityTag, "onPause() Activity!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(activityTag, "onStop() Activity!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(activityTag, "onDestroy() Activity!");
    }
}