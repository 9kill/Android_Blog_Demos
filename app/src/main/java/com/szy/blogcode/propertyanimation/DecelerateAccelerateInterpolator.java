package com.szy.blogcode.propertyanimation;

import android.animation.TimeInterpolator;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2015/12/16 14:19
 */
public class DecelerateAccelerateInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        float result;
        if (input <= 0.5) {
            result= (float) Math.sin(Math.PI*input)/2;
        } else {
            result= (float) (2-Math.sin(Math.PI*input))/2;
        }
        return result;
    }
}
