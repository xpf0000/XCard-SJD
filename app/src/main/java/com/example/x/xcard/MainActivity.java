package com.example.x.xcard;

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
import com.com.x.card.ChooseCardTypeVC;
import com.com.x.card.InputCardInfoVC;
import com.com.x.card.MakeCardVC;
import com.com.x.user.APPConfig;
import com.com.x.user.APPContact;
import com.com.x.user.ChangePwVC;
import com.com.x.user.FeedBack;
import com.com.x.user.LoginVC;
import com.com.x.user.SystemMsg;
import com.com.x.yuangong.AddYGVC;
import com.com.x.yuangong.PowerManageVC;
import com.com.x.yuangong.YGManageMainVC;
import com.x.custom.XGridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity{

    private XGridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;


    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private List<String> networkImages;
    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0" +
            ".jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c" +
                    "/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba" +
                    ".jpg"
    };

    // 图片封装为一个数组
    private int[] icon = {R.drawable.icon_autio_white, R.drawable.icon_autio_white,
            R.drawable.icon_autio_white, R.drawable.icon_autio_white, R.drawable.icon_autio_white,
            R.drawable.icon_autio_white, R.drawable.icon_autio_white, R.drawable.icon_autio_white,
            R.drawable.icon_autio_white, R.drawable.icon_autio_white, R.color.white, R.color.white};
    private String[] iconName = {"通讯录", "日历", "照相机", "时钟", "游戏", "短信", "铃声",
            "设置", "语音", "天气", "", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().setAttributes(params);

        setContentView(R.layout.activity_main);

        doHideBackBtn();
        setRightImg(R.drawable.user_head);

        setPageTitle("车港湾");


        gview = (XGridView) findViewById(R.id.homt_gview);
        gview.setScrollEnable(false);

        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 0:
                    pushVC(SystemMsg.class);
                    break;
                    case 1:
                    pushVC(FeedBack.class);
                    break;
                    case 2:
                        pushVC(APPContact.class);
                        break;
                    case 3:
                        pushVC(APPConfig.class);
                        break;
                    case 4:
                        pushVC(ChangePwVC.class);
                        break;
                    case 5:
                        pushVC(YGManageMainVC.class);
                        break;
                    case 6:
                        pushVC(PowerManageVC.class);
                        break;
                    case 7:
                        pushVC(AddYGVC.class);
                        break;

                    case 8:
                        pushVC(MakeCardVC.class);
                        break;

                    case 9:
                        pushVC(ChooseCardTypeVC.class);
                        break;

                    case 10:
                        pushVC(InputCardInfoVC.class);
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

        //网络加载例子
        networkImages = Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages);

        convenientBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("!!! Banner选择position: "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("~~~ Banner点击position: "+position);
            }
        });

    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);
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
}
