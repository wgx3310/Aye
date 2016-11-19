package aye.util;

import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by reid on 2016/11/19.
 */

public class WLReflect {
    public static String getSystemProperties(String key){
        try {
            Class osSystem = Class.forName("android.os.SystemProperties");
            Method getInvoke = osSystem.getMethod("get", new Class[]{String.class});
            return  (String) getInvoke.invoke(osSystem,  new Object[]{key});
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return  "";
    }

    public static String getSystemProperties(String key, String def){
        String defaultStr = def;
        try {
            Class osSystem = Class.forName("android.os.SystemProperties");
            Method getInvoke = osSystem.getMethod("get", new Class[]{String.class});
            defaultStr =  (String) getInvoke.invoke(osSystem,  new Object[]{key});
            if(defaultStr == null)
                defaultStr = def;

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return  defaultStr;
    }

    public static boolean getSystemPropertiesBoolean(String key, boolean def){
        Boolean defaultres = def;
        try {
            Class osSystem = Class.forName("android.os.SystemProperties");
            Method getInvoke = osSystem.getMethod("getBoolean", String.class, Boolean.class);
            defaultres =  (Boolean) getInvoke.invoke(osSystem,  key, def);
        } catch (Exception e1) {
            Log.w("WLReflect", "get boolean system properties failed: " + e1.getMessage());
        }
        return defaultres;
    }

    public static long getSystemPropertiesLong(String key, long def){
        long defaultres = def;
        try {
            Class osSystem = Class.forName("android.os.SystemProperties");
            Method getInvoke = osSystem.getMethod("getLong", String.class, Long.class);
            defaultres =  (Long) getInvoke.invoke(osSystem,  key, def);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return defaultres;
    }

    public static int getSystemPropertiesInt(String key, int def){
        int defaultres = def;
        try {
            Class osSystem = Class.forName("android.os.SystemProperties");
            Method getInvoke = osSystem.getMethod("getInt", String.class, Integer.class);
            defaultres =  (Integer) getInvoke.invoke(osSystem,  key, def);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return defaultres;
    }

    public static void setSystemProperties(String key, String def){
        try {
            Class osSystem = Class.forName("android.os.SystemProperties");
            Method getInvoke = osSystem.getMethod("set", String.class, String.class);
            getInvoke.invoke(osSystem,  key, def);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
