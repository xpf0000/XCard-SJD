package com.com.x.card;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.x.xcard.ApplicationClass;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.x.custom.DensityUtil;
import com.x.custom.XHorizontalMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by X on 16/9/8.
 */
public class CardAddVC extends BaseActivity {

    private RadioGroup radioGroup;
    private RecyclerView colorList;
    private int selectRadioId;
    private int selectColorRow = 0;

    private String[] colors = {"#436bb0","#e0617c","#dcb319","#17aa80","#171719"
            ,"#c01ee3"
    };


private CardAddColorListAdapter adapter;

    @Override
    protected void setupUi() {
        setContentView(R.layout.card_add);
        setPageTitle("添加卡片");

        selectRadioId = R.id.card_add_radio0;
        radioGroup = (RadioGroup)findViewById(R.id.card_add_radioGroup);
        colorList = (RecyclerView)findViewById(R.id.card_add_colorlist);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                selectRadioId = radioGroup.getCheckedRadioButtonId();
            }
        });

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        colorList.setLayoutManager(linearLayoutManager);

        adapter = new CardAddColorListAdapter(mContext);

        adapter.setOnItemClickLitener(new XHorizontalMenu.OnItemClickLitener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                selectColorRow = position;
                adapter.notifyDataSetChanged();
            }
        });

        colorList.setAdapter(adapter);

    }

    @Override
    protected void setupData() {

    }

    public void doChoose(View v)
    {

    }


    /**
     * 定义ListView适配器MainListViewAdapter
     */
    private class CardAddColorListAdapter extends RecyclerView.Adapter {

        private XHorizontalMenu.OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(XHorizontalMenu.OnItemClickLitener mOnItemClickLitener)
        {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        private  class ViewHolder extends RecyclerView.ViewHolder
        {
            public ViewHolder(View arg0)
            {
                super(arg0);
            }
            ImageView bg;
            ImageView color;
        }

        private LayoutInflater mInflater;

        public CardAddColorListAdapter(Context context)
        {
            mInflater = LayoutInflater.from(context);
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = mInflater.inflate(R.layout.card_add_color_cell,
                    parent, false);
            ViewHolder viewHolder = new ViewHolder(view);

            viewHolder.bg = (ImageView) view
                    .findViewById(R.id.card_add_color_cell_bg);
            viewHolder.color = (ImageView) view
                    .findViewById(R.id.card_add_color_cell_img);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder,final int i) {

            ViewHolder viewHolder = ((ViewHolder)holder);

            if(i == selectColorRow)
            {
                viewHolder.bg.setBackgroundResource(R.drawable.card_add_color_shape_selected);
            }
            else
            {
                viewHolder.bg.setBackgroundResource(R.drawable.card_add_color_shape_normal);
            }

            int c = Color.parseColor(colors[i]);
            viewHolder.color.setBackgroundColor(c);
            //System.out.println("item width: "+holder.itemView.getWidth());

            //如果设置了回调，则设置点击事件
            if (mOnItemClickLitener != null)
            {
                holder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mOnItemClickLitener.onItemClick(holder.itemView, i);
                    }
                });

            }


        }

        /**
         * 返回item的id
         */
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public int getItemCount() {
            return colors.length;
        }

    }

}


