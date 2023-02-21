package com.example.fitnesscalendar.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnesscalendar.R;

public class MenuFragment extends android.app.Fragment implements View.OnClickListener {

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
    }

    @Override
    public void onClick(View v) {
        Intent it = null;
        switch(v.getId()){
            case R.id.bt_ae:
                //it = new Intent(getActivity(), )
                //TODO: update this
            case R.id.bt_cc:
                //TODO: update this
            case R.id.bt_st:
                //TODO: update this
            case R.id.bt_lo:
                //TODO: update this
        }
        //startActivity(it)
        //TODO: update this
    }
}