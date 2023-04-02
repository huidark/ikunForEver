package com.example.fitnesscalendar.ui.fragment;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.data.TempoData;
import com.example.fitnesscalendar.model.User;
import com.example.fitnesscalendar.viewmodel.UserViewModel;

import java.util.HashMap;
import java.util.Map;


/*
This class serve as the fragment class of login page.
This class should show the usernmae text field and button to submit.
This class should update username in datastructure
 */
public class LoginFragment extends Fragment {

    //declare of variables
    private EditText usernameText;
    private Button submitButton;

    private Button registerButton;
    private MenuFragment menuFragment;

    private final String TAG = "LoginFragment";

    UserViewModel uv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        uv = new ViewModelProvider((ViewModelStoreOwner) activity).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //actions after view is created
        super.onViewCreated(view, savedInstanceState);

        //find two components inside the view
        usernameText = (EditText) view.findViewById(R.id.et_username);
        submitButton = (Button) view.findViewById(R.id.bt_login);
        registerButton = (Button) view.findViewById(R.id.bt_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                // see if this user is existed
                LiveData<User> lu = uv.getOneUser(username);
                if(lu != null){
                    Toast.makeText(getActivity(), "Username Existed", Toast.LENGTH_SHORT).show();
                }else {
                    Map<String, Double> weights = new HashMap<>();
                    weights.put("00000000", 0.0);
                    User u = new User(username, null, 0.0, weights, 0, false);
                    uv.createUser(u);
                    Toast.makeText(getActivity(), "Successfully Created", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    //TODO: option1 add username String
                    //TODO: option2 add User object
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

        //add listener for the button
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                // see if this user is existed
                LiveData<User> lu = uv.getOneUser(username);
                if(lu == null){
                    Toast.makeText(getActivity(), "No Such User", Toast.LENGTH_SHORT).show();
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