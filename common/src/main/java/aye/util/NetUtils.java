package aye.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import aye.content.Contexts;

/**
 * Created by reid on 2016/11/19.
 */

public class NetUtils {

    /**
     * 判断当前网络是否是免费网络
     */
    public static boolean isFreeNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Contexts.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null || connectivityManager.isActiveNetworkMetered()) return false;

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) return false;

        return true;
    }

    /**
     * 获取当前网络类型
     * @return  0: unknown;  1: wifi;  2: 2G;  3: 3G;  4: 4G
     */
    public static int getNetworkType(){
        int networkType = 0;
        try {
            ConnectivityManager connectiveManager = (ConnectivityManager) Contexts.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectiveManager == null) {
                return networkType;
            }

            NetworkInfo networkInfo = connectiveManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    networkType = 1;
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    switch (networkInfo.getSubtype()) {
                        case 1:  //NETWORK_TYPE_GPRS
                        case 2:  //NETWORK_TYPE_EDGE
                        case 4:  //NETWORK_TYPE_CDMA
                        case 7:  //NETWORK_TYPE_1xRTT
                        case 11: //NETWORK_TYPE_IDEN
                        case 16: //NETWORK_TYPE_GSM
                            networkType = 2;
                            break;
                        case 3:  //NETWORK_TYPE_UMTS
                        case 5:  //NETWORK_TYPE_EVDO_0
                        case 6:  //NETWORK_TYPE_EVDO_A
                        case 8:  //NETWORK_TYPE_HSDPA
                        case 9:  //NETWORK_TYPE_HSUPA
                        case 10: //NETWORK_TYPE_HSPA
                        case 12: //NETWORK_TYPE_EVDO_B
                        case 14: //NETWORK_TYPE_EHRPD
                        case 15: //NETWORK_TYPE_HSPAP
                        case 17: //NETWORK_TYPE_TD_SCDMA
                            networkType = 3;
                            break;
                        case 13: //NETWORK_TYPE_LTE
                        case 18: //NETWORK_TYPE_IWLAN
                        case 19: //NETWORK_TYPE_LTE_CA
                            networkType = 4;
                            break;
                        default:
                            networkType = 0;
                            break;
                    }
                }
            }
        }catch (Exception ne){
            ne.printStackTrace();
        }
        return networkType;
    }
}
