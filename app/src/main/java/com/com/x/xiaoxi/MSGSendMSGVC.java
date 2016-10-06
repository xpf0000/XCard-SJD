package com.com.x.xiaoxi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.com.x.card.ChooseCardTypeVC;
import com.com.x.user.FindPWVC;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;

/**
 * Created by X on 16/9/7.
 */
public class MSGSendMSGVC extends BaseActivity {

private TextView type;

    @Override
    protected void setupUi() {
        setContentView(R.layout.msg_sendmsg);
        setPageTitle("发布消息");

        type = (TextView)findViewById(R.id.msg_sendmsg_type);
    
    }

    @Override
    protected void setupData() {

    }

    public void doChooseType(View v)
    {
        Bundle bundle = new Bundle();
        bundle.putString("from","MSGSendMSGVC");

        presentVC(ChooseCardTypeVC.class,bundle);
    }

    public void btnClick(View v)
    {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 10)
        {
            Bundle b=data.getExtras(); //data为B中回传的Intent
            String str=b.getString("type");//str即为回传的值

            type.setText(str);
        }

    }
}
