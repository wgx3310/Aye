package aye.view.block;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import aye.model.Block;
import aye.model.DisplayItem;
import aye.util.ToastUtils;
import aye.view.BaseCardView;
import aye.view.widget.SliderView;
import reid.aye.R;

/**
 * Created by reid on 2016/12/3.
 */

public class SliderCardView extends BaseCardView<Block<DisplayItem>> {

    private SliderView mSliderView;
    public SliderCardView(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_card_slider;
    }

    @Override
    protected void onInitView() {
        mSliderView = (SliderView) findViewById(R.id.slider_view);
        mSliderView.setCycle(5000);
        mSliderView.setOnItemClickListener((v, pos)->{

            ToastUtils.show("click image " + pos);
        });
    }

    @Override
    protected void onBindData(Block<DisplayItem> data) {
        List<DisplayItem> items = data.items;
        List<SliderView.Slider> sliders = new ArrayList<>();
        for (DisplayItem item : items){
            SliderView.Slider slider = new SliderView.Slider(item.images.poster().url, item.title);
            sliders.add(slider);
        }
        mSliderView.setSlider(sliders);
    }
}
