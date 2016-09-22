package reid.aye;

import android.app.Application;

import reid.common.content.Contexts;
import reid.common.net.Https;

/**
 * Created by wgx33 on 2016/9/10.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Contexts.init(getApplicationContext());
        Https.init();
    }
}
