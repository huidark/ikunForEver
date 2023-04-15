package com.example.fitnesscalendar.ui.fragment;

import static com.example.fitnesscalendar.R.id.fl_container;
import static com.example.fitnesscalendar.util.NetworkChecker.isNetworkConnected;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.model.User;
import com.example.fitnesscalendar.ui.activity.GeneralContainerActivity;
import com.example.fitnesscalendar.ui.activity.ProfileActivity;
import com.example.fitnesscalendar.ui.activity.SettingsActivity;
import com.example.fitnesscalendar.viewmodel.UserViewModel;

public class SettingFragment extends Fragment implements View.OnClickListener {

    //instantiation of Delete Button
    Button delButton;
    Button changePassButton;
    Button logoutButton;
    EditText passwordEt;

    //instantiation of Activity
    Activity activity;

    //instantiation of ViewModel
    UserViewModel um;
    MenuFragment menuFragment;

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
        if(!isNetworkConnected(getActivity().getApplicationContext())){
            Log.d("network", "no connection");
            NoNetFragment noNetFragment = new NoNetFragment();
            getFragmentManager().beginTransaction()
                    .replace(fl_container, noNetFragment).commitAllowingStateLoss();
            return;
        }

        //instantiation of Button
        delButton = view.findViewById(R.id.bt_del);
        changePassButton = view.findViewById(R.id.bt_cpass);
        passwordEt = view.findViewById(R.id.et_npassword);
        logoutButton = view.findViewById(R.id.bt_logout);
        delButton.setOnClickListener(this);
        changePassButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(!isNetworkConnected(getActivity().getApplicationContext())){
            Log.d("network", "no connection");
            NoNetFragment noNetFragment = new NoNetFragment();
            getFragmentManager().beginTransaction()
                    .replace(fl_container, noNetFragment).commitAllowingStateLoss();
            return;
        }
        Intent it = null;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        switch(v.getId()){
            case R.id.bt_del:
                um.deleteUser(userName);
                sharedPreferences = getActivity().
                        getSharedPreferences("Data", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.remove("userName");
                editor.apply();
                it = new Intent(getActivity(), GeneralContainerActivity.class);
                startActivity(it);
                break;
            case R.id.bt_cpass:
                LiveData<User> currentUser = um.getOneUser(userName);
                User u = currentUser.getValue();
                u.setPassword(passwordEt.getText().toString());
                um.updateUser(u);
                Toast.makeText(getActivity(), "Update Successfully", Toast.LENGTH_SHORT).show();
                menuFragment = new MenuFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container,
                                menuFragment)
                        .commitAllowingStateLoss();
                break;
            case R.id.bt_logout:
                sharedPreferences = getActivity().
                        getSharedPreferences("Data", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.remove("userName");
                editor.apply();
                it = new Intent(getActivity(), GeneralContainerActivity.class);
                startActivity(it);
                break;
        }
    }
}