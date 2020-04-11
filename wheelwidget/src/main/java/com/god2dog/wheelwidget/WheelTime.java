package com.god2dog.wheelwidget;

import android.view.View;

import com.god2dog.wheelwidget.adater.NumericWheelAdapter;
import com.god2dog.wheelwidget.listener.ISelectTimeCallback;
import com.god2dog.wheelwidget.listener.OnItemSelectedListener;
import com.god2dog.wheelwidget.view.WheelViewWidget;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/8
 * 描述：CommonWidget
 * <p>
 * 控件所用的时间
 */
public class WheelTime {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private View view;
    private WheelViewWidget wheelYear;
    private WheelViewWidget wheelMonth;
    private WheelViewWidget wheelDay;
    private WheelViewWidget wheelHour;
    private WheelViewWidget wheelMinutes;
    private WheelViewWidget wheelSecond;

    private int gravity;

    private static final int DEFAULT_START_YEAR = 1900;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_MONTH = 1;
    private static final int DEFAULT_END_MONTH = 12;
    private static final int DEFAULT_START_DAY = 1;
    private static final int DEFAULT_END_DAY = 31;

    private int startYear = DEFAULT_START_YEAR;
    private int endYear = DEFAULT_END_YEAR;
    private int startMonth = DEFAULT_START_MONTH;
    private int endMonth = DEFAULT_END_MONTH;
    private int startDay = DEFAULT_START_DAY;
    private int endDay = DEFAULT_END_DAY;

    private boolean[] type;

    private int currentYear;

    private int textSize;

    private ISelectTimeCallback mSelectTimeChangeCallback;

    public WheelTime(View view, int gravity, boolean[] type, int textSize) {
        super();
        this.view = view;
        this.gravity = gravity;
        this.type = type;
        this.textSize = textSize;
    }

    public void setSelector(int year, int month, int day) {
        setSelector(year, month, day, 0, 0, 0);
    }

    public void setSelector(int year, int month, int day, int hour, int minute, int second) {
        //设置时间
        setDate(year, month, day, hour, minute, second);
    }

    private void setDate(final int year, int month, int day, int hour, int minute, int second) {
        //大小月
        String[] month_big = new String[]{"1", "3", "5", "7", "8", "10", "12"};
        String[] month_little = new String[]{"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(month_big);
        final List<String> list_little = Arrays.asList(month_little);

        currentYear = year;
        //年
        wheelYear = (WheelViewWidget) view.findViewById(R.id.year);
        wheelYear.setAdapter(new NumericWheelAdapter(startYear, endYear));

        wheelYear.setCurrentItem(year - startYear);
        wheelYear.setGravity(gravity);
        //月
        wheelMonth = view.findViewById(R.id.month);
        if (startYear == endYear) {
            wheelMonth.setAdapter(new NumericWheelAdapter(startMonth, endMonth));
            wheelMonth.setCurrentItem(month + 1 - startMonth);
        } else if (year == startYear) {
            wheelMonth.setAdapter(new NumericWheelAdapter(startMonth, 12));
            wheelMonth.setCurrentItem(month + 1 - startMonth);
        } else if (year == endYear) {
            wheelMonth.setAdapter(new NumericWheelAdapter(1, endMonth));
            wheelMonth.setCurrentItem(month);
        } else {
            wheelMonth.setAdapter(new NumericWheelAdapter(1, 12));
            wheelMonth.setCurrentItem(month);
        }
        wheelMonth.setGravity(gravity);

        //日
        wheelDay = view.findViewById(R.id.day);
        //判断是否是闰年
        //能被4整除但不能被100整除和能被400整除
        boolean leapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

        if (startYear == endYear && startMonth == endMonth) {
            if (list_big.contains(String.valueOf(month + 1))) {
                //31天
                if (endDay > 31) {
                    endDay = 31;
                }
                wheelDay.setAdapter(new NumericWheelAdapter(startDay, endDay));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                //30天
                if (endDay > 30) {
                    endDay = 30;
                }
                wheelDay.setAdapter(new NumericWheelAdapter(startDay, endDay));
            } else {
                if (leapYear) {
                    //闰年 29天
                    if (endDay > 29) {
                        endDay = 29;
                    }
                } else {
                    //非闰年
                    if (endDay > 28) {
                        endDay = 28;
                    }
                }
                wheelDay.setAdapter(new NumericWheelAdapter(startDay, endDay));
            }
            wheelDay.setCurrentItem(day - startDay);
        } else if (startYear == year && month + 1 == startMonth) {
            if (list_big.contains(String.valueOf(month + 1))) {
                wheelDay.setAdapter(new NumericWheelAdapter(startDay, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                wheelDay.setAdapter(new NumericWheelAdapter(startDay, 30));
            } else {
                wheelDay.setAdapter(new NumericWheelAdapter(startDay, leapYear ? 29 : 28));
            }
            wheelDay.setCurrentItem(day - startDay);
        } else if (year == endYear && month == endMonth) {
            //终止日期
            if (list_big.contains(String.valueOf(month + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                wheelDay.setAdapter(new NumericWheelAdapter(1, endDay));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                wheelDay.setAdapter(new NumericWheelAdapter(1, endDay));
            } else {
                if (leapYear) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                }
                wheelDay.setAdapter(new NumericWheelAdapter(1, endDay));
            }
            wheelDay.setCurrentItem(day - 1);
        } else {
            //中间日期的确定 判断大小月及闰年
            if (list_big.contains(String.valueOf(month + 1))) {
                wheelDay.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                wheelDay.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                //todo: 待检测
                wheelDay.setAdapter(new NumericWheelAdapter(startDay, leapYear ? 29 : 28));
            }
            wheelDay.setCurrentItem(day - 1);
        }
        wheelDay.setGravity(gravity);

        //小时
        wheelHour = view.findViewById(R.id.hour);
        wheelHour.setAdapter(new NumericWheelAdapter(0, 23));

        wheelHour.setCurrentItem(hour);
        wheelHour.setGravity(gravity);

        //分钟
        wheelMinutes = view.findViewById(R.id.minute);
        wheelMinutes.setAdapter(new NumericWheelAdapter(0, 59));

        wheelMinutes.setCurrentItem(minute);
        wheelMinutes.setGravity(gravity);

        //秒数
        wheelSecond = view.findViewById(R.id.second);
        wheelSecond.setAdapter(new NumericWheelAdapter(0, 59));

        wheelSecond.setCurrentItem(second);
        wheelSecond.setGravity(gravity);

        //对年的监听
        wheelYear.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                int year_num = position + startYear;
                currentYear = year_num;
                int currentMonthItem = wheelMonth.getCurrentItem() + 1;
                if (startYear == endYear) {
                    wheelMonth.setAdapter(new NumericWheelAdapter(startMonth, endMonth));
                    if (currentMonthItem > wheelMonth.getAdapter().getItemCount() - 1) {
                        currentMonthItem = wheelMonth.getAdapter().getItemCount() - 1;
                        wheelMonth.setCurrentItem(currentMonthItem);
                    }
                    int monthNum = currentMonthItem + startMonth;
                    if (startMonth == endMonth) {
                        //重新设置日期
                        setReDay(year_num, monthNum, startDay, endDay, list_big, list_little);
                    } else if (monthNum == startMonth) {
                        setReDay(year_num, monthNum, startDay, 31, list_big, list_little);
                    } else if (monthNum == endMonth) {
                        setReDay(year_num, monthNum, 1, endDay, list_big, list_little);
                    } else {
                        setReDay(year_num, monthNum, 1, 31, list_big, list_little);
                    }
                } else if (year_num == startYear) {
                    //起始年
                    wheelMonth.setAdapter(new NumericWheelAdapter(startMonth, 12));
                    if (currentMonthItem > wheelMonth.getAdapter().getItemCount() - 1) {
                        currentMonthItem = wheelMonth.getAdapter().getItemCount() - 1;

                        wheelMonth.setCurrentItem(currentMonthItem);
                    }
                    int monthNum = currentMonthItem + startMonth;
                    if (monthNum == startMonth) {
                        setReDay(year_num, monthNum, startDay, 31, list_big, list_little);
                    } else {
                        setReDay(year_num, monthNum, 1, 31, list_big, list_little);
                    }
                } else if (year_num == endYear) {
                    //终止年
                    wheelMonth.setAdapter(new NumericWheelAdapter(1, endMonth));
                    if (currentMonthItem > wheelMonth.getAdapter().getItemCount() - 1) {
                        currentMonthItem = wheelMonth.getAdapter().getItemCount() - 1;
                        wheelMonth.setCurrentItem(currentMonthItem);
                    }
                    int monthNum = currentMonthItem + startMonth;
                    if (monthNum == endMonth) {
                        setReDay(year_num, monthNum, 1, endDay, list_big, list_little);
                    } else {
                        setReDay(year_num, monthNum, 1, 31, list_big, list_little);
                    }
                } else {
                    wheelMonth.setAdapter(new NumericWheelAdapter(1, 12));
                    setReDay(year_num, wheelMonth.getCurrentItem() + 1, 1, 31, list_big, list_little);
                }

                if (mSelectTimeChangeCallback != null){
                    mSelectTimeChangeCallback.onTimeSelectChanged();
                }
            }
        });

        //对月份的监听
        wheelMonth.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                int monthNum = position + 1;
                if (startYear == endYear){
                    monthNum = monthNum + startMonth -  1;
                    if (startMonth == endMonth){
                        setReDay(currentYear,monthNum,startDay,endDay,list_big,list_little);
                    }else if (monthNum == startMonth){
                        setReDay(currentYear,monthNum,startDay,31,list_big,list_little);
                    }else if (monthNum == endMonth){
                        setReDay(currentYear,monthNum,1,endDay,list_big,list_little);
                    }else {
                        setReDay(currentYear,monthNum,1,31,list_big,list_little);
                    }
                }else if (currentYear == startYear){
                    monthNum = monthNum + startMonth - 1;
                    if (monthNum == startMonth){
                        setReDay(currentYear,monthNum,startDay,31,list_big,list_little);
                    }else {
                        setReDay(currentYear,monthNum,1,31,list_big,list_little);
                    }
                }else if (currentYear == endYear){
                    if (monthNum == endMonth){
                        setReDay(currentYear,wheelMonth.getCurrentItem() + 1,1,endDay,list_big,list_little);
                    }else {
                        setReDay(currentYear,wheelMonth.getCurrentItem() + 1, 1, 31,list_big,list_little);
                    }
                }else {
                    setReDay(currentYear,monthNum, 1, 31,list_big,list_little);
                }
                if (mSelectTimeChangeCallback != null){
                    mSelectTimeChangeCallback.onTimeSelectChanged();
                }
            }
        });

        setChangeListener(wheelDay);
        setChangeListener(wheelHour);
        setChangeListener(wheelMinutes);
        setChangeListener(wheelSecond);

        if (type.length != 6){
            throw new IllegalArgumentException("type 类型初始化不正确");
        }
        wheelYear.setVisibility(type[0] ? View.VISIBLE : View.GONE);
        wheelMonth.setVisibility(type[1] ? View.VISIBLE : View.GONE);
        wheelDay.setVisibility(type[2] ? View.VISIBLE : View.GONE);
        wheelHour.setVisibility(type[3] ? View.VISIBLE : View.GONE);
        wheelMinutes.setVisibility(type[4] ? View.VISIBLE : View.GONE);
        wheelSecond.setVisibility(type[5] ? View.VISIBLE : View.GONE);

        setContentTextSize();
    }

    private void setContentTextSize() {
        wheelYear.setTextSize(textSize);
        wheelMonth.setTextSize(textSize);
        wheelDay.setTextSize(textSize);
        wheelHour.setTextSize(textSize);
        wheelMinutes.setTextSize(textSize);
        wheelSecond.setTextSize(textSize);
    }

    private void setChangeListener(WheelViewWidget widget) {
        if (mSelectTimeChangeCallback != null){
            widget.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int position) {
                    mSelectTimeChangeCallback.onTimeSelectChanged();
                }
            });
        }
    }

    private void setReDay(int year_num, int monthNum, int startDay, int endDay, List<String> list_big, List<String> list_little) {
        int currentDay = wheelDay.getCurrentItem();
        if (list_big.contains(String.valueOf(monthNum))){
            if (endDay > 31){
                endDay = 31;
            }
            wheelDay.setAdapter(new NumericWheelAdapter(startDay,endDay));
        }else if (list_little.contains(String.valueOf(monthNum))){
            if (endDay > 30){
                endDay = 30;
            }
            wheelDay.setAdapter(new NumericWheelAdapter(startDay,endDay));
        }else {
            if ((year_num % 4 ==0 && year_num % 100 != 0) || (year_num % 400 == 0)){
                //闰年
                if (endDay >29){
                    endDay = 29;
                }

            }else {
                if (endDay > 28){
                    endDay = 28;
                }
            }
            wheelDay.setAdapter(new NumericWheelAdapter(startDay,endDay));
        }

        if (currentDay > wheelDay.getAdapter().getItemCount() -1){
            currentDay = wheelDay.getAdapter().getItemCount() - 1;

            wheelDay.setCurrentItem(currentDay);
        }
    }

    public void setSelectTimeChangeCallback(ISelectTimeCallback mSelectTimeChangeCallback) {
        this.mSelectTimeChangeCallback = mSelectTimeChangeCallback;
    }

    public String getTime() {
        StringBuilder sb = new StringBuilder();
        if (currentYear == startYear){
            if (( wheelMonth.getCurrentItem() + startMonth ) == startMonth ){
                sb.append((wheelYear.getCurrentItem() + startYear )).append("-")
                        .append((wheelMonth.getCurrentItem() + startMonth)).append("-")
                        .append((wheelDay.getCurrentItem() + startDay)).append(" ")
                        .append(wheelHour.getCurrentItem()).append(":")
                        .append(wheelMinutes.getCurrentItem()).append(":")
                        .append(wheelSecond.getCurrentItem());
            }else {
                sb.append((wheelYear.getCurrentItem() + startYear )).append("-")
                        .append((wheelMonth.getCurrentItem() + startMonth)).append("-")
                        .append((wheelDay.getCurrentItem() + 1)).append(" ")
                        .append(wheelHour.getCurrentItem()).append(":")
                        .append(wheelMinutes.getCurrentItem()).append(":")
                        .append(wheelSecond.getCurrentItem());
            }
        }else {
            sb.append((wheelYear.getCurrentItem() + startYear )).append("-")
                    .append((wheelMonth.getCurrentItem() + 1)).append("-")
                    .append((wheelDay.getCurrentItem() + 1)).append(" ")
                    .append(wheelHour.getCurrentItem()).append(":")
                    .append(wheelMinutes.getCurrentItem()).append(":")
                    .append(wheelSecond.getCurrentItem());
        }
        return sb.toString();
    }

    public void setItemVisible(int count) {
        wheelYear.setItemVisibleCount(count);
        wheelMonth.setItemVisibleCount(count);
        wheelDay.setItemVisibleCount(count);
        wheelHour.setItemVisibleCount(count);
        wheelMinutes.setItemVisibleCount(count);
        wheelSecond.setItemVisibleCount(count);
    }

    public void setAlphaGradient(boolean isAlphaGradient) {
        wheelYear.setAlphaGradient(isAlphaGradient);
        wheelMonth.setAlphaGradient(isAlphaGradient);
        wheelDay.setAlphaGradient(isAlphaGradient);
        wheelHour.setAlphaGradient(isAlphaGradient);
        wheelMinutes.setAlphaGradient(isAlphaGradient);
        wheelSecond.setAlphaGradient(isAlphaGradient);
    }

    public void setCyclic(boolean isCyclic) {
        wheelYear.setIsLoop(isCyclic);
    }

    public void setDividerColor(int dividerColor) {
        wheelYear.setDividerColor(dividerColor);
        wheelMonth.setDividerColor(dividerColor);
        wheelDay.setDividerColor(dividerColor);
        wheelHour.setDividerColor(dividerColor);
        wheelMinutes.setDividerColor(dividerColor);
        wheelSecond.setDividerColor(dividerColor);
    }

    public void setLineSpacingMultiplier(float multiplier) {
        wheelYear.setItemSpaceMultiplier(multiplier);
        wheelMonth.setItemSpaceMultiplier(multiplier);
        wheelDay.setItemSpaceMultiplier(multiplier);
        wheelHour.setItemSpaceMultiplier(multiplier);
        wheelMinutes.setItemSpaceMultiplier(multiplier);
        wheelSecond.setItemSpaceMultiplier(multiplier);
    }

    public void setTextColorOut(int textColorOut) {
        wheelYear.setTextColorOut(textColorOut);
        wheelMonth.setTextColorOut(textColorOut);
        wheelDay.setTextColorOut(textColorOut);
        wheelHour.setTextColorOut(textColorOut);
        wheelMinutes.setTextColorOut(textColorOut);
        wheelSecond.setTextColorOut(textColorOut);
    }

    public void setTextColorCenter(int textColorCenter) {
        wheelYear.setTextColorCenter(textColorCenter);
        wheelMonth.setTextColorCenter(textColorCenter);
        wheelDay.setTextColorCenter(textColorCenter);
        wheelHour.setTextColorCenter(textColorCenter);
        wheelMinutes.setTextColorCenter(textColorCenter);
        wheelSecond.setTextColorCenter(textColorCenter);
    }

    public void setTextXOffset(int x_offset_year, int x_offset_month, int x_offset_day, int x_offset_hours, int x_offset_minutes, int x_offset_seconds) {
        wheelYear.setTextXOffset(x_offset_year);
        wheelMonth.setTextXOffset(x_offset_month);
        wheelDay.setTextXOffset(x_offset_day);
        wheelHour.setTextXOffset(x_offset_hours);
        wheelMinutes.setTextXOffset(x_offset_minutes);
        wheelSecond.setTextXOffset(x_offset_seconds);
    }

    public void setRangeDate(Calendar startDate,Calendar endDate){
        if (startDate == null && endDate != null){
            int year = endDate.get(Calendar.YEAR);
            int month = endDate.get(Calendar.MONTH) + 1;
            int day = endDate.get(Calendar.DAY_OF_MONTH);

            if (year > startYear){
                this.endYear = year;
                this.endMonth = month;
                this.endDay = day;
            }else if (year == startYear){
                if (month > startMonth){
                    this.endYear = year;
                    this.endMonth = month;
                    this.endDay = day;
                }else if (month == startMonth){
                    if (day > startDay){
                        this.endYear = year;
                        this.endMonth = month;
                        this.endDay = day;
                    }
                }
            }
        }else if (startDate != null && endDate == null){
            int year = startDate.get(Calendar.YEAR);
            int month = startDate.get(Calendar.MONTH) + 1;
            int day = startDate.get(Calendar.DAY_OF_MONTH);

            if (year < endYear){
                this.startYear = year;
                this.startMonth = month;
                this.startDay = day;
            }else if (year == endYear){
                if (month < endMonth){
                    this.startYear = year;
                    this.startMonth = month;
                    this.startDay = day;
                }else if (month == endMonth){
                    if (day < endDay){
                        this.startYear = year;
                        this.startMonth = month;
                        this.startDay = day;
                    }
                }
            }
        }else if (startDate != null && endDate != null){
            this.startYear = startDate.get(Calendar.YEAR);
            this.endYear = endDate.get(Calendar.YEAR);
            this.startMonth = startDate.get(Calendar.MONTH) + 1;
            this.endMonth = endDate.get(Calendar.MONTH) + 1;
            this.startDay = startDate.get(Calendar.DAY_OF_MONTH);
            this.endDay = endDate.get(Calendar.DAY_OF_MONTH);
        }
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setLabels(String label_year,String label_month,String label_day , String label_hour, String label_minutes, String label_second){
        if (label_year != null){
            wheelYear.setLabel(label_year);
        }else {
            wheelYear.setLabel(view.getContext().getString(R.string.wheelview_year));
        }
        if (label_month != null){
            wheelMonth.setLabel(label_month);
        }else {
            wheelMonth.setLabel(view.getContext().getString(R.string.wheelview_month));
        }
        if (label_day != null){
            wheelDay.setLabel(label_day);
        }else {
            wheelDay.setLabel(view.getContext().getString(R.string.wheelview_day));
        }
        if (label_hour != null){
            wheelHour.setLabel(label_hour);
        }else {
            wheelHour.setLabel(view.getContext().getString(R.string.wheelview_hour));
        }
        if (label_minutes != null){
            wheelMinutes.setLabel(label_minutes);
        }else {
            wheelMinutes.setLabel(view.getContext().getString(R.string.wheelview_minutes));
        }
        if (label_second != null){
            wheelSecond.setLabel(label_second);
        }else {
            wheelSecond.setLabel(view.getContext().getString(R.string.wheelview_second));
        }

    }

    public void isCenterLabel(boolean isCenterLabel) {
        wheelYear.isCenterLabel(isCenterLabel);
        wheelMonth.isCenterLabel(isCenterLabel);
        wheelDay.isCenterLabel(isCenterLabel);
        wheelHour.isCenterLabel(isCenterLabel);
        wheelMinutes.isCenterLabel(isCenterLabel);
        wheelSecond.isCenterLabel(isCenterLabel);

    }
}
