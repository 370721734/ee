package com.jarhero790.eub.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.jarhero790.eub.R;


//为ExpandableListView自定义适配器
public class CommentExpandAdapter extends BaseExpandableListAdapter {

    private LayoutInflater layoutInflater;
    private String[] groups = {"钻视tv 今天10:00", "钻视tv 今天10:00", "钻视tv 今天10:00"};
    private String[][] childs={
            {"短视频直播电商好项目", "短视频直播电商好项目", "短视频直播电商好项目", "短视频直播电商好项目"},
            {"短视频直播电商好项目", "短视频直播电商好项目", "短视频直播电商好项目", "短视频直播电商好项目"},
            {"短视频直播电商好项目", "短视频直播电商好项目", "短视频直播电商好项目", "短视频直播电商好项目"},
            {"短视频直播电商好项目", "短视频直播电商好项目", "短视频直播电商好项目", "短视频直播电商好项目"},
            {"短视频直播电商好项目", "短视频直播电商好项目", "短视频直播电商好项目", "短视频直播电商好项目"}
            };

    public CommentExpandAdapter(LayoutInflater inflater){
       this.layoutInflater=inflater;
    }


    //返回一级列表的个数
    @Override
    public int getGroupCount() {
        return groups.length;
    }

    //返回每个二级列表的个数
    @Override
    public int getChildrenCount(int groupPosition) { //参数groupPosition表示第几个一级列表
        Log.d("smyhvae", "-->" + groupPosition);
        return childs[groupPosition].length;
    }

    //返回一级列表的单个item（返回的是对象）
    @Override
    public Object getGroup(int groupPosition) {
        return groups[groupPosition];
    }

    //返回二级列表中的单个item（返回的是对象）
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childs[groupPosition][childPosition];  //不要误写成groups[groupPosition][childPosition]
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //每个item的id是否是固定？一般为true
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //【重要】填充一级列表
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.pinglun_expandablelistview_item_group, null);
        } else {

        }
        TextView tv_group = convertView.findViewById(R.id.tv_name);
        tv_group.setText(groups[groupPosition]);
        return convertView;
    }

    //【重要】填充二级列表
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.pinglun_expandablelistview_item_child, null);
        }

        ImageView iv_icon = convertView.findViewById(R.id.iv_icon);
        TextView tv_name = convertView.findViewById(R.id.tv_name);
        TextView tv_content = convertView.findViewById(R.id.tv_content);

        tv_name.setText(childs[groupPosition][childPosition]);
        tv_content.setText(childs[groupPosition][childPosition]);
        return convertView;
    }

    //二级列表中的item是否能够被选中？可以改为true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}