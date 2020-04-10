package com.god2dog.wheelwidget;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/7
 * 描述：CommonWidget
 */
class WheelViewGestureListener extends GestureDetector.SimpleOnGestureListener {
    private final WheelViewWidget wheelWidget;

    public WheelViewGestureListener(WheelViewWidget wheelWidget) {
        this.wheelWidget = wheelWidget;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        wheelWidget.scrollBy(velocityY);
        return true;
    }
}
