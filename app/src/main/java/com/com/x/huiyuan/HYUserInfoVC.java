package com.com.x.huiyuan;

import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.com.x.card.CZManagerVC;
import com.com.x.card.XFManageVC;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.x.custom.DensityUtil;

/**
 * Created by X on 16/9/6.
 */
public class HYUserInfoVC extends BaseActivity implements PopupMenu.OnMenuItemClickListener {

    @Override
    protected void setupUi() {
        setContentView(R.layout.hy_userinfo);
        setPageTitle("会员详情");
        setRightImg(R.drawable.right_more);

        int p = DensityUtil.dip2px(mContext,7);

        setRightImgPadding(p,p,p,p);


    }

    @Override
    protected void setupData() {

    }

    @Override
    public void rightClick(View v) {
        super.rightClick(v);

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.hy_useinfo_popmenu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

//        if(item.getItemId() ==  R.id.hy_userinfo_menu_bj)
//        {
//            pushVC(CZManagerVC.class);
//        }
//        else if(item.getItemId() ==  R.id.hy_userinfo_menu_lk)
//        {
//            pushVC(XFManageVC.class);
//        }

        return false;
    }
}
