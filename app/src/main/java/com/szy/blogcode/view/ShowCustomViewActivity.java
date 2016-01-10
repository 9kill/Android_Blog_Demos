package com.szy.blogcode.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.szy.blogcode.R;

public class ShowCustomViewActivity extends AppCompatActivity {
    private static final String TAG="ShowCustomViewActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_custom_view);
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

}
