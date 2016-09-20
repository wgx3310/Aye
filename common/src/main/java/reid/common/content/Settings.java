package reid.common.content;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by wgx33 on 2016/9/20.
 */

public class Settings {

    private static final String PREF_SETTINGS = "settings";
    private static final SharedPreferences settingPrefs = Contexts.getContext().getSharedPreferences(PREF_SETTINGS, Context.MODE_PRIVATE);

    public static void addSetting(String key, String value){
        settingPrefs.edit().putString(key, value).commit();
    }

    public static void addSetting(String key, boolean value){
        settingPrefs.edit().putBoolean(key, value).commit();
    }

    public static void addSetting(String key, int value){
        settingPrefs.edit().putInt(key, value).commit();
    }

    public static void addSetting(String key, float value){
        settingPrefs.edit().putFloat(key, value).commit();
    }

    public static void addSetting(String key, long value){
        settingPrefs.edit().putLong(key, value).commit();
    }

    public static void addSetting(String key, Set<String> value){
        settingPrefs.edit().putStringSet(key, value).commit();
    }

    public static String getStringSetting(String key, String defVal){
        return settingPrefs.getString(key, defVal);
    }

    public static String getStringSetting(String key){
        return getStringSetting(key, null);
    }

    public static boolean getBooleanSetting(String key, boolean defVal){
        return settingPrefs.getBoolean(key, defVal);
    }

    public static boolean getBooleanSetting(String key){
        return getBooleanSetting(key, false);
    }

    public static int getIntSetting(String key, int defVal){
        return settingPrefs.getInt(key, defVal);
    }

    public static int getIntSetting(String key){
        return getIntSetting(key, 0);
    }

    public static float getFloatSetting(String key, float defVal){
        return settingPrefs.getFloat(key, defVal);
    }

    public static float getFloatSetting(String key){
        return getFloatSetting(key, 0);
    }

    public static long getLongSetting(String key, long defVal){
        return settingPrefs.getLong(key, defVal);
    }

    public static long getLongSetting(String key){
        return getLongSetting(key, 0);
    }

    public static Set<String> getStringSetSetting(String key, Set<String> defVal){
        return settingPrefs.getStringSet(key, defVal);
    }

    public static Set<String> getStringSetSetting(String key){
        return getStringSetSetting(key, null);
    }
}
