package com.example.fitnesscalendar.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnesscalendar.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoNetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoNetFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_net, container, false);
    }
}