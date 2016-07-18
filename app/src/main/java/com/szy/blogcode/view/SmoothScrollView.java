package com.szy.blogcode.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2016/5/4 20:06
 */
public class SmoothScrollView extends TextView implements GestureDetector.OnGestureListener{
    private GestureDetector mGestureDetector;
    private Scroller mScroller;

    public SmoothScrollView(Context context) {
        super(context);
        init(context);
    }

    public SmoothScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SmoothScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mGestureDetector=new GestureDetector(context,this);
        mGestureDetector.setIsLongpressEnabled(false);

        mScroller=new Scroller(context);
    }

    public void smoothScrollTo(int destX,int destY){
        int scrollX=getScrollX();
        int scrollY=getScrollY();
        int deltaX=destX-scrollX;
        int deltaY=destY-scrollY;
        //1000ms滑动到destX，destY，效果是慢慢滑动
        mScroller.startScroll(scrollX,scrollY,deltaX,deltaY,1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
