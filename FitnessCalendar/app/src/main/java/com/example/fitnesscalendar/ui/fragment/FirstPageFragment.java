package com.example.fitnesscalendar.ui.fragment;

import static com.example.fitnesscalendar.R.id.fl_container;
import static com.example.fitnesscalendar.util.NetworkChecker.isNetworkConnected;

import android.os.Bundle;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.data.TempoData;

import java.util.Timer;
import java.util.TimerTask;

/*
This class serve as the fragment class for first page.
This class should only create the view of first page fragment and sleep for 1.5s
 */
public class FirstPageFragment extends Fragment {
    //initialization of login fragment
    private LoginFragment loginFragment;

    //fragment name
    private static final String fragmentTag = "FirstPageFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //create view
        Log.d(fragmentTag, "FirstPageFragment onCreateView() method used!");
        View view = inflater.inflate(R.layout.fragment_first_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //actions after view is created
        super.onViewCreated(view, savedInstanceState);
        if(!isNetworkConnected(getActivity().getApplicationContext())){
            Log.d("network", "no connection");
            NoNetFragment noNetFragment = new NoNetFragment();
            getFragmentManager().beginTransaction()
                    .replace(fl_container, noNetFragment).commitAllowingStateLoss();
            return;
        }

        Log.d(fragmentTag, "FirstPageFragment onViewCreated() method used!");

        //initialization of login fragment
        loginFragment = new LoginFragment();
        //replace the fragments
        Timer timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                getFragmentManager().beginTransaction().replace(fl_container, loginFragment).commitAllowingStateLoss();
            }
        };
        timer.schedule(tt, 1500);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(fragmentTag, "FirstPageFragment onCreate() method used!");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(fragmentTag, "FirstPageFragment onStart() method used!");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(fragmentTag, "FirstPageFragment onResume() method used!");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(fragmentTag, "FirstPageFragment onPause() method used!");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(fragmentTag, "FirstPageFragment onStop() method used!");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(fragmentTag, "FirstPageFragment onDestroyView() method used!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(fragmentTag, "FirstPageFragment onDestroy() method used!");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(fragmentTag, "FirstPageFragment onDetach() method used!");
    }
}