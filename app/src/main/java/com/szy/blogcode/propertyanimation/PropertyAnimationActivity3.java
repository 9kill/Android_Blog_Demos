package com.szy.blogcode.propertyanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.szy.blogcode.R;

public class PropertyAnimationActivity3 extends AppCompatActivity {

    private FrameLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation3);
        rootView= (FrameLayout) getWindow().getDecorView();
    }

    public void systemInterpolator(View v){
        SystemInterpolatorAnimatorView view=new SystemInterpolatorAnimatorView(this);
        rootView.addView(view);
    }

    public void customInterpolator(View v){
        CustomInterpolatorAnimatorView view=new CustomInterpolatorAnimatorView(this);
        rootView.addView(view);
    }

}
