package com.god2dog.dialoglibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BottomListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Item> mItems = Collections.emptyList();
    private OnItemClickListener onItemClickListener;

    public BottomListAdapter(List<Item> mItems) {
        setList(mItems);
    }

    private void setList(List<Item> mItems) {
        this.mItems = mItems == null ? new ArrayList<Item>() : mItems;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_dialog,null,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Item item = mItems.get(position);
        if (holder instanceof ItemViewHolder){
            ItemViewHolder viewHolder= (ItemViewHolder) holder;

            viewHolder.message.setText(item.getTitle());
            viewHolder.message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) onItemClickListener.click(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView message;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.tvItemMessage);
        }
    }
}
