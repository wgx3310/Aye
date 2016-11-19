package aye.adapter;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import aye.fragment.BaseFragment;

/**
 * Created by reid on 2016/9/22.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mFragments = new ArrayList<>();
    public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        mFragments.clear();
        if (list != null){
            mFragments.addAll(list);
        }
    }

    @Override
    public BaseFragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getItem(position).getArguments().getString("title");
    }
}
