package com.wiz.hungrybutn.network;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferanceHandler {
    private Activity activity;
    private static SharedPreferanceHandler handler = null;

    private SharedPreferanceHandler(Activity activity) {
        this.activity = activity;
    }

    public static SharedPreferanceHandler getInstance(Activity activity) {
        if (handler == null) {
            handler = new SharedPreferanceHandler(activity);

        }
        return handler;

    }

    public void putString(String key, String value) {
        SharedPreferences preferences = activity.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).apply();
    }

    public void putBoolean(String key, Boolean value) {
        SharedPreferences preferences = activity.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences preferences = activity.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        preferences.edit().putInt(key, value).apply();
    }

    public String getString(String key) {
        SharedPreferences preferences = activity.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String retrivedData = preferences.getString(key, null);//second parameter default value.
        return retrivedData;
    }

    public int getInt(String key) {
        SharedPreferences preferences = activity.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        int retrivedData = preferences.getInt(key, 0);//second parameter default value.
        return retrivedData;
    }

    public boolean getBoolean(String key) {
        SharedPreferences preferences = activity.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        boolean retrivedData = (boolean) preferences.getBoolean(key, false);//second parameter default value.
        return retrivedData;
    }


}
