package com.com.x.yuangong;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.com.x.user.FindPWVC;
import com.example.x.xcard.ApplicationClass;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.x.custom.XGetPhoto;

import java.io.File;

/**
 * Created by X on 16/9/4.
 */
public class AddYGVC extends BaseActivity {

    private ImageView header;

    @Override
    protected void setupUi() {
        setContentView(R.layout.yg_manage_add);
        setPageTitle("添加员工");

        header = (ImageView)findViewById(R.id.yg_manage_add_header);

    }

    @Override
    protected void setupData() {

    }

    public void headerClick(View v)
    {
        XGetPhoto.show(this, true, new XGetPhoto.onGetPhotoListener() {
            @Override
            public void getPhoto(Bitmap img) {

                header.setImageBitmap(img);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        XGetPhoto.handleResult(requestCode,resultCode,data);
    }




    public void btnClick(View v)
    {

    }

}