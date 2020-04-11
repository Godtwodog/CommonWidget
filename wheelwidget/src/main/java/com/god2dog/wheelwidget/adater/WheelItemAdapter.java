package com.god2dog.wheelwidget.adater;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/2
 * 描述：CommonWidget
 */
public interface WheelItemAdapter<T> {
    //获取item
    T getItem(int position);
    //获取item数量
    int getItemCount();

    int getIndexOf(T t);
}
