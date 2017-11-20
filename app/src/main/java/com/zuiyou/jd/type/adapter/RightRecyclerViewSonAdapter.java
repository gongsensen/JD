package com.zuiyou.jd.type.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zuiyou.jd.R;
import com.zuiyou.jd.type.bean.TypeGcRecyclerBean;
import com.zuiyou.jd.type.bean.TypeLeftSonBean;
import com.zuiyou.jd.type.presenter.TypeRecyclerViewGcViewPresenter;
import com.zuiyou.jd.baseview.AllView;

import java.util.List;

/**
 * 类描述：布局右侧大的RecyclerView请求数据,二级列表
 * 创建人:巩森森
 */

public class RightRecyclerViewSonAdapter extends RecyclerView.Adapter<RightRecyclerViewSonAdapter.MyViewHolder> {
    private List<TypeLeftSonBean.DatasBean.ClassListBean> son_list;
    private FragmentActivity activity;
    private Context context;
    private String leftid;
    private String gc_id;
    private RightRecyclerViewGcAdapter rightRecyclerViewGcAdapter;
    private boolean boo = false;

    public RightRecyclerViewSonAdapter(List<TypeLeftSonBean.DatasBean.ClassListBean> son_list, FragmentActivity activity, Context context, String leftid) {
        this.son_list = son_list;
        this.activity = activity;
        this.context = context;
        this.leftid = leftid;
        if (!boo && gc_id == null) {
            boo = true;
            gc_id = son_list.get(0).getGc_id();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.leftsonrecycleview, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txt_left_son_box.setText(son_list.get(position).getGc_name());
        gc_id = son_list.get(position).getGc_id();
    }

    @Override
    public int getItemCount() {
        return son_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements AllView {
        private View itemView;
        private TextView txt_left_son_box;
        private RecyclerView rightrecyclerviewtitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            txt_left_son_box = (TextView) itemView.findViewById(R.id.txt_left_son_box);
            rightrecyclerviewtitle = (RecyclerView) itemView.findViewById(R.id.right_recyclerview_title);

            TypeRecyclerViewGcViewPresenter typeRecyclerViewGcViewPresenter = new TypeRecyclerViewGcViewPresenter(this);
            String url = "http://169.254.242.49/mobile/index.php?act=goods_class&gc_id=" + gc_id;
            System.out.println("===========" + gc_id);
            typeRecyclerViewGcViewPresenter.GcDataSon(url);
        }

        @Override
        public void leftsuccedData(String result) {

        }

        @Override
        public void rightsuccedData(final String result) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    TypeGcRecyclerBean typeGcRecyclerBean = gson.fromJson(result, TypeGcRecyclerBean.class);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(context(), 3);
                    List<TypeGcRecyclerBean.DatasBean.ClassListBean> class_list = typeGcRecyclerBean.getDatas().getClass_list();
                    rightRecyclerViewGcAdapter = new RightRecyclerViewGcAdapter(class_list, activity);
                    rightrecyclerviewtitle.setLayoutManager(gridLayoutManager);
                    rightrecyclerviewtitle.setAdapter(rightRecyclerViewGcAdapter);
                }
            });
        }

        @Override
        public Context context() {
            return itemView.getContext();
        }
    }
}
