package com.szy.blogcode.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.szy.blogcode.R;

public class ShowCustomViewActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG="ShowCustomViewActivity";
    private SmoothScrollView smoothScrollView;
    private Qview qview;
    private RippleView mRippleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_custom_view);
        initView();
        //可加减的的编辑输入框
        addAndSubtractEditTextView();
        //可伸缩文本
        addExpandableText();

    }

    private void addExpandableText() {
        ExpandableTextView expTv1 = (ExpandableTextView)findViewById(R.id.expand_text_view);
        expTv1.setText(getString(R.string.expandable_text));
    }

    private void initView() {
        Button btn_scroll = (Button) findViewById(R.id.btn_scroll);
        smoothScrollView = (SmoothScrollView) findViewById(R.id.smoothScrollView);
        HoveringView hoveringView = (HoveringView) findViewById(R.id.hoveringView);
        Button progress=(Button)findViewById(R.id.progress);
        qview = (Qview) findViewById(R.id.qview);
        mRippleView = (RippleView) findViewById(R.id.rippleView);
        Button btn_start_ripple = (Button) findViewById(R.id.btn_start_ripple);
        Button btn_stop_ripple = (Button) findViewById(R.id.btn_stop_ripple);



        btn_scroll.setOnClickListener(this);
        hoveringView.setOnClickListener(this);
        progress.setOnClickListener(this);
        btn_start_ripple.setOnClickListener(this);
        btn_stop_ripple.setOnClickListener(this);
        mRippleView.setRippleStateListener(new RippleView.RippleListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onStop() {

            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
            @Override
            public void onRippleUpdate(ValueAnimator animation) {
            }
        });
    }

    private void addAndSubtractEditTextView() {
        AddAndSubtractEditTextView aas= (AddAndSubtractEditTextView) findViewById(R.id.etv_aas);
        aas.setOnNumberChangeListener(new AddAndSubtractEditTextView.OnNumberChangeListener() {

            @Override
            public void OnAdd(int currentVale) {
                Log.d(TAG, "OnAdd int currentVale=" + currentVale);
            }

            @Override
            public void OnSubtract(int currentVale) {
                Log.d(TAG, "OnSubtract int currentVale=" + currentVale);
            }

            @Override
            public void OnAdd(float currentVale) {
                Log.d(TAG, "OnAdd float currentVale=" + currentVale);
            }

            @Override
            public void OnSubtract(float currentVale) {
                Log.d(TAG, "OnSubtract float currentVale=" + currentVale);
            }

            @Override
            public void OnSerach(int currentVale) {
                Log.d(TAG, "OnSerach int currentVale=" + currentVale);
            }

            @Override
            public void OnSerach(float currentVale) {
                Log.d(TAG, "OnSerach float currentVale=" + currentVale);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scroll:
                smoothScrollView.smoothScrollTo(-100,-100);
                break;
            case R.id.hoveringView:
                Toast.makeText(this, "click hoveringView", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_start_ripple:
                mRippleView.startRipple();
                break;
            case R.id.btn_stop_ripple:
                mRippleView.stopRipple();
                break;
        }
    }

}
