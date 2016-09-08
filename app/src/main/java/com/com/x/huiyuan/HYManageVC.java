package com.com.x.huiyuan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.x.custom.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by X on 16/9/5.
 */
public class HYManageVC extends BaseActivity {

    private ListView list;
    private HYManageAdapter adapter;
    private List<Map<String, Object>> dataArr;

    private int selectRow = -1;

    @Override
    protected void setupUi() {
        setContentView(R.layout.hy_manage);
        setPageTitle("会员管理");
        setRightImg(R.drawable.add);
        int p = DensityUtil.dip2px(mContext,7);
        setRightImgPadding(p,p,p,p);

        list = (ListView)findViewById(R.id.hy_manage_list);

        dataArr = getData();
        // 获取MainListAdapter对象
        adapter = new HYManageAdapter();
        // 将MainListAdapter对象传递给ListView视图
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                selectRow = i;

                toInfo();
            }
        });

    }

    private void toInfo()
    {
        pushVC(HYUserInfoVC.class);
    }

    private void alertShow()
    {
        AlertView rightAlert = new AlertView(null, null, null, null,
                new String[]{"领卡", "编辑资料", "取消"},
                mContext, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                System.out.println("点击了: "+position);

            }
        });

        rightAlert.show();
    }

    @Override
    protected void setupData() {

    }

    @Override
    public void rightClick(View v) {
        super.rightClick(v);
        alertShow();
    }


    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for(int i = 0; i<20;i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "张无忌");
            map.put("tel", "18037975857");
            map.put("img", R.drawable.yg_header);
            map.put("no","NO."+(100020+i)+"");
            list.add(map);
        }

        return list;
    }


    /**
     * 定义ListView适配器MainListViewAdapter
     */
    class HYManageAdapter extends BaseAdapter {

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
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.hy_manage_cell, null);

                // 实例化一个封装类ListItemView，并实例化它的两个域
                listItemView = new ListItemView();
                listItemView.header = (ImageView) convertView
                        .findViewById(R.id.hy_manage_cell_img);
                listItemView.name = (TextView) convertView
                        .findViewById(R.id.hy_manage_cell_name);
                listItemView.tel = (TextView) convertView
                        .findViewById(R.id.hy_manage_cell_tel);
                listItemView.no = (TextView) convertView
                        .findViewById(R.id.hy_manage_cell_no);

                // 将ListItemView对象传递给convertView
                convertView.setTag(listItemView);
            } else {
                // 从converView中获取ListItemView对象
                listItemView = (ListItemView) convertView.getTag();
            }

            // 获取到mList中指定索引位置的资源
            int img = (int) dataArr.get(position).get("img");
            String name = (String) dataArr.get(position).get("name");
            String tel = (String) dataArr.get(position).get("tel");
            String no = (String) dataArr.get(position).get("no");

            // 将资源传递给ListItemView的两个域对象
            listItemView.header.setImageResource(img);
            //listItemView.imageView.setImageDrawable(img);
            listItemView.name.setText(name);
            listItemView.tel.setText(tel);
            listItemView.no.setText(no);

            // 返回convertView对象
            return convertView;
        }

    }

    /**
     * 封装两个视图组件的类
     */
    class ListItemView {
        ImageView header;
        TextView name;
        TextView tel;
        TextView no;
    }

}
