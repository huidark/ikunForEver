package com.example.fitnesscalendar.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.data.DataStructure;

/*
This class serve as the fragment class of login page.
This class should show the usernmae text field and button to submit.
This class should update username in datastructure
 */
public class LoginFragment extends Fragment {

    //declare of variables
    private EditText usernameText;
    private Button submitButton;

    private MenuFragment menuFragment;
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

        //add listener for the button
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //update username in data structure
                String username = usernameText.getText().toString();
                Bundle bundlel = getArguments();
                //TODO: add try/catch to next line
                DataStructure ds = (DataStructure) bundlel.getSerializable("ds");
                ds.setUsername(username);
                //jump to menu fragment
                menuFragment = new MenuFragment();
                Bundle bundlem = new Bundle();
                bundlem.putSerializable("ds", ds);
                menuFragment.setArguments(bundlem);
                getFragmentManager().beginTransaction().replace(R.id.fl_container, menuFragment).addToBackStack(null).commitAllowingStateLoss();
            }
        });
    }
}