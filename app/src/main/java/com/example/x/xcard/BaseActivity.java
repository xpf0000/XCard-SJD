package com.example.x.xcard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 所有界面的基类
 *
 * @author wangtao 11-13
 *
 */
public abstract class BaseActivity extends AppCompatActivity implements
		Properties {

	protected Context mContext;
	/** 操作缓存 */
	protected static SaveCache mMaveCache;

	private ToolBarHelper mToolBarHelper ;
	public Toolbar toolbar ;

	private TextView pageTitle;
	private ImageView backBtn;
	private ImageView rightImg;
	private TextView rightTxt;

	private boolean isPush = true;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		mContext = this;
		setupUi();
		setupData();
		//新页面接收数据
		Bundle bundle = this.getIntent().getExtras();

		if (bundle != null && bundle.containsKey("isPush"))
		{
			isPush = bundle.getBoolean("isPush");
		}

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

	@Override
	protected void onResume() {
		super.onResume();
		// MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
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
	 * 启动另外一个界面通过push动画
	 *
	 * @param activity
	 */
	public void pushVC(Class activity) {

		if(!CountTime.isBeyoundTime("启动界面", 300)){
			return;
		}

		Intent intentActive = new Intent(this, activity);

		//用Bundle携带数据
		Bundle bundle=new Bundle();
		bundle.putBoolean("isPush", true);
		intentActive.putExtras(bundle);

		startActivity(intentActive);
		overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
	}


	/**
	 * 启动另外一个界面通过push动画
	 *
	 * @param activity
	 * @param bundle
	 */
	public void pushVC(Class activity,Bundle bundle) {

		if(!CountTime.isBeyoundTime("启动界面", 300)){
			return;
		}

		Intent intentActive = new Intent(this, activity);
		//用Bundle携带数据
		bundle.putBoolean("isPush", true);
		intentActive.putExtras(bundle);

		startActivity(intentActive);
		overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
	}



	/**
	 * 启动另外一个界面通过present动画
	 *
	 * @param activity
	 */
	public void presentVC(Class activity) {

		if(!CountTime.isBeyoundTime("启动界面", 300)){
			return;
		}

		Intent intentActive = new Intent(this, activity);
		//用Bundle携带数据
		Bundle bundle=new Bundle();
		bundle.putBoolean("isPush", false);
		intentActive.putExtras(bundle);

		startActivity(intentActive);
		overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
	}

	/**
	 * 启动另外一个界面通过present动画
	 *
	 * @param activity
	 * @param bundle
	 */
	public void presentVC(Class activity,Bundle bundle) {

		if(!CountTime.isBeyoundTime("启动界面", 300)){
			return;
		}

		Intent intentActive = new Intent(this, activity);
		//用Bundle携带数据
		bundle.putBoolean("isPush", false);
		intentActive.putExtras(bundle);

		startActivity(intentActive);
		overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
	}

	public void popVC(View v)
	{
		this.finish();
		if(isPush)
		{
			overridePendingTransition(R.anim.pop_left_out,R.anim.pop_left_in);
		}
		else
		{
			overridePendingTransition(R.anim.pop_up_out,R.anim.pop_up_in);
		}

	}

	public void rightClick(View v)
	{
		System.out.println("点击右侧菜单!!!!!!");
	}









	@Override
	public void setContentView(int layoutResID) {

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

		mToolBarHelper = new ToolBarHelper(this,layoutResID) ;
		toolbar = mToolBarHelper.getToolBar() ;
		setContentView(mToolBarHelper.getContentView());
        /*把 toolbar 设置到Activity 中*/
		setSupportActionBar(toolbar);
        /*自定义的一些操作*/
		onCreateCustomToolBar(toolbar) ;

		LayoutInflater layoutInflater = LayoutInflater.from(mContext);
		ViewGroup decorView = (ViewGroup)getWindow().getDecorView().findViewById(android.R.id.content);

		printAllSubView(decorView);

		final View dview = getWindow().getDecorView().findViewById(android.R.id.content);
		decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			int previousKeyboardHeight = -1;
			@Override
			public void onGlobalLayout() {
				Rect rect = new Rect();
				dview.getWindowVisibleDisplayFrame(rect);
				int displayHeight = rect.bottom - rect.top;
				int height = dview.getHeight();
				int keyboardHeight = height - displayHeight;
				if (previousKeyboardHeight != keyboardHeight) {
					boolean hide = (double) displayHeight / height > 0.8;



					//listener.onSoftKeyBoardChange(keyboardHeight, !hide);
				}


				System.out.println("keyboardHeight: "+keyboardHeight);

				previousKeyboardHeight = height;

			}
		});





	}




	@SuppressLint("NewApi")
	private void printAllSubView(ViewGroup v)
	{
		for(int i=0;i<v.getChildCount();i++)
		{
			View temp = v.getChildAt(i);

			if (temp instanceof TextView)
			{
				final TextView tv = (TextView) temp;

				 tv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View view, boolean b) {

						System.out.println("软键盘是否弹出: "+b);

						if(b)
						{
							Rect rect = new Rect();


							int[] outLocation = new int[2];
							int[] outLocation1 = new int[2];


							tv.getLocationOnScreen(outLocation);
							tv.getLocationInWindow(outLocation1);


							int displayHeight = rect.bottom - rect.top;
							int height = getWindow().getDecorView().findViewById(android.R.id.content).getHeight();
							int keyboardHeight = height - displayHeight;
							System.out.println("x: "+outLocation[0]+" y: "+outLocation[1]+" x1: "+outLocation1[0]+" y1: "+outLocation1[1]);

						}

					}
				});

				tv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
					@Override
					public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {

						System.out.println(i1+" "+i2+" "+i3+" "+i4+" "+i5+" "+i6+" "+i7);
					}
				});

				tv.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
					@Override
					public void onViewAttachedToWindow(View view) {

						System.out.println("onViewAttachedToWindow ~~~~~~");
					}

					@Override
					public void onViewDetachedFromWindow(View view) {
						System.out.println("onViewDetachedFromWindow ~~~~~~");
					}
				});

				tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
						System.out.println("onEditorAction i: "+i+" KeyEvent: "+keyEvent);
						return false;
					}
				});


				tv.setOnGenericMotionListener(new View.OnGenericMotionListener() {
					@Override
					public boolean onGenericMotion(View view, MotionEvent motionEvent) {

						System.out.println("onGenericMotion motionEvent: "+motionEvent);

						return false;
					}
				});


			}

			if (temp instanceof ViewGroup)
			{
				printAllSubView((ViewGroup)temp);
			}

		}
	}



	public void onCreateCustomToolBar(Toolbar toolbar){
		toolbar.setContentInsetsRelative(0,0);
		toolbar.showOverflowMenu() ;
		getLayoutInflater().inflate(R.layout.toobar_button, toolbar) ;

		try {
			pageTitle = (TextView) toolbar.findViewById(R.id.APP_Nav_Title);
			backBtn = (ImageView) toolbar.findViewById(R.id.APP_Back_Btn);
			rightImg = (ImageView) toolbar.findViewById(R.id.APP_Right_Img);
			rightTxt = (TextView) toolbar.findViewById(R.id.APP_Right_Txt);
			rightImg.setVisibility(View.GONE);
			rightTxt.setVisibility(View.GONE);
		}
		catch (Exception e)
		{

		}


	}

	public void setPageTitle(String txt)
	{
		if(pageTitle != null)
		{
			pageTitle.setText(txt);
		}
	}

	public void setRightImg(int id)
	{
		if(rightImg != null)
		{
			rightImg.setImageResource(id);
			rightImg.setVisibility(View.VISIBLE);
			rightTxt.setVisibility(View.GONE);
		}
	}

	public void setRightTxt(String txt)
	{
		if(rightTxt != null)
		{
			rightTxt.setText(txt);
			rightTxt.setVisibility(View.VISIBLE);
			rightImg.setVisibility(View.GONE);
		}

	}


	public void doHideBackBtn()
	{
		if(backBtn != null)
		{
			backBtn.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home){
			finish();
			return true ;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		System.out.println("onWindowFocusChanged !!!!!!");

		if (hasFocus && Build.VERSION.SDK_INT >= 19) {
			View decorView = getWindow().getDecorView();
			decorView.setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

	}


}
