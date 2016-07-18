package com.szy.blogcode.view;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;
import com.szy.blogcode.utils.UIUtil;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2015/12/14 9:13
 */
public class HoveringView extends ImageView{



    public interface OnHoveringViewClickListener{
        public abstract void onClick();
    }
    private OnHoveringViewClickListener mListener;

    public void setOnClickListener(OnHoveringViewClickListener listener){
        this.mListener=listener;
    }

    private Context mContext;
    private int mSlop;
    private int mLastX;
    private int mLastY;
    private int downX;
    private int downY;


    public HoveringView(Context context) {
        super(context);
        init(context);
    }

    public HoveringView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        mContext=context;
        ViewConfiguration vc=ViewConfiguration.get(context);
        mSlop=vc.getScaledTouchSlop()*2;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        DisplayMetrics displayMetrics=mContext.getResources().getDisplayMetrics();
        int contentWidth=displayMetrics.widthPixels-this.getWidth();
        int contentHeight=displayMetrics.heightPixels- UIUtil.getStatusBarHeight()-UIUtil.dip2px(55)-this.getHeight();
        int x = (int) event.getRawX();
        int x2 = (int) event.getX();
        int x3= (int) getX();
        int y= (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX=x;
                downY=y;
                Log.d("HoveringView", "left="+getLeft());
                Log.d("HoveringView", "tx="+getTranslationX());
                Log.d("HoveringView", "x="+x);
                Log.d("HoveringView", "x2="+x2);
                Log.d("HoveringView", "x3="+x3);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx=x-mLastX;
                int dy=y-mLastY;
                int translationX= (int) (ViewHelper.getTranslationX(this)+dx);
                int translationY= (int) (ViewHelper.getTranslationY(this)+dy);
                if (translationX>=0&&translationX<=contentWidth){
                    ViewHelper.setTranslationX(this,translationX);
                }
                if (translationY>=0&&translationY<=contentHeight){
                    ViewHelper.setTranslationY(this,translationY);
                }
                break;
            case MotionEvent.ACTION_UP:
                float deltaX=Math.abs(event.getRawX()-downX);
                float deltaY=Math.abs(event.getRawY()-downY);
                if (deltaX < mSlop && deltaY < mSlop) {
                    if (mListener != null) {
                        mListener.onClick();
                    }
                } else {
                    if (ViewHelper.getTranslationX(this)<contentWidth/2){
                        ObjectAnimator anim=ObjectAnimator.ofFloat(this, "translationX", ViewHelper.getTranslationX(this), 0f);
                        anim.setDuration(200);
                        anim.start();
                    }else {
                        ObjectAnimator anim=ObjectAnimator.ofFloat(this,"translationX",ViewHelper.getTranslationX(this),contentWidth);
                        anim.setDuration(200);
                        anim.start();
                    }
                }
                break;
            default:
                break;
        }
        mLastX=x;
        mLastY=y;
        return true;
    }

}
