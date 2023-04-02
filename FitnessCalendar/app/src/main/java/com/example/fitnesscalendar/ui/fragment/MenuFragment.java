package com.example.fitnesscalendar.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.ui.activity.CalenderActivity;
import com.example.fitnesscalendar.ui.activity.ProfileActivity;
import com.example.fitnesscalendar.ui.activity.SettingsActivity;

public class MenuFragment extends Fragment implements View.OnClickListener {

    //instantiation of buttons
    Button ccButton;
    Button aeButton;
    Button stButton;
    Button pfButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //create view
        View view = inflater.inflate(R.layout.fragment_menu, container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //actions after view is created
        super.onViewCreated(view, savedInstanceState);
        //instantiate and register buttons

        aeButton = view.findViewById(R.id.bt_ae);
        aeButton.setOnClickListener(this);

        stButton = view.findViewById(R.id.bt_st);
        stButton.setOnClickListener(this);

        pfButton = view.findViewById(R.id.bt_pf);
        pfButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent it = null;
        switch(v.getId()){
            case R.id.bt_ae:
                it = new Intent(getActivity(), CalenderActivity.class);
                break;
            case R.id.bt_st:
                it = new Intent(getActivity(), SettingsActivity.class);
                break;
            case R.id.bt_pf:
                it = new Intent(getActivity(), ProfileActivity.class);
                break;
        }
        startActivity(it);
    }
}