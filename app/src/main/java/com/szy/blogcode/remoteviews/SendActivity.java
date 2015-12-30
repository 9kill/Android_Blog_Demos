package com.szy.blogcode.remoteviews;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

import com.szy.blogcode.R;

public class SendActivity extends AppCompatActivity {
    private static final String MYBROADCAST="com.action.mybroadcast";
    private static final String EXTRA_REMOTEVIEWS="remoteviews";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

    }
    public void send(View v){
        int viewId=getResources().getIdentifier("layout_notification", "layout", getPackageName());
        RemoteViews remoteViews=new RemoteViews(getPackageName(),viewId);
        remoteViews.setTextViewText(R.id.tv_notification_title, "重大消息");
        remoteViews.setTextViewText(R.id.tv_notification_content,"今晚打老虎");
        remoteViews.setImageViewResource(R.id.iv_notification, R.mipmap.ic_scale);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,new Intent(this,ReceiveActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_notification,pendingIntent);
        Intent intent=new Intent();
        intent.setAction(MYBROADCAST);
        intent.putExtra(EXTRA_REMOTEVIEWS, remoteViews);
        sendBroadcast(intent);
    }
}
