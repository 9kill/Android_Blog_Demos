package com.szy.blogcode.remoteviews;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.szy.blogcode.R;

/**通过remoteviews，实现跨进程更新UI：
 * 先调用ReceiveActivity，注册广播，（ReceiveActivity要在AndroidManifest.xml中注册为远程进程android:process=":remote"）
 * 调用SendActivity，发送广播，传递remoteviews
 * 在ReceiveActivity收到广播，获取remoteviews，加载布局文件，进行更细UI操作
 *
 */

public class ReceiveActivity extends AppCompatActivity {
    private static final String MYBROADCAST="com.action.mybroadcast";
    private static final String EXTRA_REMOTEVIEWS="remoteviews";
    private LinearLayout mContainer;
    private BroadcastReceiver mBroadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("ReceiveActivity","OnReceive");
            RemoteViews mRemoteViews=intent.getParcelableExtra(EXTRA_REMOTEVIEWS);
            if (mRemoteViews!=null){
                updateUI(mRemoteViews);
            }
        }
    };

    private void updateUI(RemoteViews remoteViews) {
        int viewId=getResources().getIdentifier("layout_notification", "layout", getPackageName());
//        View view = remoteViews.apply(this, mContainer);
        View view=getLayoutInflater().inflate(viewId,mContainer,false);
        remoteViews.reapply(this,view);
        mContainer.addView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        mContainer= (LinearLayout) findViewById(R.id.container);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MYBROADCAST);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    public void skipToSendActivity(View view) {
        startActivity(new Intent(this, SendActivity.class));
    }
}
