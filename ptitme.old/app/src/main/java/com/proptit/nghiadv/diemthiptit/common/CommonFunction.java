package com.proptit.nghiadv.diemthiptit.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nghia on 4/15/2018.
 */

public class CommonFunction {
    public static Date parseStringToDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss");
        try {
            Date date = format.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean validateFloat(float number) {
        int numberInInteger = (int) number;
        return !(numberInInteger == number);
    }


    public static String formatDataMark(float number) {
        if (validateFloat(number)) {
            return String.valueOf(number);
        }

        int numberInteger = (int) number;
        return String.valueOf(numberInteger);
    }

    public static String formatDateToString(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);
        return formattedTime;
    }
}
