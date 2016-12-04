package aye.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import aye.common.VH;
import aye.model.Block;
import aye.model.DisplayItem;
import aye.util.DisplayItemUtils;
import aye.view.BaseCardView;
import aye.view.ViewCreator;
import aye.ViewType;
import reid.recycler.RecyclerList;

/**
 * Created by reid on 2016/12/3.
 */

public class RecyclerAdapter extends RecyclerList.Adapter<Block<DisplayItem>, VH> {

    public RecyclerAdapter() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(ViewCreator.createCardView(parent.getContext(), viewType));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseCardView view = (BaseCardView) holder.itemView;
        view.bindData(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        return DisplayItemUtils.getUiId(getData().get(position));
    }

    @Override
    public int getSpanSize(int position) {
        return ViewType.getSpanSize(getItemViewType(position));
    }

    @Override
    public int getItemBottomPadding(int position) {
        return ViewType.getBottomPadding(getItemViewType(position));
    }
}
