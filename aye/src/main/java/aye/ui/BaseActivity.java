package aye.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import aye.fragment.BaseFragment;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by reid on 2016/9/10.
 */

public class BaseActivity extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName();
    protected Handler mHandler = new Handler();
    private CompositeSubscription mCompositeSubscription;

    //当前Fragment
    protected BaseFragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        //取消所有的网络请求
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void addSubscription(Subscription s) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(s);
    }

    /**
     * 切换Fragment
     * @param layout layout id
     * @param to 要显示的Fragment
     */
    protected void switchFragment(int layout, BaseFragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment != null) {
            if (mCurrentFragment == to) return;

            if (to.isAdded()) {
                transaction.hide(mCurrentFragment).show(to).commitAllowingStateLoss();
            } else {
                transaction.hide(mCurrentFragment).add(layout, to).commitAllowingStateLoss();
            }
        } else {
            if (to.isAdded()) {
                transaction.show(to).commitAllowingStateLoss();
            } else {
                transaction.add(layout, to).commitAllowingStateLoss();
            }
        }

        mCurrentFragment = to;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
