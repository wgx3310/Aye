package reid.common.util;


import reid.common.content.Contexts;

/**
 * Created by wgx33 on 2016/9/27.
 */

public class DisplayUtils {
    public static int getScreenWidth(){
        return Contexts.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(){
        return Contexts.getContext().getResources().getDisplayMetrics().heightPixels;
    }


}
