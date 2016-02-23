package com.szy.blogcode.imagecache;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.szy.blogcode.R;

public class ImageLoaderActivity extends AppCompatActivity {
    public LruCache<String,Bitmap> mMemoryCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
        ImageView iv = (ImageView) findViewById(R.id.iv_image_loader);

        int maxMemory= (int) (Runtime.getRuntime().maxMemory()/1024);
        int cacheMemory=maxMemory/8;
        mMemoryCache=new LruCache<String,Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
            }
        };
    }


}
