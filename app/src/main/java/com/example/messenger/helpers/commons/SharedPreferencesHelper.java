package com.example.messenger.helpers.commons;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.messenger.base.MainApplication;

public class SharedPreferencesHelper<T> {
    public static final SharedPreferencesHelper INSTANCE = new SharedPreferencesHelper();

    private static final String PREFS_NAME = "shared_preferences_helper";
    private final SharedPreferences mSharedPreferences;

    private SharedPreferencesHelper() {
        mSharedPreferences = (SharedPreferences) MainApplication.self().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public <T> T get(String key, Class<T> anonymousClass) {
        if (anonymousClass == String.class) {
            return (T) mSharedPreferences.getString(key, "");
        } else if (anonymousClass == Integer.class) {
            return (T) Integer.valueOf(mSharedPreferences.getInt(key, 0));
        } else if (anonymousClass == Float.class) {
            return (T) Float.valueOf(mSharedPreferences.getFloat(key, 0));
        } else if (anonymousClass == Long.class) {
            return (T) Long.valueOf(mSharedPreferences.getLong(key, 0));
        }

        return null;
    }

    public <T> void put(String key, T data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        }
        editor.apply();
    }

    public void remove(String key){
        mSharedPreferences.edit().remove(key).apply();
    }

    public void clear(){
        mSharedPreferences.edit().clear().apply();
    }
}
