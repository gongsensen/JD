package com.zuiyou.jd.home.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zuiyou.jd.R;
import com.zuiyou.jd.home.bean.HomeBean;

import java.util.List;

/**
 * 类描述：
 * 创建人:巩森森
 */

public class HomeDataAdapter extends RecyclerView.Adapter<HomeDataAdapter.MyViewHolder> {
    private List<HomeBean.DatasBean.GoodsListBean> goods_list;
    private Context context;
    private Context context1;

    public HomeDataAdapter(List<HomeBean.DatasBean.GoodsListBean> goods_list, Context context1, Context context) {
        this.goods_list = goods_list;
        this.context = context;
        this.context1 = context1;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.homerecycleitem, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /**
         * 替换字符串上的id
         */
        String replace = goods_list.get(position).getGoods_image_url().replace("127.0.0.1", "169.254.23.1");

        Glide.with(context1)
                .load(replace)//设置图片的加载路径
                .placeholder(R.mipmap.ic_launcher)//设置正在加载中的图片
                .error(R.mipmap.find_bl)//设置加载错误显示的图片
                .into(holder.imghomeimg);//设置给谁

        holder.txthometext.setText(goods_list.get(position).getGoods_name());
        holder.txthomeprice.setText("￥：" + goods_list.get(position).getGoods_price());
        holder.txthomecommenton.setText(goods_list.get(position).getIs_presell() + "评价");
        holder.txthomegoodreception.setText(goods_list.get(position).getIs_virtual() + "好评");
    }

    @Override
    public int getItemCount() {
        return goods_list.size();
    }

    public class MyViewHolder extends ViewHolder {
        private View itemView;
        private final ImageView imghomeimg;
        private final TextView txthomecommenton;
        private final TextView txthometext;
        private final TextView txthomeprice;
        private final TextView txthomegoodreception;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imghomeimg = (ImageView) itemView.findViewById(R.id.img_home_img);
            txthomecommenton = (TextView) itemView.findViewById(R.id.txt_home_commenton);
            txthometext = (TextView) itemView.findViewById(R.id.txt_home_text);
            txthomeprice = (TextView) itemView.findViewById(R.id.txt_home_price);
            txthomegoodreception = (TextView) itemView.findViewById(R.id.txt_home_goodreception);
        }
    }
}
