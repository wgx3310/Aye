package aye.view.block;

import android.content.Context;
import android.widget.TextView;

import aye.model.DisplayItem;
import aye.view.BaseCardView;
import reid.aye.R;

/**
 * Created by reid on 2016/12/10.
 */

public class HistoryBlockView extends BaseCardView {

    private TextView mHistoryDate;
    private TextView mHistoryDesc;

    public HistoryBlockView(Context context) {
        super(context);
    }

    @Override
    protected void onBindData(DisplayItem data) {
        mHistoryDate.setText(data.title);
        mHistoryDesc.setText(data.desc);
    }

    @Override
    protected void onInitView() {
        mHistoryDate = (TextView) findViewById(R.id.history_date);
        mHistoryDesc = (TextView) findViewById(R.id.history_desc);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_card_history;
    }
}
