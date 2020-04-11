package com.god2dog.wheelwidget.adater;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/8
 * 描述：CommonWidget
 */
public class NumericWheelAdapter implements WheelItemAdapter {
    private int minValue;
    private int maxValue;

    public NumericWheelAdapter(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }


    @Override
    public Object getItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            return position + minValue;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return maxValue - minValue + 1;
    }

    @Override
    public int getIndexOf(Object o) {
        try {
            return (int) o - minValue;
        }catch (Exception e){
            return  -1;
        }
    }
}
