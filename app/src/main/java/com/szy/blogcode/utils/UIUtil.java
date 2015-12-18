package com.szy.blogcode.utils;

import android.content.res.Resources;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.nineoldandroids.view.ViewHelper;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2015/12/10 14:34
 */
public class UIUtil {
    private static  String TAG="UIUtil";

    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";

    public interface OnScaleViewClickListener {
        public abstract void onClick();
    }

    private static boolean clickUp;


    public static void setScale(final View view, final OnScaleViewClickListener listener){
        SpringSystem springSystem=SpringSystem.create();
        final Spring spring=springSystem.createSpring();
        final Handler handler=new Handler();
        final Runnable callback=new Runnable() {
            @Override
            public void run() {
                if (listener!=null){
                    listener.onClick();
                }
            }
        };
        spring.addListener(new SimpleSpringListener(){
            @Override
            public void onSpringUpdate(Spring spring) {
                float value= (float) spring.getCurrentValue();
                float scale= (float) (1f-0.382*value);
                ViewHelper.setScaleX(view, scale);
                ViewHelper.setScaleY(view,scale);
                if (scale==1f&&clickUp){
                    handler.removeCallbacks(callback);
                    handler.post(callback);
                }
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        spring.setEndValue(1);
                        break;
                    case MotionEvent.ACTION_UP:
                        clickUp=true;
                       spring.setEndValue(0);

                }
                return true;
            }

        });
    }

    /**
     * dip转换为px
     * dip2px
     * @param dpValue
     * @return
     * @since 1.0
     */

    public static int dip2px(int dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转换为dip
     * px2dip
     * @param pxValue
     * @return
     * @since 1.0
     */
    public static int px2dip(int pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 计算状态栏高度高度
     * getStatusBarHeight
     *
     * @return
     */
    public static int getStatusBarHeight() {
        return getInternalDimensionSize(Resources.getSystem(), STATUS_BAR_HEIGHT_RES_NAME);
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
