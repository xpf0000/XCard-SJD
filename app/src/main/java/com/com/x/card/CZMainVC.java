package com.com.x.card;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;

/**
 * Created by X on 16/9/5.
 */
public class CZMainVC extends BaseActivity {

    private RadioButton radioButton;
    private String type = "充值卡";
    private String title = "";

    private EditText editText;
    private TextView tel;
    private TextView name;

    @Override
    protected void setupUi() {
        setContentView(R.layout.card_cz_main);

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null && bundle.containsKey("title"))
        {
            title = bundle.getString("title");
        }

        setPageTitle(title);

        radioButton = (RadioButton)findViewById(R.id.cz_main_radio0);

        editText = (EditText)findViewById(R.id.card_cz_main_edit);
        tel = (TextView)findViewById(R.id.card_cz_main_tel);
        name = (TextView)findViewById(R.id.card_cz_main_name);




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
        Bundle bundle = new Bundle();
        bundle.putString("type",type);
        bundle.putString("tel",tel.getText().toString());
        bundle.putString("name",name.getText().toString());

        switch (title)
        {
            case "消费":
                pushVC(XFInfoVC.class,bundle);
                break;
            case "充值":
                pushVC(CZInfoVC.class,bundle);
                break;
            default:
                break;
        }


    }

}