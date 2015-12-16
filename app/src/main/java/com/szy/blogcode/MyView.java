package com.szy.blogcode;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2015/12/14 9:13
 */
public class MyView extends TextView{

    private int mLastX;
    private int mLastY;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y= (int) event.getRawY();
        Log.d("MyView", "x=" + x);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
               break;
            case MotionEvent.ACTION_MOVE:
                int dx=x-mLastX;
                int dy=y-mLastY;
                int translationX= (int) (ViewHelper.getTranslationX(this)+dx);
                int translationY= (int) (ViewHelper.getTranslationY(this)+dy);
                ViewHelper.setTranslationX(this,translationX);
                ViewHelper.setTranslationY(this,translationY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mLastX=x;
        mLastY=y;
        return true;
    }


}
