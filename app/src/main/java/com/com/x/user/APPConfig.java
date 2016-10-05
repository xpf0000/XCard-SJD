package com.com.x.user;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.x.xcard.ApplicationClass;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.x.custom.FileSizeUtil;
import com.x.custom.XAPPUtil;
import com.x.custom.XNetUtil;

/**
 * Created by X on 16/9/2.
 */
public class APPConfig extends BaseActivity {

    private TextView tel;
    private TextView cacheSize;
    private Button btn;

    @Override
    protected void setupUi() {
        setContentView(R.layout.app_config);
        setPageTitle("设置");

        tel = (TextView)findViewById(R.id.app_config_tel);
        cacheSize = (TextView)findViewById(R.id.app_config_size);
        btn = (Button)findViewById(R.id.app_config_btn);

    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshUI();
    }

    private void refreshUI()
    {
        double size = FileSizeUtil.getFileOrFilesSize(ImageLoader.getInstance().getDiskCache().getDirectory().getPath(),3);

        cacheSize.setText(size+"M");

        XNetUtil.APPPrintln("Cache Size: "+size);

        if(XAPPUtil.APPCheckIsLogin())
        {
            String tel = ApplicationClass.APPDataCache.User.getMobile();
            this.tel.setText(tel.substring(0,3)+"****"+tel.substring(7,11));
            btn.setVisibility(View.VISIBLE);
        }
        else
        {
            tel.setText("请先登录");
            btn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void setupData() {

    }


    public void cleanCache(View v){
        ImageLoader.getInstance().getDiskCache().clear();
        double size = FileSizeUtil.getFileOrFilesSize(ImageLoader.getInstance().getDiskCache().getDirectory().getPath(),3);
        cacheSize.setText(size+"M");
    }

    public void toChangePass(View v){
        //pushVC();
        pushVC(UpdatePassVC.class);
    }

    public void toUpdateTel(View v)
    {
        pushVC(UpdateTelVC.class);
    }

    public void btnClick(View v)
    {
        pushVC(FindPWVC.class);
    }

}
