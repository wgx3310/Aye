package aye.view.block;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import aye.ViewType;
import aye.common.VH;
import aye.model.Block;
import aye.model.DisplayItem;
import aye.util.DisplayItemUtils;
import aye.util.ToastUtils;
import aye.view.BaseCardView;
import aye.view.ViewCreator;
import reid.aye.R;
import reid.recycler.RecyclerList;

/**
 * Created by reid on 2016/12/4.
 * 包含子RecyclerView，绑定数据后必须重新计算高度
 */

public class PortBlockView extends BaseCardView<Block<DisplayItem>> {

    private RecyclerList mRecyclerList;
    private PortAdapter mAdapter;

    public PortBlockView(Context context) {
        super(context);
        setFocusable(false);
        setFocusableInTouchMode(false);
    }

    @Override
    protected void onBindData(Block<DisplayItem> data) {
        mAdapter.setData(data.items);
        resetRecyclerLayoutParams();
    }

    /**
     * 绑定数据源后重新计算高度，否则无法正常显示
     */
    private void resetRecyclerLayoutParams() {
        ViewGroup.LayoutParams layoutParams = mRecyclerList.getLayoutParams();
        layoutParams.height = calculateRecyclerHeight();
        mRecyclerList.setLayoutParams(layoutParams);
    }

    /**
     * 计算当前RecyclerView的高度
     *
     * @return RecyclerView 高度
     */
    private int calculateRecyclerHeight() {
        if (data.items == null || data.items.isEmpty()) return 0;

        int height = 0;

        for (int i = 0; i < data.items.size(); i++) {
            DisplayItem displayItem = data.items.get(i);
            GridLayoutManager.SpanSizeLookup spanSizeLookup = mRecyclerList.getSpanSizeLookup();
            int spanIndex = spanSizeLookup.getSpanIndex(i, mRecyclerList.getSpanCount());
            if (spanIndex != 0) {
                continue;
            }

            height += ViewType.getHeight(DisplayItemUtils.getUiId(displayItem));
        }
        return height;
    }

    @Override
    protected void onInitView() {
        mRecyclerList = (RecyclerList) findViewById(R.id.port_recycler);
        mRecyclerList.setHasFixedSize(true);
        //设置该项防止与父RecyclerView下拉刷新冲突
        mRecyclerList.setNestedScrollingEnabled(false);
        //设置不可获取焦点，防止返回该页面时发生滚动
        mRecyclerList.setFocusableInTouchMode(false);
        mRecyclerList.setFocusable(false);

        mAdapter = new PortAdapter();
        mRecyclerList.setAdapter(mAdapter);
        mRecyclerList.setOnItemClickListener((vh)->{
            ToastUtils.show("PortBlockView onItemClick " + vh.getAdapterPosition());
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_card_port;
    }

    @Override
    public boolean isClickable() {
        return true;
    }

    public class PortAdapter extends RecyclerList.Adapter<DisplayItem, VH> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(ViewCreator.createCardView(parent.getContext(), viewType));
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            BaseCardView view = (BaseCardView) holder.itemView;
            view.bindData(getItem(position));
        }

        @Override
        public int getItemViewType(int position) {
            return DisplayItemUtils.getUiId(getData().get(position));
        }

        @Override
        public int getItemBottomPadding(int position) {
            return 0;
        }

        @Override
        public int getSpanSize(int position) {
            return ViewType.getSpanSize(getItemViewType(position));
        }
    }

}
