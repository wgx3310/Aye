package aye.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import reid.aye.R;
import aye.adapter.ViewPagerAdapter;

/**
 * Created by wgx33 on 2016/9/27.
 */

public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View root) {
        initViewPager();
        initTabLayout();
    }

    private void initViewPager() {
        mViewPager = (ViewPager) mRootView.findViewById(R.id.view_pager);
        mAdapter = new ViewPagerAdapter(getFragmentManager(), initFragmentList());
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        mViewPager.setAdapter(mAdapter);
    }

    private List<BaseFragment> initFragmentList() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(RecyclerFragment.newInstance("Tab1"));
        fragmentList.add(RecyclerFragment.newInstance("Tab2"));
        fragmentList.add(RecyclerFragment.newInstance("Tab3"));
        fragmentList.add(RecyclerFragment.newInstance("Tab4"));
        fragmentList.add(RecyclerFragment.newInstance("Tab5"));
        return fragmentList;
    }

    private void initTabLayout() {
        mTabLayout = (TabLayout) mRootView.findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager, true);
    }
}
