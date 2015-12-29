package com.szy.blogcode.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2015/12/22 16:16
 */
public class HorizontalScrollView extends ViewGroup {

    private Scroller mScroller;
    private VelocityTracker mTracker;

    private int mLastX;
    private int mLastY;
    private int mLastInterceptX;
    private int mLastInterceptY;


    private int mChildWidth;
    private int mChildrenSize;
    private int mChildIndex;
    private int mSlop;

    public HorizontalScrollView(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mTracker = VelocityTracker.obtain();
        ViewConfiguration vc = ViewConfiguration.get(getContext());
        mSlop = vc.getScaledTouchSlop() * 2;
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
        mChildrenSize = childCount;
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
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastInterceptX;
                int deltaY = y - mLastInterceptY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                int deltaUpX=x-mLastInterceptX;
                if (deltaUpX > mSlop) {
                    intercept=true;
                } else {
                    intercept=false;
                }
                break;
        }
        Log.d("HorizongtalScrollView", "intercept=" + intercept);
        mLastInterceptX = x;
        mLastInterceptY = y;
        mLastX = x;
        mLastInterceptY = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mTracker.computeCurrentVelocity(1000);
                int xVelocity = (int) mTracker.getXVelocity();
                if (Math.abs(xVelocity) > 100) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1));
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScroll(dx, 0);
                mTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScroll(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, dy, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mTracker.recycle();
        super.onDetachedFromWindow();
    }
}
