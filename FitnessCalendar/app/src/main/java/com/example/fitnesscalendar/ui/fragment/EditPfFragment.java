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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.model.User;
import com.example.fitnesscalendar.ui.activity.ProfileActivity;
import com.example.fitnesscalendar.viewmodel.UserViewModel;

import java.util.HashMap;

public class EditPfFragment extends Fragment implements View.OnClickListener {

    //instantiation of text views
    TextView unText;//TODO: change this later
    EditText agText;
    EditText gdText;
    EditText htText;
    EditText wtText;//TODO: change this later

    //instantiation of buttons
    Button svButton;

    //instantiation of UserViewModel
    UserViewModel um;

    //instantiation of activity
    Activity activity;

    //instantiation of username
    String userName;

    //instantiation of show profile fragment
    ShowPfFragment showPfFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create User View Model
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
        return inflater.inflate(R.layout.fragment_edit_pf, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // instantiation of edit texts
        unText = view.findViewById(R.id.et_name);
        agText = view.findViewById(R.id.et_age);
        gdText = view.findViewById(R.id.et_gender);
        htText = view.findViewById(R.id.et_height);
        wtText = view.findViewById(R.id.et_weight);

        //instantiation of button
        svButton = view.findViewById(R.id.bt_sv);

        //register listener
        svButton.setOnClickListener(this);

        //TODO: currently do not allow for editing username and weight
        unText.setText(userName);
        unText.setEnabled(false);
        wtText.setText("WEIGHT");
        wtText.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        String height = htText.getText().toString();
        String age = agText.getText().toString();
        String gender = gdText.getText().toString();

        //check empty case
        if(height.isEmpty()){
            Toast.makeText(getActivity(), "fill up height", Toast.LENGTH_SHORT).show();
            return;
        }
        if(age.isEmpty()){
            Toast.makeText(getActivity(), "fill up age", Toast.LENGTH_SHORT).show();
            return;
        }
        if(gender.isEmpty()){
            Toast.makeText(getActivity(), "fill up gender", Toast.LENGTH_SHORT).show();
            return;
        }

        if(Integer.valueOf(age) == 0){
            Toast.makeText(getActivity(), "age should not be zero", Toast.LENGTH_SHORT).show();
        } else if(Math.abs(Double.valueOf(height) - 0) < 0.001 ){
            Toast.makeText(getActivity(), "height should not be zero", Toast.LENGTH_SHORT).show();
        }
        //TODO: I don't want to restrict the gender ^_^
        else{
            //TODO: should update weight later
            User user = new User(userName, gender, Double.valueOf(height),
            new HashMap<String, Double>(), Integer.valueOf(age));
            um.updateUser(user);
            //TODO: this should be executed base on Fetcher's result
            Toast.makeText(getActivity(), "Update Successfully", Toast.LENGTH_SHORT).show();


            showPfFragment = new ShowPfFragment();
            getFragmentManager().popBackStack();
            /*
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_container
                            ,showPfFragment)
                    .commitAllowingStateLoss();



             */
        }
    }
}