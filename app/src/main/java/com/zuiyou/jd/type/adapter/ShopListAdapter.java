package com.zuiyou.jd.type.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zuiyou.jd.R;
import com.zuiyou.jd.type.bean.ShopListBean;

import java.util.List;

/**
 * 类描述：商品列表适配器
 * 创建人:巩森森
 */

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.MyViewHolder> {
    private List<ShopListBean.GoodsListBean> goods_list;
    private Context context;

    public ShopListAdapter(List<ShopListBean.GoodsListBean> goods_list, Context context) {
        this.goods_list = goods_list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.LayoutManager layoutManager = ((RecyclerView) parent).getLayoutManager();
        View inflate = null;
        if (layoutManager instanceof GridLayoutManager) {
            //inflate的时候,需要传入parent和attachToRoot==false; 使用传入三个参数的方法
            inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoplistitem, parent, false);
        } else if (layoutManager instanceof LinearLayoutManager) {
            //inflate的时候,需要传入parent和attachToRoot==false; 使用传入三个参数的方法
            inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_horizontal, parent, false);

        }
        return new MyViewHolder(inflate);
    }

    /**
     * Glide加载图片,
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context)
                .load(goods_list.get(position).getImage_url())//设置图片的加载路径
                .placeholder(R.mipmap.ic_launcher)//设置正在加载中的图片
                .error(R.mipmap.find_bl)//设置加载错误显示的图片
                .into(holder.imgshowlistimg);//设置给谁
        holder.txtshowlisttext.setText(goods_list.get(position).getGoods_name());
        holder.txtshowlistprice.setText("￥：" + goods_list.get(position).getNormal_price());
        holder.txtshowlistcommenton.setText(goods_list.get(position).getCnt() + "评价");
        holder.txtshowlistgoodreception.setText(goods_list.get(position).getGoods_id() + "好评");
    }

    @Override
    public int getItemCount() {
        return goods_list.size();
    }


    //初始化控件
    public class MyViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        private final TextView txtshowlisttext;
        private final TextView txtshowlistprice;
        private final TextView txtshowlistcommenton;
        private final TextView txtshowlistgoodreception;
        private final ImageView imgshowlistimg;
        
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            txtshowlisttext = (TextView) itemView.findViewById(R.id.txt_showlist_text);
            txtshowlistprice = (TextView) itemView.findViewById(R.id.txt_showlist_price);
            txtshowlistcommenton = (TextView) itemView.findViewById(R.id.txt_showlist_commenton);
            txtshowlistgoodreception = (TextView) itemView.findViewById(R.id.txt_showlist_goodreception);
            imgshowlistimg = (ImageView) itemView.findViewById(R.id.img_showlist_img);
        }
    }
}
