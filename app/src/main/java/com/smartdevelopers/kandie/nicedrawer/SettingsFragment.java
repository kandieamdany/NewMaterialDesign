package com.smartdevelopers.kandie.nicedrawer;

import android.os.Bundle;
import android.preference.PreferenceActivity;



/**
 * Created by 4331 on 22/07/2015.
 */
public class SettingsFragment extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
