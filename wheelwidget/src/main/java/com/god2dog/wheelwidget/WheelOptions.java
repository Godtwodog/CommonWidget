package com.god2dog.wheelwidget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/8
 * 描述：CommonWidget
 *  初始化参数
 */
public class WheelOptions {
    public Context context;

    private static final int VIEW_BTN_COLOR_NORMAL = 0xFF057dff;
    private static final int VIEW_BG_COLOR_TITLE = 0xFFf5f5f5;
    private static final int VIEW_COLOR_TITLE = 0xFF000000;
    private static final int VIEW_BG_COLOR_DEFAULT = 0xFFFFFFFF;

    public ViewGroup decorView;

    public int outSideColor = -1;

    public boolean cancelable;

    public CustomListener customListener;

    public String textContentCancel;
    public String textContentSubmit;
    public String textContentTitle;

    public int textColorCancel = VIEW_BTN_COLOR_NORMAL;
    public int textColorConfirm = VIEW_BTN_COLOR_NORMAL;
    public int textColorTitle = VIEW_COLOR_TITLE;

    public float textSizeCancel =17;
    public float textSizeConfirm = 17;
    public float textSizeTitle = 18;

    public int layoutRes;

    public int bgColorWheel = VIEW_BG_COLOR_DEFAULT;
    public int bgColorTitle = VIEW_BG_COLOR_DEFAULT;

    public OnTimeSelectListener timeSelectListener;
    public OnTimeSelectChangeListener timeSelectChangeListener;
    public View.OnClickListener cancelListener;

    public int textGravity = Gravity.CENTER;
    public int textContentSize;
    public boolean[] type = new boolean[]{true, true, true, false, false, false};//显示类型，默认显示： 年月日;

    public String label_year, label_month, label_day, label_hours, label_minutes, label_seconds;//单位
    public int x_offset_year, x_offset_month, x_offset_day, x_offset_hours, x_offset_minutes, x_offset_seconds;

    public int startYear;
    public int endYear;

    public Calendar startDate;
    public Calendar endDate;
    public Calendar date;
    public int itemVisibleCount = 9;
    public boolean isAlphaGradient = false;
    public boolean isCyclic =false;
    public int dividerColor = 0xFFd5d5d5; //分割线的颜色
    public float lineSpacingMultiplier = 1.6F;
    public int textColorOut = 0xFFa8a8a8; //分割线以外的文字颜色
    public int textColorCenter = 0xFF2a2a2a;
    public boolean isDialog = true;
    public boolean isCenterLabel = false;
    public int titleBackgroundColor = VIEW_BG_COLOR_TITLE;
}
