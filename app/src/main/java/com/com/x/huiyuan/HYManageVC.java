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
import com.com.x.AppModel.MemberModel;
import com.com.x.huodong.HDManageVC;
import com.example.x.xcard.ApplicationClass;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.x.custom.DensityUtil;
import com.x.custom.XNetUtil;
import com.x.custom.XNotificationCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by X on 16/9/5.
 */
public class HYManageVC extends BaseActivity {

    private PullToRefreshListView list;
    private HYManageAdapter adapter;
    private List<MemberModel> dataArr;

    private int page = 1;
    private boolean end = false;
    String sid = ApplicationClass.APPDataCache.User.getShopid();

    private int selectRow = -1;

    @Override
    protected void setupUi() {
        setContentView(R.layout.hy_manage);
        setPageTitle("会员管理");

        list = (PullToRefreshListView)findViewById(R.id.hy_manage_list);

        adapter = new HYManageAdapter();

        list.setAdapter(adapter);

        list.setMode(PullToRefreshBase.Mode.BOTH);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                selectRow = i;

                toInfo();
            }
        });

        list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                XNetUtil.APPPrintln("onPullDownToRefresh ~~~~~~~~~");

                //new FinishRefresh().execute();
                page = 1;
                end = false;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                XNetUtil.APPPrintln("onPullUpToRefresh ~~~~~~~~~");
                getData();

            }
        });

        getData();

    }

    private void toInfo()
    {
        pushVC(HYUserInfoVC.class);
    }



    @Override
    protected void setupData() {

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
