package com.com.x.yuangong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by X on 16/9/3.
 */
public class PowerManageVC extends BaseActivity {


    private ListView list;
    private PowerManageAdapter adapter;
    private List<Map<String, Object>> dataArr;

    private String[] titleArr = {"新用户开卡", "充值", "消费", "管理卡类", "岗位设置", "消息", "活动",
            "会员管理", "会员密码重置", "店铺设置", "员工管理"};


    @Override
    protected void setupUi() {

        setContentView(R.layout.power_manage);
        setPageTitle("权限管理");

        list = (ListView) findViewById(R.id.power_manage_list);
        dataArr = getData();
        // 获取MainListAdapter对象
        adapter = new PowerManageAdapter();
        // 将MainListAdapter对象传递给ListView视图
        list.setAdapter(adapter);
    }

    @Override
    protected void setupData() {

    }

    public void btnClick(View v)
    {

    }


    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for(String str : titleArr)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", str);
            map.put("checked",false);
            list.add(map);
        }

        return list;
    }


/**
 * 定义ListView适配器MainListViewAdapter
 */
class PowerManageAdapter extends BaseAdapter {

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ListItemView listItemView;

        // 初始化item view
        if (convertView == null) {
            // 通过LayoutInflater将xml中定义的视图实例化到一个View中
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.power_manage_cell, null);

            // 实例化一个封装类ListItemView，并实例化它的两个域
            listItemView = new ListItemView();
            listItemView.title = (TextView) convertView
                    .findViewById(R.id.power_manage_cell_title);
            listItemView.checkBox = (CheckBox) convertView
                    .findViewById(R.id.power_manage_cell_checkbox);

            // 将ListItemView对象传递给convertView
            convertView.setTag(listItemView);
        } else {
            // 从converView中获取ListItemView对象
            listItemView = (ListItemView) convertView.getTag();
        }

        // 获取到mList中指定索引位置的资源
        listItemView.checkBox.setTag(position);

        listItemView.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                System.out.println("compoundButton.tag: "+compoundButton.getTag()+" b: "+b);

                dataArr.get(position).put("checked",b);

                adapter.notifyDataSetChanged();
            }
        });



        String title = (String) dataArr.get(position).get("title");
        boolean checked = (boolean)dataArr.get(position).get("checked");
        listItemView.title.setText(title);

        listItemView.checkBox.setChecked(checked);

        // 返回convertView对象
        return convertView;
    }

}

/**
 * 封装两个视图组件的类
 */
class ListItemView {
    TextView title;
    CheckBox checkBox;
}

}
