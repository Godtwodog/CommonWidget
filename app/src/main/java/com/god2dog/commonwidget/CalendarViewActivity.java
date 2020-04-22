package com.god2dog.commonwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.god2dog.calendarview.DatePickerController;
import com.god2dog.calendarview.DayPickerView;
import com.god2dog.calendarview.SimpleMonthAdapter;

import java.util.ArrayList;
import java.util.List;

public class CalendarViewActivity extends AppCompatActivity {
    private DayPickerView dayPickerView;
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        dayPickerView =findViewById(R.id.dayPickerView);

        DayPickerView.DataModel dataModel = new DayPickerView.DataModel();
        dataModel.yearStart = 2020;
        dataModel.monthStart = 3;
        dataModel.monthCount = 20;
//        dataModel.defTag = "";
        dataModel.leastDaysNum = 2;
        dataModel.mostDaysNum = 100;

//        SimpleMonthAdapter.CalendarDay startDay = new SimpleMonthAdapter.CalendarDay(2016, 6, 5);
//        SimpleMonthAdapter.CalendarDay endDay = new SimpleMonthAdapter.CalendarDay(2016, 6, 20);
//        SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays = new SimpleMonthAdapter.SelectedDays<>(startDay, endDay);
//        dataModel.selectedDays = selectedDays;

//        SimpleMonthAdapter.CalendarDay tag = new SimpleMonthAdapter.CalendarDay(2016, 7, 15);
//        tag.setTag("标签1");
//
//        SimpleMonthAdapter.CalendarDay tag2 = new SimpleMonthAdapter.CalendarDay(2016, 8, 15);
//        tag2.setTag("标签2");
//        List<SimpleMonthAdapter.CalendarDay> tags = new ArrayList<>();
//        tags.add(tag);
//        tags.add(tag2);
//        dataModel.tags = tags;

        dayPickerView.setParameter(dataModel, new DatePickerController() {
            @Override
            public void onDayOfMonthSelected(SimpleMonthAdapter.CalendarDay calendarDay) {
                Toast.makeText(context, "onDayOfMonthSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateRangeSelected(List<SimpleMonthAdapter.CalendarDay> selectedDays) {
                Toast.makeText(context, "onDateRangeSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void alertSelectedFail(FailEven even) {
                Toast.makeText(context, "alertSelectedFail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
