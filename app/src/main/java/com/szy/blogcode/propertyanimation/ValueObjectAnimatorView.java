package com.szy.blogcode.propertyanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
public class ValueObjectAnimatorView extends View {
    private static final int RADIUS=50;

    private Point mCurrentPoint;

    private Paint mPaint;

    private Context mContext;

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public ValueObjectAnimatorView(Context context) {
        super(context);
        init(context);
    }

    public ValueObjectAnimatorView(Context context, AttributeSet attrs) {
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
        DisplayMetrics displayMetrics=mContext.getResources().getDisplayMetrics();
        Point startPoint=new Point(displayMetrics.widthPixels-RADIUS,RADIUS);
        Point endPoint=new Point(RADIUS,displayMetrics.heightPixels-RADIUS);
        ValueAnimator anim=ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        ObjectAnimator anim2=ObjectAnimator.ofObject(this,"color",new ColorEvaluator(), "#0000FF","#ff0000");
        AnimatorSet set=new AnimatorSet();
        set.play(anim).with(anim2);
        set.setDuration(3000);
        set.start();
    }
}
