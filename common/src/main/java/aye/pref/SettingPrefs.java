package aye.pref;

/**
 * Created by reid on 2016/11/14.
 */

public class SettingPrefs extends BasePrefs{
    private static final String PREF_SETTINGS = "settings";

    private static SettingPrefs instance;

    public static SettingPrefs getInstance(){
        if (instance == null){
            instance = new SettingPrefs();
        }
        return instance;
    }

    private SettingPrefs(){
        super(PREF_SETTINGS);
    }

    public boolean isFirstLaunch() {
        return getBoolean("first_launch", true);
    }

    public void setHasLaunch() {
        putBoolean("first_launch", false);
    }
}
