package com.szy.blogcode.xadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2015/12/11 15:24
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mDatas;
    private int mLayoutId;

    public CommonAdapter(Context context,List<T> datas,int layoutId){
        this.mContext=context;
        this.mDatas=datas;
        this.mLayoutId=layoutId;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=ViewHolder.getHolder(mContext,position,convertView,parent, mLayoutId);
        bindData(holder,getItem(position));
        return holder.getConvertView();
    }

    public abstract void bindData(ViewHolder holder,T t);
}
