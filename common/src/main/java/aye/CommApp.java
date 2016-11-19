package aye;

import android.app.Application;

import aye.content.Contexts;
import aye.net.Https;

/**
 * Created by reid on 2016/11/14.
 */

public class CommApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Contexts.init(getApplicationContext());

        Https.init();
    }
}
