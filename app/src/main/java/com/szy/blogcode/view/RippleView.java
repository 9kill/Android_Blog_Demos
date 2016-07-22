package com.szy.blogcode.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2016/7/18 15:47
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RippleView extends View implements ValueAnimator.AnimatorUpdateListener,ValueAnimator.AnimatorListener {
    private static final int mTextSize=dip2px(20);
    private Paint mInStrokePaint;
    private Paint mOutStrokePaint;
    private Paint mTextPaint;
    private int mRadius;
    private int mChangeRadius;
    private float mCx;
    private float mCy;
    private ValueAnimator mAnimation;
    private RippleListener mRippleListener;
    private float mFraction;

    public RippleView(Context context) {
        super(context);
        init();
    }

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mInStrokePaint = new Paint();
        mInStrokePaint.setStyle(Paint.Style.FILL);
        mInStrokePaint.setAntiAlias(true);
        mInStrokePaint.setColor(Color.RED);
        mInStrokePaint.setStrokeWidth(dip2px(1));

        mOutStrokePaint = new Paint();
        mOutStrokePaint.setStyle(Paint.Style.STROKE);
        mOutStrokePaint.setAntiAlias(true);
        mOutStrokePaint.setColor(Color.WHITE);
        mOutStrokePaint.setStrokeWidth(dip2px(1));

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(mTextSize);

        mRadius=dip2px(50);
        mChangeRadius=mRadius;

    }

    public static int dip2px(int dpValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCx=getMeasuredWidth()/2;
        mCy=getMeasuredHeight()/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
        canvas.drawCircle(mCx,mCy,mRadius,mInStrokePaint);
        if (mChangeRadius >= 1.5 * mRadius) {
            canvas.drawCircle(mCx,mCy,mChangeRadius-(mRadius/2),mOutStrokePaint);
            mOutStrokePaint.setAlpha((int) (255*(1-mFraction)));
        }
        if (mChangeRadius >= 2* mRadius) {
            canvas.drawCircle(mCx,mCy,mChangeRadius-mRadius,mOutStrokePaint);
        }
        mOutStrokePaint.setAlpha((int) (255*(1-mFraction)));
        String value=(int)(mFraction*100)+"%";
        canvas.drawText(value,mCx-mTextPaint.measureText(value)/2,mCy+mTextSize/2,mTextPaint);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void startRipple(){
        if (mAnimation == null) {
            mAnimation = new ValueAnimator();
            mAnimation.setIntValues(mRadius, (int) (mCx > mCy ? mCx * 1.4 : mCy * 1.4));
            mAnimation.setDuration(1500);
            mAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//            mAnimation.setRepeatCount(3);
            mAnimation.addUpdateListener(this);
            mAnimation.addListener(this);
            mAnimation.start();
        } else {
            if (!mAnimation.isRunning()) {
                mAnimation.start();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void stopRipple(){
        if (mAnimation != null) {
            mAnimation.end();
            mAnimation=null;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        mChangeRadius= (int) animation.getAnimatedValue();
        mFraction=animation.getAnimatedFraction();
        postInvalidate();
        if (mRippleListener != null) {
            mRippleListener.onRippleUpdate(animation);
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
       mChangeRadius=mRadius;
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    public interface RippleListener{
        public void onStart();
        public void onStop();
        /** 每一贞动画回调 */
        void onRippleUpdate(ValueAnimator animation);
    }

    /** 水波纹状态接口对象注入 */
    public void setRippleStateListener(RippleListener listener) {
        mRippleListener = listener;
    }
}
