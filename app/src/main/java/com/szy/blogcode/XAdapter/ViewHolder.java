package com.szy.blogcode.xadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2015/12/11 14:54
 */
public class ViewHolder {

    private View mConvertview;
    private SparseArray<View> mViews;
    protected static int mPosition;

    public ViewHolder(Context context,int position, ViewGroup parent,int layoutId){
        mConvertview= LayoutInflater.from(context).inflate(layoutId,parent,false);
        mConvertview.setTag(this);
        mViews=new SparseArray<>();

    }

    public View getConvertView(){
        return mConvertview;
    }
    public static ViewHolder getHolder(Context context,int position, View convertView, ViewGroup parent,int layoutId){
        mPosition=position;
        if (convertView==null){
            return new ViewHolder(context,position,parent,layoutId);
        }else {
            return (ViewHolder) convertView.getTag();
        }
    }


    public View getViewById(int viewId){
        View view=mViews.get(viewId);
        if (view==null){
            view=mConvertview.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return view;
    }

    public ViewHolder setText(int viewId,String text){
        TextView tv= (TextView) getViewById(viewId);
        tv.setText(text);
        return this;
    }

}
