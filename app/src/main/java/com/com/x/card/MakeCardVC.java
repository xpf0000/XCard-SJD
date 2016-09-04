package com.com.x.card;

import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.com.x.user.FindPWVC;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;

/**
 * Created by X on 16/9/4.
 */
public class MakeCardVC extends BaseActivity {

    private EditText edit;
    private LinearLayout notic;
    private TextView msg;
    private TextView btn;

    @Override
    protected void setupUi() {
        setContentView(R.layout.card_banka);
        setPageTitle("办卡");

        edit = (EditText)findViewById(R.id.card_make_edit);
        notic = (LinearLayout)findViewById(R.id.card_make_notic);
        msg = (TextView)findViewById(R.id.card_make_noticMsg);
        btn = (TextView)findViewById(R.id.card_make_btn);

        notic.setVisibility(View.GONE);

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String txt = edit.getText().toString();
                if(txt.length() == 11)
                {
                    msg.setText(txt+"暂不是会员,点击\"下一步\"办卡");
                    notic.setVisibility(View.VISIBLE);
                    btn.setBackgroundColor(ContextCompat.getColor(mContext, R.color.APPOrange));
                    btn.setClickable(true);
                }
                else
                {
                    btn.setBackgroundColor(ContextCompat.getColor(mContext, R.color.CardBtnGray));
                    btn.setClickable(false);
                    notic.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void setupData() {

    }

    public void btnClick(View v)
    {

    }

}
