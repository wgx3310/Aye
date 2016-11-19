package aye.ui;


import android.os.Bundle;

import reid.aye.R;

/**
 * Created by reid on 2016/11/19.
 */
public class SettingsActivity extends BaseSettingsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
    }
}
