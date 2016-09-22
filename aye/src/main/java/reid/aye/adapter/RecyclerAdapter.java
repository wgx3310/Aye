package reid.aye.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import reid.aye.fragment.BaseFragment;

/**
 * Created by wgx33 on 2016/9/22.
 */

public class RecyclerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mFragments = new ArrayList<>();
    public RecyclerAdapter(FragmentManager fm, List<BaseFragment> list) {
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
