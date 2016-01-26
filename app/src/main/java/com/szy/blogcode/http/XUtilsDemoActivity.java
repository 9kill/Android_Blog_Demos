package com.szy.blogcode.http;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.blogcode.BuildConfig;
import com.szy.blogcode.R;
import com.szy.blogcode.utils.GifBinder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.IOException;

public class XUtilsDemoActivity extends AppCompatActivity {
    private TextView tv_http_test;
    private ImageView iv_image_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xutils_demo);

        tv_http_test = (TextView) findViewById(R.id.tv_http_test);
        iv_image_test = (ImageView) findViewById(R.id.iv_image_test);

        x.Ext.init(getApplication());//在自定义application的oncreate()方法中初始化
        x.Ext.setDebug(BuildConfig.LOG_DEBUG);//开启log

//        testHttp();
        testImageLoader();
    }

    private void testImageLoader() {
        ImageOptions imageOptions=new ImageOptions.Builder()
                .setSize(100,100)
                .setIgnoreGif(false).build();
//        x.image().bind(iv_image_test,"http://img0.imgtn.bdimg.com/it/u=3642740202,468770952&fm=21&gp=0.jpg",imageOptions);//加载网络gif
//          x.image().bind(iv_image_test,"assets://ic_gif_test.jpg",imageOptions);//加载本地assets文件下的gif
        try {
            GifBinder.bindGif(iv_image_test, getAssets().open("home_event1.gif"));//
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testHttp() {
        RequestParams params = new RequestParams("http://app.api.huxiu.com/article/get_feed_article");
        params.addQueryStringParameter("TIMESTAMP", "1453270481");
        params.addQueryStringParameter("NONCE", "huxiu_android_app");
        params.addQueryStringParameter("SIGNATURE", "c02aad7fd33d0993174bd063c9171367c174ea88");
        params.addQueryStringParameter("os", "android");
        params.addQueryStringParameter("version", "3.0");
        params.addQueryStringParameter("platform", "MI 3-4.4.4");
        params.addQueryStringParameter("page", "0");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                tv_http_test.setText(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                tv_http_test.setText(ex.toString());
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
