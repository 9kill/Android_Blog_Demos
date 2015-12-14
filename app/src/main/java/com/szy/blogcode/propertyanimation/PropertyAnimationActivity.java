package com.szy.blogcode.propertyanimation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.szy.blogcode.R;

public class PropertyAnimationActivity extends AppCompatActivity{

    private Button btn_alpha_paa;
    private Button btn_rotation_paa;
    private Button btn_translation_paa;
    private Button btn_scale_paa;
    private Button btn_set_paa;
    private Button btn_set_xml_paa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
        btn_alpha_paa= (Button) findViewById(R.id.btn_alpha_paa);
        btn_rotation_paa= (Button) findViewById(R.id.btn_rotation_paa);
        btn_translation_paa= (Button) findViewById(R.id.btn_translation_paa);
        btn_scale_paa= (Button) findViewById(R.id.btn_scale_paa);
        btn_set_paa= (Button) findViewById(R.id.btn_set_paa);
        btn_set_xml_paa= (Button) findViewById(R.id.btn_set_xml_paa);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void alpha(View v){
        ObjectAnimator animator=ObjectAnimator.ofFloat(btn_alpha_paa,"alpha",1f,0f,1f);
        animator.setDuration(1000);
        animator.start();
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void rotation(View v){
        ObjectAnimator animator=ObjectAnimator.ofFloat(btn_rotation_paa,"rotation",0f,360f);
        animator.setDuration(1000);
        animator.start();
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void translationX(View v){
        ObjectAnimator animatorX=ObjectAnimator.ofFloat(btn_translation_paa, "translationX", 0f, 500f, 0f);
        animatorX.setDuration(1000);
        animatorX.start();
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void scaleY(View v){
        ObjectAnimator animator=ObjectAnimator.ofFloat(btn_scale_paa,"scaleY",1f,2f,1f);
        animator.setDuration(1000);
        animator.start();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void set(View v){
        /**
         *   after(Animator anim)   将现有动画插入到传入的动画之后执行
             after(long delay)   将现有动画延迟指定毫秒后执行
             before(Animator anim)   将现有动画插入到传入的动画之前执行
             with(Animator anim)   将现有动画和传入的动画同时执行
         */
        ObjectAnimator moveIn=ObjectAnimator.ofFloat(btn_set_paa,"translationX",0f,300f);
        ObjectAnimator rotation=ObjectAnimator.ofFloat(btn_set_paa,"rotation",0f,360f);
        ObjectAnimator fadeInOut=ObjectAnimator.ofFloat(btn_set_paa,"alpha",1f,0f,1f);
        ObjectAnimator moveOut=ObjectAnimator.ofFloat(btn_set_paa,"translationX",300f,0f);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(rotation).after(moveIn).with(fadeInOut).before(moveOut);
        animatorSet.setDuration(1000);//设置每个动画的执行时间
        animatorSet.addListener(new AnimatorListenerAdapter() {
            //TODO 可以重写任意一个想要监听的方法
        });
        animatorSet.start();
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setUseXml(View v){
        AnimatorSet animatorSet= (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.anim_set);
        animatorSet.setTarget(btn_set_xml_paa);
        animatorSet.start();
    }

}
