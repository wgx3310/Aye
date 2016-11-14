package reid.aye;

import reid.common.CommApp;
import reid.common.net.Https;

/**
 * Created by wgx33 on 2016/9/10.
 */

public class App extends CommApp {

    @Override
    public void onCreate() {
        super.onCreate();

        Https.init();
    }
}
