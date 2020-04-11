package com.god2dog.wheelwidget;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.god2dog.wheelwidget.view.WheelViewWidget;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/7
 * 描述：CommonWidget
 */
public class MessageHandler extends Handler {
    public static final int TYPE_INVALIDATE_LOOP_VIEW = 1000;
    public static final int TYPE_SMOOTH_SCROLL = 2000;
    public static final int TYPE_ITEM_SELECTED = 3000;

    private WheelViewWidget wheelWidget;

    public MessageHandler(WheelViewWidget wheelWidget) {
        this.wheelWidget = wheelWidget;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case TYPE_INVALIDATE_LOOP_VIEW:
                wheelWidget.invalidate();
                break;
            case TYPE_SMOOTH_SCROLL:
                wheelWidget.smoothScroll(WheelViewWidget.ACTION.DAGGLE);
                break;
            case TYPE_ITEM_SELECTED:
                wheelWidget.onItemSelected();
                break;
        }
    }
}
