package com.example.fitnesscalendar.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fitnesscalendar.R;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        ArrayList barArraylist;

        try{
            // get the data from the ShowPfFragment
            Bundle extras = getIntent().getExtras();
            HashMap<String, Double> weightDataMap = (HashMap<String, Double>) extras.getSerializable("weightDataMap");
            System.out.println(weightDataMap.toString());
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
//    private void getData()
//    {
//        barArraylist = new ArrayList();
//        barArraylist.add(new BarEntry(2f,10));
//        barArraylist.add(new BarEntry(3f,20));
//        barArraylist.add(new BarEntry(4f,30));
//        barArraylist.add(new BarEntry(5f,40));
//        barArraylist.add(new BarEntry(6f,50));
//    }
}
