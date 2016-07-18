package com.szy.blogcode.umengfeebback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.szy.blogcode.R;
import com.umeng.fb.FeedbackAgent;

public class UMengFBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umeng_fb);
        FeedbackAgent agent = new FeedbackAgent(this);
        agent.startFeedbackActivity();
        finish();
    }
}
