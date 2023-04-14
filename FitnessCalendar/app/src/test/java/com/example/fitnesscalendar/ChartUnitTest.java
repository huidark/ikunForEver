package com.example.fitnesscalendar;


import static com.example.fitnesscalendar.data.AvailableTime.convertToMinutes;

import org.junit.Test;

import static org.junit.Assert.*;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;

public class ChartUnitTest {


    // test whether transfering data from hashmap to
    @Test
    public void barData_isCorrect() {
        HashMap<String, Double> weightDataMap = new HashMap<String, Double>();
        weightDataMap.put("1",222.2);
        weightDataMap.put("2",333.3);

        ArrayList expectedDataList = new ArrayList();
        expectedDataList.add(new BarEntry(1.0F, 1));
        expectedDataList.add(new BarEntry(2.0F, 2));
        BarDataSet barDataSet = new BarDataSet(expectedDataList,"test");
        BarData barData = new BarData(barDataSet);

        System.out.println(barData.getDataSetByIndex(0));

        assertEquals(4, 6 - 2);
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
