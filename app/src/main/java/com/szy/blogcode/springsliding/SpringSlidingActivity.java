package com.szy.blogcode.springsliding;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.nineoldandroids.animation.ValueAnimator;
import com.szy.blogcode.BaseActivity;
import com.szy.blogcode.R;

import java.util.Timer;
import java.util.TimerTask;

public class SpringSlidingActivity extends BaseActivity implements View.OnClickListener {
    private static final int MESSAGE_SCROLL_TO = 1;
    private static final int MESSAGE_SLEEP_SCROLL_TO = 2;
    private static final int FRAME_COUNT = 30;
    private static final int DELAYED_TIME = 33;

    private Button btnAnimation;
    private Button btnHandler;
    private Button btnViewPostDelayed;
    private Button btnThreadSleep;
    private Button btnTimerTimertask;

    private int mCount = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SCROLL_TO:
                    mCount++;
                    if (mCount < FRAME_COUNT) {
                        float fraction = mCount / (float) FRAME_COUNT;
                        int scrollX = (int) (fraction * (-100));
                        btnHandler.scrollTo(scrollX, 0);
                        handler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME);
                    }
                    break;
                case MESSAGE_SLEEP_SCROLL_TO:
                    float fraction = mCount / (float) FRAME_COUNT;
                    int scrollX = (int) (fraction * (-100));
                    btnThreadSleep.scrollTo(scrollX, 0);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring_sliding);
        initViews();
    }

    private void initViews() {
        btnAnimation = (Button) findViewById(R.id.btn_animation);
        btnHandler = (Button) findViewById(R.id.btn_handler);
        btnViewPostDelayed = (Button) findViewById(R.id.btn_view_postDelayed);
        btnThreadSleep = (Button) findViewById(R.id.btn_thread_sleep);
        btnTimerTimertask= (Button) findViewById(R.id.btn_timer_timertask);

        btnAnimation.setOnClickListener(this);
        btnHandler.setOnClickListener(this);
        btnViewPostDelayed.setOnClickListener(this);
        btnThreadSleep.setOnClickListener(this);
        btnTimerTimertask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_animation:
                animationSpringSliding();
                break;
            case R.id.btn_handler:
                handlerSpringSliding();
                break;

            case R.id.btn_view_postDelayed:
                viewPostDelayedSpringSliding();
                break;

            case R.id.btn_thread_sleep:
                threadSleepSpringSliding();
                break;

            case R.id.btn_timer_timertask:
                timerTimerTaskSpringSliding();
                break;

        }
    }

    private void timerTimerTaskSpringSliding() {
        mCount=0;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mCount < FRAME_COUNT) {
                    mCount++;
                    float fraction = mCount / (float) FRAME_COUNT;
                    int scrollX = (int) (fraction * (-100));
                    btnTimerTimertask.scrollTo(scrollX, 0);
                } else {
                    mCount=0;
                    this.cancel();
                }
            }
        },0,DELAYED_TIME);
    }

    private void threadSleepSpringSliding() {
        mCount = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (mCount < FRAME_COUNT) {
                        mCount++;
                        handler.sendEmptyMessage(MESSAGE_SLEEP_SCROLL_TO);
                        Thread.sleep(DELAYED_TIME);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void viewPostDelayedSpringSliding() {
        mCount = 0;
        continuePostDelayed(0);
    }

    private void continuePostDelayed(long delayedTime) {
        if (mCount < FRAME_COUNT) {
            mCount++;
            btnViewPostDelayed.postDelayed(new Runnable() {
                @Override
                public void run() {
                    float fraction = mCount / (float) FRAME_COUNT;
                    int scrollX = (int) (fraction * (-100));
                    btnViewPostDelayed.scrollTo(scrollX, 0);
                    continuePostDelayed(DELAYED_TIME);
                }
            }, delayedTime);
        }
    }

    private void handlerSpringSliding() {
        //大约1000ms向右弹性滑动100px
        mCount = 0;//reset
        handler.sendEmptyMessage(MESSAGE_SCROLL_TO);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void animationSpringSliding() {
        //way 1
//        ObjectAnimator.ofFloat(btnAnimation, "translationX", 0, 100).setDuration(1000).start();
        //way 2
        final int startX = 0;
        final int deltaX=-100;
        ValueAnimator valueAnimator=ValueAnimator.ofInt(0, 1).setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction=valueAnimator.getAnimatedFraction();
                int scrollX=startX+(int)(fraction*deltaX);
                btnAnimation.scrollTo(scrollX,0);
            }
        });
        valueAnimator.start();

    }
}
