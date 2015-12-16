package com.szy.blogcode.propertyanimation;

import android.animation.TypeEvaluator;
import android.annotation.TargetApi;
import android.os.Build;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2015/12/15 16:05
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ColorEvaluator implements TypeEvaluator {

    private int mCurrentRed=-1;

    private int mCurrentGreen=-1;

    private int mCurrentBlue=-1;

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        String startColor= (String) startValue;
        String endColor= (String) endValue;
        int startRed= Integer.parseInt(startColor.substring(1,3),16);
        int startGreen=Integer.parseInt(startColor.substring(3,5),16);
        int startBlue=Integer.parseInt(startColor.substring(5,7),16);
        int endRed= Integer.parseInt(endColor.substring(1,3),16);
        int endGreen=Integer.parseInt(endColor.substring(3,5),16);
        int endBlue=Integer.parseInt(endColor.substring(5,7),16);
        //初始化颜色的值
        if (mCurrentRed==-1){
            mCurrentRed=startRed;
        }
        if (mCurrentGreen==-1){
            mCurrentGreen=startGreen;
        }
        if (mCurrentBlue==-1){
            mCurrentBlue=startBlue;
        }
        //计算颜色的差值
        int redDiff=Math.abs(startRed-endRed);
        int greenDiff=Math.abs(startGreen-endGreen);
        int blueDiff=Math.abs(startBlue-endBlue);
        int colorDiff=redDiff+greenDiff+blueDiff;
        //获取RGB当前十进制的颜色值
        if (startRed!=endRed){
            mCurrentRed=getCurrentColor(startRed,endRed,fraction,redDiff);
        }
        if (startGreen!=endGreen) {
            mCurrentGreen=getCurrentColor(startGreen,endGreen,fraction,greenDiff);
        }
        if (startBlue!=endBlue) {
            mCurrentBlue=getCurrentColor(startBlue,endBlue,fraction,blueDiff);
        }
        //组装RGB的十进制转换成十六进制
        String currentColor="#"+getHexString(mCurrentRed)+getHexString(mCurrentGreen)+getHexString(mCurrentBlue);
        return currentColor;
    }

    private int getCurrentColor(int startColor, int endColor, float fraction, int colorDiff) {
        int currentColor=0;
        if (startColor > endColor) {
            currentColor= (int) (startColor-(fraction*colorDiff));
            if (currentColor<endColor){
                currentColor=endColor;
            }
        } else {
            currentColor= (int) (startColor+fraction*colorDiff);
            if (currentColor>endColor){
                currentColor=endColor;
            }
        }
        return currentColor;
    }

    private String getHexString(int value){
        String hexString=Integer.toHexString(value);
        if (hexString.length()==1){
            hexString="0"+hexString;
        }
        return hexString;
    }
}
