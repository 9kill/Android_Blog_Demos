package com.szy.blogcode.propertyanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.szy.blogcode.R;

public class PropertyAnimationActivity2 extends AppCompatActivity {

    private FrameLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation2);
        rootView= (FrameLayout) getWindow().getDecorView();

    }

    public void valueanimation(View v){
        ValueAnimatorView view=new ValueAnimatorView(this);
        rootView.addView(view);
    }

    public void valueaobjectnimation(View v){
        ValueObjectAnimatorView view=new ValueObjectAnimatorView(this);
        rootView.addView(view);
    }

}
