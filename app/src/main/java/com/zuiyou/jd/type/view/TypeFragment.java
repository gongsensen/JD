package com.zuiyou.jd.type.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.zuiyou.jd.R;
import com.zuiyou.jd.type.bean.TypeLeftBean;
import com.zuiyou.jd.type.bean.TypeLeftSonBean;
import com.zuiyou.jd.type.presenter.LeftTypePresenter;
import com.zuiyou.jd.type.presenter.LeftTypeSonPresenter;
import com.zuiyou.jd.baseview.AllView;
import com.zuiyou.jd.type.adapter.LeftRecyclerViewAdapter;
import com.zuiyou.jd.type.adapter.RightRecyclerViewSonAdapter;

import java.util.List;

/**
 * 类描述：分类,
 * 创建人:巩森森
 */

public class TypeFragment extends Fragment implements AllView {

    private View view;
    private RecyclerView leftrecyclerViewson;
    private RecyclerView leftrecyclerView;
    private LeftTypePresenter typePresenter;
    private LeftTypeSonPresenter leftTypeSonPresenter;
    private String leftid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_type, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        typePresenter = new LeftTypePresenter(this);
        leftTypeSonPresenter = new LeftTypeSonPresenter(this);
        //初始化控件
        init();
        //请求接口
        getData("http://169.254.29.49/mobile/index.php?act=goods_class");
        SongetData("http://169.254.29.49/mobile/index.php?act=goods_class&gc_id=1");
    }

    private void getData(String s) {
        typePresenter.getLeftData(s);

    }

    private void init() {
        //初始化控件
        leftrecyclerView = (RecyclerView) view.findViewById(R.id.left_recyclerview);
        leftrecyclerViewson = (RecyclerView) view.findViewById(R.id.left_recyclerview_son);
    }

    /**
     * 解析数据
     * 加入适配器
     *
     * @param result
     */
    @Override
    public void leftsuccedData(final String result) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Gson gson = new Gson();
                TypeLeftBean typeLeftBean = gson.fromJson(result, TypeLeftBean.class);
                List<TypeLeftBean.DatasBean.ClassListBean> leftList = typeLeftBean.getDatas().getClass_list();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                leftrecyclerView.setLayoutManager(linearLayoutManager);
                final LeftRecyclerViewAdapter leftRecyclerViewAdapter = new LeftRecyclerViewAdapter(leftList);
                leftrecyclerView.setAdapter(leftRecyclerViewAdapter);
                leftRecyclerViewAdapter.setSendIdCallBack(new LeftRecyclerViewAdapter.SendIdCallBack() {
                    @Override
                    public void sendId(View view, String s) {
                        leftid = s;
                        SongetData("http://169.254.29.49/mobile/index.php?act=goods_class&gc_id=" + s);
                        leftRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });

            }
        });

    }

    @Override
    public void rightsuccedData(final String result) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                TypeLeftSonBean typeLeftSonBean = gson.fromJson(result, TypeLeftSonBean.class);
                List<TypeLeftSonBean.DatasBean.ClassListBean> son_list = typeLeftSonBean.getDatas().getClass_list();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

                RightRecyclerViewSonAdapter rightRecyclerViewSonAdapter = new RightRecyclerViewSonAdapter(son_list, getActivity(), getContext(), leftid);
                leftrecyclerViewson.setLayoutManager(linearLayoutManager);
                leftrecyclerViewson.setAdapter(rightRecyclerViewSonAdapter);
            }
        });
    }

    private void SongetData(String s) {
        leftTypeSonPresenter.LeftDataSon(s);
    }

    @Override
    public Context context() {
        return null;
    }
}
