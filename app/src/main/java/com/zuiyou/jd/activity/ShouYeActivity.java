package com.zuiyou.jd.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.zuiyou.jd.R;
import com.zuiyou.jd.find.view.FindFragment;
import com.zuiyou.jd.home.utils.ViewpagerUtils;
import com.zuiyou.jd.home.view.HomeFragment;
import com.zuiyou.jd.mine.view.MyFragment;
import com.zuiyou.jd.shopcar.view.FragShoppingCar;
import com.zuiyou.jd.type.view.TypeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：首页的Activity,主要实现功能,Viewpager与Fragment的联动,
 * Viewpager.OnTouchLinisener()这是禁止viewpage滑动,但是在加入某些功能导致这个功能失效
 * 在后续有自己写了一个viewpager适配器,禁止了Viewpager滑动
 * 创建人:巩森森
 */

public class ShouYeActivity extends FragmentActivity {
    @BindView(R.id.viewpager)
    ViewpagerUtils viewpager;
    @BindView(R.id.alphaIndicator)
    AlphaTabsIndicator alphaIndicator;
    private List<Fragment> list = new ArrayList<>();

//    private ViewPager viewPager;
//    private RadioGroup rg_viewpager;
//    private ArrayList<Fragment> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouye);
        ButterKnife.bind(this);
        initData();
        //底部栏配置viewpager
        alphaIndicator.setViewPager(viewpager);
    }

    private void initData() {
        list.add(new HomeFragment());
        list.add(new TypeFragment());
        list.add(new FindFragment());
        list.add(new FragShoppingCar());
        list.add(new MyFragment());
        //viewpager适配器
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
    }


}
