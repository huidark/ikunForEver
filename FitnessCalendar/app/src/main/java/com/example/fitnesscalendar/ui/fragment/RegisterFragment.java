package com.example.fitnesscalendar.ui.fragment;

import static com.example.fitnesscalendar.R.id.fl_container;
import static com.example.fitnesscalendar.util.NetworkChecker.isNetworkConnected;

import android.app.Activity;
import android.content.Context;
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
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.model.User;
import com.example.fitnesscalendar.viewmodel.UserViewModel;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    Button registerButton;
    EditText passwordEt;
    EditText passwordcEt;
    String username;
    UserViewModel uv;
    MenuFragment menuFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        uv = new ViewModelProvider((ViewModelStoreOwner) activity).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        registerButton = view.findViewById(R.id.bt_register);
        passwordEt = view.findViewById(R.id.et_rpassword);
        passwordcEt = view.findViewById(R.id.et_rpasswordc);
        Bundle bundle = getArguments();
        username = bundle.getString("username");
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
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isNetworkConnected(getActivity().getApplicationContext())){
                    Log.d("network", "no connection");
                    NoNetFragment noNetFragment = new NoNetFragment();
                    getFragmentManager().beginTransaction()
                            .replace(fl_container, noNetFragment).commitAllowingStateLoss();
                    return;
                }
                String etPassword = passwordEt.getText().toString();
                String etPasswordc = passwordcEt.getText().toString();
                if(!etPassword.equals(etPasswordc)){
                    Toast.makeText(getActivity(), "not consistent", Toast.LENGTH_SHORT).show();
                }else {
                    Map<String, Double> weights = new HashMap<>();
                    weights.put("00000000", 0.0);
                    User u = new User(username, null, 0.0, weights, 0, false, etPassword);
                    uv.createUser(u);
                    Toast.makeText(getActivity(), "Successfully Created", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    //TODO: option1 add username String
                    //TODO: option2 add User object
                    //TODO: set up password
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