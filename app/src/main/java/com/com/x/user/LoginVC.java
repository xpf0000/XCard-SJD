package com.com.x.user;

import android.view.View;

import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;

/**
 * Created by X on 16/9/1.
 */
public class LoginVC extends BaseActivity {

    @Override
    protected void setupUi() {
        setContentView(R.layout.user_login);
        setPageTitle("登录");

    }

    @Override
    protected void setupData() {

    }

    public void toFindPWVC(View v)
    {
        pushVC(FindPWVC.class);
    }

}