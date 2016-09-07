package com.com.x.huodong;

import android.view.View;

import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.x.custom.DensityUtil;
import com.x.custom.XHorizontalMain;
import com.x.custom.XHorizontalMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by X on 16/9/6.
 */
public class HDManageVC extends BaseActivity {

    private XHorizontalMenu menu;
    private XHorizontalMain main;
    private List<XHorizontalMenu.XHorizontalModel> dataArr;

    @Override
    protected void setupUi() {
        setContentView(R.layout.hd_manage);
        setPageTitle("活动管理");
        setRightImg(R.drawable.add);
        int p = DensityUtil.dip2px(mContext,7);
        setRightImgPadding(p,p,p,p);


        main = (XHorizontalMain) findViewById(R.id.hd_manage_main);
        menu = (XHorizontalMenu) findViewById(R.id.hd_manage_menu);
        main.setMenu(menu);

        menu.setLineHeight(2)
                .setNormalTxtSize(17)
                .setSelectedTxtSize(19)
                .setOnePageNum(4);

        dataArr = getData();

        menu.setData(dataArr);



    }

    @Override
    protected void setupData() {

    }


    @Override
    public void rightClick(View v) {
        super.rightClick(v);
        pushVC(HDCreateVC.class);
    }

    private List<XHorizontalMenu.XHorizontalModel> getData() {
        List<XHorizontalMenu.XHorizontalModel> list = new ArrayList<XHorizontalMenu.XHorizontalModel>();

        XHorizontalMenu.XHorizontalModel model = new XHorizontalMenu.XHorizontalModel();
        model.setTitle("全部");
        model.setView(new HDManageFragment());
        list.add(model);

        XHorizontalMenu.XHorizontalModel model1 = new XHorizontalMenu.XHorizontalModel();
        model1.setTitle("进行中");
        model1.setView(new HDManageFragment());
        list.add(model1);

        XHorizontalMenu.XHorizontalModel model2 = new XHorizontalMenu.XHorizontalModel();
        model2.setTitle("未开始");
        model2.setView(new HDManageFragment());
        list.add(model2);

        XHorizontalMenu.XHorizontalModel model3 = new XHorizontalMenu.XHorizontalModel();
        model3.setTitle("已结束");
        model3.setView(new HDManageFragment());
        list.add(model3);


        return list;
    }






}