package com.zuiyou.jd.type.adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zuiyou.jd.R;
import com.zuiyou.jd.type.bean.TypeGcRecyclerBean;
import com.zuiyou.jd.type.view.ShopsListActivity;

import java.util.List;

/**
 * 类描述：三级列表
 * 创建人:巩森森
 */

public class RightRecyclerViewGcAdapter extends RecyclerView.Adapter<RightRecyclerViewGcAdapter.MyViewHolder> {
    private List<TypeGcRecyclerBean.DatasBean.ClassListBean> class_list;
    private FragmentActivity activity;

    public RightRecyclerViewGcAdapter(List<TypeGcRecyclerBean.DatasBean.ClassListBean> class_list, FragmentActivity activity) {
        this.class_list = class_list;
        this.activity = activity;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_vertical, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtgctitle.setText(class_list.get(position).getGc_name());
        holder.txtgctitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ShopsListActivity.class);
                activity.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
//        Toast.makeText(activity, "class_list.size():" + class_list.size(), Toast.LENGTH_SHORT).show();
        return class_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView txtgctitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            txtgctitle = (TextView) itemView.findViewById(R.id.txt_gc_title);
        }
    }
}
