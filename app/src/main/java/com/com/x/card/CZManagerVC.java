package com.com.x.card;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.example.x.xcard.ApplicationClass;
import com.example.x.xcard.BaseActivity;
import com.example.x.xcard.R;
import com.x.custom.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by X on 16/9/6.
 */
public class CZManagerVC extends BaseActivity  {

    private LineChartView chart;

    @Override
    protected void setupUi() {
        setContentView(R.layout.cz_manage);
        setPageTitle("充值管理");

        chart = (LineChartView)findViewById(R.id.cz_manage_chart);

        int h = ApplicationClass.SW * 4 / 5;
        h = DensityUtil.px2dip(mContext,h);

        ViewGroup.LayoutParams layoutParams1 = chart.getLayoutParams();
        layoutParams1.height = DensityUtil.dip2px(mContext,h);
        chart.setLayoutParams(layoutParams1);


        List<PointValue> values = new ArrayList<PointValue>();
        values.add(new PointValue(0, 0.0F));
        values.add(new PointValue(1, 1.0F));
        values.add(new PointValue(2, 0.0F));
        values.add(new PointValue(3, 4.0F));
        values.add(new PointValue(4, 2.0F));
        values.add(new PointValue(5, 3.0F));
        values.add(new PointValue(6, 1.0F));

        int c = ContextCompat.getColor(mContext, R.color.APPOrange);

        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(values).setColor(Color.BLUE).setCubic(true);

        line.setCubic(false);
        line.setHasPoints(false);
        line.setColor(c);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);


        Axis axisX = new Axis().setHasLines(true);
        Axis axisY = new Axis().setHasLines(true);
        Axis axisXT = new Axis().setHasLines(true);
        Axis axisYR = new Axis().setHasLines(true);

        axisX.setTextColor(c);
        axisX.setLineColor(ChartUtils.DEFAULT_DARKEN_COLOR);
        axisX.setTextSize(10);

        List<AxisValue> xArr = new ArrayList<>();
        List<AxisValue> xTArr = new ArrayList<>();
        for(int i=0;i<7;i++)
        {
            AxisValue value = new AxisValue(i);
            value.setLabel("08-"+(20+i));
            xArr.add(value);

            AxisValue value1 = new AxisValue(i);
            value1.setLabel("");
            xTArr.add(value1);

        }

        List<AxisValue> yArr = new ArrayList<>();

        for(int i=0;i<5;i++)
        {
            AxisValue value = new AxisValue(i);
            value.setLabel("");
            yArr.add(value);
        }

        axisX.setValues(xArr);
        axisXT.setValues(xTArr);
        axisY.setValues(yArr);

        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        data.setAxisXTop(axisXT);
        //data.setAxisYRight(new Axis().setHasLines(true));



        data.setBaseValue(2);
        chart.setZoomEnabled(false);
        chart.setLineChartData(data);


        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 5;
        v.left = 0;
        v.right = 6;
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);

    }

    @Override
    protected void setupData() {

    }

    public void toCZDetail(View v)
    {
        pushVC(CZDetailVC.class);
    }

}
