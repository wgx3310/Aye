package aye.view.block;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import aye.adapter.RecyclerAdapter;
import aye.model.Block;
import aye.model.DisplayItem;
import aye.view.BaseCardView;
import aye.view.ViewCreator;
import aye.view.ViewType;
import reid.aye.R;
import reid.recycler.RecyclerList;

/**
 * Created by reid on 2016/12/4.
 */

public class PortBlockView extends BaseCardView<Block<DisplayItem>> {

    private RecyclerList mRecyclerList;
    private PortAdpater mAdapter;

    public PortBlockView(Context context) {
        super(context);
    }

    @Override
    protected void onBindData(Block<DisplayItem> data) {
        mAdapter.setData(data.items);
    }

    @Override
    protected void onInitView() {
        mRecyclerList = (RecyclerList) findViewById(R.id.port_recycler);
        mAdapter = new PortAdpater();
        mRecyclerList.setAdapter(mAdapter);
        mRecyclerList.setOnItemClickListener(new RecyclerList.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView rv, RecyclerView.ViewHolder vh) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_card_port;
    }

    private class PortAdpater extends RecyclerList.Adapter<DisplayItem, PortAdpater.VH>{

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
        public int getItemBottomPadding(int position) {
            return 0;
        }

        @Override
        public int getSpanSize(int position) {
            return ViewType.getSpanSize(getItemViewType(position));
        }

        public class VH extends RecyclerView.ViewHolder {
            public VH(View itemView) {
                super(itemView);
            }
        }
    }
}
