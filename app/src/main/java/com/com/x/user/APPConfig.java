package com.com.x.user;

import android.view.View;

import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;

/**
 * Created by X on 16/9/2.
 */
public class APPConfig extends BaseActivity {

    @Override
    protected void setupUi() {
        setContentView(R.layout.app_config);
        setPageTitle("设置");

    }

    @Override
    protected void setupData() {

    }

    public void toChangePass(View v){
        //pushVC();
    }

    public void btnClick(View v)
    {
        pushVC(FindPWVC.class);
    }

}
