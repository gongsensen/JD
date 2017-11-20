package com.zuiyou.jd.type.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.bawei.swiperefreshlayoutlibrary.SwipyRefreshLayout;
import com.bawei.swiperefreshlayoutlibrary.SwipyRefreshLayoutDirection;
import com.google.gson.Gson;
import com.zuiyou.jd.R;
import com.zuiyou.jd.type.bean.ShopListBean;
import com.zuiyou.jd.type.presenter.ShopsListPresenter;
import com.zuiyou.jd.baseview.AllView;
import com.zuiyou.jd.type.adapter.ShopListAdapter;
import com.zuiyou.jd.app.MyApp;

import java.util.List;

/**
 * 类描述：商品列表页面
 * 创建人:巩森森
 */

public class ShopsListActivity extends AppCompatActivity implements View.OnClickListener, AllView {
    private RecyclerView recyclerviewshopshoplist;
    private RadioButton shopclose;
    private ShopsListPresenter shopsParticularsPresenter;
    private SwipyRefreshLayout srl;
    private Handler handler;
    private int page = 1;
    private CheckBox rbChange;
    private ShopListAdapter shopListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopslist);
        //初始化控件
        initview();
        //一个presenter
        shopsParticularsPresenter = new ShopsListPresenter(this);

        //ok请求数据
        getData("http://apiv3.yangkeduo.com/v5/newlist?page=1&size=20");


        //上拉刷新下拉加载,用swiperefreshlayout
        srl.setDirection(SwipyRefreshLayoutDirection.BOTH);
        handler = new Handler();
        srlflashData();
    }

    /**
     * 上拉刷新,下拉加载数据
     */
    private void srlflashData() {
        srl.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(int index) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getData("http://apiv3.yangkeduo.com/v5/newlist?page=" + page + "&size=20");
                        srl.setRefreshing(false);
                    }
                }, 2000);
            }

            @Override
            public void onLoad(int index) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getData("http://apiv3.yangkeduo.com/v5/newlist?page=" + page + "&size=20");
                        srl.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    //向presenter传递接口请求数据
    private void getData(String s) {
        shopsParticularsPresenter.ShopsParticularsData(s);
    }

    /**
     * 初始化控件
     */
    private void initview() {
        recyclerviewshopshoplist = (RecyclerView) findViewById(R.id.recyclerview_shop_shoplist);
        shopclose = (RadioButton) findViewById(R.id.rb_shop_close);
        srl = (SwipyRefreshLayout) findViewById(R.id.srl);
        rbChange = (CheckBox) findViewById(R.id.rb_change);
        shopclose.setOnClickListener(this);
        rbChange.setOnClickListener(this);
    }


    /**
     * 点击事件
     * 关闭详情页面
     * 改变布局
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_shop_close:
                finish();
                break;
            case R.id.rb_change:
                RecyclerView.LayoutManager layoutManager = recyclerviewshopshoplist.getLayoutManager();
                if (layoutManager == null) {
                    return;
                }
                if (layoutManager instanceof GridLayoutManager) {
                    recyclerviewshopshoplist.setLayoutManager(linearLayoutManager);
//                    recyclerviewshopshoplist.setAdapter(shopListAdapter);
                } else if (layoutManager instanceof LinearLayoutManager) {
                    recyclerviewshopshoplist.setLayoutManager(gridLayoutManager);
//                    recyclerviewshopshoplist.setAdapter(shopListAdapter);
                }
                recyclerviewshopshoplist.setAdapter(shopListAdapter);
                break;
        }
    }

    /**
     * Gson解析传过来的数据
     *
     * @param result
     */
    @Override
    public void leftsuccedData(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                ShopListBean shopListBean = gson.fromJson(result, ShopListBean.class);
                List<ShopListBean.GoodsListBean> goods_list = shopListBean.getGoods_list();
                //添加两种布局LinearLayoutManager和GridLayoutManager
                //传参数:上下文和list集合
                linearLayoutManager = new LinearLayoutManager(context());
//                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                gridLayoutManager = new GridLayoutManager(context(), 2);
                recyclerviewshopshoplist.setLayoutManager(gridLayoutManager);
                shopListAdapter = new ShopListAdapter(goods_list, context());
                recyclerviewshopshoplist.setAdapter(shopListAdapter);

            }
        });
    }

    @Override
    public void rightsuccedData(String result) {

    }

    @Override
    public Context context() {
        return MyApp.AppContext();
    }
}
