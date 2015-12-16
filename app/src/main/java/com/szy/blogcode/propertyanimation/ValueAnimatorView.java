package com.szy.blogcode.propertyanimation;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2015/12/15 14:57
 */
public class ValueAnimatorView extends View {
    private static final int RADIUS=50;

    private Point mCurrentPoint;

    private Paint mPaint;

    private Context mContext;

    public ValueAnimatorView(Context context) {
        super(context);
        init(context);
    }

    public ValueAnimatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext=context;
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurrentPoint == null) {
            mCurrentPoint=new Point(RADIUS,RADIUS);
            drawCircle(canvas);
            startSlideAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x=mCurrentPoint.x;
        float y=mCurrentPoint.y;
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void startSlideAnimation() {
        Point startPoint=new Point(RADIUS,RADIUS);
        DisplayMetrics displayMetrics=mContext.getResources().getDisplayMetrics();
        Point endPoint=new Point(displayMetrics.widthPixels-RADIUS,displayMetrics.heightPixels-RADIUS);
        ValueAnimator anim=ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPoint= (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.setDuration(3000);
        anim.start();
    }
}
