package com.ccs.testapp;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesConfig {
    private static final String MY_PREFERENCE_NAME = "com.ccs.testapp";
    public static final String CHOOSED_BLOCK = "chosed_block";
    public static void saveTotalInPref(Context context, int total) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(CHOOSED_BLOCK, total);
        editor.apply();
    }

    public static int loadTotalFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getInt(CHOOSED_BLOCK, 0);
    }
}
