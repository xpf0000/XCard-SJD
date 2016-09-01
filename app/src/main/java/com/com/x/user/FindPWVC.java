package com.com.x.user;

import android.view.View;

import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;

/**
 * Created by X on 16/9/1.
 */
public class FindPWVC extends BaseActivity {

    @Override
    protected void setupUi() {
        setContentView(R.layout.user_findpw);
        setPageTitle("找回密码");

    }

    @Override
    protected void setupData() {

    }

    public void toFindPWNewVC(View v)
    {
        pushVC(FindPWNewVC.class);
    }

}