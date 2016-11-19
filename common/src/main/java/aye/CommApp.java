package aye;

import android.app.Application;

import aye.content.Contexts;
import aye.net.Https;
import aye.util.FontUtils;

/**
 * Created by reid on 2016/11/14.
 */

public class CommApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Contexts.init(getApplicationContext());

        //设置系统默认字体
        FontUtils.resetTypefaceDefaultFont("SERIF", getApplicationContext(), "fonts/default.otf");
        Https.init();
    }
}
