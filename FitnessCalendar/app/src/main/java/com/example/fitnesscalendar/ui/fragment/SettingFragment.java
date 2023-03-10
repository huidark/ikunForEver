package com.example.fitnesscalendar.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.ui.activity.ProfileActivity;
import com.example.fitnesscalendar.ui.activity.SettingsActivity;
import com.example.fitnesscalendar.viewmodel.UserViewModel;

public class SettingFragment extends Fragment implements View.OnClickListener {

    //instantiation of Delete Button
    Button delButton;

    //instantiation of Activity
    Activity activity;

    //instantiation of ViewModel
    UserViewModel um;

    //instantiation of username
    String userName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = requireActivity();
        um = new ViewModelProvider(
                (ViewModelStoreOwner) activity).get(
                UserViewModel.class);

        //get username from shared preference
        SharedPreferences sharedPreferences = getActivity().
                getSharedPreferences("Data", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", null);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //instantiation of Button
        delButton = view.findViewById(R.id.bt_del);
        delButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent it = null;
        switch(v.getId()){
            case R.id.bt_del:
                um.deleteUser(userName);
                SharedPreferences sharedPreferences = getActivity().
                        getSharedPreferences("Data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("userName");
                editor.apply();
                it = new Intent(getActivity(), ProfileActivity.class);
                break;
        }
        startActivity(it);
    }
}