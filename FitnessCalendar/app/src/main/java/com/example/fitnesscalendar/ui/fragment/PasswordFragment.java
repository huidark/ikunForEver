package com.example.fitnesscalendar.ui.fragment;

import static com.example.fitnesscalendar.R.id.fl_container;
import static com.example.fitnesscalendar.util.NetworkChecker.isNetworkConnected;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.model.User;

public class PasswordFragment extends Fragment {

    Button loginButton;
    EditText passwordEt;
    MenuFragment menuFragment;
    String username;
    String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password, container, false);
        loginButton = view.findViewById(R.id.bt_login);
        passwordEt = view.findViewById(R.id.et_ppassword);
        Bundle bundle = getArguments();
        username = bundle.getString("username");
        password = bundle.getString("password");
        return view;
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
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!isNetworkConnected(getActivity().getApplicationContext())){
                    Log.d("network", "no connection");
                    NoNetFragment noNetFragment = new NoNetFragment();
                    getFragmentManager().beginTransaction()
                            .replace(fl_container, noNetFragment).commitAllowingStateLoss();
                    return;
                }
                String userPassword = passwordEt.getText().toString();
                if(!userPassword.equals(password)){
                    Toast.makeText(getActivity(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userName", username);
                    editor.apply();
                    menuFragment = new MenuFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_container,
                                    menuFragment)
                            .commitAllowingStateLoss();
                }
            }
        });
    }
}