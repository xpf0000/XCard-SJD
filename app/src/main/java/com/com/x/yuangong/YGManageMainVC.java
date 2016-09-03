package com.com.x.yuangong;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.AlertViewAdapter;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.x.xcard.ApplicationClass;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.x.custom.XHorizontalMain;
import com.x.custom.XHorizontalMenu;

import org.w3c.dom.Text;

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

    private XHorizontalMenu menu;
    private XHorizontalMain main;
    private List<XHorizontalMenu.XHorizontalModel> dataArr;
    private ArrayList<View> mlistview = new ArrayList<View>(); //viewpager中的内容

    private AlertView mAlertViewExt;//窗口拓展例子
    private EditText etName;//拓展View内容
    private InputMethodManager imm;


    @Override
    protected void setupUi() {
        setContentView(R.layout.yg_manage_main);
        setPageTitle("员工管理");
        setRightImg(R.drawable.yg_add);

        main = (XHorizontalMain) findViewById(R.id.yg_manage_viewpager);
        menu = (XHorizontalMenu) findViewById(R.id.XHorizontalList);
        main.setMenu(menu);

        menu.setLineHeight(2)
                .setNormalTxtSize(17)
                .setSelectedTxtSize(19)
                .setCellInterval(30);

        dataArr = getData();

        menu.setData(dataArr);

        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        //拓展窗口
        mAlertViewExt = new AlertView("请输入岗位名称", null, null, null, new String[]{"确认添加","取消"}, this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {

                closeKeyboard();
                //判断是否是拓展窗口View，而且点击的是非取消按钮
                if(o == mAlertViewExt && position != AlertView.CANCELPOSITION){
                    String name = etName.getText().toString();
                    if(name.isEmpty()){
                        Toast.makeText(mContext, "啥都没填呢", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(mContext, "hello,"+name, Toast.LENGTH_SHORT).show();
                    }

                    return;
                }
                Toast.makeText(mContext, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();

            }
        });

        mAlertViewExt.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
                closeKeyboard();
                Toast.makeText(mContext, "消失了", Toast.LENGTH_SHORT).show();
            }
        });


        ViewGroup extView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertext_form,null);
        etName = (EditText) extView.findViewById(R.id.etName);
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                //输入框出来则往上移动
                boolean isOpen=imm.isActive();
                mAlertViewExt.setMarginBottom(isOpen&&focus ? 120 :0);
                System.out.println(isOpen);
            }
        });
        mAlertViewExt.addExtView(extView);


    }

    @Override
    protected void setupData() {

    }

    @Override
    public void rightClick(View v) {
        super.rightClick(v);

        if(menu.getSelected() == 1)
        {
            mAlertViewExt.show();

            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            ViewGroup decorView = (ViewGroup)getWindow().getDecorView().findViewById(android.R.id.content);

            //ViewGroup rootView = (ViewGroup) layoutInflater.inflate(com.bigkoo.alertview.R.layout.layout_alertview, decorView, false);

//            for(int i=0;i<decorView.getChildCount();i++)
//            {
//                System.out.println("view: "+((ViewGroup)decorView.getChildAt(i)).getChildCount());
//            }

            printAllSubView(decorView);

        }

    }

    private void printAllSubView(ViewGroup v)
    {
        for(int i=0;i<v.getChildCount();i++)
        {
            View temp = v.getChildAt(i);

            if (temp instanceof TextView)
            {
                TextView tv = (TextView) temp;
                if(tv.getText().equals("取消"))
                {
                    tv.setTextColor(ContextCompat.getColor(mContext, R.color.APPTXTBlack));
                    System.out.println("view: "+temp);
                }
            }




            if (temp instanceof ViewGroup)
            {
                printAllSubView((ViewGroup)temp);
            }


        }
    }


    private void closeKeyboard() {
        //关闭软键盘
        imm.hideSoftInputFromWindow(etName.getWindowToken(),0);
        //恢复位置
        mAlertViewExt.setMarginBottom(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            if(mAlertViewExt != null && mAlertViewExt.isShowing()){
                mAlertViewExt.dismiss();
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    private List<XHorizontalMenu.XHorizontalModel> getData() {
        List<XHorizontalMenu.XHorizontalModel> list = new ArrayList<XHorizontalMenu.XHorizontalModel>();

        XHorizontalMenu.XHorizontalModel model = new XHorizontalMenu.XHorizontalModel();
        model.setTitle("员工");
        model.setView(new YGManageLeft());
        list.add(model);

        XHorizontalMenu.XHorizontalModel model1 = new XHorizontalMenu.XHorizontalModel();
        model1.setTitle("岗位管理");
        model1.setView(new GWManageRight());
        list.add(model1);

        return list;
    }






}
