package com.szy.blogcode.propertyanimation;

import android.animation.TypeEvaluator;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2015/12/15 15:21
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint= (Point) startValue;
        Point endPoint= (Point) endValue;
        int x= (int) (startPoint.x+fraction*fraction*(endPoint.x-startPoint.x));
        int y= (int) (startPoint.y+fraction*(endPoint.y-startPoint.y));
        Point point=new Point(x,y);
        return point;
    }
}
