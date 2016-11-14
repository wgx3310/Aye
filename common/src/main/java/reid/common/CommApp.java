package reid.common;

import android.app.Application;

import reid.common.content.Contexts;

/**
 * Created by wgx33 on 2016/11/14.
 */

public class CommApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Contexts.init(getApplicationContext());
    }
}
