package reid.aye.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wgx33 on 2016/9/22.
 */

public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    protected Handler mHandler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        initView(mRootView);
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public abstract String getTitle();
    protected abstract int getLayoutResId();
    protected abstract void initView(View root);
}
