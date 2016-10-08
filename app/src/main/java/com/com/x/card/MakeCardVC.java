package com.com.x.card;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.com.x.AppModel.UserModel;
import com.com.x.user.FindPWVC;
import com.example.x.xcard.ApplicationClass;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.x.custom.XNetUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.x.xcard.ApplicationClass.APPService;

/**
 * Created by X on 16/9/4.
 */
public class MakeCardVC extends BaseActivity {

    private EditText edit;
    private LinearLayout notic;
    private TextView msg;
    private TextView btn;
    private ImageView icon;
    private String sid = ApplicationClass.APPDataCache.User.getShopid();

    private String title = "办卡";

    private UserModel user;

    @Override
    protected void setupUi() {
        setContentView(R.layout.card_banka);
        setPageTitle(title);

        edit = (EditText)findViewById(R.id.card_make_edit);
        notic = (LinearLayout)findViewById(R.id.card_make_notic);
        msg = (TextView)findViewById(R.id.card_make_noticMsg);
        btn = (TextView)findViewById(R.id.card_make_btn);
        icon = (ImageView)findViewById(R.id.card_banka_icon);
        notic.setVisibility(View.GONE);

        decorView.setSystemUiVisibility(0);
        //setFullScreen();

        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus(); //edittext是一个EditText控件
        Timer timer = new Timer(); //设置定时器
        timer.schedule(new TimerTask() {
            @Override
            public void run() { //弹出软键盘的代码
                InputMethodManager imm = (InputMethodManager)getSystemService(mContext.INPUT_METHOD_SERVICE);
                imm.showSoftInputFromInputMethod(edit.getWindowToken(), 0);
            }
        }, 1000); //设置300毫秒的时长


        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                checkUser();
            }
        });

    }

    private void checkUser()
    {
        String txt = edit.getText().toString().trim();
        if(txt.length() == 11)
        {
            XNetUtil.Handle(APPService.shopdGetUserInfoM(txt, sid), new XNetUtil.OnHttpResult<List<UserModel>>() {

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onSuccess(List<UserModel> userModels) {
                    notic.setVisibility(View.VISIBLE);

                    if(userModels.size() > 0)
                    {
                        user = userModels.get(0);
                        msg.setText("会员编号: NO."+user.getUid()+", 姓名: "+user.getTruename());
                        btn.setBackgroundColor(ContextCompat.getColor(mContext, R.color.APPOrange));
                        btn.setClickable(true);
                    }
                    else
                    {
                        msg.setText("该手机号码暂时不是会员, 请先注册为怀府网会员");
                        btn.setBackgroundColor(ContextCompat.getColor(mContext, R.color.CardBtnGray));
                        btn.setClickable(false);
                    }

                }
            });
        }
        else
        {
            user = null;
            btn.setBackgroundColor(ContextCompat.getColor(mContext, R.color.CardBtnGray));
            btn.setClickable(false);
            notic.setVisibility(View.GONE);
        }

    }

    @Override
    protected void setupData() {



    }

    public void btnClick(View v)
    {
        String txt = edit.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("tel",txt);
        pushVC(ChooseCardTypeVC.class,bundle);
    }

}
