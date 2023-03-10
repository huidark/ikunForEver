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

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.model.User;
import com.example.fitnesscalendar.ui.activity.ProfileActivity;
import com.example.fitnesscalendar.ui.activity.SettingsActivity;
import com.example.fitnesscalendar.viewmodel.UserViewModel;

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

    //instantiation of username
    String userName;
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
        //TODO: if not using thread pool in UserViewModel then this can only be synchronized and slow
        //TODO: is there case that the following lines will cause exception?
        User user = um.getOneUser(userName).getValue();
        {
            if(user.getUserWeight() == null) {
                Log.d("mapnull", user.getUserName());
                Log.d("mapnull", user.getUserHeight()+"");
                Log.d("mapnull", "infragment");
            }
            unText.setText(user.getUserName());
            if (user.getUserAge() != 0) {
                String age = user.getUserAge()+"";
                agText.setText(age);
            } else {
                agText.setText("set by edit");
            }

            if (Math.abs(user.getUserHeight() - 0) < 0.001) {
                htText.setText(user.getUserHeight() + "");
            } else {
                htText.setText("set by edit");
            }
            if (user.getUserGender() != null) {
                gdText.setText(user.getUserGender());
            } else {
                gdText.setText("set by edit");
            }
            /*TODO: Map has problem
            if (Math.abs(User.getLatestWeight(user) - 0) < 0.001) {
                wtText.setText(User.getLatestWeight(user) + "");
            } else {
                wtText.setText("set by edit");
            }
             */
        }


        //TODO: this observer actually does nothing ^^
        //observe current user data
        um.observerDataOneUser().observe((LifecycleOwner) activity, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                unText.setText(user.getUserName());
                if(user.getUserAge() != 0){
                    String age = user.getUserAge()+"";
                    agText.setText(age);
                }else{
                    agText.setText("AGE: set by edit");
                }

                if(Math.abs(user.getUserHeight() - 0) > 0.001){
                    Log.d("testheight", user.getUserHeight()+"");
                    Log.d("testheight", Math.abs(user.getUserHeight() - 0)+"");
                    htText.setText(user.getUserHeight()+"");
                }else{
                    htText.setText("HEIGHT: set by edit");
                }
                if(user.getUserGender() != null){
                    gdText.setText(user.getUserGender());
                }else{
                    gdText.setText("GENDER: set by edit");
                }
                /*TODO: Map has problem
                if(Math.abs(User.getLatestWeight(user) - 0) < 0.001){
                    wtText.setText(User.getLatestWeight(user)+"");
                }else{
                    wtText.setText("set by edit");
                }

                 */

            }
        });

        //set up click listener
        cwButton.setOnClickListener(this);
        etButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_tmw:
                //TODO:implement this later. This is used to trace the weight of an user.
                break;
            case R.id.bt_ed:
                editPfFragment = new EditPfFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container
                                ,editPfFragment).addToBackStack(null)
                        .commitAllowingStateLoss();
                break;
        }
    }
}