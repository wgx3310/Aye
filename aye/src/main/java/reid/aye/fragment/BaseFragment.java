package reid.aye.fragment;

import android.app.Fragment;
import android.os.Handler;

/**
 * Created by wgx33 on 2016/9/22.
 */

public abstract class BaseFragment extends Fragment {

    protected Handler mHandler = new Handler();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public abstract String getTitle();
}
