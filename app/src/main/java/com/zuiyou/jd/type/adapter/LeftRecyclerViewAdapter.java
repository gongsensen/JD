package com.zuiyou.jd.type.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.zuiyou.jd.R;
import com.zuiyou.jd.type.bean.TypeLeftBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类描述：
 * 创建人:巩森森
 */

public class LeftRecyclerViewAdapter extends RecyclerView.Adapter<LeftRecyclerViewAdapter.MyViewHolder> {
    private List<TypeLeftBean.DatasBean.ClassListBean> leftList;
    private HashMap<Integer, Boolean> map;


    public LeftRecyclerViewAdapter(List<TypeLeftBean.DatasBean.ClassListBean> leftList) {
        this.leftList = leftList;
        map = new HashMap<>();
        for (int i = 0; i < leftList.size(); i++) {
            if (i == 0) {
                map.put(i, true);
            } else {
                map.put(i, false);
            }

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.leftrecyclerview, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.ck_left_re.setText(leftList.get(position).getGc_name());
        holder.ck_left_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singlesel(position);
                msendIdCallBack.sendId(v, leftList.get(position).getGc_id());

            }
        });
        holder.ck_left_re.setChecked(map.get(position));
    }


    @Override
    public int getItemCount() {
        return leftList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private final CheckBox ck_left_re;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ck_left_re = (CheckBox) itemView.findViewById(R.id.ck_left_re);
        }
    }

    /**
     * 单选
     *
     * @param postion
     */
    public void singlesel(int postion) {
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(false);
        }
        map.put(postion, true);
        notifyDataSetChanged();
    }

    private SendIdCallBack msendIdCallBack;

    public interface SendIdCallBack {
        void sendId(View view, String leftid);
    }

    public void setSendIdCallBack(SendIdCallBack msendIdCallBack) {
        this.msendIdCallBack = msendIdCallBack;
    }
}
