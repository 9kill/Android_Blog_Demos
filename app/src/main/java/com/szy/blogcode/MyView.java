package com.szy.blogcode;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2015/12/14 9:13
 */
public class MyView extends TextView{

    private Scroller mScroller;


    public MyView(Context context) {
        super(context);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int downX = 0;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX= (int) event.getX();
                Log.d("MyView", "DownX=" + downX);
                return true;
            case MotionEvent.ACTION_MOVE:
                int dx= (int) (downX-event.getX());
                Log.d("MyView", "DX=" + dx);
                scrollTo(dx, 0);
                return true;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }


    public void smoothScroll(int destX,int destY){
        int scrollX=getScrollX();
        int dx=destX-scrollX;
        mScroller.startScroll(scrollX,0,dx,0,1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            Log.d("MyView", "scrollX=" + getScrollX());
            scrollTo(mScroller.getCurrX(),0);
            postInvalidate();
        }
    }
}
