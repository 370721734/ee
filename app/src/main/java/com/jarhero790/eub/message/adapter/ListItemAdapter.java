package com.jarhero790.eub.message.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4 0004.
 */

public abstract class ListItemAdapter<T> extends BaseAdapter {
    //1.实现方法
    //2.实现抽象
    //3.传入上下文
    protected Context context;
    protected List<T> list;

    //4.构造方法
    public ListItemAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
