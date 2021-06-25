package com.example.messenger;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import java.util.Map;
import java.util.prefs.Preferences;

public class UserPrefences extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_prefecence);
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = getPreferenceScreen().getSharedPreferences();
        Map<String, ?> preferencesMap = sharedPreferences.getAll();
        for (Map.Entry<String, ?> preferenceEntry : preferencesMap.entrySet()){
            Preference pref = findPreference(preferenceEntry.getKey());
            if(pref instanceof EditTextPreference){
                EditTextPreference EditPref = (EditTextPreference) pref;
                pref.setSummary(EditPref.getText());
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) {
            EditTextPreference EditPref = (EditTextPreference) pref;
            pref.setSummary(EditPref.getText());
        }
    }


}
