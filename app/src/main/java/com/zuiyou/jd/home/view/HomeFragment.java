package com.zuiyou.jd.home.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.library.zxing.activity.QRCodeScanFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.zuiyou.jd.R;
import com.zuiyou.jd.home.bean.HomeBean;
import com.zuiyou.jd.home.presenter.HomePresenter;
import com.zuiyou.jd.baseview.AllView;
import com.zuiyou.jd.activity.LiShiSouSuoActivity;
import com.zuiyou.jd.home.adapter.HomeDataAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 创建人:巩森森
 */
//集成扫描二维码的Fragment
public class HomeFragment extends QRCodeScanFragment implements AllView {
    private View view;
    private RadioButton rpsaoqr;
    private Banner banner;
    //banner图片地址
    String arr[] = {"https://img30.360buyimg.com/da/jfs/t8164/338/1395234822/160593/7d50319a/59b90cecN61514b41.jpg",
            "https://img14.360buyimg.com/da/jfs/t9058/201/1001582625/146687/f0647c40/59b27772Nc766a1f6.jpg",
            "https://img14.360buyimg.com/da/jfs/t9256/38/1402735114/337388/550b7c93/59b955b0Ne9644fe0.jpg",
            "https://img11.360buyimg.com/da/jfs/t8419/4/815266117/218642/78903406/59af4981Nea57fb82.jpg",
            "https://img11.360buyimg.com/babel/jfs/t8563/102/1393701174/88723/bda85274/59b8c3d4Nd349a04a.jpg",
            "https://img13.360buyimg.com/da/jfs/t8860/195/1256523975/179896/e5261de7/59b73f70Ndcba33ae.jpg",
            "https://img10.360buyimg.com/babel/jfs/t7234/50/2989904721/95724/7ddf2ca5/59b773c6Nb0f6ee13.jpg"};
    private List<String> list;
    private RecyclerView recyclerviewhome;
    private HomePresenter homePresenter;
    private RelativeLayout rel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        initview();
        //扫描二维码点击事件
        rpsaoqrOnclick();
        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"seccess",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(),LiShiSouSuoActivity.class));
            }
        });
        //banner轮播
        list = new ArrayList<>();
        for (String s : arr) {
            list.add(s);
        }
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(list);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//小圆点样式
        banner.setDelayTime(2000);//切换时间
        banner.start();
        //new 一个 present作为接口调用的中转站
        homePresenter = new HomePresenter(this);
        getData("http://169.254.29.49/mobile/index.php?act=goods&op=goods_list&page=100&%20gc_id=587");
    }

    //扫描二维码点击事件
    private void rpsaoqrOnclick() {
        rpsaoqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScanQRCode();
            }
        });
    }

    @Override
    public void leftsuccedData(final String result) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                HomeBean homeBean = gson.fromJson(result, HomeBean.class);
                List<HomeBean.DatasBean.GoodsListBean> goods_list = homeBean.getDatas().getGoods_list();
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                HomeDataAdapter homeDataAdapter = new HomeDataAdapter(goods_list, getContext(), getActivity());
                recyclerviewhome.setLayoutManager(gridLayoutManager);
                recyclerviewhome.setAdapter(homeDataAdapter);
            }
        });
    }

    private void getData(String s) {
        homePresenter.HomeData(s);

    }

    @Override
    public void rightsuccedData(String result) {

    }

    @Override
    public Context context() {
        return null;
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }

    }

    /*
    初始化控件
     */
    private void initview() {
        rpsaoqr = (RadioButton) view.findViewById(R.id.rp_sao_qr);
        banner = (Banner) view.findViewById(R.id.banner);
        recyclerviewhome = (RecyclerView) view.findViewById(R.id.recyclerview_home);
        rel = (RelativeLayout) view.findViewById(R.id.rel);
    }
}
