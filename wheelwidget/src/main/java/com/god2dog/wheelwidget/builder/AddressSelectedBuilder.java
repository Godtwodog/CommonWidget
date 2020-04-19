package com.god2dog.wheelwidget.builder;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;

import com.god2dog.wheelwidget.AddressSelectedView;
import com.god2dog.wheelwidget.WheelOptions;
import com.god2dog.wheelwidget.listener.CustomListener;
import com.god2dog.wheelwidget.listener.OnOptionsSelectChangedListener;
import com.god2dog.wheelwidget.listener.OnOptionsSelectedListener;

public class AddressSelectedBuilder {
    //配置类
    private WheelOptions mPickerOptions;


    //Required
    public AddressSelectedBuilder(Context context, OnOptionsSelectedListener listener) {
        mPickerOptions = new WheelOptions(WheelOptions.TYPE_WHEEL_ADDRESS);
        mPickerOptions.context = context;
        mPickerOptions.onOptionsSelectedListener = listener;
    }

    //Option
    public AddressSelectedBuilder setSubmitText(String textContentConfirm) {
        mPickerOptions.textContentSubmit = textContentConfirm;
        return this;
    }

    public AddressSelectedBuilder setCancelText(String textContentCancel) {
        mPickerOptions.textContentCancel = textContentCancel;
        return this;
    }

    public AddressSelectedBuilder setTitleText(String textContentTitle) {
        mPickerOptions.textContentTitle = textContentTitle;
        return this;
    }

    public AddressSelectedBuilder isDialog(boolean isDialog) {
        mPickerOptions.isDialog = isDialog;
        return this;
    }

    public AddressSelectedBuilder addOnCancelClickListener(View.OnClickListener cancelListener) {
        mPickerOptions.cancelListener = cancelListener;
        return this;
    }


    public AddressSelectedBuilder setSubmitColor(int textColorConfirm) {
        mPickerOptions.textColorConfirm = textColorConfirm;
        return this;
    }

    public AddressSelectedBuilder setCancelColor(int textColorCancel) {
        mPickerOptions.textColorCancel = textColorCancel;
        return this;
    }


    /**
     * {@link #setOutSideColor} instead.
     *
     * @param backgroundId color resId.
     */
    @Deprecated
    public AddressSelectedBuilder setBackgroundId(int backgroundId) {
        mPickerOptions.outSideColor = backgroundId;
        return this;
    }

    /**
     * 显示时的外部背景色颜色,默认是灰色
     *
     * @param outSideColor color resId.
     * @return
     */
    public AddressSelectedBuilder setOutSideColor(int outSideColor) {
        mPickerOptions.outSideColor = outSideColor;
        return this;
    }

    /**
     * ViewGroup 类型
     * 设置PickerView的显示容器
     *
     * @param decorView Parent View.
     * @return
     */
    public AddressSelectedBuilder setDecorView(ViewGroup decorView) {
        mPickerOptions.decorView = decorView;
        return this;
    }

    public AddressSelectedBuilder setLayoutRes(int res, CustomListener listener) {
        mPickerOptions.layoutRes = res;
        mPickerOptions.customListener = listener;
        return this;
    }

    public AddressSelectedBuilder setBgColor(int bgColorWheel) {
        mPickerOptions.bgColorWheel = bgColorWheel;
        return this;
    }

    public AddressSelectedBuilder setTitleBgColor(int bgColorTitle) {
        mPickerOptions.bgColorTitle = bgColorTitle;
        return this;
    }

    public AddressSelectedBuilder setTitleColor(int textColorTitle) {
        mPickerOptions.textColorTitle = textColorTitle;
        return this;
    }

    public AddressSelectedBuilder setSubCalSize(int textSizeSubmitCancel) {
        mPickerOptions.textSizeConfirm = textSizeSubmitCancel;
        mPickerOptions.textSizeCancel = textSizeSubmitCancel;
        return this;
    }

    public AddressSelectedBuilder setTitleSize(int textSizeTitle) {
        mPickerOptions.textSizeTitle = textSizeTitle;
        return this;
    }

    public AddressSelectedBuilder setContentTextSize(int textSizeContent) {
        mPickerOptions.textContentSize = textSizeContent;
        return this;
    }

    public AddressSelectedBuilder setOutSideCancelable(boolean cancelable) {
        mPickerOptions.cancelable = cancelable;
        return this;
    }


    public AddressSelectedBuilder setLabels(String label1, String label2, String label3) {
        mPickerOptions.label1 = label1;
        mPickerOptions.label2 = label2;
        mPickerOptions.label3 = label3;
        return this;
    }

    /**
     * 设置Item 的间距倍数，用于控制 Item 高度间隔
     *
     * @param lineSpacingMultiplier 浮点型，1.0-4.0f 之间有效,超过则取极值。
     */
    public AddressSelectedBuilder setLineSpacingMultiplier(float lineSpacingMultiplier) {
        mPickerOptions.lineSpacingMultiplier = lineSpacingMultiplier;
        return this;
    }

    /**
     * Set item divider line type color.
     *
     * @param dividerColor color resId.
     */
    public AddressSelectedBuilder setDividerColor(@ColorInt int dividerColor) {
        mPickerOptions.dividerColor = dividerColor;
        return this;
    }
    /**
     * Set the textColor of selected item.
     *
     * @param textColorCenter color res.
     */
    public AddressSelectedBuilder setTextColorCenter(int textColorCenter) {
        mPickerOptions.textColorCenter = textColorCenter;
        return this;
    }

    /**
     * Set the textColor of outside item.
     *
     * @param textColorOut color resId.
     */
    public AddressSelectedBuilder setTextColorOut(@ColorInt int textColorOut) {
        mPickerOptions.textColorOut = textColorOut;
        return this;
    }

    public AddressSelectedBuilder setTypeface(Typeface font) {
        mPickerOptions.typeface = font;
        return this;
    }

    public AddressSelectedBuilder setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        mPickerOptions.isLoop1 = cyclic1;
        mPickerOptions.isLoop2 = cyclic2;
        mPickerOptions.isLoop3 = cyclic3;
        return this;
    }

    public AddressSelectedBuilder setSelectOptions(int option1) {
        mPickerOptions.selectedIndex1 = option1;
        return this;
    }

    public AddressSelectedBuilder setSelectOptions(int option1, int option2) {
        mPickerOptions.selectedIndex1 = option1;
        mPickerOptions.selectedIndex2 = option2;
        return this;
    }

    public AddressSelectedBuilder setSelectOptions(int option1, int option2, int option3) {
        mPickerOptions.selectedIndex1 = option1;
        mPickerOptions.selectedIndex2 = option2;
        mPickerOptions.selectedIndex3 = option3;
        return this;
    }

    public AddressSelectedBuilder setTextXOffset(int xoffset_one, int xoffset_two, int xoffset_three) {
        mPickerOptions.x_offset_one = xoffset_one;
        mPickerOptions.x_offset_two = xoffset_two;
        mPickerOptions.x_offset_three = xoffset_three;
        return this;
    }

    public AddressSelectedBuilder isCenterLabel(boolean isCenterLabel) {
        mPickerOptions.isCenterLabel = isCenterLabel;
        return this;
    }


    /**
     * 设置最大可见数目
     *
     * @param count 建议设置为 3 ~ 9之间。
     */
    public AddressSelectedBuilder setItemVisibleCount(int count) {
        mPickerOptions.itemVisibleCount = count;
        return this;
    }

    /**
     * 透明度是否渐变
     *
     * @param isAlphaGradient true of false
     */
    public AddressSelectedBuilder isAlphaGradient(boolean isAlphaGradient) {
        mPickerOptions.isAlphaGradient = isAlphaGradient;
        return this;
    }

    /**
     * 切换选项时，是否还原第一项
     *
     * @param isRestoreItem true：还原； false: 保持上一个选项
     * @return TimePickerBuilder
     */
    public AddressSelectedBuilder isRestoreItem(boolean isRestoreItem) {
        mPickerOptions.isRestore = isRestoreItem;
        return this;
    }

    /**
     * @param listener 切换item项滚动停止时，实时回调监听。
     * @return
     */
    public AddressSelectedBuilder setOptionsSelectChangeListener(OnOptionsSelectChangedListener listener) {
        mPickerOptions.onOptionsSelectedChangeListener = listener;
        return this;
    }


    public <T> AddressSelectedView<T> build() {
        return new AddressSelectedView<>(mPickerOptions);
    }
}
