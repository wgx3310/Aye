package reid.common.util;

import android.text.TextUtils;
import android.util.Log;

import reid.common.Constants;

/**
 * Created by wgx33 on 2016/9/22.
 */

public class Logger {

    public static void v(String tag, String msg){
        if (Constants.DEBUG && !TextUtils.isEmpty(msg)){
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if (Constants.DEBUG && !TextUtils.isEmpty(msg)){
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if (Constants.DEBUG && !TextUtils.isEmpty(msg)){
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if (Constants.DEBUG && !TextUtils.isEmpty(msg)){
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if (Constants.DEBUG && !TextUtils.isEmpty(msg)){
            Log.e(tag, msg);
        }
    }
}
