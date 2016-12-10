package aye.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import aye.adapter.RecyclerAdapter;
import aye.common.OnItemClickListener;
import aye.model.Block;
import aye.model.DisplayItem;
import aye.net.DataLoader;
import aye.util.ToastUtils;
import reid.aye.R;
import reid.recycler.RecyclerList;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by reid on 2016/9/22.
 */

public class RecyclerFragment extends BaseFragment {

    private RecyclerList mRecyclerList;
    private RecyclerAdapter mAdapter;
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

        mRecyclerList = (RecyclerList) root.findViewById(R.id.recycler_list);
        mAdapter = new RecyclerAdapter();
        mRecyclerList.setAdapter(mAdapter);

        mRecyclerList.setRefreshingColorResources(android.R.color.holo_orange_light, android.R
                .color.holo_blue_light, android.R.color.holo_green_light, android.R.color
                .holo_red_light);
        mRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        mRecyclerList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                ToastUtils.show("onItemClick " + vh.getAdapterPosition() + " - " + mAdapter.getItem
                        (vh.getAdapterPosition()).id);
            }
        });
        loadData();
    }

    private void loadData() {
        Observable<Block<DisplayItem>> observable = DataLoader.loadData(mTitle);
        if (observable != null) {
            Subscription subscribe = observable.subscribe(new Action1<Block<DisplayItem>>() {
                @Override
                public void call(Block<DisplayItem> block) {
                    mAdapter.setData(block.blocks);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    ToastUtils.show("加载数据失败 ");
                    Log.e("TAG", "loadData " + throwable.getMessage());
                    mRecyclerList.setRefreshing(false);
                }
            });
            addSubscription(subscribe);
        } else {
            ToastUtils.show("加载数据失败");
            mRecyclerList.setRefreshing(false);
        }
    }
}
