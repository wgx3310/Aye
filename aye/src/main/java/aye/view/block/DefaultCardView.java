package aye.view.block;

import android.content.Context;

import aye.model.Block;
import aye.model.DisplayItem;
import aye.view.BaseCardView;
import reid.aye.R;

/**
 * Created by reid on 2016/12/3.
 */

public class DefaultCardView extends BaseCardView<Block<DisplayItem>> {

    public DefaultCardView(Context context) {
        super(context);
    }

    @Override
    protected void onBindData(Block<DisplayItem> data) {

    }

    @Override
    protected void onInitView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_card_default;
    }
}
