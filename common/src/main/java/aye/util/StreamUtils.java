package aye.util;

import java.io.Closeable;
import java.net.HttpURLConnection;

/**
 * Created by reid on 2016/11/20.
 */

public class StreamUtils {
    public static void closeSafely(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void disconnectSafely(HttpURLConnection connect){
        if(connect != null){
            try {
                connect.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
