package com.szy.blogcode.doodle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.szy.blogcode.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoodleActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnSave;
    private Button mBtnClear;
    private ImageView mIvImage;
    private Bitmap mPanel;
    private Canvas mCanvas;
    private Paint mPaint;
    private int mDownX;
    private int mDownY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle);
        initViews();
    }

    private void initViews() {
        mIvImage = (ImageView) findViewById(R.id.iv_image);
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mBtnClear = (Button) findViewById(R.id.btn_clear);
        mIvImage.setOnTouchListener(new DoodleTouchListener());
        mBtnSave.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_save) {
            saveDoodle();
        } else if (id == R.id.btn_clear) {
            //draw white color to canvas
            mCanvas.drawColor(Color.WHITE);
            mIvImage.setImageBitmap(mPanel);
            mPanel = null;
        }
    }

    private void saveDoodle() {
        File cacheDir = Environment.getExternalStorageDirectory();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        File cacheFile = new File(cacheDir, "doodle_" + sdf.format(date) +
                ".jpg");
        Log.d("DoodleActivity", "savePath=" + cacheFile.getPath());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(cacheFile);
            boolean isSuccess = mPanel.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            if (isSuccess) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class DoodleTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    initPanel();
                    mDownX = (int) event.getX();
                    mDownY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int moveX = (int) event.getX();
                    int moveY = (int) event.getY();
                    mCanvas.drawLine(mDownX, mDownY, moveX, moveY, mPaint);
                    mIvImage.setImageBitmap(mPanel);
                    //record last down position
                    mDownX = moveX;
                    mDownY = moveY;
                    break;
                case MotionEvent.ACTION_UP:
                    break;

            }
            return true;
        }
    }

    /**
     * 初始化画纸
     */
    private void initPanel() {
        if (mPanel == null) {
            //create canvas,bitmap,paint
            mPanel = Bitmap.createBitmap(mIvImage.getWidth(), mIvImage.getHeight(), Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mPanel);
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.RED);
            mPaint.setStrokeWidth(5);
            //canvas draw yellow color on panel
            mCanvas.drawColor(Color.YELLOW);
            //show panel on imageview
            mIvImage.setImageBitmap(mPanel);
        }
    }
}
