package aye.pref;

import android.content.Context;
import android.content.SharedPreferences;

import aye.content.Contexts;

/**
 * Created by reid on 2016/11/14.
 */

public class BasePrefs {
    private final static String PREFS_NAME = "PREFS_NAME";
    private static final String KEY_TIME_MODIFIED = "timeModified";

    protected final SharedPreferences preferences;

    public BasePrefs(String prefName) {
        this.preferences = Contexts.getContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.putLong(KEY_TIME_MODIFIED, System.currentTimeMillis());
        editor.apply();
    }

    public Integer getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.putLong(KEY_TIME_MODIFIED, System.currentTimeMillis());
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.putLong(KEY_TIME_MODIFIED, System.currentTimeMillis());
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.putLong(KEY_TIME_MODIFIED, System.currentTimeMillis());
        editor.apply();
    }

    public long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }
}
