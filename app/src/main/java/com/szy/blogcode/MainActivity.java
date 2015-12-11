package com.szy.blogcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;

import com.szy.blogcode.XAdapter.CommonAdapter;
import com.szy.blogcode.XAdapter.ViewHolder;
import com.szy.blogcode.XAdapter.XAdapterActivity;
import com.szy.blogcode.utils.UIUtil;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv1= (ImageView) findViewById(R.id.iv1);
        ImageView iv2= (ImageView) findViewById(R.id.iv2);
        UIUtil.setScale(iv1, new UIUtil.OnScaleViewClickListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(MainActivity.this, XAdapterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        UIUtil.setScaleWithShake(iv2, null);
        String[] activities=getResources().getStringArray(R.array.activities);
        List<String> datas= Arrays.asList(activities);
        ListView listView= (ListView) findViewById(R.id.main_listview);
        listView.setAdapter(new CommonAdapter<String>(this,datas,R.layout.item_main_activity) {
            @Override
            public void bindData(ViewHolder holder, String s) {
                holder.setText(R.id.main_tv,s);
            }
        });

    }
}
