package com.example.x.xcard;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.com.x.AppModel.BannerModel;
import com.com.x.card.CZMainVC;
import com.com.x.card.CZManagerVC;
import com.com.x.card.CardManageVC;
import com.com.x.card.MakeCardVC;
import com.com.x.card.XFManageVC;
import com.com.x.huiyuan.HYManageVC;
import com.com.x.huodong.HDManageVC;
import com.com.x.user.APPConfig;
import com.com.x.user.LoginVC;
import com.com.x.user.ShopSetupVC;
import com.com.x.user.SystemMsg;
import com.com.x.xiaoxi.MSGManageVC;
import com.com.x.yuangong.YGManageMainVC;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.robin.lazy.cache.CacheLoaderManager;
import com.robin.lazy.cache.disk.naming.FileNameGenerator;
import com.robin.lazy.cache.disk.naming.HashCodeFileNameGenerator;
import com.x.custom.XActivityindicator;
import com.x.custom.XGridView;
import com.x.custom.XNetUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.x.xcard.ApplicationClass.APPService;

public class MainActivity extends BaseActivity {

    private XGridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;

    List<BannerModel> bannerArr = new ArrayList<BannerModel>();

    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private List<String> networkImages = new ArrayList<String>();

    // 图片封装为一个数组
    private int[] icon = {R.drawable.index_icon01, R.drawable.index_icon02,
            R.drawable.index_icon03, R.drawable.index_icon04, R.drawable.index_icon05,
            R.drawable.index_icon06, R.drawable.index_icon07, R.drawable.index_icon08,
            R.drawable.index_icon10, R.drawable.index_icon09, R.drawable.index_icon11, R.color.white};
    private String[] iconName = {"会员管理", "充值管理", "消费管理", "活动管理", "消息管理", "卡类管理", "店铺设置",
            "员工管理", "系统公告", "设置", "更多", ""};
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().setAttributes(params);

        setContentView(R.layout.activity_main);

        doHideBackBtn();
        //setRightImg(R.drawable.user_head);

        setPageTitle("车港湾");


        gview = (XGridView) findViewById(R.id.homt_gview);
        gview.setScrollEnable(false);

        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(!checkIsLogin())
                {
                    return;
                }

                switch (i) {
                    case 0:
                        pushVC(HYManageVC.class);
                        break;
                    case 1:
                        pushVC(CZManagerVC.class);
                        break;
                    case 2:
                        pushVC(XFManageVC.class);
                        break;
                    case 3:
                        pushVC(HDManageVC.class);
                        break;
                    case 4://消息管理
                        pushVC(MSGManageVC.class);
                        break;
                    case 5://卡类管理
                        pushVC(CardManageVC.class);
                        break;
                    case 6://店铺设置
                        pushVC(ShopSetupVC.class);
                        break;
                    case 7:
                        pushVC(YGManageMainVC.class);
                        break;

                    case 8:
                        pushVC(SystemMsg.class);
                        break;

                    case 9:
                        pushVC(APPConfig.class);

                        break;

                    default:
                        break;
                }

            }
        });


        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.home_item_cell, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);


        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        convenientBanner.setPageIndicator(new int[]{R.drawable.banner_dot_default, R.drawable.banner_dot_selected})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        getBanner();

        convenientBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("!!! Banner选择position: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("~~~ Banner点击position: " + position);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    private void getBanner() {

        XNetUtil.Handle(APPService.getBanner(), new XNetUtil.OnHttpResult<List<BannerModel>>() {
            @Override
            public void onError(Throwable e) {

                XNetUtil.APPPrintln(e);

            }

            @Override
            public void onSuccess(List<BannerModel> bannerModels) {

                bannerArr = bannerModels;
                for(BannerModel model:bannerModels)
                {
                    System.out.println(model.toString());
                    networkImages.add(model.getPicurl());
                }

                convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, networkImages);


            }
        });




    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        //convenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    public List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }

    @Override
    public void rightClick(View v) {
        System.out.println("点击右侧菜单~~~~~~~~");
        presentVC(LoginVC.class);
    }

    public void toBanKa(View v) {

        Bundle bundle = new Bundle();
        bundle.putString("title", "办卡");

        pushVC(MakeCardVC.class, bundle);
    }

    public void toXiaoFei(View v) {

        Bundle bundle = new Bundle();
        bundle.putString("title", "消费");

        pushVC(CZMainVC.class, bundle);
    }

    public void toChongZhi(View v) {

        Bundle bundle = new Bundle();
        bundle.putString("title", "充值");

        pushVC(CZMainVC.class, bundle);
    }


    public void btnClick(View v) {
        pushVC(TestVC.class);
    }

    @Override
    protected void setupData() {

    }

    @Override
    protected void setupUi() {

    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
