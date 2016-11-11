package com.example.x.xcard;

import android.content.Context;
import android.content.Intent;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.x.custom.XNotificationCenter;

import java.util.Map;

import static com.example.x.xcard.ApplicationClass.APPDataCache;

/**
 * Created by admins on 2016/8/9.
 */
public class Message extends MessageReceiver {

    @Override
    protected void onMessage(Context context, CPushMessage cPushMessage) {
        super.onMessage(context, cPushMessage);
        System.out.println("cPushMessage:"+cPushMessage.getTitle()+" | "+cPushMessage.getContent());

        if(cPushMessage.getTitle().equals("账号在其它设备已登陆"))
        {
            XNotificationCenter.getInstance().postNotice("AccountLogout",null);
        }

    }

    @Override
    protected void onNotification(Context context, String title, String info, Map<String, String> map) {
        super.onNotification(context, title, info, map);
        System.out.println("cPush title:"+title+" | info: "+info);

    }


}
