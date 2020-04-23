package com.god2dog.commonwidget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private String[] colors = new String[]{"#CCFF99","#41F1E5","#8D41F1","#FF99CC"};

    public GuidePagerAdapter(Context context, List<Object> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_guide,parent,false);
        return new GuideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GuideViewHolder){
            GuideViewHolder viewHolder = (GuideViewHolder) holder;
            viewHolder.content.setText((String) datas.get(position));
            viewHolder.content.setBackgroundColor(Color.parseColor(colors[position % 4]));
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class GuideViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        public GuideViewHolder(@NonNull View itemView) {
            super(itemView);
            content =itemView.findViewById(R.id.tv_guide);
        }
    }
}
