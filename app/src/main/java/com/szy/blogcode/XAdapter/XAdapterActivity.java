package com.szy.blogcode.xadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.szy.blogcode.R;

import java.util.ArrayList;
import java.util.List;

public class XAdapterActivity extends AppCompatActivity {

    private List<Bean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xadapter);
        initDatas();
        initView();
    }

    private void initDatas() {
        mDatas=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Bean bean=new Bean("汪峰"+i,"章子怡帮你上头条");
            mDatas.add(bean);
        }
    }
    private void initView() {
        ListView listview= (ListView) findViewById(R.id.listview);
        listview.setAdapter(new CommonAdapter<Bean>(this,mDatas,R.layout.item_xadapter_activity) {
            @Override
            public void bindData(ViewHolder holder, final Bean bean) {
                holder.setText(R.id.tv_name,bean.name).setText(R.id.tv_content, bean.content);
                final CheckBox cb= (CheckBox) holder.getViewById(R.id.cb);
                cb.setChecked(bean.isChecked);
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bean.isChecked=cb.isChecked();
                    }
                });
            }
        });
    }
}
