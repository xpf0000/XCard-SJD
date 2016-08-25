package com.example.x.xcard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 所有界面的基类
 *
 * @author wangtao 11-13
 *
 */
public abstract class BaseActivity extends AppCompatActivity implements
		Properties {
	/**
	 * 导航栏高度
	 */
	public int TILE_HEIGHT = 200;
	/**
	 * 像素密度
	 */
	public float screenDensity = 1;
	/**
	 * 屏幕高度
	 */
	public static int screenHeight = 1920;
	/**
	 * 屏幕宽度
	 */
	public static int screenWidth = 1080;


	protected Context mContext;
	/** 操作缓存 */
	protected static SaveCache mMaveCache;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		mContext = this;
		//toggleHideyBar();
		initCacheData();
		setupUi();
		setupData();
		setupScreen();
	}

	public void setupScreen() {
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		if (frame.top != 0 && frame.top != 200) {
			this.TILE_HEIGHT = frame.top;// 获取导航栏的高度,这里必须在界面绘制出来才能正确获取
		}

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		this.screenDensity = metrics.density;
		this.screenHeight = metrics.heightPixels;
		this.screenWidth = metrics.widthPixels;
	}

	private void initCacheData() {
		if (mMaveCache != null) {
			return;
		}
		mMaveCache = new SaveCache(mContext);
	}

	/**
	 * 初始化ui
	 */
	protected abstract void setupUi();

	/**
	 *
	 */
	protected abstract void setupData();

	/**
	 * 根据界面名称打印日志
	 *
	 * @param message
	 */
	protected void doLogMsg(String message) {
		if (IS_SHOW_DEBUG) {
			Log.i(getLocalClassName(), "" + message);
		}
	};

	/**
	 * 短消息提示
	 *
	 * @param msg
	 */
	public void doShowToast(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长消息提示
	 *
	 * @param msg
	 */
	public void doShowToastLong(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * 长消息提示
	 *
	 * @param msg
	 */
	public void doShowToastLong(int msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * 启动另外一个界面通过动画
	 *
	 * @param activity
	 */
	public void doStartOterAnim(Class activity) {
		Intent intentActive = new Intent(this, activity);
		startActivity(intentActive);
	}

	/**
	 * 启动另外一个界面
	 *
	 * @param activity
	 */
	public void doStartOter(Class activity) {
		if(!CountTime.isBeyoundTime("启动界面", 300)){
			return;

		}
		Intent intentActive = new Intent(this, activity);
		startActivity(intentActive);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// MobclickAgent.onPause(this);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	protected void doSetTitle(int id, int title) {
		RelativeLayout linear = (RelativeLayout) findViewById(id);
		TextView tvTitle = (TextView) linear
				.findViewById(R.id.include_title_tv);
		ImageView ivBack = (ImageView) linear
				.findViewById(R.id.include_back_iv);
		tvTitle.setText(title);
		ivBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	protected void doSetTitle(int id, String title) {
		RelativeLayout linear = (RelativeLayout) findViewById(id);
		TextView tvTitle = (TextView) linear
				.findViewById(R.id.include_title_tv);
		ImageView ivBack = (ImageView) linear
				.findViewById(R.id.include_back_iv);
		tvTitle.setText(title);
		ivBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	public void doShowMesage(String msg, boolean isActivityRun) {
		// new AlertDialog.Builder(this).setMessage(msg).setTitle("温馨提示")
		// .setCancelable(true)
		// .setNegativeButton("确定", new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface arg0, int arg1) {
		// arg0.dismiss();
		// }
		// }).create().show();
		if (!isActivityRun) {
			return;
		}
		new AlertDialog.Builder(mContext).setMessage(msg).setTitle("温馨提示")
				.setNegativeButton("确定", null).show();
	}

	public void doShowMesage(String msg) {
		doShowMesage(msg, true);
	}

	public static void doShowMesage(Activity c, String msg) {
		new AlertDialog.Builder(c).setMessage(msg).setTitle("温馨提示")
				.setNegativeButton("确定", null).show();
	}

	public static void doSaveDataToFile(Context context, Object obj, String key) {
		if (!IS_SHOW_DEBUG) {
			return;
		}
		if (obj == null) {
			return;
		}
		SaveSdcardData save = new SaveSdcardData(context, "log");
		File dir = new File(save.getParentPath());
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File file = new File(save.getParentPath() + "/" + key + ".txt");
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			out.write(new String(obj + "").getBytes("utf-8"));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * Detects and toggles immersive mode (also known as "hidey bar" mode).
	 */
	public void toggleHideyBar() {

		// BEGIN_INCLUDE (get_current_ui_flags)
		// The UI options currently enabled are represented by a bitfield.
		// getSystemUiVisibility() gives us that bitfield.
		int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
		int newUiOptions = uiOptions;
		// END_INCLUDE (get_current_ui_flags)
		// BEGIN_INCLUDE (toggle_ui_flags)
		boolean isImmersiveModeEnabled =
				((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
//		if (isImmersiveModeEnabled) {
//			Log.i(TAG, "Turning immersive mode mode off. ");
//		} else {
//			Log.i(TAG, "Turning immersive mode mode on.");
//		}

		// Navigation bar hiding:  Backwards compatible to ICS.
		if (Build.VERSION.SDK_INT >= 14) {
			newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		}

		// Status bar hiding: Backwards compatible to Jellybean
		if (Build.VERSION.SDK_INT >= 16) {
			newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
		}

		// Immersive mode: Backward compatible to KitKat.
		// Note that this flag doesn't do anything by itself, it only augments the behavior
		// of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
		// all three flags are being toggled together.
		// Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
		// Sticky immersive mode differs in that it makes the navigation and status bars
		// semi-transparent, and the UI flag does not get cleared when the user interacts with
		// the screen.
		if (Build.VERSION.SDK_INT >= 18) {
			newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
		}

		getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
		//END_INCLUDE (set_ui_flags)
	}




	private ToolBarHelper mToolBarHelper ;
	public Toolbar toolbar ;

	@Override
	public void setContentView(int layoutResID) {

		mToolBarHelper = new ToolBarHelper(this,layoutResID) ;
		toolbar = mToolBarHelper.getToolBar() ;
		setContentView(mToolBarHelper.getContentView());
        /*把 toolbar 设置到Activity 中*/
		setSupportActionBar(toolbar);
        /*自定义的一些操作*/
		onCreateCustomToolBar(toolbar) ;
	}

	public void onCreateCustomToolBar(Toolbar toolbar){
		toolbar.setContentInsetsRelative(0,0);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home){
			finish();
			return true ;
		}
		return super.onOptionsItemSelected(item);
	}



}
