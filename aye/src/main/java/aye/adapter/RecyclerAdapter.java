package aye.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import aye.model.Block;
import aye.model.DisplayItem;
import aye.view.BaseCardView;
import aye.view.ViewCreator;
import aye.view.ViewType;
import reid.recycler.RecyclerList;

/**
 * Created by reid on 2016/12/3.
 */

public class RecyclerAdapter extends RecyclerList.Adapter<Block<DisplayItem>, RecyclerAdapter.VH> {

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
        return getData().get(position).ui.id();
    }

    @Override
    public int getSpanSize(int position) {
        //for test
        if (getItemViewType(position) > 1){
            return 4;
        }

        return ViewType.getSpanSize(getItemViewType(position));
    }

    @Override
    public int getItemBottomPadding(int position) {
        return ViewType.getBottomPadding(getItemViewType(position));
    }

    public class VH extends RecyclerView.ViewHolder {
        public VH(View itemView) {
            super(itemView);
        }
    }
}
