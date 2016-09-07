package com.com.x.card;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.com.x.user.FindPWVC;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;

/**
 * Created by X on 16/9/4.
 */
public class ChooseCardTypeVC extends BaseActivity {

    private String from = "";

    @Override
    protected void setupUi() {
        setContentView(R.layout.card_choosetype);
        setPageTitle("卡选择");

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null && bundle.containsKey("from"))
        {
            from = bundle.getString("from");
        }

    }

    @Override
    protected void setupData() {

    }

    public void doChoose(View v)
    {
        String tag = (String) v.getTag();

        switch (from)
        {
            case "MSGSendMSGVC":

                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("type", tag);

                setResult(10, intent);
                doPop();
                break;
            default:
                Bundle bundle = new Bundle();
                bundle.putString("type",tag);
                pushVC(InputCardInfoVC.class,bundle);
                System.out.println("choose tag: "+tag);
                break;
        }


    }

}