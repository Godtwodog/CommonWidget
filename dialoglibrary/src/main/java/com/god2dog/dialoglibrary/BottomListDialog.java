package com.god2dog.dialoglibrary;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BottomListDialog extends Dialog {
    private List<Item> itemList;
    private int mShowMaxNumber = 7;
    private Display display;
    private RecyclerView recyclerView;
    private RelativeLayout mTopBar;
    private TextView mTitle;
    private Button btnCancel;
    private ImageView ivCancel;
    private BottomListAdapter mAdapter;

    private String title;

    private CANCEL cancelStyle = CANCEL.OTHER;

   public enum CANCEL{
        TOP,BOTTOM,OTHER
    }

    public BottomListDialog(@NonNull Context context) {
        super(context, R.style.BottomListDialog);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = windowManager.getDefaultDisplay();
        }
        init();
    }

    private void init() {
        setContentView(R.layout.bottom_list_dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        recyclerView = findViewById(R.id.containerRecycler);
        mTopBar = findViewById(R.id.rlTopBar);
        mTitle = findViewById(R.id.tvTitle);
        btnCancel = findViewById(R.id.btn_cancel);
        ivCancel =findViewById(R.id.iv_top_close);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setTitle(String title) {
        this.title =title;
        mTitle.setText(title);
    }

    public void setCancelStyle(CANCEL cancelStyle) {
        this.cancelStyle = cancelStyle;
    }

    public List<Item> getItemList() {
        return itemList;
    }


    public void addItemList(List<Item> itemList,OnItemClickListener onItemClickListener) {
        if (itemList == null || itemList.size() <= 0) {
            return;
        }
        this.itemList = itemList;
        int size = itemList.size();
        if (size >= mShowMaxNumber) {
            ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
            layoutParams.width = display.getWidth();
            layoutParams.height = display.getHeight() / 2;
            recyclerView.setLayoutParams(layoutParams);
        } else {

        }
        mAdapter = new BottomListAdapter(itemList);
        mAdapter.setOnItemClickListener(onItemClickListener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void show() {
        if (cancelStyle == CANCEL.OTHER){
            ivCancel.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
        }else if (cancelStyle == CANCEL.BOTTOM){
            ivCancel.setVisibility(View.GONE);
            btnCancel.setVisibility(View.VISIBLE);
        }else if (cancelStyle == CANCEL.TOP){
            ivCancel.setVisibility(View.VISIBLE);
            ivCancel.setVisibility(View.GONE);
        }
        super.show();
    }
}
