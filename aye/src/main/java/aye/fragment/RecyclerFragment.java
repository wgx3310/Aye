package aye.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import aye.adapter.BlockAdapter;
import aye.model.Block;
import aye.model.DisplayItem;
import aye.net.DataLoader;
import aye.util.ToastUtils;
import reid.aye.R;
import rx.Observable;
import rx.Subscription;

/**
 * Created by reid on 2016/9/22.
 */

public class RecyclerFragment extends BaseFragment {

    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;

    private String mTitle;

    public static RecyclerFragment newInstance(String title) {
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

        initSwipeLayout();
        initRecyclerView();
        loadData();
    }

    private void initSwipeLayout() {
        mSwipeLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipe_layout);
        mSwipeLayout.setOnRefreshListener(() -> {

        });
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        BlockAdapter adapter = new BlockAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    private void loadData() {
        Observable<Block<DisplayItem>> observable = DataLoader.loadData(mTitle);
        if (observable != null) {
            Subscription subscribe = observable.subscribe(d -> {

            }, t -> ToastUtils.show("加载数据失败"));
            addSubscription(subscribe);
        } else {
            ToastUtils.show("加载数据失败");
        }
    }
}
