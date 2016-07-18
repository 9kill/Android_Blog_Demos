package com.szy.blogcode.porterduffxfermode;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.szy.blogcode.R;
import com.szy.blogcode.utils.BitmapUtil;

import org.xutils.common.Callback;
import org.xutils.x;

public class PorterDuffXfermodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porter_duff_xfermode);
        final PorterDuffXfermodeView view= (PorterDuffXfermodeView) findViewById(R.id.image);
        x.image().bind(view, "http://img5.imgtn.bdimg.com/it/u=3432927473,3545292754&fm=21&gp=0.jpg", new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(final Drawable result) {
                PorterDuffXfermodeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.setTopBitmap(BitmapUtil.drawableToBitamp(result));
                        view.postInvalidate();
                    }
                });

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
