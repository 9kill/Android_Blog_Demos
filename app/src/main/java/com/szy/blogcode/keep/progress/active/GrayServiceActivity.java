package com.szy.blogcode.keep.progress.active;

import android.content.Intent;
import android.os.Bundle;

import com.szy.blogcode.BaseActivity;
import com.szy.blogcode.R;

public class GrayServiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gray_service);
        startService(new Intent(this, GrayService.class));
    }
}
