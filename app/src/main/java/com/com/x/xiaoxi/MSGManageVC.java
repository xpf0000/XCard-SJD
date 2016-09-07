package com.com.x.xiaoxi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.x.custom.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by X on 16/9/7.
 */
public class MSGManageVC extends BaseActivity {

    private ListView list;
    private MSGManageAdapter adapter;
    private List<Map<String, Object>> dataArr;

    @Override
    protected void setupUi() {
        setContentView(R.layout.msg_manage);
        setPageTitle("消息管理");

        setRightImg(R.drawable.add);
        int p = DensityUtil.dip2px(mContext,7);
        setRightImgPadding(p,p,p,p);

        list = (ListView) findViewById(R.id.msg_manage_list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        dataArr = getData();


        // 获取MainListAdapter对象
        adapter = new MSGManageAdapter();

        // 将MainListAdapter对象传递给ListView视图
        list.setAdapter(adapter);

//        SimpleAdapter adapter = new SimpleAdapter(this,dataArr,R.layout.system_msg_cell,
//                new String[]{"title","info","img"},
//                new int[]{R.id.title,R.id.info,R.id.img});
//
//        list.setAdapter(adapter);

    }

    @Override
    protected void setupData() {

    }

    @Override
    public void rightClick(View v) {
        super.rightClick(v);
        pushVC(MSGSendMSGVC.class);

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
    class MSGManageAdapter extends BaseAdapter {

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
                convertView = LayoutInflater.from(MSGManageVC.this).inflate(
                        R.layout.msg_manage_cell, null);

                // 实例化一个封装类ListItemView，并实例化它的两个域
                listItemView = new ListItemView();
                listItemView.imageView = (ImageView) convertView
                        .findViewById(R.id.msg_manage_cell_img);
                listItemView.title = (TextView) convertView
                        .findViewById(R.id.msg_manage_cell_title);
                listItemView.time = (TextView) convertView
                        .findViewById(R.id.msg_manage_cell_time);

                // 将ListItemView对象传递给convertView
                convertView.setTag(listItemView);
            } else {
                // 从converView中获取ListItemView对象
                listItemView = (ListItemView) convertView.getTag();
            }

//            // 获取到mList中指定索引位置的资源
//            int img = (int) dataArr.get(position).get("img");
//            String title = (String) dataArr.get(position).get("title");
//            String info = (String) dataArr.get(position).get("info");
//
//            // 将资源传递给ListItemView的两个域对象
//            listItemView.imageView.setImageResource(img);
//            //listItemView.imageView.setImageDrawable(img);
//            listItemView.title.setText(title);
//            listItemView.info.setText(info);

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
        TextView time;
    }

}