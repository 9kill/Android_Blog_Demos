package com.szy.blogcode.slidingconflict;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import com.szy.blogcode.view.HorizontalScrollView;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2016/6/7 11:22
 */
public class HorizontalScrollViewEx extends HorizontalScrollView {
    /**
     * 点击事件的最后坐标位置
     */
    private int mLastX;
    private int mLastY;
    /**
     * 点击事件的最后坐标位置（Intercept）
     */
    private int mLastXIntercept;
    private int mLastYIntercept;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mChildWidth;
    private int mChildIndex=0;
    private int mChildCount;
    private int mChildrenSize;

    public HorizontalScrollViewEx(Context context) {
        super(context);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller=new Scroller(context);
        mVelocityTracker=VelocityTracker.obtain();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            View child = getChildAt(0);
            int chileWidth = child.getWidth();
            int childHeight = child.getHeight();
            setMeasuredDimension(chileWidth * childCount, childHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            View child = getChildAt(0);
            int childWidth = child.getWidth();
            setMeasuredDimension(childWidth * childCount, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            View child = getChildAt(0);
            int childHeight = child.getHeight();
            setMeasuredDimension(widthSpecSize, childHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        int childCount = getChildCount();
        mChildCount = childCount;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int childWidth = child.getMeasuredWidth();
                mChildWidth = childWidth;
                child.layout(childLeft, 0, childLeft + childWidth, child.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        int x= (int) event.getX();
        int y=(int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept=false;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx=x-mLastXIntercept;
                int dy=y-mLastYIntercept;
                if (Math.abs(dx) > Math.abs(dy)) {
                    intercept=true;
                } else {
                    intercept=false;
                }
            case MotionEvent.ACTION_UP:
                intercept=false;
                break;
        }
        mLastXIntercept=x;
        mLastYIntercept=y;
        mLastX=x;
        mLastY=y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x= (int) event.getX();
        int y=(int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int dx=x-mLastX;
                scrollBy(-dx,0);
                break;
            case MotionEvent.ACTION_UP:
                int smoothToChildIndex;
                int smoothDx;
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity=mVelocityTracker.getXVelocity();
                /**
                 * 1.当滑动速率大于50，
                 * 向右滑动，smoothToChildIndex减1（小于0，取0）；
                 * 向左滑动smoothToChildIndex加1（大于childcount-1，取childcount）
                 *2.当滑动速率小于50，
                 * 向右滑动，滑动距离大于屏幕的一半，smoothToChildIndex减1，否则smoothToChildIndex不变
                 * 向左滑动，滑动距离大于屏幕一半，smoothToChildIndex加1，否则smoothToChildIndex不变。
                 */
                if (Math.abs(xVelocity) > 50) {
                    if (xVelocity > 0) {
                        smoothToChildIndex=mChildIndex==0?0:mChildIndex-1;
                        if (mChildIndex == 0) {
                            smoothDx=0;
                        } else {
//                            smoothToChildIndex=mChildIndex-1;
                            smoothDx=(mChildIndex*mChildWidth-getScrollX())-mChildWidth;
                        }
                    } else {
                        smoothToChildIndex=mChildIndex==mChildCount-1?mChildCount-1:mChildIndex+1;
                        if (mChildIndex == mChildCount - 1) {
                            smoothDx=0;
                        } else {
                            smoothToChildIndex=mChildIndex+1;
                            smoothDx = smoothToChildIndex * mChildWidth-getScrollX();
                        }
                    }
                } else {
                    if (xVelocity>0) {
                        if ((getScrollY()+mChildWidth/2)/mChildWidth<mChildIndex) {
                            smoothToChildIndex=mChildIndex-1;
                            smoothDx=(mChildIndex*mChildWidth-getScrollX())-mChildWidth;
                        } else {
                            smoothToChildIndex=mChildIndex;
                        }
                    } else {
                        if ((getScrollY()+mChildWidth/2)/mChildWidth>mChildIndex) {
                            smoothToChildIndex=mChildIndex+1;
                        } else {
                            smoothToChildIndex=mChildIndex;
                        }
                    }
                }
                startSmoothScroll(smoothToChildIndex);
                break;
        }
        mLastX=x;
        mLastY=y;
        return super.onTouchEvent(event);
    }

    private void startSmoothScroll(int smoothToChildIndex) {
        int sx=getScrollX();
        int dx=smoothToChildIndex*mChildWidth-getScrollX();
        mScroller.startScroll(sx,0,dx,0,1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
