package aye.view.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import reid.aye.R;

/**
 * Created by reid on 2016/12/3.
 */

public class SliderView extends RelativeLayout {

    private static final int CYCLE_ANIMATION = 5000;

    private ViewPager mViewPager;
    private SliderIndicator mIndicator;
    private SliderAdapter mAdapter;

    private int mCycle = CYCLE_ANIMATION;
    private OnItemClickListener mOnItemClickListener;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean stop = true;

    public SliderView(Context context) {
        this(context, null);
    }

    public SliderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SliderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_slider_view, this);
        mViewPager = (ViewPager) findViewById(R.id.slider_pager);
        mViewPager.setOffscreenPageLimit(3);
        mAdapter = new SliderAdapter(getContext());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(mAdapter);

        mIndicator = (SliderIndicator) findViewById(R.id.slider_indicator);
        mIndicator.attachToViewPager(mViewPager);
    }

    public void setSlider(List<Slider> list) {
        if (list != null) {
            mAdapter.setData(list);
            if (list.size() > 1) {
                mViewPager.setCurrentItem(1, false);
            }
            startAnimation();
        }
    }

    /**
     * 开始滑动
     */
    public void startAnimation() {
        if (stop) {
            stop = false;
            mHandler.postDelayed(swipe, mCycle);
        }
    }

    /**
     * 结束滑动
     */
    public void stopAnimation() {
        mHandler.removeCallbacks(swipe);
        stop = true;
    }

    Runnable swipe = new Runnable() {
        @Override
        public void run() {
            mHandler.removeCallbacks(this);
            int count = mAdapter.getCount();
            if (!stop & count > 1) {
                int currentItem = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(++currentItem % count, true);
                mHandler.postDelayed(swipe, mCycle);
            } else {
                stopAnimation();
            }
        }
    };

    public void setCycle(int cycle) {
        if (cycle > 0) {
            mCycle = cycle;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean ret = super.dispatchTouchEvent(event);
        if (ret) {
            (mViewPager.getParent()).requestDisallowInterceptTouchEvent(true);
        }
        return ret;
    }

    public static class Slider {
        private String url;
        private String title;
        private String des;

        public Slider(String url, String title) {
            this.url = url;
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }

    private class SliderAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        private List<Slider> mData = new ArrayList<>();
        private List<ImageView> mImage = new ArrayList<>();
        private Context mContext;

        public SliderAdapter(Context context) {
            mContext = context;
        }

        public void setData(List<Slider> data) {
            mData.clear();
            if (data != null && !data.isEmpty()) {
                mData.addAll(data);
                initImageViews(data);
            }
            notifyDataSetChanged();
        }

        private void initImageViews(List<Slider> data) {
            mImage.clear();
            if (data == null || data.isEmpty()) return;

            ImageView view = new ImageView(mContext);
            view.setTag(R.integer.slider_image_id, data.size() - 1);
            view.setOnClickListener(mOnClickListener);
            mImage.add(view);

            if (data.size() > 1) {

                for (int i = 0; i < data.size(); i++) {
                    ImageView v = new ImageView(mContext);
                    v.setTag(R.integer.slider_image_id, i);
                    v.setOnClickListener(mOnClickListener);
                    mImage.add(v);
                }

                ImageView iv = new ImageView(mContext);
                iv.setTag(R.integer.slider_image_id, 0);
                iv.setOnClickListener(mOnClickListener);
                mImage.add(iv);
            }

        }

        @Override
        public int getCount() {
            return mImage.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = mImage.get(position);
            RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            Slider slider = getItem(position);
            if (slider != null && !TextUtils.isEmpty(slider.getUrl())) {
                Glide.with(mContext).load(slider.getUrl()).centerCrop().into(imageView);
            }
            container.addView(imageView, params);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        Slider getItem(int position) {
            if (position >= 0 && position < mImage.size()) {
                int truePos = (position - 1 + mData.size()) % mData.size();
                return mData.get(truePos);
            }
            return null;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (mImage.size() > 1) { //多于1，才会循环跳转
                if (position < 1) { //首位之前，跳转到末尾（N）
                    position = mData.size(); //注意这里是mList，而不是mViews
                    mViewPager.setCurrentItem(position, false);
                } else if (position > mData.size()) { //末位之后，跳转到首位（1）
                    mViewPager.setCurrentItem(1, false); //false:不显示跳转过程的动画
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                stopAnimation();
            } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                startAnimation();
            }
        }
    }

    /**
     * 每个ImageView点击回调接口
     */
    OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = (Integer) view.getTag(R.integer.slider_image_id);
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, pos);
            }
        }
    };

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
