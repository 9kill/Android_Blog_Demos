package com.szy.blogcode.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2016/5/30 18:06
 */
public class Qview extends View {
    private Paint mPaint;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mRadius;
    private Path mPath=new Path();
    private Path mLinePath;

    public Qview(Context context) {
        super(context);
        init();
    }

    public Qview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Qview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mLinePath = new Path();

    }
    private float mAnimatorPercent;
    private int mStart;
    private int mEnd;
    private int length;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void progress(int start, int end, long duration){
        mStart=start;
        mEnd=end;
        length=mEnd-mStart;
        ValueAnimator animator=ValueAnimator.ofFloat(0f,1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorPercent= (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(duration);
        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mScreenWidth=getMeasuredWidth();
        mScreenHeight=getMeasuredHeight();
        mRadius= (int) (Math.min(mScreenHeight,mScreenWidth)*0.65);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        //draw line
//        canvas.drawLine(100f,100f,200f,200f,mPaint);
        //draw sanjiao
//        mLinePath.moveTo(200f,200f);
//        mLinePath.lineTo(300f,200f);
//        mLinePath.lineTo(200f,300f);
//        mLinePath.close();
//        canvas.drawPath(mLinePath,mPaint);
        if (mStart > 0) {
            canvas.drawLine(mStart,200f,mStart+length*mAnimatorPercent,200f,mPaint);
//            mStart+=length*mAnimatorPercent;
        }

    }
}
