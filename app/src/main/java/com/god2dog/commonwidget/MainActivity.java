package com.god2dog.commonwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.god2dog.wheelwidget.listener.OnTimeSelectChangeListener;
import com.god2dog.wheelwidget.listener.OnTimeSelectListener;
import com.god2dog.wheelwidget.builder.TimeSelectBuilder;
import com.god2dog.wheelwidget.view.TimeSelectedView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TimeSelectedView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTimePicker();

        Button button = findViewById(R.id.btnShowDialog);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.show();
            }
        });
    }


    private void initTimePicker() {//Dialog 模式下，在底部弹出
        timer = new TimeSelectBuilder(this, new OnTimeSelectListener() {

            @Override
            public void onTimeSelected(Date date, View view) {

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChange(Date date) {

                    }
                })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(9) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(1.8f)
                .isCenterLabel(false)
                .isAlphaGradient(true)
                .setGravity(Gravity.CENTER)
                .setContentTextSize(20)
                .isCyclic(true)
                .build();

//        Dialog mDialog = timer.getDialog();
//        if (mDialog != null) {
//
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    Gravity.BOTTOM);
//
//            params.leftMargin = 0;
//            params.rightMargin = 0;
//            timer.getDialogContainerLayout().setLayoutParams(params);
//
//            Window dialogWindow = mDialog.getWindow();
//            if (dialogWindow != null) {
//                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
//                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
//                dialogWindow.setDimAmount(0.3f);
//            }
//        }
    }

}
