package aye;

import aye.net.Https;

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
