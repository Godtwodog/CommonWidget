package com.god2dog.wheelwidget;

import java.util.TimerTask;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/7
 * 描述：CommonWidget
 * 平滑移动的实现
 */
public class SmoothScrollTimerTask extends TimerTask {
    private final WheelViewWidget wheelWidget;
    private int offset;
    private int realOffset;
    private int realTotalOffset;

    public SmoothScrollTimerTask(WheelViewWidget wheelWidget,int offset) {
        this.wheelWidget = wheelWidget;
        this.offset = offset;
        realTotalOffset = Integer.MAX_VALUE;
        realOffset = 0;
    }

    @Override
    public void run() {
        if (realTotalOffset == Integer.MAX_VALUE){
            realTotalOffset = offset;
        }

        realOffset = (int) ((float)realTotalOffset * 0.1F);
        if (realOffset == 0){
          realOffset = realTotalOffset < 0 ? -1 : 1;
        }

        if (Math.abs(realTotalOffset) <= 1){
            wheelWidget.cancelFuture();
            wheelWidget.getHandler().sendEmptyMessage(MessageHandler.TYPE_ITEM_SELECTED);
        }else {
            wheelWidget.setTotalScrollY(wheelWidget.getTotalScrollY() + realOffset);
            //普通模式
            if (!wheelWidget.isLoop()){
                float itemHeight = wheelWidget.getItemHeight();
                float top = (float) (-wheelWidget.getInitPosition()) * itemHeight;
                float bottom = (float) (wheelWidget.getItemCount() - 1 - wheelWidget.getInitPosition()) * itemHeight;
                if (wheelWidget.getTotalScrollY() <= top || wheelWidget.getTotalScrollY() >= bottom){
                    wheelWidget.setTotalScrollY(wheelWidget.getTotalScrollY() - realOffset);
                    wheelWidget.cancelFuture();
                    wheelWidget.getHandler().sendEmptyMessage(MessageHandler.TYPE_ITEM_SELECTED);
                    return;
                }
            }
            wheelWidget.getHandler().sendEmptyMessage(MessageHandler.TYPE_INVALIDATE_LOOP_VIEW);
            realTotalOffset = realTotalOffset - realOffset;
        }
    }
}
