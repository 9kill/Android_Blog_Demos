package com.szy.blogcode.propertyanimation;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.szy.blogcode.R;

public class PropertyAnimationActivity2 extends AppCompatActivity {

    private FrameLayout rootView;
    private View btn_view_property_animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation2);
        rootView= (FrameLayout) getWindow().getDecorView();
        btn_view_property_animator=findViewById(R.id.btn_view_property_animator);

    }

    public void valueanimation(View v){
        ValueAnimatorView view=new ValueAnimatorView(this);
        rootView.addView(view);
    }

    public void valueaobjectnimation(View v){
        ValueObjectAnimatorView view=new ValueObjectAnimatorView(this);
        rootView.addView(view);
    }

    public void interpolator(View v) {
        startActivity(new Intent(this, PropertyAnimationActivity3.class));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public void viewPropertyAnimator(View v){
        btn_view_property_animator.animate()
                .x(300f)
                .y(500f)
                .rotation(360f)
                .setDuration(1000);
    }
}
