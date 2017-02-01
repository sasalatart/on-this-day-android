package com.salatart.onthisday.Utils;

import android.app.Activity;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by sasalatart on 10/23/16.
 */

public class DateUtils {
    private final static String DATE_FORMAT = "dd/MM";

    public static void checkDateValidity(Activity activity, int day, int month, int oldVal, NumberPicker picker) {
        String date = day + "/" + month;

        if (!isDateValid(date)) {
            picker.setValue(oldVal);
            String text = new DateFormatSymbols().getMonths()[month - 1] + " " + day + " is not a valid date.";
            Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean isDateValid(String date) {
        if (date.equals("29/2")) {
            return true;
        }

        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
