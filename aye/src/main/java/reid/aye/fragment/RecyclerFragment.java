package reid.aye.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import reid.aye.R;

/**
 * Created by wgx33 on 2016/9/22.
 */

public class RecyclerFragment extends BaseFragment {

    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;

    private String mTitle;

    public static RecyclerFragment newInstance(String title){
        RecyclerFragment fragment = new RecyclerFragment();
        Bundle data = new Bundle(1);
        data.putString("title", title);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString("title");
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_recycler;
    }

    @Override
    protected void initView(View root) {
        mSwipeLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_layout);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
    }
}
