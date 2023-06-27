package com.ccs.testapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SP {
    private static final String MY_PREFERENCE_NAME = "com.ccs.testapp";

    public static final String email = "email";

    public static void em(Context context, String total) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(email, total);
        editor.apply();
    }

    public static String getEm(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(email, "");
    }
    public static void registerPref(Context context, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        pref.registerOnSharedPreferenceChangeListener(listener);
    }


}
