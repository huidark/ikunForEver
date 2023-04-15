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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.data.AvailableTime;
import com.example.fitnesscalendar.model.DeEvent;
import com.example.fitnesscalendar.model.Event;
import com.example.fitnesscalendar.model.Train;
import com.example.fitnesscalendar.viewmodel.EventViewModel;
import com.example.fitnesscalendar.viewmodel.UserViewModel;

public class AddEventFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    EditText tpEditText;
    EditText clEditText;
    Spinner drSpinner;
    Spinner tmSpinner;
    Button nextButton;
    Activity activity;
    String date;
    EventViewModel em;
    TimeLineFragment timeLineFragment;
    AvailableTime at;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        em = new ViewModelProvider(
                (ViewModelStoreOwner) activity).get(
                EventViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        //TODO: get bundle data
        Bundle bundle = getArguments();
        at = (AvailableTime) bundle.getSerializable("time");
        date = bundle.getString("date");
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

        drSpinner = view.findViewById(R.id.sp_dr);
        tpEditText = view.findViewById(R.id.et_tp);
        clEditText = view.findViewById(R.id.et_cl);
        tmSpinner = view.findViewById(R.id.sp_tm);
        nextButton = view.findViewById(R.id.bt_nt);

        //instantiate the drop down menu
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.duration_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adaptertm = ArrayAdapter.createFromResource(getContext(),
                R.array.start_values, android.R.layout.simple_spinner_item);
        adaptertm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tmSpinner.setAdapter(adaptertm);


        drSpinner.setOnItemSelectedListener(this);
        tmSpinner.setOnItemSelectedListener(this);

        nextButton.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //do nothing
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
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
        int selectedDPosition = drSpinner.getSelectedItemPosition();
        String selectedDuration = (String) drSpinner.getItemAtPosition(selectedDPosition);
        Log.d("ATT selectedDU", selectedDuration);
        int duration = Integer.valueOf(selectedDuration.split(":")[0]) * 60;

        int selectedTPosition = tmSpinner.getSelectedItemPosition();
        String selectedTime = (String) tmSpinner.getItemAtPosition(selectedTPosition);

        Log.d("ATT selectedTIME", selectedTime);

        if(AvailableTime.checkTimeConflict(at.getEvents(), selectedTime, duration)){
            Log.d("num", at.getEvents().size() + "");
            Toast.makeText(getActivity(), "Conflict Time!", Toast.LENGTH_SHORT).show();
        }else{
            String type = tpEditText.getText().toString();
            String calories = clEditText.getText().toString();
            int caloriesI = Integer.valueOf(calories);
            SharedPreferences sharedPreferences = getActivity().
                    getSharedPreferences("Data", Context.MODE_PRIVATE);
            String userName = sharedPreferences.getString("userName", null);

            Event event = new Event(userName, duration, date, selectedTime);
            Train train = new Train(type, true, false, Integer.valueOf(calories));
            DeEvent deEvent = new DeEvent(userName, event, train);
            em.createEvent(deEvent);

            timeLineFragment = new TimeLineFragment();
            Bundle bundle = new Bundle();
            bundle.putString("date", date);
            timeLineFragment.setArguments(bundle);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_container
                            ,timeLineFragment)
                    .commitAllowingStateLoss();
        }
    }

}