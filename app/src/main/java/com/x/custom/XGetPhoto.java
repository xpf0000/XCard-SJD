package com.x.custom;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.x.xcard.Properties;

import java.io.File;

/**
 * Created by X on 16/9/4.
 */
public class XGetPhoto implements Properties {

    private static boolean allowEdit = false;

    private static Activity activity;

    /** Acitivity要实现这个接口，这样Fragment和Activity就可以共享事件触发的资源了 */
    public interface onGetPhotoListener
    {
        void getPhoto(Bitmap img);
    }

    private static onGetPhotoListener myListener;

    public XGetPhoto() {

    }

    public static void show(Context context,boolean canEdit,onGetPhotoListener listener) {

        allowEdit = canEdit;
        show(context,listener);
    }


    public static void show(Context context,onGetPhotoListener listener)
    {
        if(context instanceof Activity)
        {
            activity = (Activity)context;
        }
        else {
            Toast.makeText(context, "只能在Activity中调用此方法", Toast.LENGTH_LONG).show();
            return;
        }

        myListener = listener;

        new AlertView("上传头像", null, "取消", null,
                new String[]{"拍照", "从相册中选择"},
                activity, AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {

                System.out.println("click postion: "+position);

                switch (position)
                {
                    case 0:
                        getImageFromCamera();
                        break;
                    case 1:
                        getImageFromAlbum();
                        break;
                    default:
                        break;
                }


            }
        }).show();
    }


    protected static void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        intent
                .setAction(Intent.ACTION_GET_CONTENT);

        activity.startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    protected static void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");

            getImageByCamera.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(activity.getExternalFilesDir(""), IMAGE_FILE_NAME)));

            activity.startActivityForResult(getImageByCamera, CAMERA_REQUEST_CODE);
        }
        else {
            Toast.makeText(activity, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    public static void handleResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == IMAGE_REQUEST_CODE) {

            File temp1 = new File(getPath(activity,data.getData()));//兼容写法，不同手机版本路径不同
            Uri uri = Uri.fromFile(temp1);
            if(allowEdit)
            {
                startPhotoZoom(uri);
            }
            else
            {
                Bitmap img = getBitmapFromUri(uri);
                if(img != null && myListener != null)
                {
                    myListener.getPhoto(img);
                }
            }

        }
        else if (requestCode == CAMERA_REQUEST_CODE ) {

            File temp = new File(activity.getExternalFilesDir("") + "/" + IMAGE_FILE_NAME);
            Uri uri = Uri.fromFile(temp);

            if(allowEdit)
            {
                startPhotoZoom(uri);
            }
            else
            {
                Bitmap img = getBitmapFromUri(uri);
                if(img != null && myListener != null)
                {
                    myListener.getPhoto(img);
                }
            }
        }
        else if(requestCode == RESULT_REQUEST_CODE)
        {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap img = extras.getParcelable("data");
                if(img != null && myListener != null)
                {
                    myListener.getPhoto(img);
                }
            }
        }

        allowEdit = false;
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public static void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高 有些手机不能支持太高，可写320等
        intent.putExtra("outputX", 640);
        intent.putExtra("outputY", 640);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, RESULT_REQUEST_CODE);
    }



    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return "";
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }



    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    private static Bitmap getBitmapFromUri(Uri uri)
    {
        try
        {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
