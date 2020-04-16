package com.god2dog.addresspickerview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/16
 * 描述：CommonWidget
 */
public class AreaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AddressModel.CityModel.AreaModel> datas;
    private Context context;

    private OnItemSelectedListener listener;
    //1 左边 2 右边
    private int imageStyle = 2;

    public void setListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public AreaAdapter(List<AddressModel.CityModel.AreaModel> datas, Context context) {
        this(datas,context,2);
    }

    public AreaAdapter(List<AddressModel.CityModel.AreaModel> datas, Context context, int imageStyle) {
        this.datas = datas;
        this.context = context;
        this.imageStyle = imageStyle;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_address, null);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddressModel.CityModel.AreaModel item = datas.get(position);
        if (holder instanceof BaseViewHolder){
            BaseViewHolder viewHolder = (BaseViewHolder) holder;
            ImageView image;
            if (imageStyle ==2){
               viewHolder.leftImage.setVisibility(View.GONE);
               image = viewHolder.rightImage;
            }else {
                viewHolder.rightImage.setVisibility(View.GONE);
                image = viewHolder.leftImage;
            }
            image.setVisibility(item.isStatus() ?  View.VISIBLE : View.GONE);
            viewHolder.name.setText(item.getName());
            viewHolder.name.setTextColor(item.isStatus() ? Color.parseColor("#ff4490f6") : Color.parseColor("#ff333333"));
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        ImageView leftImage;
        ImageView rightImage;
        TextView name;

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            leftImage = itemView.findViewById(R.id.ivLeftImage);
            rightImage = itemView.findViewById(R.id.ivRightImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemSelected(v,getBindingAdapterPosition());
                    }
                }
            });
        }
    }
}
