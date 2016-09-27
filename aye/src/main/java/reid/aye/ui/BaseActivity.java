package reid.aye.ui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wgx33 on 2016/9/10.
 */

public class BaseActivity extends AppCompatActivity {

    protected Handler mHandler = new Handler();



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
