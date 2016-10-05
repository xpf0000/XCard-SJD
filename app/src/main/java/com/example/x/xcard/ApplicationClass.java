package com.example.x.xcard;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.robin.lazy.cache.CacheLoaderConfiguration;
import com.robin.lazy.cache.CacheLoaderManager;
import com.robin.lazy.cache.disk.naming.HashCodeFileNameGenerator;
import com.robin.lazy.cache.memory.MemoryCache;
import com.x.custom.FileSizeUtil;
import com.x.custom.ServicesAPI;
import com.x.custom.XNetUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import ch.qos.logback.core.util.FileUtil;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationClass extends Application {

	public static int stateBarHeight = 0;
	public static int navBarHeight = 0;

	public static int SW = 0;
	public static int SH = 0;

	public static Context context;

	public static Retrofit retrofit;

	public static ServicesAPI APPService;

	static public DataCache APPDataCache;

	/**
	 * 创建全局变量 全局变量一般都比较倾向于创建一个单独的数据类文件，并使用static静态变量
	 * 
	 * 这里使用了在Application中添加数据的方法实现全局变量
	 * 注意在AndroidManifest.xml中的Application节点添加android:name=".MyApplication"属性
	 * 
	 */
	private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

	public WindowManager.LayoutParams getMywmParams() {
		return wmParams;
	}





	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();

		CacheLoaderManager.getInstance().init(this, new HashCodeFileNameGenerator(), 1024 * 1024 * 64, 200, 50);
		APPDataCache = new DataCache();
		initImageLoader();
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		SW = displayMetrics.widthPixels;
		SH = displayMetrics.heightPixels;

		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {

				Request request = chain.request();

				XNetUtil.APPPrintln("URL: "+request.url().toString());
				if(request.body() != null)
				{
					XNetUtil.APPPrintln("Body: "+request.body().toString());
				}

				Response response = chain.proceed(request);

				return response;
			}
		}).build();


		retrofit = new Retrofit.Builder()
				.baseUrl(ServicesAPI.APPUrl)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.callFactory(client)
				.build();

		APPService = retrofit.create(ServicesAPI.class);

		System.out.println("================init============");
	}

	//初始化网络图片缓存库
	private void initImageLoader() {
		//网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
				showImageForEmptyUri(R.drawable.app_default)
				.cacheInMemory(true).cacheOnDisk(true).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);

	}


}
