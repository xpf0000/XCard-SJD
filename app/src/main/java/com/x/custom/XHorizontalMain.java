package com.x.custom;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.com.x.yuangong.YGManageLeft;
import com.example.x.xcard.R;

/**
 * Created by X on 16/9/2.
 */
public class XHorizontalMain extends ViewPager {

    private Context context;
    private MyFragmentPageAdapter mAdapter;

    private void init(Context context)
    {
        this.context = context;
//3.0及其以上版本，只需继承Activity，通过getFragmentManager获取事物
        FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
        //初始化自定义适配器
        mAdapter =  new MyFragmentPageAdapter(fm);
        //绑定自定义适配器
        setAdapter(mAdapter);
    }

    public XHorizontalMain(Context context) {
        super(context);
        init(context);
    }

    public XHorizontalMain(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private class MyFragmentPageAdapter extends FragmentPagerAdapter {

        public MyFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new YGManageLeft();
                case 1:
                    return XHorizontalFragment.newInstance(position);
                default:
                    return null;
            }
        }

    }

    public static class XHorizontalFragment extends Fragment {

        int mNum; //页号

        public static XHorizontalFragment newInstance(int num) {
            XHorizontalFragment fragment = new XHorizontalFragment();
            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //这里我只是简单的用num区别标签，其实具体应用中可以使用真实的fragment对象来作为叶片
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }
        /**为Fragment加载布局时调用**/
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {



            View view = inflater.inflate(R.layout.user_login, null);

            System.out.println(getActivity());

//            TextView tv = (TextView) view.findViewById(R.id.text);
//            tv.setText("fragment+" + mNum);
            return view;
        }
    }



}
