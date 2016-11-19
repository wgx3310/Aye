package aye.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

import aye.content.Contexts;
import aye.pref.SettingPrefs;

/**
 * Created by reid on 2016/11/19.
 */

public class AccountUtils {
    private static final String TAG = AccountUtils.class.getSimpleName();

    private static final String PERSIST_RADIO_MEID = "persist.radio.meid";
    private static final String PERSIST_RADIO_IMEI2 = "persist.radio.imei2";
    private static final String PERSIST_RADIO_IMEI1 = "persist.radio.imei1";
    private static final String PERSIST_RADIO_IMEI = "persist.radio.imei";

    private static String macAddress;
    private static String mImeiId;
    private static String sDeviceMD5Id;

    public static String getAndroidId() {
        return Settings.Secure.getString(Contexts.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getImeiId() {
        return getDeviceId();
    }

    public static ArrayList<String> getIMEI() {
        ArrayList<String> results = new ArrayList<String>();
        String property;

        if (!TextUtils.isEmpty(property = WLReflect.getSystemProperties(PERSIST_RADIO_IMEI))) {
            results.add(property);
            return results;
        }

        if (!TextUtils.isEmpty(property = WLReflect.getSystemProperties(PERSIST_RADIO_IMEI1))) {
            results.add(property);
            return results;
        }

        if (!TextUtils.isEmpty(property = WLReflect.getSystemProperties(PERSIST_RADIO_IMEI2))) {
            results.add(property);
            return results;
        }


        if (!TextUtils.isEmpty(property = WLReflect.getSystemProperties(PERSIST_RADIO_MEID))) {
            results.add(property);
            return results;
        }

        if (!TextUtils.isEmpty(property = WLReflect.getSystemProperties("ro.ril.miui.imei"))) {
            results.add(property);
            return results;
        }

        if (!TextUtils.isEmpty(property = WLReflect.getSystemProperties("ro.ril.miui.imei.0"))) {
            results.add(property);
            return results;
        }
        if (!TextUtils.isEmpty(property = WLReflect.getSystemProperties("ro.ril.miui.imei.1"))) {
            results.add(property);
            return results;
        }

        if (!TextUtils.isEmpty(property = WLReflect.getSystemProperties("ro.ril.oem.imei"))) {
            results.add(property);
            return results;
        }

        if (!TextUtils.isEmpty(property = WLReflect.getSystemProperties("ro.ril.oem.imei1"))) {
            results.add(property);
            return results;
        }

        return results;
    }

    public static String getDeviceId() {
        String newId = null;

        TelephonyManager tm = (TelephonyManager) Contexts.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            try {
                newId = tm.getDeviceId();
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }

        if (TextUtils.isEmpty(newId) && !TextUtils.isEmpty(mImeiId)) {
            return mImeiId;
        } else if (!TextUtils.isEmpty(newId) && TextUtils.equals(newId, mImeiId)) {
            return mImeiId;
        } else {
            String oldId = SettingPrefs.getInstance().getString("last_valid_device_id", null);
            if (TextUtils.isEmpty(oldId)) {
                try {
                    oldId = Settings.System.getString(Contexts.getContext().getContentResolver(), "last_valid_device_id");
                } catch (Exception var5) {
                }

                if (!TextUtils.isEmpty(oldId)) {
                    SettingPrefs.getInstance().putString("last_valid_device_id", oldId);
                }
            }

            if (TextUtils.isEmpty(newId)) {
                newId = oldId;
            } else if (!TextUtils.equals(newId, oldId)) {
                SettingPrefs.getInstance().putString("last_valid_device_id", oldId);
            }

            mImeiId = newId;

            if (TextUtils.isEmpty(mImeiId)) {
                ArrayList<String> ss = getIMEI();
                if (ss.size() > 0) {
                    mImeiId = ss.get(0);

                    SettingPrefs.getInstance().putString("last_valid_device_id", oldId);
                }
            }
            return mImeiId;
        }
    }

    @SuppressLint("WifiManagerLeak")
    public static String getMacAddress() {
        if (TextUtils.isEmpty(macAddress)) {
            WifiManager wifiManager = (WifiManager) Contexts.getContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                macAddress = wifiInfo.getMacAddress();
                return macAddress;
            }
        }
        return macAddress;
    }

    public static String getUid() {
        String imei = getImeiId();
        if (!TextUtils.isEmpty(imei)) {
            return imei;
        } else if (!TextUtils.isEmpty(getMacAddress())) {
            return getMacAddress();
        } else if (!TextUtils.isEmpty(getAndroidId())) {
            return getAndroidId();
        }
        return null;
    }
}
