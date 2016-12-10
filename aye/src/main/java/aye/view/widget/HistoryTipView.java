package aye.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import reid.aye.R;

/**
 * Created by reid on 2016/12/10.
 */

public class HistoryTipView extends View {

    private int mMinWidth;
    private int mMinHeight;
    private int mRadius;

    private Paint mPaint;
    private Paint mStrokePaint;
    private Paint mDotLinePaint;

    public HistoryTipView(Context context) {
        this(context, null);
    }

    public HistoryTipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistoryTipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mMinWidth = getResources().getDimensionPixelOffset(R.dimen.size_60);
        mMinHeight = getResources().getDimensionPixelOffset(R.dimen.size_60);

        mRadius = getResources().getDimensionPixelOffset(R.dimen.size_18);

        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);

        mStrokePaint = new Paint();
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setColor(Color.RED);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(3);

        mDotLinePaint = new Paint();
        mDotLinePaint.setAntiAlias(true);
        mDotLinePaint.setColor(Color.RED);
        mDotLinePaint.setStyle(Paint.Style.STROKE);
        mDotLinePaint.setStrokeWidth(4);
        mDotLinePaint.setPathEffect(new DashPathEffect(new float[]{5, 4}, 0));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSize < mMinWidth) {
            widthSize = mMinWidth;
        }

        if (heightSize < mMinHeight) {
            heightSize = mMinHeight;
        }

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(getWidth() / 2, 0, getWidth()/2, getHeight() / 5, mStrokePaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 5 + mRadius, mRadius, mStrokePaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 5 + mRadius, mRadius * 2 / 3, mPaint);
        canvas.drawLine(getWidth() / 2, getHeight() / 5 + 2 * mRadius, getWidth()/2, getHeight(),
                mStrokePaint);

        Path path = new Path();
        path.moveTo(getWidth()/2+mRadius, getHeight()/5+mRadius);
        path.lineTo(getWidth(), getHeight()/5+mRadius);
        canvas.drawPath(path, mDotLinePaint);
    }
}
