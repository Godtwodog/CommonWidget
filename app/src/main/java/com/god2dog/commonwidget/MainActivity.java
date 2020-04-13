package com.god2dog.commonwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.god2dog.dialoglibrary.BottomDialog;
import com.god2dog.dialoglibrary.BottomListDialog;
import com.god2dog.dialoglibrary.CustomDialog;
import com.god2dog.dialoglibrary.Item;
import com.god2dog.dialoglibrary.OnItemClickListener;
import com.god2dog.wheelwidget.listener.OnTimeSelectChangeListener;
import com.god2dog.wheelwidget.listener.OnTimeSelectListener;
import com.god2dog.wheelwidget.builder.TimeSelectBuilder;
import com.god2dog.wheelwidget.view.TimeSelectedView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TimeSelectedView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        initTimePicker();

        initBottomDialog();

        initBottomListDialog();

        Button button = findViewById(R.id.btnShowDialog);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.show();
            }
        });
    }

    private void initBottomListDialog() {
        final ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Item item = new Item();
            item.setTitle("第"+(i + 1) +"个item");

            items.add(item);
        }

        findViewById(R.id.bottomListDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomListDialog dialog = new BottomListDialog(MainActivity.this);
                dialog.addItemList(items, new OnItemClickListener() {
                    @Override
                    public void click(Item item) {
                        Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_LONG).show();
                    }
                });

                dialog.show();
            }
        });
    }

    private void initBottomDialog() {
        findViewById(R.id.horizontal_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new BottomDialog(MainActivity.this)
                        .title(R.string.share_title)
                        .orientation(CustomDialog.HORIZONTAL)
                        .inflateMenu(R.menu.menu_share, new OnItemClickListener() {
                            @Override
                            public void click(Item item) {
                                Toast.makeText(MainActivity.this, getString(R.string.share_title) + item.getTitle(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        findViewById(R.id.horizontal_multi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new BottomDialog(MainActivity.this)
                        .title(R.string.share_title)
                        .orientation(CustomDialog.HORIZONTAL)
                        .inflateMenu(R.menu.menu_share, new OnItemClickListener() {
                            @Override
                            public void click(Item item) {
                                Toast.makeText(MainActivity.this, getString(R.string.share_title) + item.getTitle(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .inflateMenu(R.menu.menu_main, new OnItemClickListener() {
                            @Override
                            public void click(Item item) {
                                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        findViewById(R.id.vertical).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new BottomDialog(MainActivity.this)
                        .title(R.string.title_item)
                        .orientation(CustomDialog.VERTICAL)
                        .inflateMenu(R.menu.menu_share, new OnItemClickListener() {
                            @Override
                            public void click(Item item) {
                                Toast.makeText(MainActivity.this, getString(R.string.share_title) + item.getTitle(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        findViewById(R.id.grid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new BottomDialog(MainActivity.this)
                        .title(R.string.title_item)
                        .layout(CustomDialog.GRID)
                        .orientation(CustomDialog.VERTICAL)
                        .inflateMenu(R.menu.menu_grid, new OnItemClickListener() {
                            @Override
                            public void click(Item item) {
                                Toast.makeText(MainActivity.this, getString(R.string.share_title) + item.getTitle(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
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
