package com.com.x.yuangong;

import android.app.LocalActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.x.xcard.ApplicationClass;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.x.custom.XHorizontalMain;
import com.x.custom.XHorizontalMenu;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by X on 16/9/2.
 */
public class YGManageMainVC extends BaseActivity {

    private XHorizontalMenu listView;
    private XHorizontalMain viewPager;
    private List<XHorizontalMenu.XHorizontalModel> dataArr;
    private ArrayList<View> mlistview = new ArrayList<View>(); //viewpager中的内容


    @Override
    protected void setupUi() {
        setContentView(R.layout.yg_manage_main);
        setPageTitle("员工管理");
        setRightImg(R.drawable.yg_add);

        viewPager = (XHorizontalMain) findViewById(R.id.yg_manage_viewpager);
        listView = (XHorizontalMenu) findViewById(R.id.XHorizontalList);
        viewPager.setMenu(listView);

        listView.setLineHeight(2)
                .setNormalTxtSize(17)
                .setSelectedTxtSize(19)
                .setCellInterval(30);

        dataArr = getData();

        listView.setData(dataArr);


    }

    @Override
    protected void setupData() {

    }


    private List<XHorizontalMenu.XHorizontalModel> getData() {
        List<XHorizontalMenu.XHorizontalModel> list = new ArrayList<XHorizontalMenu.XHorizontalModel>();

        XHorizontalMenu.XHorizontalModel model = new XHorizontalMenu.XHorizontalModel();
        model.setTitle("员工");
        model.setView(new YGManageLeft());
        list.add(model);

        XHorizontalMenu.XHorizontalModel model1 = new XHorizontalMenu.XHorizontalModel();
        model1.setTitle("岗位管理");
        list.add(model1);

        for(int i=0;i<8;i++)
        {
            XHorizontalMenu.XHorizontalModel model2 = new XHorizontalMenu.XHorizontalModel();
            model2.setTitle("选项"+i);
            list.add(model2);
        }


        return list;
    }






}
