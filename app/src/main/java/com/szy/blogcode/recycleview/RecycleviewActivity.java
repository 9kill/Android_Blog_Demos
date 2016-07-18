package com.szy.blogcode.recycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.szy.blogcode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍RecylerView常见用法，包含了：

 1.系统提供了几种LayoutManager的使用；
 2.如何通过自定义ItemDecoration去设置分割线，或者一些你想作为分隔的drawable，注意这里
 巧妙的使用了系统的listDivider属性，你可以尝试添加使用divider和dividerHeight属性。
 3.如何使用ItemAnimator为RecylerView去添加Item移除、添加的动画效果。
 4.介绍了如何添加ItemClickListener与ItemLongClickListener。

 可以看到RecyclerView可以实现：
 1.ListView的功能
 2.GridView的功能
 3.横向ListView的功能，参考Android 自定义RecyclerView 实现真正的Gallery效果
 4.横向ScrollView的功能
 5.瀑布流效果
 6.便于添加Item增加和移除动画
 */
public class RecycleviewActivity extends AppCompatActivity {

    private StaggeredHomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        List<String> datas=initData();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        //LayoutManager 布局显示管理器
//        LinearLayoutManager layoutManager= new LinearLayoutManager(this);// LinearLayoutManager 现行管理器，支持横向、纵向。
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        GridLayoutManager layoutManager=new GridLayoutManager(this,4);//GridLayoutManager 网格布局管理器
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);//StaggeredGridLayoutManager 瀑布就式布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //ItemDecoration item分割线
//        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST);
//        RecyclerView.ItemDecoration itemDecoration=new DividerGridItemDecoration(this);
//        recyclerView.addItemDecoration(itemDecoration);
        //ItemAnimator   item增删动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setItemAnimator(new SlideInOutLeftItemAnimator(recyclerView));
        mAdapter=new StaggeredHomeAdapter(this,datas);
//        recyclerView.setAdapter(new MyAdapter(datas));
        recyclerView.setAdapter(mAdapter);

    }

    private List<String> initData() {
        List<String> datas = new ArrayList<>();
        for (int i='A';i<='Z';i++) {
            datas.add(""+(char)i);
        }
        return datas;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycleview,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_add:
                mAdapter.addData(1);
                break;
            case R.id.id_action_delete:
                mAdapter.removeData(1);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        private List<String> mDatas;
        private List<Integer> mHeights=new ArrayList<>();
        private MyAdapter(List<String> datas){
            mDatas=datas;
            for (int i=0;i<mDatas.size();i++) {
                mHeights.add((int) (100+Math.random()*300));
            }
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(RecycleviewActivity.this).inflate(R.layout.item_cheese, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) holder.name.getLayoutParams();
            params.height=mHeights.get(position);
            holder.name.setLayoutParams(params);
            holder.name.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public void addData(int position)
        {
            mDatas.add(position, "Insert One");
            notifyItemInserted(position);
        }


        public void removeData(int position)
        {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

}
