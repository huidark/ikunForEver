package com.example.fitnesscalendar.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.model.User;
import com.example.fitnesscalendar.ui.activity.BarChartActivity;
import com.example.fitnesscalendar.ui.activity.ProfileActivity;
import com.example.fitnesscalendar.ui.activity.SettingsActivity;
import com.example.fitnesscalendar.viewmodel.UserViewModel;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ShowPfFragment extends Fragment implements View.OnClickListener {

    //instantiation of text views
    TextView unText;
    TextView agText;
    TextView gdText;
    TextView htText;
    TextView wtText;

    //instantiation of buttons
    Button cwButton;
    Button etButton;

    //instantiation of edit fragment
    EditPfFragment editPfFragment;

    //instantiation of ViewModel
    UserViewModel um;

    //instantiation of activity
    Activity activity;

    //instantiation of Bundle
    Bundle bundle;

    //instantiation of username
    String userName;
    User user;
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
        bundle = new Bundle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_pf, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //instantiation of test views
        unText = view.findViewById(R.id.tv_name);
        agText = view.findViewById(R.id.tv_age);
        gdText = view.findViewById(R.id.tv_gender);
        htText = view.findViewById(R.id.tv_height);
        wtText = view.findViewById(R.id.tv_weight);

        //instantiation of buttons
        cwButton = view.findViewById(R.id.bt_tmw);
        etButton = view.findViewById(R.id.bt_ed);

        //manually get the user'info for the first time
        user = um.getOneUser(userName).getValue();
        {
            //set up shown text
            agText.setText("AGE: " + user.getUserAge() + "");
            htText.setText("HEIGHT: "+ user.getUserHeight() + "");
            unText.setText(user.getUserName());
            gdText.setText("GENDER: "+ user.getUserGender());
            wtText.setText("WEIGHT: " + User.getLatestWeight(user) + "");

            //set up bundle for edit
            bundle.putInt("AGE", user.getUserAge());
            bundle.putDouble("HEIGHT", user.getUserHeight());
            bundle.putDouble("WEIGHT", User.getLatestWeight(user));
            bundle.putString("GENDER", user.getUserGender());


        }



        //set up click listener
        cwButton.setOnClickListener(this);
        etButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_tmw:
                // create a new map to store the date and weight and then pass it to BarChartActivity
                HashMap<String, Double> weightDataMap = new HashMap<>(user.getUserWeight());
                Intent intent = new Intent(getActivity(), BarChartActivity.class);
                bundle.putSerializable("weightDataMap", (Serializable) weightDataMap);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.bt_ed:
                editPfFragment = new EditPfFragment();
                editPfFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container
                                ,editPfFragment)
                        .commitAllowingStateLoss();
                break;
        }
    }
}