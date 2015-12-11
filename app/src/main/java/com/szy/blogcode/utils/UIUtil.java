package com.szy.blogcode.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2015/12/10 14:34
 */
public class UIUtil {
    private static  String TAG="UIUtil";
    private static String FLAG_SCALE_NO_SHAKE="flag_scale_no_shake";//view scale no shake
    private static String FLAG_SCALE_WITH_SHAKE="flag_scale_with_shake";//view after scale,can shake

    public static void setScale(final View view,OnScaleViewClickListener listener){
        setScale(view,listener,FLAG_SCALE_NO_SHAKE);
    }

    public static void setScaleWithShake(final View view,OnScaleViewClickListener listener){
        setScale(view,listener,FLAG_SCALE_WITH_SHAKE);
    }
    private static void setScale(final View view, final OnScaleViewClickListener listener, final String flag){
        SpringSystem springSystem=SpringSystem.create();
        final Spring spring=springSystem.createSpring();
        final Handler handler=new Handler();
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                final float scale = 1f - (value * 0.4f);
                handler.postDelayed(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public void run() {
                        view.setScaleX(scale);
                        view.setScaleY(scale);
                    }
                }, 10);
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
                        if (FLAG_SCALE_WITH_SHAKE.equals(flag)) {
                            float[] floatArray = new float[]{-0.5f, 0, 0.5f, 0};
                            scaleValueInRangeDelayed(floatArray, 100, 0);
                        } else {
                            spring.setEndValue(0);
                        }
                        break;
                }
                if (listener == null) {
                    return true;
                } else {
                    return false;
                }
            }

            int tempPos;

            private void scaleValueInRangeDelayed(final float[] floatArray, final int delayedTime, int pos) {
                tempPos = pos;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        spring.setEndValue(floatArray[tempPos]);
                        tempPos++;
                        if (tempPos < floatArray.length)
                            scaleValueInRangeDelayed(floatArray, delayedTime, tempPos);
                    }
                }, delayedTime);
            }

        });
        view.setOnClickListener(new View.OnClickListener() {
            Runnable action = new Runnable() {
                @Override
                public void run() {
                    if (listener!=null){
                        listener.onClick();
                    }
                }
            };

            @Override
            public void onClick(View v) {
                v.removeCallbacks(action);
                v.postDelayed(action, 250);
            }
        });

    }
    public interface OnScaleViewClickListener {
        abstract void onClick();
    }
}
