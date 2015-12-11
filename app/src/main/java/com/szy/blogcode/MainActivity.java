package com.szy.blogcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.szy.blogcode.utils.UIUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv1= (ImageView) findViewById(R.id.iv1);
        ImageView iv2= (ImageView) findViewById(R.id.iv2);
        UIUtil.setScale(iv1);
        UIUtil.setScaleWithShake(iv2);
    }
}
