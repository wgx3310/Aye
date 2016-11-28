package aye.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aye.model.Block;
import aye.model.DisplayItem;
import aye.net.DataLoader;
import aye.util.ToastUtils;
import reid.aye.R;
import reid.recycler.RecyclerList;
import rx.Observable;
import rx.Subscription;

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

        List<Block<DisplayItem>> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Block<DisplayItem> block = new Block<>();
            block.id = "pos:" + i;
            list.add(block);
        }
        mAdapter = new RecyclerAdapter(list);
        mRecyclerList.setAdapter(mAdapter);

        mRecyclerList.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        mRecyclerList.setOnRefreshListener(() -> loadData());
        mRecyclerList.setOnItemClickListener(new RecyclerList.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView rv, RecyclerView.ViewHolder vh) {
                ToastUtils.show("onItemClick " + vh.getAdapterPosition() + " - " + mAdapter.getItem(vh.getAdapterPosition()).id);
            }
        });
        loadData();
    }

    private void loadData() {
        Observable<Block<DisplayItem>> observable = DataLoader.loadData(mTitle);
        if (observable != null) {
            Subscription subscribe = observable.subscribe(d -> {
                mAdapter.addData(d.blocks);
            }, t -> ToastUtils.show("加载数据失败"));
            addSubscription(subscribe);
        } else {
            ToastUtils.show("加载数据失败");
        }
    }

    static class RecyclerAdapter extends RecyclerList.Adapter<Block<DisplayItem>, RecyclerAdapter.VH> {

        public RecyclerAdapter(List<Block<DisplayItem>> data) {
            super(data);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView view = new TextView(parent.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
            view.setLayoutParams(params);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Block<DisplayItem> item = getItem(position);
            ((TextView) holder.itemView).setText("This is TextView, Id is " + item.id);
        }

        public static class VH extends RecyclerView.ViewHolder {

            public VH(View itemView) {
                super(itemView);
            }
        }
    }
}
