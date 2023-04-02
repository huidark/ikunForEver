package com.example.fitnesscalendar.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.fitnesscalendar.R;

import java.sql.Time;
import java.util.Calendar;
import java.util.TimeZone;

//TODO：问题1 button不显示 问题2 时间总冲突ok了 问题3 backstack有错误

public class CheckDateFragment extends Fragment {
    private CalendarView calendarView;
    private Button nextButton;
    private Bundle bundle;
    TimeLineFragment timeLineFragment;
    //TODO: getDate does not work correctly, so use a stupid method
    int mYear;
    int mMonth;
    int mDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_date, container, false);

        calendarView = view.findViewById(R.id.cv_cd);
        nextButton = view.findViewById(R.id.bt_cdb);

        // Set the minimum and maximum dates of the calendar

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month + 1;
                mDay = dayOfMonth;
            }
        });

        // Set a listener to log the selected date when the button is clicked
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDateString = String.format("%d%02d%02d", mYear, mMonth, mDay);
                Log.d("date geted", selectedDateString);
                timeLineFragment = new TimeLineFragment();

                //pass the date
                bundle = new Bundle();
                bundle.putString("date", selectedDateString);
                timeLineFragment.setArguments(bundle);
                //transit to timeline
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container
                                ,timeLineFragment)
                        .commitAllowingStateLoss();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}