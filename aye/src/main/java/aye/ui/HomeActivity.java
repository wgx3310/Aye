package aye.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import aye.fragment.BaseFragment;
import aye.fragment.HistoryFragment;
import aye.fragment.ToolsFragment;
import aye.util.ToastUtils;
import reid.aye.R;
import aye.fragment.HomeFragment;

public class HomeActivity extends BaseActivity implements NavigationView
        .OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavView;
    private Toolbar mToolbar;

    private long mLastBackTime;

    private BaseFragment mHomeFragment;
    private BaseFragment mToolsFragment;
    private BaseFragment mHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        initDrawer();
        initNavigation();

        initFragment();
        switchFragment(R.id.content_layout, mHomeFragment);
    }

    private void initFragment() {
        mHomeFragment = HomeFragment.newInstance();
        mToolsFragment = ToolsFragment.newInstance();
        mHistoryFragment = HistoryFragment.newInstance();
    }

    /**
     * init navigation view
     */
    private void initNavigation() {
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mNavView.setNavigationItemSelectedListener(this);
        mNavView.setCheckedItem(R.id.nav_news);
        setTitle(R.string.nav_news);
    }

    /**
     * init drawer layout and drawer toggle
     */
    private void initDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string
                .navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mHandler.post(() -> mToggle.syncState());
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else if (System.currentTimeMillis() - mLastBackTime > 2000) {
            ToastUtils.show(R.string.w_exit_app);
            mLastBackTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_news:
                setTitle(R.string.nav_news);
                switchFragment(R.id.content_layout, mHomeFragment);
                break;
            case R.id.nav_camera:
                break;
            case R.id.nav_tools:
                setTitle(R.string.nav_tools);
                switchFragment(R.id.content_layout, mToolsFragment);
                break;
            case R.id.nav_history:
                setTitle(R.string.nav_history);
                switchFragment(R.id.content_layout, mHistoryFragment);
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_setting:
                Intent settingIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingIntent);
                break;
        }
        mHandler.postDelayed(() -> mDrawer.closeDrawer(GravityCompat.START), 200);

        return true;
    }
}
