package aye.content;

import android.content.Context;

/**
 * Created by wgx33 on 2016/9/20.
 */

public class Contexts {
    private static Context mAppContext;

    public static void init(Context context){
        mAppContext = context;
    }

    public static Context getContext(){
        return mAppContext;
    }
}
