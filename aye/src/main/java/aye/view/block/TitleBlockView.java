package aye.view.block;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import aye.model.DisplayItem;
import aye.view.BaseCardView;
import reid.aye.R;

/**
 * Created by reid on 2016/12/4.
 */

public class TitleBlockView extends BaseCardView<DisplayItem> {

    private TextView mTitleView;
    private View mDivider;

    public TitleBlockView(Context context) {
        super(context);
    }

    @Override
    protected void onBindData(DisplayItem data) {
        mTitleView.setText(Html.fromHtml(data.title));
        setDividerVisible(data.ui.divider() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onInitView() {
        mTitleView = (TextView) findViewById(R.id.card_title);
        mDivider = findViewById(R.id.divider_line);
    }

    public void setDividerVisible(int visibility) {
        mDivider.setVisibility(visibility);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_card_title;
    }
}
