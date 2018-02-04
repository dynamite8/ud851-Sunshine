package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;


// COMPLETED (4) Create SettingsFragment and extend PreferenceFragmentCompat
// COMPLETED (10) Implement OnSharedPreferenceChangeListener from SettingsFragment
public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    // COMPLETED (8) Create a method called setPreferenceSummary that accepts a Preference and an Object and sets the summary of the preference
    private void setPreferenceSummary(Preference preference, Object object) {
        String value = object.toString();
        String key = preference.getKey();

        if (preference instanceof ListPreference) {
            ListPreference list = (ListPreference) preference;
            int indexOfValue = list.findIndexOfValue(value);
            if (indexOfValue >= 0) {
                CharSequence labelOfValue = list.getEntries()[indexOfValue];
                list.setSummary(labelOfValue);
            }
        } else {
            EditTextPreference editTextPreference = (EditTextPreference) preference;
            editTextPreference.setSummary(value);
        }
    }

    // COMPLETED (5) Override onCreatePreferences and add the preference xml file using addPreferencesFromResource

    // COMPLETED (13) Unregister SettingsFragment (this) as a SharedPreferenceChangedListener in onStop
    @Override
    public void onStop() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }


    // COMPLETED (12) Register SettingsFragment (this) as a SharedPreferenceChangedListener in onStart
    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        // Adds the preference screen to the settings fragment
        addPreferencesFromResource(R.xml.preference_screen);

        // Do step 9 within onCreatePreference
        // COMPLETED (9) Set the preference summary on each preference that isn't a CheckBoxPreference
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();
        for (int i = 0; i < preferenceScreen.getPreferenceCount(); i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
        }

    }

    // COMPLETED (11) Override onSharedPreferenceChanged to update non CheckBoxPreferences when they are changed
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = getPreferenceScreen().findPreference(key);
        if (!(preference instanceof CheckBoxPreference)) {
            String value = sharedPreferences.getString(key, "");
            setPreferenceSummary(preference, value);
        }
    }
}
