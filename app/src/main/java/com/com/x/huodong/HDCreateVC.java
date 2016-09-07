package com.com.x.huodong;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.x.custom.XGetPhoto;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by X on 16/9/7.
 */


@RuntimePermissions
public class HDCreateVC  extends BaseActivity {

    private Button header;

    @Override
    protected void setupUi() {
        setContentView(R.layout.hd_create);
        setPageTitle("发布活动");

        header = (Button) findViewById(R.id.hd_create_header);
    }

    @Override
    protected void setupData() {

    }

    public void doSubmit(View v)
    {

    }

    public void chooseImg(View v)
    {

    }

    public void chooseStartTime(View v)
    {

    }

    public void chooseEndTime(View v)
    {

    }

    public void chooseHeaderImg(View v)
    {
        HDCreateVCPermissionsDispatcher.MethodAWithCheck(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        XGetPhoto.handleResult(requestCode,resultCode,data);
    }


    @NeedsPermission(Manifest.permission.CAMERA)
    void MethodA() {

                XGetPhoto.show(this, new XGetPhoto.onGetPhotoListener() {
            @SuppressLint("NewApi")
            @Override
            public void getPhoto(Bitmap img) {

                Drawable drawable =new BitmapDrawable(getResources(), img);
                header.setBackground(drawable);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HDCreateVCPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);


    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void MethodB(final PermissionRequest request) {

        request.proceed();

//        new AlertDialog.Builder(this)
//                .setMessage("是否允许使用相机")
//                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        request.proceed();
//                    }
//                })
//                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        request.cancel();
//                    }
//                })
//                .show();

    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void MethodC() {

        Toast.makeText(this, "被拒绝了~~~", Toast.LENGTH_SHORT).show();

    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void MethodD() {

        Toast.makeText(this, "不再询问", Toast.LENGTH_SHORT).show();
    }
}
