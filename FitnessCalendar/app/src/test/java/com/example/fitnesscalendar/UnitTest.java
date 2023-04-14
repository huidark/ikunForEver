package com.example.fitnesscalendar;


import static com.example.fitnesscalendar.data.AvailableTime.convertToMinutes;
import static com.example.fitnesscalendar.ui.activity.BarChartActivity.getBarData;

import org.junit.Test;

import static org.junit.Assert.*;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;

public class UnitTest {


    // test whether transferring data from hashmap to arraylist is successfully
    @Test
    public void barData_axis_x_isCorrect() {
        HashMap<String, Double> sampleData = new HashMap<String, Double>();

        sampleData.put("1",222.2);
        sampleData.put("2",333.3);
        ArrayList barArraylist = getBarData(sampleData);

        BarEntry test = (BarEntry) barArraylist.get(1);
        String result = String.valueOf(test.getX());
        assertEquals("2.0", result);

    }
    // test whether the y axis data is corrected casted
    @Test
    public void barData_axis_y_isCorrect() {
        HashMap<String, Double> sampleData = new HashMap<String, Double>();

        sampleData.put("1",222.0);
        sampleData.put("2",333.0);
        ArrayList barArraylist = getBarData(sampleData);

        BarEntry test = (BarEntry) barArraylist.get(0);
        String result = String.valueOf(test.getY());
        assertEquals("222.0", result);

    }
    // test whether the convertToMinutes works properly
    @Test
    public void checkTime_isCorrect(){
        String expectedTime = "3:20";
        int actualTime = convertToMinutes(expectedTime);
        assertEquals(200, actualTime);
    }
    @Test
    public void checkTime_isWrong(){
        String unexpectedTime = "5:20";
        int actualTime = convertToMinutes(unexpectedTime);
        assertNotEquals(10, actualTime);
    }
}
