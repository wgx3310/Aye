package aye.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import reid.aye.R;

/**
 * Created by reid on 2016/11/21.
 */

public class AngleImage extends ImageView {

    public static final int TYPE_ROUND = 0;
    public static final int TYPE_CIRCLE = 1;

    private static int DEFAULT_RADIUS = 10;

    private int mType;
    private int mRadius;
    private int mWidth;

    private Matrix mMatrix;
    private Paint mPaint;

    private RectF mRectF;
    private BitmapShader mBitmapShader;

    public AngleImage(Context context) {
        this(context, null);
    }

    public AngleImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AngleImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AngleImage);
        mType = array.getInt(R.styleable.AngleImage_type, TYPE_ROUND);

        int defRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_RADIUS, getResources().getDisplayMetrics());
        mRadius = array.getDimensionPixelOffset(R.styleable.AngleImage_radius, defRadius);
        array.recycle();

        init();
    }

    private void init() {
        mMatrix = new Matrix();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mType == TYPE_CIRCLE) {
            mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
            mRadius = mWidth / 2;
            setMeasuredDimension(mWidth, mWidth);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mType == TYPE_ROUND) {
            mRectF = new RectF(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (getDrawable() == null) return;

        setBitmapShader();
        if (mType == TYPE_ROUND) {
            canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
        } else {
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        }
    }

    private void setBitmapShader() {
        if (getDrawable() == null) return;

        Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        if (bitmap == null) return;

        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        int drawableWidth = bitmap.getWidth();
        int drawableHeight = bitmap.getHeight();
        float dx = 0, dy = 0;
        if (mType == TYPE_CIRCLE) {
            // 拿到bitmap宽或高的小值
            int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
            scale = mWidth * 1.0f / size;

        } else if (mType == TYPE_ROUND) {
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例
            // 缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值
            scale = Math.max(getWidth() * 1.0f / bitmap.getWidth(), getHeight()
                    * 1.0f / bitmap.getHeight());
        }

        if (drawableWidth * viewHeight > viewWidth * drawableHeight) {
            dx = (viewWidth - drawableWidth * scale) * 0.5f;
        } else {
            dy = (viewHeight - drawableHeight * scale) * 0.5f;
        }

        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        mMatrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));

        mBitmapShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mBitmapShader);
    }

    public void setType(int type){
        if (type < TYPE_ROUND || type > TYPE_CIRCLE){
            mType = TYPE_ROUND;
        }

        mType = type;
        invalidate();
    }

    public void setRoundRadius(int radius){
        if (radius < 0) return;

        if (mType != TYPE_ROUND) return;

        if (mRadius != radius){
            mRadius = radius;
            invalidate();
        }
    }
}
