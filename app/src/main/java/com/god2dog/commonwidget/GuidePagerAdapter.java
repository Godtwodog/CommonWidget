package com.god2dog.commonwidget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/17
 * 描述：CommonWidget
 */
public class GuidePagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Object> datas;

    public GuidePagerAdapter(Context context, List<Object> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_guide,null);
        return new GuideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GuideViewHolder){
            GuideViewHolder viewHolder = (GuideViewHolder) holder;

        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class GuideViewHolder extends RecyclerView.ViewHolder{

        public GuideViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
