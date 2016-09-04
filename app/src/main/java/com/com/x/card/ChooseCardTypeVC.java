package com.com.x.card;

import android.view.View;

import com.com.x.user.FindPWVC;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;

/**
 * Created by X on 16/9/4.
 */
public class ChooseCardTypeVC extends BaseActivity {

    @Override
    protected void setupUi() {
        setContentView(R.layout.card_choosetype);
        setPageTitle("卡选择");

    }

    @Override
    protected void setupData() {

    }

    public void doChoose(View v)
    {
        String tag = (String) v.getTag();

        System.out.println("choose tag: "+tag);
    }

}