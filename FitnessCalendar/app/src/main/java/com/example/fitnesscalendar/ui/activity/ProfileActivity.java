package com.example.fitnesscalendar.ui.activity;

import static com.example.fitnesscalendar.R.id.fl_container;
import static com.example.fitnesscalendar.util.NetworkChecker.isNetworkConnected;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.model.User;
import com.example.fitnesscalendar.ui.fragment.EditBPfFragment;
import com.example.fitnesscalendar.ui.fragment.NoNetFragment;
import com.example.fitnesscalendar.ui.fragment.ShowPfFragment;
import com.example.fitnesscalendar.viewmodel.UserViewModel;

public class ProfileActivity extends AppCompatActivity {

    //instantiation of ShowPfFragment
    ShowPfFragment showPfFragment;

    //instantiation of EditBPfFragment
    EditBPfFragment editBPfFragment;

    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //create fragment manager and transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(!isNetworkConnected(getApplicationContext())){
            Log.d("network", "no connection");
            NoNetFragment noNetFragment = new NoNetFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(fl_container, noNetFragment).commitAllowingStateLoss();
            return;
        }

        //get user name
        //TODO: this sharedpreference actually should use Bundle
        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", null);

        //check if the user has registered or not
        UserViewModel um = new ViewModelProvider((ViewModelStoreOwner) this).get(UserViewModel.class);
        LiveData<User> lu = um.getOneUser(userName);
        if (!lu.getValue().getRegistered()) {
            //create instantiation of show blank profile fragment
            editBPfFragment = new EditBPfFragment();

            //add the fragment into frame layout container
            fragmentTransaction.add(fl_container, editBPfFragment).commitAllowingStateLoss();
        } else {

            //create an instantiation of show profile fragment
            showPfFragment = new ShowPfFragment();

            //add the fragment into frame layout container
            fragmentTransaction.add(fl_container, showPfFragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}