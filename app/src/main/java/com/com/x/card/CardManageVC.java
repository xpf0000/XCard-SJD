package com.com.x.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.x.custom.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by X on 16/9/8.
 */
public class CardManageVC extends BaseActivity {

    @Override
    protected void setupUi() {
        setContentView(R.layout.card_manage);
        setPageTitle("卡管理");
        setRightImg(R.drawable.add);
        int p = DensityUtil.dip2px(mContext,7);
        setRightImgPadding(p,p,p,p);


    }

    @Override
    protected void setupData() {

    }

    @Override
    public void rightClick(View v) {
        super.rightClick(v);

        pushVC(CardAddVC.class);
    }

    public void doChoose(View v)
    {
        final String tag = (String) v.getTag();

        AlertView rightAlert = new AlertView(null, null, null, null,
                new String[]{"删除", "编辑", "取消"},
                mContext, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                System.out.println("点击了: "+position);

                if(position == 0)
                {
                    ViewGroup vg = (ViewGroup) getWindow().getDecorView();
                    hiddenViewByTag(vg,tag);
                }

            }
        });

        rightAlert.show();
    }



    private void hiddenViewByTag(ViewGroup v,String tag)
    {
        for(int i=0;i<v.getChildCount();i++)
        {
            View temp = v.getChildAt(i);

            if(temp.getTag() instanceof  String)
            {
                if(temp.getTag().toString().equals(tag))
                {
                    temp.setVisibility(View.GONE);
                    System.out.println("tag is 充值卡： "+temp);
                }
            }

            if (temp instanceof ViewGroup)
            {
                hiddenViewByTag((ViewGroup)temp,tag);
            }


        }
    }

}





