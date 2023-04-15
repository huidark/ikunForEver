package com.example.fitnesscalendar.ui.activity;

import static com.example.fitnesscalendar.R.id.fl_container;
import static com.example.fitnesscalendar.util.NetworkChecker.isNetworkConnected;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.util.Log;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.ui.fragment.FirstPageFragment;
import com.example.fitnesscalendar.ui.fragment.NoNetFragment;

/*
This class serve as the activity class for the first page/login page/menu page
This class should use three fragments, namely first page/login page/menu page fragments flexibly
in different cases.
 */
public class GeneralContainerActivity extends AppCompatActivity {

    //declare fragments to be used.
    private FirstPageFragment firstPageFragment;

    private NoNetFragment noNetFragment;

    //activity name
    private static final String activityTag = "GeneralContainerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //initialize the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_page_container);
        Log.d(activityTag, "onCreate() Activity!");

        if(!isNetworkConnected(getApplicationContext())){
            Log.d("network", "no connection");
            noNetFragment = new NoNetFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(fl_container, noNetFragment).commitAllowingStateLoss();
        }else {
            //initialization of first page fragment
            firstPageFragment = new FirstPageFragment();
            //add the fragment into frame layout container
            getSupportFragmentManager().beginTransaction()
                    .add(fl_container, firstPageFragment).commitAllowingStateLoss();
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