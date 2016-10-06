package com.com.x.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.com.x.AppModel.UserModel;
import com.example.x.xcard.ApplicationClass;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.x.custom.DensityUtil;
import com.x.custom.XAPPUtil;
import com.x.custom.XNetUtil;

import java.util.List;

import static com.example.x.xcard.ApplicationClass.APPService;

/**
 * Created by X on 16/9/8.
 */
public class ShopSetupVC extends BaseActivity {

    private EditText address;
    private EditText tel;
    private RoundedImageView img;
    private TextView name;
    private TextView type;

    String sid = ApplicationClass.APPDataCache.User.getShopid();

    @Override
    protected void setupUi() {
        setContentView(R.layout.shop_setup);
        setPageTitle("店铺设置");

        address = (EditText)findViewById(R.id.shop_setup_address);
        tel = (EditText)findViewById(R.id.shop_setup_tel);

        img = (RoundedImageView)findViewById(R.id.shop_setup_img);
        name = (TextView)findViewById(R.id.shop_setup_name);
        type = (TextView)findViewById(R.id.shop_setup_type);

        XNetUtil.Handle(APPService.shopdGetShopInfo(sid), new XNetUtil.OnHttpResult<List<UserModel>>() {
            @Override
            public void onError(Throwable e) {
                doShowToast(e.toString());
            }

            @Override
            public void onSuccess(List<UserModel> userModels) {

                if(userModels.size() > 0)
                {
                    UserModel model = userModels.get(0);
                    ApplicationClass.APPDataCache.User.setLogo(model.getLogo());
                    ApplicationClass.APPDataCache.User.setShopname(model.getShopname());
                    ApplicationClass.APPDataCache.User.setTel(model.getTel());
                    ApplicationClass.APPDataCache.User.setAddress(model.getAddress());
                    ApplicationClass.APPDataCache.User.setInfo(model.getInfo());

                    ApplicationClass.APPDataCache.User.save();

                    show();

                }




            }
        });

    }

    private void show()
    {
        ImageLoader.getInstance().displayImage(ApplicationClass.APPDataCache.User.getLogo(),img);
        name.setText(ApplicationClass.APPDataCache.User.getShopname());
        type.setText(ApplicationClass.APPDataCache.User.getShopcategory());
        address.setText(ApplicationClass.APPDataCache.User.getAddress());
        tel.setText(ApplicationClass.APPDataCache.User.getTel());
    }

    @Override
    protected void setupData() {

    }

    public void submit(final View v)
    {
        if(!XAPPUtil.isNull(address) || !XAPPUtil.isNull(tel))
        {
            return;
        }

        final String t = tel.getText().toString().trim();
        final String a = address.getText().toString().trim();

        v.setEnabled(false);

        XNetUtil.Handle(APPService.shopdUpdateShopInfo(sid,a,t), "信息修改成功", "信息修改失败", new XNetUtil.OnHttpResult<Boolean>() {
            @Override
            public void onError(Throwable e) {
                XNetUtil.APPPrintln(e);
                v.setEnabled(true);
            }

            @Override
            public void onSuccess(Boolean aBoolean) {
                v.setEnabled(true);
            }
        });
    }
}
