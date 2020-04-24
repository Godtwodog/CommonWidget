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
        dataModel.yearStart = 1970;
        dataModel.monthStart = 1;
        dataModel.monthCount = 130 * 12;
        dataModel.leastDaysNum = 1;
        dataModel.mostDaysNum = 100;


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
