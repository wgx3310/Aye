package aye.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import reid.aye.R;

/**
 * Created by reid on 2016/12/3.
 */

public class SliderIndicator extends View {

    private Bitmap mIndicatorNormal;
    private Bitmap mIndicatorSelected;

    private int mDivider;
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int mIndicatorCount;

    private int mMinHeight;
    private int mImageTop;

    private int mTrueCount;
    private int mCurrentPosition;

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private OnPageChangeListener mOnPageChangeListener;
    private OnAdapterChangeListener mOnAdapterChangeListener;
    private DataSetObserver mDataSetObserver;

    public SliderIndicator(Context context) {
        this(context, null);
    }

    public SliderIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SliderIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        mIndicatorNormal = BitmapFactory.decodeResource(getResources(), R.drawable
                .banner_indicate_normal);
        mIndicatorSelected = BitmapFactory.decodeResource(getResources(), R.drawable
                .banner_indicate_selected);

        mIndicatorNormal.setDensity(getResources().getDisplayMetrics().densityDpi);
        mIndicatorSelected.setDensity(getResources().getDisplayMetrics().densityDpi);

        mIndicatorWidth = mIndicatorNormal.getWidth();
        mIndicatorHeight = mIndicatorNormal.getHeight();

        mMinHeight = getResources().getDimensionPixelOffset(R.dimen.size_90);
        mDivider = getResources().getDimensionPixelOffset(R.dimen.size_21);
    }

    /**
     * 设置圆点个数
     *
     * @param count 圆点个数
     */
    public void setIndicatorCount(int count) {
        if (mViewPager != null) return;
        if (count > 1) {
            mIndicatorCount = count;
            invalidate();
        }
    }

    /**
     * 绑定ViewPager
     */
    public void attachToViewPager(ViewPager pager) {
        if (mViewPager != null) {
            if (mOnPageChangeListener != null) {
                mViewPager.removeOnPageChangeListener(mOnPageChangeListener);
            }
            if (mOnAdapterChangeListener != null) {
                mViewPager.removeOnAdapterChangeListener(mOnAdapterChangeListener);
            }
        }

        if (pager != null) {
            mViewPager = pager;

            if (mOnPageChangeListener == null) {
                mOnPageChangeListener = new OnPageChangeListener();
            }
            pager.addOnPageChangeListener(mOnPageChangeListener);

            PagerAdapter adapter = pager.getAdapter();
            if (adapter != null) {
                setPagerAdapter(adapter);
            }

            if (mOnAdapterChangeListener == null) {
                mOnAdapterChangeListener = new OnAdapterChangeListener();
            }
            pager.addOnAdapterChangeListener(mOnAdapterChangeListener);

            setCurrentPosition(pager.getCurrentItem());
        } else {
            mViewPager = null;
            setPagerAdapter(null);
        }
    }

    /**
     * 设置当前选中位置
     *
     * @param currentItem 当前位置
     */
    private void setCurrentPosition(int currentItem) {
        mCurrentPosition = currentItem;
        invalidate();
    }

    private void setPagerAdapter(PagerAdapter adapter) {
        if (mPagerAdapter != null && mDataSetObserver != null) {
            mPagerAdapter.unregisterDataSetObserver(mDataSetObserver);
        }

        mPagerAdapter = adapter;
        if (adapter != null) {
            if (mDataSetObserver == null) {
                mDataSetObserver = new DataSetObserver();
            }
            adapter.registerDataSetObserver(mDataSetObserver);
        }

        populateFromPagerAdapter();
    }

    private void populateFromPagerAdapter() {
        if (mPagerAdapter != null) {
            if (mPagerAdapter.getCount() > 1) {
                mTrueCount = mPagerAdapter.getCount() - 2;
            } else {
                mTrueCount = mPagerAdapter.getCount();
            }
        }
        mIndicatorCount = mPagerAdapter != null ? mTrueCount : 0;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            height = mMinHeight;
        }

        if (height < mMinHeight) {
            height = mMinHeight;
        }
        mImageTop = height / 2 - mIndicatorHeight / 2;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIndicatorCount > 1) {
            for (int i = 0; i < mIndicatorCount; i++) {
                Bitmap bitmap = i == mCurrentPosition ? mIndicatorSelected : mIndicatorNormal;

                int left = getWidth() / 2 - (mIndicatorCount / 2 - i) * (mIndicatorWidth +
                        mDivider) + (mIndicatorCount % 2 == 1 ? -mIndicatorWidth / 2 : mDivider /
                        2);
                canvas.drawBitmap(bitmap, left, mImageTop, null);
            }
        }
    }

    private class OnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            int truePos = (position - 1 + mTrueCount) % mTrueCount;
            if (truePos < mIndicatorCount) {
                mCurrentPosition = truePos;
                invalidate();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class OnAdapterChangeListener implements ViewPager.OnAdapterChangeListener {

        @Override
        public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter
                oldAdapter, @Nullable PagerAdapter newAdapter) {
            if (mViewPager == viewPager) {
                setPagerAdapter(newAdapter);
            }
        }
    }

    private class DataSetObserver extends android.database.DataSetObserver {
        @Override
        public void onChanged() {
            populateFromPagerAdapter();
        }

        @Override
        public void onInvalidated() {
            populateFromPagerAdapter();
        }
    }
}
