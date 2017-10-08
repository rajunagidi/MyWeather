package com.prudvi.weather;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Prudvi Raju on 10/6/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.settings_content, new SettingsFragment(), "pref").commit();
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        public static final String UNITS = "units";
        public static final String ZIP_CODE = "zip_code";

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onResume() {
            super.onResume();

            Preference preference = getPreferenceScreen().findPreference(ZIP_CODE);
            updatePreference(preference);

            preference = getPreferenceScreen().findPreference(UNITS);
            updatePreference(preference);
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Preference preference = getPreferenceScreen().findPreference(key);
            updatePreference(preference);
        }

        public void updatePreference(Preference preference) {
            if (preference == null) return;
            if (preference instanceof EditTextPreference) {
                preference.setSummary(((EditTextPreference) preference).getText());
            } else if (preference instanceof ListPreference) {
                preference.setSummary(((ListPreference) preference).getValue());
            }
        }
    }

}
