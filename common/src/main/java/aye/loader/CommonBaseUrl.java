package aye.loader;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import aye.Constants;
import aye.content.Contexts;
import aye.util.AccountUtils;
import aye.util.NetUtils;
import aye.util.Utils;

/**
 * Created by reid on 2016/11/19.
 */

public class CommonBaseUrl {

    public static final Context mContext = Contexts.getContext();
    private static CommonBaseUrl mInstance;

    private CommonBaseUrl() {

    }

    public static CommonBaseUrl getInstance() {
        if (mInstance == null) {
            synchronized (CommonBaseUrl.class) {
                if (mInstance == null) {
                    mInstance = new CommonBaseUrl();
                }
            }
        }
        return mInstance;
    }

    public static int versionCode;
    public static String versionName;
    public static String resolution;

    public String addCommonParams(String url) {
        if (TextUtils.isEmpty(url)) return null;

        CommonUrlBuilder urlBuilder = new CommonUrlBuilder(url);
        urlBuilder.put("_locale", getLocale());
        urlBuilder.put("_res", getResolution());
        urlBuilder.put("_model", Build.DEVICE);
        urlBuilder.put("_miuiver", Build.VERSION.INCREMENTAL);
        urlBuilder.put("_model", Build.MODEL);
        urlBuilder.put("_andver", String.valueOf(Build.VERSION.SDK_INT));
        urlBuilder.put("_appver", String.valueOf(getVersionCode()));
        urlBuilder.put("_ts", String.valueOf(System.currentTimeMillis() / 1000));
        urlBuilder.put("_ver", getVersionName());
        urlBuilder.put("_eimi", Utils.getMD5(AccountUtils.getImeiId()));
        urlBuilder.put("_dimac", Utils.getMD5(AccountUtils.getMacAddress()));
        urlBuilder.put("_diand", Utils.getMD5(AccountUtils.getAndroidId()));
        urlBuilder.put("_diu", Utils.getMD5(AccountUtils.getUid()));
        urlBuilder.put("_nettype", String.valueOf(NetUtils.getNetworkType()));

        String tmpUrl = urlBuilder.toUrl();
        String path;
        try {
            path = new URL(tmpUrl).getPath();
        } catch (MalformedURLException e) {
            return tmpUrl;
        }

        int indexOfPath = tmpUrl.indexOf(path);
        String strForSign = tmpUrl.substring(indexOfPath);
        String sign = genSignature(strForSign);

        urlBuilder.put("opaque", sign);

        return urlBuilder.toUrl();
    }

    protected String genSignature(String str) {
        String opaque = null;
        try {
            opaque = Utils.getSignature(str.getBytes(), Constants.API_KEY.getBytes());
        } catch (InvalidKeyException e) {
            Log.e("InvalidKeyException", "InvalidKeyException");
        } catch (NoSuchAlgorithmException e) {
            Log.e("Exception", "NoSuchAlgorithmException");
        }
        return opaque;
    }

    protected String getLocale() {
        Locale locale = mContext.getResources().getConfiguration().locale;
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    public static int getVersionCode() {
        if (versionCode > 0) {
            return versionCode;
        }

        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getVersionName() {
        if (!TextUtils.isEmpty(versionName)) {
            return versionName;
        }

        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getResolution() {
        if (TextUtils.isEmpty(resolution)) {
            int screenWidth = Utils.getScreenWidth();
            switch (screenWidth){
                case 720:
                    resolution = "hd720";
                    break;
                case 1080:
                    resolution = "hd1080";
                    break;
                case 1440:
                    resolution = "hd1440";
                    break;
                case 2160:
                    resolution = "hd2160";
                    break;
                default:
                    resolution = screenWidth + "x" + Utils.getScreenHeight();
                    break;
            }
        }

        return resolution;
    }
}
