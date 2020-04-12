package com.god2dog.dialoglibrary;

import android.content.Context;


import java.util.List;

public class BottomDialog {

    private CustomDialog customDialog;

    public BottomDialog(Context context) {
        customDialog = new CustomDialog(context);
    }

    public BottomDialog title(String title) {
        customDialog.title(title);
        return this;
    }

    public BottomDialog title(int title) {
        customDialog.title(title);
        return this;
    }

    public BottomDialog background(int res) {
        customDialog.background(res);
        return this;
    }

    public BottomDialog inflateMenu(int menu, OnItemClickListener onItemClickListener) {
        customDialog.inflateMenu(menu, onItemClickListener);
        return this;
    }

    public BottomDialog layout(int layout) {
        customDialog.layout(layout);
        return this;
    }

    public BottomDialog orientation(int orientation) {
        customDialog.orientation(orientation);
        return this;
    }

    public BottomDialog addItems(List<Item> items, OnItemClickListener onItemClickListener) {
        customDialog.addItems(items, onItemClickListener);
        return this;
    }

    /**
     * @deprecated
     */
    public BottomDialog itemClick(OnItemClickListener listener) {
        customDialog.setItemClick(listener);
        return this;
    }

    public void show() {
        customDialog.show();
    }
}
