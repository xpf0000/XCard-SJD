package com.x.custom;

import android.content.Context;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.x.xcard.ApplicationClass;

/**
 * Created by X on 2016/10/2.
 */

public class XActivityindicator {

    private static SVProgressHUD hud;

    public static SVProgressHUD getHud() {
        return hud;
    }

    public static SVProgressHUD create(Context context)
    {
        if(hud != null)
        {
            hud.dismissImmediately();
            hud = null;
        }

        XNetUtil.APPPrintln("ApplicationClass.context: "+ApplicationClass.context);

        hud = new SVProgressHUD(context);

        XNetUtil.APPPrintln("hud: "+hud);

        hud.getProgressBar().setRoundWidth(DensityUtil.dip2px(ApplicationClass.context,1));



        return hud;
    }

}
