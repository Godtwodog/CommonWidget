package com.god2dog.wheelwidget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/8
 * 描述：CommonWidget
 * 时间选择控件
 */
public class TimeSelectedView extends BaseSelectedView implements View.OnClickListener {
    private WheelTime wheelTime;
    private static final String TAG_CANCEL = "cancel";
    private static final String TAG_SUBMIT = "submit";

    public TimeSelectedView(WheelOptions wheelOptions) {
        super(wheelOptions.context);
        mWheelOptions = wheelOptions;
        initView(wheelOptions.context);
    }

    private void initView(Context context) {
        setDialogOutsideCancelable();
        initViews();
        initAnim();

        if (mWheelOptions.customListener == null) {
            LayoutInflater.from(context).inflate(R.layout.selected_view_time, contentContainer);

            TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
            RelativeLayout topBar = (RelativeLayout) findViewById(R.id.rvTopBar);

            Button btnCancel = (Button) findViewById(R.id.btnCancel);
            Button btnSubmit = (Button) findViewById(R.id.btnSubmit);

            btnCancel.setTag(TAG_CANCEL);
            btnSubmit.setTag(TAG_SUBMIT);

            btnCancel.setOnClickListener(this);
            btnSubmit.setOnClickListener(this);

            //设置文字
            btnCancel.setText(TextUtils.isEmpty(mWheelOptions.textContentCancel) ? context.getResources().getText(R.string.wheelview_cancel) : mWheelOptions.textContentCancel);
            btnSubmit.setText(TextUtils.isEmpty(mWheelOptions.textContentSubmit) ? context.getResources().getText(R.string.wheelview_submit) : mWheelOptions.textContentSubmit);
            tvTitle.setText(TextUtils.isEmpty(mWheelOptions.textContentTitle) ? "" : mWheelOptions.textContentTitle);
            //设置文字颜色
            btnCancel.setTextColor(mWheelOptions.textColorCancel);
            btnSubmit.setTextColor(mWheelOptions.textColorConfirm);
            tvTitle.setTextColor(mWheelOptions.textColorTitle);
            //设置文字大小
            btnCancel.setTextSize(mWheelOptions.textSizeCancel);
            btnSubmit.setTextSize(mWheelOptions.textSizeConfirm);
            tvTitle.setTextSize(mWheelOptions.textSizeTitle);
        } else {
            mWheelOptions.customListener.customLayout(LayoutInflater.from(context).inflate(mWheelOptions.layoutRes, contentContainer));
        }

        LinearLayout timeSelectorView = (LinearLayout) findViewById(R.id.ll_time_selector);
        timeSelectorView.setBackgroundColor(mWheelOptions.bgColorWheel);

        initWheelTime(timeSelectorView);
    }

    private void initWheelTime(LinearLayout selectorView) {
        wheelTime = new WheelTime(selectorView, mWheelOptions.textGravity, mWheelOptions.type, mWheelOptions.textContentSize);
        if (mWheelOptions.timeSelectChangeListener != null) {
            wheelTime.setSelectTimeChangeCallback(new ISelectTimeCallback() {
                @Override
                public void onTimeSelectChanged() {
                    try {
                        Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                        mWheelOptions.timeSelectChangeListener.onTimeSelectChange(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        if (mWheelOptions.startYear > 0 && mWheelOptions.endYear > 0 && mWheelOptions.startYear <= mWheelOptions.endYear) {
            //设置日期范围
            setRange();
        }

        //手动设置时间
        if (mWheelOptions.startDate != null && mWheelOptions.endDate != null) {
            if (mWheelOptions.startDate.getTimeInMillis() > mWheelOptions.endDate.getTimeInMillis()) {
                throw new IllegalArgumentException("起始日期不能大于终止日期");
            } else {
                setRangeDate();
            }
        } else if (mWheelOptions.startDate != null) {
            if (mWheelOptions.startDate.get(Calendar.YEAR) < 1900) {
                throw new IllegalArgumentException("起始日期不能早于1900年");
            } else {
                setRangeDate();
            }
        } else if (mWheelOptions.endDate != null) {
            if (mWheelOptions.endDate.get(Calendar.YEAR) > 2100) {
                throw new IllegalArgumentException("终止日期不能大于2100年");
            } else {
                setRangeDate();
            }
        } else {
            //没有使用时间范围限制，则使用默认的时间范围
            setRangeDate();
        }

        setTime();
        wheelTime.setTextXOffset(mWheelOptions.x_offset_year,mWheelOptions.x_offset_month,mWheelOptions.x_offset_day,
                mWheelOptions.x_offset_hours,mWheelOptions.x_offset_minutes,mWheelOptions.x_offset_seconds);
        wheelTime.setItemVisible(mWheelOptions.itemVisibleCount);
        wheelTime.setAlphaGradient(mWheelOptions.isAlphaGradient);
        setOutSideCancelable(mWheelOptions.cancelable);
        wheelTime.setCyclic(mWheelOptions.isCyclic);
        wheelTime.setDividerColor(mWheelOptions.dividerColor);
        wheelTime.setLineSpacingMultiplier(mWheelOptions.lineSpacingMultiplier);
        wheelTime.setTextColorOut(mWheelOptions.textColorOut);
        wheelTime.setTextColorCenter(mWheelOptions.textColorCenter);
    }

    private void setOutSideCancelable(boolean cancelable) {

    }

    public void setDate(Calendar date) {
        mWheelOptions.date = date;
        setTime();
    }

    private void setTime() {
        int year, month, day, hour, minutes, second;
        Calendar calendar = Calendar.getInstance();
        //没有设置选中时间，则默认为当前时间
        if (mWheelOptions.date == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minutes = calendar.get(Calendar.MINUTE);
            second = calendar.get(Calendar.SECOND);
        } else {
            year = mWheelOptions.date.get(Calendar.YEAR);
            month = mWheelOptions.date.get(Calendar.MONTH);
            day = mWheelOptions.date.get(Calendar.DAY_OF_MONTH);
            hour = mWheelOptions.date.get(Calendar.HOUR_OF_DAY);
            minutes = mWheelOptions.date.get(Calendar.MINUTE);
            second = mWheelOptions.date.get(Calendar.SECOND);
        }
        wheelTime.setSelector(year, month, day, hour, minutes, second);
    }

    private void setRangeDate() {
        wheelTime.setRangeDate(mWheelOptions.startDate, mWheelOptions.endDate);
        initDefaultSelectedDate();
    }

    private void initDefaultSelectedDate() {
        if (mWheelOptions.startDate != null && mWheelOptions.endDate != null) {
            if (mWheelOptions.date == null
                    || mWheelOptions.date.getTimeInMillis() < mWheelOptions.startDate.getTimeInMillis()
                    || mWheelOptions.date.getTimeInMillis() > mWheelOptions.endDate.getTimeInMillis()) {
                mWheelOptions.date = mWheelOptions.startDate;
            }
        } else if (mWheelOptions.startDate != null) {
            mWheelOptions.date = mWheelOptions.startDate;
        } else if (mWheelOptions.endDate != null) {
            mWheelOptions.date = mWheelOptions.endDate;
        }
    }

    private void setRange() {
        wheelTime.setStartYear(mWheelOptions.startYear);
        wheelTime.setEndYear(mWheelOptions.endYear);
    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
        } else if (tag.equals(TAG_CANCEL)) {
            if (mWheelOptions.cancelListener != null) {
                mWheelOptions.cancelListener.onClick(v);
            }
        }
        dismiss();
    }

    private void returnData() {
        if (mWheelOptions.timeSelectChangeListener != null) {
            try {

                Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                mWheelOptions.timeSelectListener.onTimeSelected(date, clickView);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    public void setTitleText(String title) {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        if (title != null) {
            tvTitle.setText(title);
        }
    }

    @Override
    protected boolean isDialog() {
        return mWheelOptions.isDialog;
    }
}
