package com.com.x.card;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;

/**
 * Created by X on 16/9/5.
 */
public class CZMainVC extends BaseActivity {

    private RadioButton radioButton;
    private String type = "充值卡";

    @Override
    protected void setupUi() {
        setContentView(R.layout.card_cz_main);
        setPageTitle("充值");

        radioButton = (RadioButton)findViewById(R.id.cz_main_radio0);




    }

    @Override
    protected void setupData() {

    }

    public void doChooseType(View v)
    {
        String tag = v.getTag().toString();

        if(!tag.equals(type))
        {
            radioButton.setChecked(false);
            radioButton = (RadioButton) v;
            type = tag;
        }

    }

    public void toNext(View v)
    {

    }

}