package reid.aye;

import android.app.Application;
import android.content.Context;

import reid.common.content.Contexts;
import reid.common.net.Nets;

/**
 * Created by wgx33 on 2016/9/10.
 */

public class App extends Application {

    private static Context mAppContext;
    public static Context getAppContext(){
        return mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        Contexts.init(mAppContext);

        Nets.init();
    }
}
