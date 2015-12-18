package com.szy.blogcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.szy.blogcode.utils.UIUtil;
import com.szy.blogcode.view.HoveringView;
import com.szy.blogcode.xadapter.CommonAdapter;
import com.szy.blogcode.xadapter.ViewHolder;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

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
        HoveringView myview= (HoveringView) findViewById(R.id.myview);
        ImageView iv= (ImageView) findViewById(R.id.iv);
        UIUtil.setScale(iv, new UIUtil.OnScaleViewClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(getApplicationContext(),"Onclick",Toast.LENGTH_SHORT).show();
            }
        });
        initData();
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
