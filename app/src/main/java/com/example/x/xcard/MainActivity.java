package com.example.x.xcard;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.systemUiVisibility =View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().setAttributes(params);

        setContentView(R.layout.activity_main);

        doHideBackBtn();
        setRightImg(R.drawable.icon_user_white);
        setRightTxt("登录");

        setPageTitle("xxxpppfff");

    }

    @Override
    public void rightClick(View v) {
        System.out.println("点击右侧菜单~~~~~~~~");
        presentVC(TestVC.class);
    }

    public void btnClick(View v)
    {
        pushVC(TestVC.class);
    }

    @Override
    protected void setupData() {

    }

    @Override
    protected void setupUi() {

    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
