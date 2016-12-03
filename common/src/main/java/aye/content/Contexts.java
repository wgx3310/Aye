package aye.content;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by reid on 2016/9/20.
 */

public class Contexts {
    private static Context mAppContext;

    public static void init(Context context){
        mAppContext = context.getApplicationContext();
    }

    public static Context getContext(){
        return mAppContext;
    }

    public static int getDimensionPixelOffset(int resId){
        return getContext().getResources().getDimensionPixelOffset(resId);
    }

    public static int dp2px(float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }
}
