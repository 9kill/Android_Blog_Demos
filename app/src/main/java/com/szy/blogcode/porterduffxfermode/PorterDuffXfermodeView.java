package com.szy.blogcode.porterduffxfermode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.szy.blogcode.R;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2016/3/14 14:14
 */
public class PorterDuffXfermodeView extends ImageView {

    private Paint mPaint;
    private Bitmap mMaskBitmap;
    private Bitmap mTopBitmap;

    private Xfermode mPorterDuffXfermode;
    // 图层混合模式
    private PorterDuff.Mode mPorterDuffMode;
    // 总宽高
    private int mTotalWidth, mTotalHeight;
    private Rect mBottomDestRect;
    private Rect mTopDestRect;

    public void setTopBitmap(Bitmap topBitmap) {
        mTopBitmap = topBitmap;
    }

    public PorterDuffXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.PorterDuffXfermodeView,0,0);
        try {
            if (getDrawable() != null) {
                mTopBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
            }
            mMaskBitmap = convert9PathToBitmap(a.getDrawable(R.styleable.PorterDuffXfermodeView_mask));
        } finally {
            if (a!= null) {
                a.recycle();
            }
        }
        init();
    }

    public PorterDuffXfermodeView(Context context) {
        super(context);
        init();
    }
    public void init(){
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }



    public Bitmap convert9PathToBitmap(Drawable drawable) {
        if (drawable!=null) {
                Bitmap bitmap=Bitmap.createBitmap(mTopBitmap.getWidth(),mTopBitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, mTopBitmap.getWidth(), mTopBitmap.getHeight());
                drawable.draw(canvas);
                return bitmap;
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(mPaint);
        canvas.drawBitmap(mMaskBitmap, 0f, 0f, null);
        mPaint.setXfermode(mPorterDuffXfermode);
        canvas.drawBitmap(mTopBitmap, 0f, 0f, mPaint);
        mPaint.setXfermode(null);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
//        int halfHeight = h/2 ;

//        mBottomSrcRect = new Rect(0, 0, mMaskBitmap.getWidth(), mMaskBitmap.getHeight());
        mBottomDestRect = new Rect(0, 0, mTotalWidth,mTotalHeight);

//        mTopSrcRect = new Rect(0, 0, mTopBitmap.getWidth(), mTopBitmap.getHeight());
        mTopDestRect = new Rect(0, 0,mTotalWidth, mTotalHeight);
    }

}
