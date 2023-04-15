package com.example.fitnesscalendar.ui.activity;

import static com.example.fitnesscalendar.R.id.fl_container;
import static com.example.fitnesscalendar.util.NetworkChecker.isNetworkConnected;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.ui.fragment.NoNetFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BarChartActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        ArrayList barArraylist;

        try{
            // get the data from the ShowPfFragment
            Bundle extras = getIntent().getExtras();
            // the data of the user's weight is passed from the ShowPfFragment in a map
            HashMap<String, Double> weightDataMap = (HashMap<String, Double>) extras.getSerializable("weightDataMap");
            //System.out.println(weightDataMap.toString());
            barArraylist = new ArrayList();
            float order = 1;
            // fill the arraylist with the user's date and weight information
            for (Map.Entry<String, Double> entry : weightDataMap.entrySet()) {
                //float key = Float.parseFloat(entry.getKey());
                float key = order;
                int value = entry.getValue().intValue();
                barArraylist.add(new BarEntry(key, value));
                order++;
            }
            BarEntry test = new BarEntry(1.2f, 12);
            test.getY();
            // create the chart
            BarChart barChart = findViewById(R.id.barchart);
            BarDataSet barDataSet = new BarDataSet(barArraylist,"Weight Trace");
            BarData barData = new BarData(barDataSet);
            barChart.setData(barData);
            //color bar data set
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            //text color
            barDataSet.setValueTextColor(Color.BLACK);
            //settting text size
            barDataSet.setValueTextSize(16f);
            barChart.getDescription().setEnabled(true);
        }catch (Exception e) {
            Log.e("BarChartActivity", "Error in onCreate", e);
        }

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
                onBackPressed();
            }
        });
    }

    public static ArrayList getBarData(HashMap<String, Double> data){
        ArrayList barArraylist = new ArrayList();

        float order = 1;
        // fill the arraylist with the user's date and weight information
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            //float key = Float.parseFloat(entry.getKey());
            float key = order;
            int value = entry.getValue().intValue();
            barArraylist.add(new BarEntry(key, value));
            order++;
        }
        return barArraylist;
    }
}
