package com.szy.blogcode;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.szy.blogcode.xadapter.CommonAdapter;
import com.szy.blogcode.xadapter.ViewHolder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.main_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String className = (String) parent.getItemAtPosition(position);
                try {
                    Class clazz = Class.forName(className);
                    startActivity(new Intent(MainActivity.this, clazz));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        initData();
//        getSupportFragmentManager().beginTransaction().add(R.id.webView_container,new WebViewFragment()).commit();
        File httpCacheDir=new File(getCacheDir(),"http");
        int  httpCacheSize=10*1024*1024;
        try {
            HttpResponseCache.install(httpCacheDir,httpCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onStop() {
        super.onStop();
        HttpResponseCache cache=HttpResponseCache.getInstalled();
        if (cache != null) {
            cache.flush();
        }
    }

    private void initData() {
        String[] activities=getResources().getStringArray(R.array.activities);
        List<String> datas= Arrays.asList(activities);
        listView.setAdapter(new CommonAdapter<String>(this,datas,R.layout.item_main_activity) {
            @Override
            public void bindData(ViewHolder holder, String s) {
                int startIndex=s.lastIndexOf(".")+1;
                holder.setText(R.id.main_tv,s.substring(startIndex));
            }
        });
    }
}
