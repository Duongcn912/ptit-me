package com.proptit.nghiadv.diemthiptit.common;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nghia on 6/29/2017.
 */

public class SharedPreferenceSupport {

    public static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Key.FILE_LOGIN, MODE_PRIVATE);
        return sharedPref;
    }

    public static SharedPreferences getSharedPreferences(String key,Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(key, MODE_PRIVATE);
        return sharedPref;
    }


    public static void clearSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
