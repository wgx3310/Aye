package reid.aye.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import reid.aye.R;
import reid.aye.adapter.RecyclerAdapter;
import reid.aye.fragment.BaseFragment;
import reid.aye.fragment.RecyclerFragment;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavView;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        initDrawer();
        initNavigation();
        initViewPager();
        initTabLayout();
    }

    private void initTabLayout() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager, true);
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new RecyclerAdapter(getFragmentManager(), initFragmentList());
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

    //init navigation view
    private void initNavigation() {
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mNavView.setNavigationItemSelectedListener(this);
    }

    //init drawer layout and drawer toggle
    private void initDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mHandler.post(() -> mToggle.syncState());
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mHandler.postDelayed(()->mDrawer.closeDrawer(GravityCompat.START), 200);

        return true;
    }
}
