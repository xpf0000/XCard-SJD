package com.com.x.card;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;

import org.w3c.dom.Text;

/**
 * Created by X on 16/9/5.
 */
public class CZInfoVC extends BaseActivity {

    private String type = "充值卡";
    private ImageView img;
    private TextView nameTv;
    private TextView telTv;
    private TextView typeTv;

    @Override
    protected void setupUi() {
        setContentView(R.layout.card_cz_info);
        setPageTitle("充值");

        img = (ImageView)findViewById(R.id.card_cz_info_img);
        nameTv = (TextView) findViewById(R.id.card_cz_info_name);
        telTv = (TextView)findViewById(R.id.card_cz_info_tel);
        typeTv = (TextView)findViewById(R.id.card_cz_info_type);


        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null && bundle.containsKey("type"))
        {
            type = bundle.getString("type");
            typeTv.setText("卡类型："+type);
        }

        int src = R.drawable.card_type0;

        switch (type)
        {
            case "充值卡" :
                src = R.drawable.card_type0;
                break;
            case "积分卡" :
                src = R.drawable.card_type1;
                break;
            case "计次卡" :
                src = R.drawable.card_type2;
                break;
            case "打折卡" :
                src = R.drawable.card_type3;
                break;
            default:
                break;

        }

        img.setImageResource(src);


    }

    @Override
    protected void setupData() {

    }


    public void btnClick(View v)
    {

    }

}
