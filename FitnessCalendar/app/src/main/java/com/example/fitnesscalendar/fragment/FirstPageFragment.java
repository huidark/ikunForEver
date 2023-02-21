package com.example.fitnesscalendar.fragment;

import static com.example.fitnesscalendar.R.id.fl_container;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.data.DataStructure;

import java.util.Timer;
import java.util.TimerTask;

/*
This class serve as the fragment class for first page.
This class should only create the view of first page fragment and sleep for 1.5s
 */
public class FirstPageFragment extends android.app.Fragment {
    //initialization of login fragment
    private LoginFragment loginFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //create view
        View view = inflater.inflate(R.layout.fragment_first_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //actions after view is created
        super.onViewCreated(view, savedInstanceState);

        Bundle bundlel = getArguments();
        DataStructure ds = (DataStructure) bundlel.getSerializable("ds");

        //initialization of login fragment
        loginFragment = new LoginFragment();
        //add data structure to bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("ds", ds);
        loginFragment.setArguments(bundle);
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
}