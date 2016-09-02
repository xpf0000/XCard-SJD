package com.com.x.yuangong;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.x.xcard.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by X on 16/9/2.
 */
public class YGManageLeft extends Fragment
{
    private ListView list;
    private SystemMsgAdapter adapter;
    private List<Map<String, Object>> dataArr;
    private Context context;

    /** Acitivity要实现这个接口，这样Fragment和Activity就可以共享事件触发的资源了 */
    public interface MyListener
    {
        public void showMessage(int index);
    }

    private MyListener myListener;

    @Override
    public void onAttach(Context context) {
        if (context instanceof MyListener) {
            myListener = (MyListener) context;
        }

        this.context = context;
        System.out.println("LeftFragment--->onAttach");


        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        System.out.println("LeftFragment--->onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        System.out.println("LeftFragment--->onCreateView");
        return inflater.inflate(R.layout.yg_manage_yg, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        System.out.println("LeftFragment--->onResume");

        list = (ListView) getActivity().findViewById(R.id.yg_manage_yg_list);

        dataArr = getData();

        // 获取MainListAdapter对象
        adapter = new SystemMsgAdapter();

        // 将MainListAdapter对象传递给ListView视图
        list.setAdapter(adapter);

    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for(int i = 0; i<20;i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", "系统通知"+i);
            map.put("info", "通知详情 "+i);
            map.put("orSee", false);
            map.put("img", R.drawable.system_msg_icon_u);
            list.add(map);
        }

        return list;
    }





    /**
     * 定义ListView适配器MainListViewAdapter
     */
    class SystemMsgAdapter extends BaseAdapter {

        /**
         * 返回item的个数
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return dataArr.size();
        }

        /**
         * 返回item的内容
         */
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return dataArr.get(position);
        }

        /**
         * 返回item的id
         */
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        /**
         * 返回item的视图
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListItemView listItemView;

            // 初始化item view
            if (convertView == null) {
                // 通过LayoutInflater将xml中定义的视图实例化到一个View中
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.system_msg_cell, null);

                // 实例化一个封装类ListItemView，并实例化它的两个域
                listItemView = new ListItemView();
                listItemView.imageView = (ImageView) convertView
                        .findViewById(R.id.system_msg_cell_img);
                listItemView.title = (TextView) convertView
                        .findViewById(R.id.system_msg_cell_title);
                listItemView.info = (TextView) convertView
                        .findViewById(R.id.system_msg_cell_info);
                listItemView.time = (TextView) convertView
                        .findViewById(R.id.system_msg_cell_time);

                // 将ListItemView对象传递给convertView
                convertView.setTag(listItemView);
            } else {
                // 从converView中获取ListItemView对象
                listItemView = (ListItemView) convertView.getTag();
            }

            // 获取到mList中指定索引位置的资源
            int img = (int) dataArr.get(position).get("img");
            String title = (String) dataArr.get(position).get("title");
            String info = (String) dataArr.get(position).get("info");

            // 将资源传递给ListItemView的两个域对象
            listItemView.imageView.setImageResource(img);
            //listItemView.imageView.setImageDrawable(img);
            listItemView.title.setText(title);
            listItemView.info.setText(info);

            // 返回convertView对象
            return convertView;
        }

    }

    /**
     * 封装两个视图组件的类
     */
    class ListItemView {
        ImageView imageView;
        TextView title;
        TextView info;
        TextView time;
    }

}
