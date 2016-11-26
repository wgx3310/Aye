package aye.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aye.ui.BaseActivity;
import rx.Subscription;

/**
 * Created by reid on 2016/9/22.
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

    public String getTitle(){
        return getClass().getSimpleName();
    }

    protected abstract int getLayoutResId();
    protected abstract void initView(View root);

    protected void addSubscription(Subscription s){
        if (getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).addSubscription(s);
        }
    }
}
