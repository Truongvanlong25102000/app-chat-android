package com.example.messenger;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.widget.DatePicker;

import androidx.annotation.Nullable;

import com.example.messenger.activites.login.LoginActivity;
import com.example.messenger.fragments.UserFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;
import java.util.prefs.Preferences;

public class UserPrefences extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_prefecence);
        final Preference ngaysinh = (Preference) findPreference("preBirthday");
        Preference logout = findPreference("preLogout");
        final int[] ngay = {1};
        final int[] thang = {0};
        final int[] nam = {2000};
        ngaysinh.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ngaysinh.setSummary(dayOfMonth + "-" + (month+1) + "-" + year);
                        ngay[0] = dayOfMonth;
                        thang[0] = month;
                        nam[0] = year;
                    }
                };
                DatePickerDialog datePickerDialog = null;
                datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, dateSetListener, nam[0], thang[0], ngay[0]);
                datePickerDialog.show();
                return false;
            }
        });

        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = getPreferenceScreen().getSharedPreferences();
        Map<String, ?> preferencesMap = sharedPreferences.getAll();
        for (Map.Entry<String, ?> preferenceEntry : preferencesMap.entrySet()){
            Preference pref = findPreference(preferenceEntry.getKey());
            updateSummary(pref);
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);
        updateSummary(pref);
    }

    public void updateSummary(Preference pref){
        if (pref instanceof EditTextPreference) {
            EditTextPreference EditPref = (EditTextPreference) pref;
            if(pref.getTitle().toString().toLowerCase().contains("mật khẩu")){
                pref.setSummary("********");
            }else {
                pref.setSummary(EditPref.getText());
            }
        }
    }


}
