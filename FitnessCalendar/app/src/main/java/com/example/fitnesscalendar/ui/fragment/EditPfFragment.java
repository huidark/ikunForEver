package com.example.fitnesscalendar.ui.fragment;

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
import com.example.fitnesscalendar.ui.activity.ProfileActivity;
import com.example.fitnesscalendar.util.Date;
import com.example.fitnesscalendar.viewmodel.UserViewModel;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

public class EditPfFragment extends Fragment implements View.OnClickListener {

    //instantiation of text views
    TextView unText;
    EditText agText;
    EditText gdText;
    EditText htText;
    EditText wtText;

    //instantiation of buttons
    Button svButton;

    //instantiation of UserViewModel
    UserViewModel um;

    //instantiation of activity
    Activity activity;

    //instantiation of variables
    String userName;
    String password;
    double height;
    double weight;
    int age;
    String gender;


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

        // see if there is data in the bundle that we will give to user
        Bundle bundle = this.getArguments();
        gender = bundle.getString("GENDER");
        height = bundle.getDouble("HEIGHT");
        weight = bundle.getDouble("WEIGHT");
        age = bundle.getInt("AGE");

        if(gender != null){
            agText.setHint("AGE: " + age);
            gdText.setHint("GENDER: " + gender);
            htText.setHint("HEIGHT: " + height);
            wtText.setHint("WEIGHT: " + weight);
        }

        //instantiation of button
        svButton = view.findViewById(R.id.bt_sv);

        //register listener
        svButton.setOnClickListener(this);

        unText.setText(userName);
        unText.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        String heightEt = htText.getText().toString();
        String ageEt = agText.getText().toString();
        String genderEt = gdText.getText().toString();
        String weightEt = wtText.getText().toString();
        Log.d("Weightetfix", weightEt);

        //check empty case
        if(heightEt.isEmpty()){
            heightEt = height + "";
        }
        if(ageEt.isEmpty()){
            ageEt = age + "";
        }
        if(genderEt.isEmpty()){
            genderEt = gender;
        }
        if(weightEt.isEmpty()){
            weightEt = weight + "";
        }

        if(Integer.valueOf(ageEt) == 0){
            Toast.makeText(getActivity(), "age should not be zero", Toast.LENGTH_SHORT).show();
        } else if(Math.abs(Double.valueOf(heightEt) - 0) < 0.001 ){
            Toast.makeText(getActivity(), "height should not be zero", Toast.LENGTH_SHORT).show();
        } else if(Math.abs(Double.valueOf(weightEt) - 0) < 0.001) {
            Toast.makeText(getActivity(), "weight should not be zero", Toast.LENGTH_SHORT).show();
        }

        //TODO: I don't want to restrict the gender ^_^
        else{
            //update weight and other attributes
            LiveData<User> currentUser = um.getOneUser(userName);
            Map<String, Double> currentWeight = currentUser.getValue().getUserWeight();
            password = currentUser.getValue().getPassword();
            //weight is recorded based on the date
            String currentDate = Date.generateDate();
            currentWeight.put(currentDate, Double.valueOf(weightEt));
            User user = new User(userName, genderEt, Double.valueOf(heightEt),
            currentWeight, Integer.valueOf(ageEt), true, password);
            um.updateUser(user);
            Toast.makeText(getActivity(), "Update Successfully", Toast.LENGTH_SHORT).show();
            showPfFragment = new ShowPfFragment();
            //getFragmentManager().popBackStack();

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_container
                            ,showPfFragment)
                    .commitAllowingStateLoss();

        }
    }
}