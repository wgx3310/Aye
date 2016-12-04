package aye.view.block;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import aye.model.DisplayItem;
import aye.util.DisplayItemUtils;
import aye.view.BaseCardView;
import reid.aye.R;

/**
 * Created by reid on 2016/12/4.
 */

public class CardShortBlockView extends BaseCardView<DisplayItem> {

    private ImageView mImageView;
    private TextView mTitleView;

    public CardShortBlockView(Context context) {
        super(context);
    }

    @Override
    protected void onBindData(DisplayItem data) {
        String postUrl = DisplayItemUtils.getPostUrl(data);
        if (!TextUtils.isEmpty(postUrl)) {
            Glide.with(getContext()).load(postUrl).centerCrop().into(mImageView);
        }
        mTitleView.setText(data.title);
    }

    @Override
    protected void onInitView() {
        mImageView = (ImageView) findViewById(R.id.card_image);
        mTitleView = (TextView) findViewById(R.id.card_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_card_short;
    }
}
