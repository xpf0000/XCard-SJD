package com.com.x.yuangong;

import android.content.Context;
import android.os.Bundle;
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
import com.example.x.xcard.R;
import com.x.custom.XHorizontalBaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by X on 16/9/3.
 */
public class GWManageRight extends XHorizontalBaseFragment
{
    private ListView list;
    private GWManageYGAdapter adapter;
    public List<Map<String, Object>> dataArr;
    private Context context;

    public int selectIndex = 0;
    private int doType = -1;

    public void refresh()
    {
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void lazyLoad() {

        System.out.println("RightFragment--->lazyLoad !!!");

        dataArr = getData();
        // 获取MainListAdapter对象
        adapter = new GWManageYGAdapter();
        // 将MainListAdapter对象传递给ListView视图

        if(list != null)
        {
            list.setAdapter(adapter);
        }

    }

    /** Acitivity要实现这个接口，这样Fragment和Activity就可以共享事件触发的资源了 */
    public interface MyListener
    {
        public void showMessage(int index);
    }

    private MyListener myListener;

    public void setMyListener(MyListener m)
    {
        myListener = m;
    }


    private void rightAlertShow()
    {
        AlertView rightAlert = new AlertView(null, null, null, null,
                new String[]{"修改岗位名称", "修改岗位权限", "取消"},
                context, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                System.out.println("点击了: "+position);
                doType = position;
            }
        });

        rightAlert.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {

                if(myListener != null)
                {
                    myListener.showMessage(doType);
                }
            }
        });

        rightAlert.show();
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof MyListener) {
            myListener = (MyListener) context;
        }

        this.context = context;
        System.out.println("RightFragment--->onAttach");


        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        System.out.println("RightFragment--->onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        System.out.println("RightFragment--->onCreateView");
        View v = inflater.inflate(R.layout.yg_manage_gw, container, false);
        list = (ListView) v.findViewById(R.id.yg_manage_gw_list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectIndex = i;

                rightAlertShow();
            }
        });

        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        System.out.println("RightFragment--->onResume");
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for(int i = 0; i<10;i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", "经理");
            list.add(map);
        }

        return list;
    }





    /**
     * 定义ListView适配器MainListViewAdapter
     */
    class GWManageYGAdapter extends BaseAdapter {

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
                        R.layout.yg_manage_gw_cell, null);

                // 实例化一个封装类ListItemView，并实例化它的两个域
                listItemView = new ListItemView();
                listItemView.title = (TextView) convertView
                        .findViewById(R.id.yg_manage_gw_cell_title);

                // 将ListItemView对象传递给convertView
                convertView.setTag(listItemView);
            } else {
                // 从converView中获取ListItemView对象
                listItemView = (ListItemView) convertView.getTag();
            }

            // 获取到mList中指定索引位置的资源
            String title = (String) dataArr.get(position).get("title");

            listItemView.title.setText(title);

            // 返回convertView对象
            return convertView;
        }

    }

    /**
     * 封装两个视图组件的类
     */
    class ListItemView {
        TextView title;
    }

}
