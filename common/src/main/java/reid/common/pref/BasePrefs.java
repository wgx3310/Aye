package reid.common.pref;

import android.content.Context;
import android.content.SharedPreferences;

import reid.common.content.Contexts;

/**
 * Created by wgx33 on 2016/11/14.
 */

public class BasePrefs {
    private final static String PREFS_NAME = "PREFS_NAME";
    private static final String KEY_TIME_MODIFIED = "timeModified";

    protected final SharedPreferences preferences;

    public BasePrefs(String prefName) {
        this.preferences = Contexts.getContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    protected String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    protected void setString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.putLong(KEY_TIME_MODIFIED, System.currentTimeMillis());
        editor.apply();
    }

    protected Integer getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    protected void setInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.putLong(KEY_TIME_MODIFIED, System.currentTimeMillis());
        editor.apply();
    }

    protected void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.putLong(KEY_TIME_MODIFIED, System.currentTimeMillis());
        editor.apply();
    }

    protected boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    protected void setLong(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.putLong(KEY_TIME_MODIFIED, System.currentTimeMillis());
        editor.apply();
    }

    protected long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }
}
