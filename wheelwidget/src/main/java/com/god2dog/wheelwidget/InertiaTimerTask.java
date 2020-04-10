package com.god2dog.wheelwidget;

import java.util.TimerTask;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/7
 * 描述：CommonWidget
 */
public final class InertiaTimerTask extends TimerTask {
    //当前滑动速度
    private float mCurrentVelocityY;
    //初始滑动速度
    private float mFirstVelocityY;
    private WheelViewWidget wheelWidget;

    public InertiaTimerTask(WheelViewWidget wheelWidget,float velocityY) {
        super();
        this.wheelWidget = wheelWidget;
        this.mFirstVelocityY = velocityY;
        mCurrentVelocityY = Integer.MAX_VALUE;
    }

    @Override
    public void run() {
        if (mCurrentVelocityY == Integer.MAX_VALUE){
            if (Math.abs(mFirstVelocityY) > 2000F){
                mCurrentVelocityY = mFirstVelocityY > 0 ? 2000F : -2000F;
            }else {
                mCurrentVelocityY = mFirstVelocityY;
            }

            if (Math.abs(mCurrentVelocityY) > 0.0F &&Math.abs(mCurrentVelocityY) <=20F){
                wheelWidget.cancelFuture();
                wheelWidget.getHandler().sendEmptyMessage(MessageHandler.TYPE_SMOOTH_SCROLL);
                return;
            }

            int dy = (int) (mCurrentVelocityY / 100F);
            wheelWidget.setTotalScrollY(wheelWidget.getTotalScrollY() - dy);
            //普通模式，非循环
            if (!wheelWidget.isLoop()){
                float itemHeight =  wheelWidget.getItemHeight();
                float top = ( -wheelWidget.getInitPosition()) * itemHeight;
                float bottom = (wheelWidget.getItemCount() - 1 - wheelWidget.getInitPosition()) * itemHeight;

                if (wheelWidget.getTotalScrollY() - itemHeight * 0.25 < top){
                    top = wheelWidget.getTotalScrollY() + dy;
                }else if (wheelWidget.getTotalScrollY() + itemHeight * 0.25 > bottom){
                    bottom = wheelWidget.getTotalScrollY() + dy;
                }

                if (wheelWidget.getTotalScrollY() >= top){
                    mCurrentVelocityY = 40F;
                    wheelWidget.setTotalScrollY((int)top);
                }else if (wheelWidget.getTotalScrollY() <= bottom){
                    mCurrentVelocityY = -40F;
                    wheelWidget.setTotalScrollY((int)bottom);
                }

                if (mCurrentVelocityY < 0.0F){
                    mCurrentVelocityY = mCurrentVelocityY + 20F;
                }else {
                    mCurrentVelocityY = mCurrentVelocityY - 20F;
                }

                wheelWidget.getHandler().sendEmptyMessage(MessageHandler.TYPE_INVALIDATE_LOOP_VIEW);
            }
        }
    }
}
