package com.szy.blogcode.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.szy.blogcode.R;
import com.szy.blogcode.utils.UIUtil;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2015/12/29 10:59
 */
public class CubeTextView extends TextView {

    private Paint mSquarePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mTextColor;
    private float mTextSize;
    private int mTextBackgroudColor;
    private int mSpacingBackgroudColor;


    public CubeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int defaultTextColor=Color.parseColor("#ffffff");
        int defaultTextSize=UIUtil.sp2px(16);
        int defaultTextBackgroudColor=Color.parseColor("#007FFF");
        int defaultSpacingBackgroudColor=Color.parseColor("#ffffff");
        TypedArray a=getContext().getTheme().obtainStyledAttributes(attrs,R.styleable.CubeTextView,0,0);
        try {
            mTextColor=a.getColor(R.styleable.CubeTextView_textColor,defaultTextColor);
            mTextSize =a.getDimension(R.styleable.CubeTextView_textSize, defaultTextSize);
            mTextBackgroudColor = a.getColor(R.styleable.CubeTextView_textBackgroud, defaultTextBackgroudColor);
            mSpacingBackgroudColor = a.getColor(R.styleable.CubeTextView_spacingBackgroup, defaultSpacingBackgroudColor);
        }finally {
            a.recycle();
        }
        mSquarePaint.setColor(mTextBackgroudColor);
        mRectPaint.setColor(mSpacingBackgroudColor);

        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int rectSpacing= UIUtil.dip2px(5);

        int width = getWidth()+rectSpacing;
        int height = getHeight();

        int textLength=getText().toString().length();

        int squareWidth=width/textLength-rectSpacing;


        for (int i = 0; i < textLength; i++) {
            RectF squareRectf=new RectF((squareWidth+rectSpacing)*i,0,squareWidth+(squareWidth+rectSpacing)*i,height);
            canvas.drawRect(squareRectf,mSquarePaint);
            if (i+1<textLength) {
                RectF rectRectf = new RectF(squareWidth + (squareWidth + rectSpacing) * i, 0, (squareWidth + rectSpacing) * (i + 1), height);
                canvas.drawRect(rectRectf, mRectPaint);
            }
        }

        char[] chars=getText().toString().toCharArray();
        for (int i = 0; i < textLength;i++ ) {
            canvas.drawText(String.valueOf(chars[i]),squareWidth/2+(squareWidth+rectSpacing)*i,height/2+getTextSize()/2,mTextPaint);
        }

    }
}