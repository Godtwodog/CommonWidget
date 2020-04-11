package com.god2dog.wheelwidget.builder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;

import com.god2dog.wheelwidget.WheelOptions;
import com.god2dog.wheelwidget.listener.CustomListener;
import com.god2dog.wheelwidget.listener.OnTimeSelectChangeListener;
import com.god2dog.wheelwidget.listener.OnTimeSelectListener;
import com.god2dog.wheelwidget.view.TimeSelectedView;

import java.util.Calendar;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/9
 * 描述：CommonWidget
 */
public class TimeSelectBuilder {
    private WheelOptions mWheelOptions;

    public TimeSelectBuilder (Context context, OnTimeSelectListener onTimeSelectListener){
        mWheelOptions = new WheelOptions();
        mWheelOptions.context = context;
        mWheelOptions.timeSelectListener = onTimeSelectListener;
    }

    public TimeSelectBuilder setGravity(int gravity){
        mWheelOptions.textGravity = gravity;
        return this;
    }

    public TimeSelectBuilder addOnCancelClickListener(View.OnClickListener onCancelListener){
        mWheelOptions.cancelListener = onCancelListener;
        return this;
    }
    public TimeSelectBuilder setType(boolean[] type){
        mWheelOptions.type = type;
        return this;
    }

    public TimeSelectBuilder setSubmitText(String submit){
        mWheelOptions.textContentSubmit = submit;
        return this;
    }

    public TimeSelectBuilder isDialog(boolean isDialog){
        mWheelOptions.isDialog = isDialog;
        return this;
    }

    public TimeSelectBuilder setCancelText(String cancel){
        mWheelOptions.textContentCancel = cancel;
        return this;
    }
    public TimeSelectBuilder setTitleText(String text){
        mWheelOptions.textContentTitle = text;
        return this;
    }

    public TimeSelectBuilder setSubmitColor(int  textColorSubmit){
        mWheelOptions.textColorConfirm = textColorSubmit;
        return this;
    }

    public TimeSelectBuilder setCancelColor(int textColorCancel){
        mWheelOptions.textColorCancel = textColorCancel;
        return this;
    }
    public TimeSelectBuilder setDecorView(ViewGroup decorView){
        mWheelOptions.decorView = decorView;
        return this;
    }

    public TimeSelectBuilder setBackgroundColor(int backgroundColor){
        mWheelOptions.bgColorWheel = backgroundColor;
        return this;
    }

    public TimeSelectBuilder setTitleBackgroundColor(int titleBackgroundColor){
        mWheelOptions.titleBackgroundColor = titleBackgroundColor;
        return this;
    }
    public TimeSelectBuilder setTitleSize(int textSizeTitle){
        mWheelOptions.textSizeTitle = textSizeTitle;
        return this;
    }
    public TimeSelectBuilder setSubmitTextSize(int textSizeSubmit){
        mWheelOptions.textSizeConfirm = textSizeSubmit;
        return this;
    }

    public TimeSelectBuilder setCancelTextSize(int textSizeCancel){
        mWheelOptions.textSizeCancel = textSizeCancel;
        return this;
    }
    public TimeSelectBuilder setContentTextSize(int textSizeContent){
        mWheelOptions.textContentSize = textSizeContent;
        return this;
    }
    public TimeSelectBuilder setItemVisibleCount(int itemVisibleCount){
        mWheelOptions.itemVisibleCount = itemVisibleCount;
        return this;
    }
    public TimeSelectBuilder isAlphaGradient(boolean isAlphaGradient){
        mWheelOptions.isAlphaGradient = isAlphaGradient;
        return this;
    }
    public TimeSelectBuilder setDate(Calendar date){
        mWheelOptions.date = date;
        return this;
    }
    public TimeSelectBuilder setLayoutRes(int res, CustomListener customListener) {
        mWheelOptions.layoutRes = res;
        mWheelOptions.customListener = customListener;
        return this;
    }


    /**
     * 设置起始时间
     * 因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
     */

    public TimeSelectBuilder setRangDate(Calendar startDate, Calendar endDate) {
        mWheelOptions.startDate = startDate;
        mWheelOptions.endDate = endDate;
        return this;
    }


    /**
     * 设置间距倍数,但是只能在1.0-4.0f之间
     *
     * @param lineSpacingMultiplier
     */
    public TimeSelectBuilder setLineSpacingMultiplier(float lineSpacingMultiplier) {
        mWheelOptions.lineSpacingMultiplier = lineSpacingMultiplier;
        return this;
    }

    /**
     * 设置分割线的颜色
     *
     * @param dividerColor
     */

    public TimeSelectBuilder setDividerColor(@ColorInt int dividerColor) {
        mWheelOptions.dividerColor = dividerColor;
        return this;
    }

    /**
     * {@link #setOutSideColor} instead.
     *
     * @param backgroundId color resId.
     */
    @Deprecated
    public TimeSelectBuilder setBackgroundId(int backgroundId) {
        mWheelOptions.outSideColor = backgroundId;
        return this;
    }

    /**
     * 显示时的外部背景色颜色,默认是灰色
     *
     * @param outSideColor
     */
    public TimeSelectBuilder setOutSideColor(@ColorInt int outSideColor) {
        mWheelOptions.outSideColor = outSideColor;
        return this;
    }

    /**
     * 设置分割线之间的文字的颜色
     *
     * @param textColorCenter
     */
    public TimeSelectBuilder setTextColorCenter(@ColorInt int textColorCenter) {
        mWheelOptions.textColorCenter = textColorCenter;
        return this;
    }

    /**
     * 设置分割线以外文字的颜色
     *
     * @param textColorOut
     */
    public TimeSelectBuilder setTextColorOut(@ColorInt int textColorOut) {
        mWheelOptions.textColorOut = textColorOut;
        return this;
    }

    public TimeSelectBuilder isCyclic(boolean cyclic) {
        mWheelOptions.isCyclic = cyclic;
        return this;
    }

    public TimeSelectBuilder setOutSideCancelable(boolean cancelable) {
        mWheelOptions.cancelable = cancelable;
        return this;
    }


    public TimeSelectBuilder setLabel(String label_year, String label_month, String label_day, String label_hours, String label_mins, String label_seconds) {
        mWheelOptions.label_year = label_year;
        mWheelOptions.label_month = label_month;
        mWheelOptions.label_day = label_day;
        mWheelOptions.label_hours = label_hours;
        mWheelOptions.label_minutes = label_mins;
        mWheelOptions.label_seconds = label_seconds;
        return this;
    }

    /**
     * 设置X轴倾斜角度[ -90 , 90°]
     *
     * @param x_offset_year    年
     * @param x_offset_month   月
     * @param x_offset_day     日
     * @param x_offset_hours   时
     * @param x_offset_minutes 分
     * @param x_offset_seconds 秒
     * @return
     */
    public TimeSelectBuilder setTextXOffset(int x_offset_year, int x_offset_month, int x_offset_day,
                                            int x_offset_hours, int x_offset_minutes, int x_offset_seconds) {
        mWheelOptions.x_offset_year = x_offset_year;
        mWheelOptions.x_offset_month = x_offset_month;
        mWheelOptions.x_offset_day = x_offset_day;
        mWheelOptions.x_offset_hours = x_offset_hours;
        mWheelOptions.x_offset_minutes = x_offset_minutes;
        mWheelOptions.x_offset_seconds = x_offset_seconds;
        return this;
    }

    public TimeSelectBuilder isCenterLabel(boolean isCenterLabel) {
        mWheelOptions.isCenterLabel = isCenterLabel;
        return this;
    }

    /**
     * @param listener 切换item项滚动停止时，实时回调监听。
     * @return
     */
    public TimeSelectBuilder setTimeSelectChangeListener(OnTimeSelectChangeListener listener) {
        mWheelOptions.timeSelectChangeListener = listener;
        return this;
    }

    public TimeSelectedView build() {
        return new TimeSelectedView(mWheelOptions);
    }
}
