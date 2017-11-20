package com.zuiyou.jd.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.zuiyou.jd.R;
import com.zuiyou.jd.mine.utils.GlideCircleTransform;
import com.zuiyou.jd.app.MyApp;


/**
 * 类描述：我的设置页面————Fragment
 * 创建人:巩森森
 */

public class MyFragment extends Fragment implements View.OnClickListener {

    private View view;
    private static ImageView user_head;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        initview();

    }

    //初始化控件
    private void initview() {
        user_head = (ImageView) view.findViewById(R.id.user_head);
        RelativeLayout my_login = (RelativeLayout) view.findViewById(R.id.my_login);
        my_login.setOnClickListener(this);


    }

    //点击登录跳转
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);

    }

    public static void setsendUrl(String str) {

        Glide.with(MyApp.AppContext())
                .load(str)//设置图片的加载路径
                .transform(new GlideCircleTransform(MyApp.AppContext()))
                .into(user_head);//设置给谁
    }


}
