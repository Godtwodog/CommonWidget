package com.god2dog.wheelwidget.adater;

import java.util.List;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/18
 * 描述：CommonWidget
 */
public class ArrayOptionAdapter<T> implements WheelItemAdapter {

    private List<T> items;

    public ArrayOptionAdapter(List<T> items) {
        this.items = items;
    }

    @Override
    public Object getItem(int position) {
        if (position >= 0 && position < items.size()) {
            return items.get(position);
        } else {
            return "";
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public int getIndexOf(Object o) {
        return items.indexOf(o);
    }
}
