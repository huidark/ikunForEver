package com.example.fitnesscalendar.util;

import java.util.Calendar;

public class Date {

    public static String generateDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Add 1 to get the actual month
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String sYear = year + "";
        String sMonth = "";
        String sDay = "";
        if (month < 10) {
            sMonth = "0";
        }
        if (day < 10) {
            sDay = "0";
        }

        sMonth = sMonth.concat(month + "");
        sDay = sDay.concat(day + "");

        return sYear + sMonth + sDay;
    }

}
