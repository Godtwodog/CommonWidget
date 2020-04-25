package com.god2dog.commonwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.god2dog.addresspickerview.AddressPickerView;
import com.god2dog.basecode.AddressModel;
import com.god2dog.calendarview.DatePickerController;
import com.god2dog.calendarview.DayPickerView;
import com.god2dog.calendarview.SimpleMonthAdapter;
import com.god2dog.dialoglibrary.BottomDialog;
import com.god2dog.dialoglibrary.BottomListDialog;
import com.god2dog.dialoglibrary.CustomDialog;
import com.god2dog.dialoglibrary.Item;
import com.god2dog.dialoglibrary.OnItemClickListener;
import com.god2dog.wheelwidget.AddressSelectedView;
import com.god2dog.wheelwidget.builder.AddressSelectedBuilder;
import com.god2dog.wheelwidget.builder.TimeSelectBuilder;
import com.god2dog.wheelwidget.listener.OnOptionsSelectedListener;
import com.god2dog.wheelwidget.listener.OnTimeSelectChangeListener;
import com.god2dog.wheelwidget.view.TimeSelectedView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TimeSelectedView timer;
    //时间选择器
    private TimePickerView pvTime;
    private int[] i;
    private List<AddressModel> datas = new ArrayList<>();
    private DayPickerView.DataModel dataModel;

    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initJson();

        initTimePicker();

        initBottomDialog();

        initBottomListDialog();

        initTimeSelect();

        initJump();

        initAddress();

        initAddressStyleTwo();
    }

    private void initData() {

        dataModel = new DayPickerView.DataModel();
        dataModel.yearStart = 1970;
        dataModel.monthStart = 1;
        dataModel.monthCount = 130 * 12;
        dataModel.leastDaysNum = 1;
        dataModel.mostDaysNum = 100;
    }

    private void initTimeSelect() {
        findViewById(R.id.showTimePickerView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });
    }

    private void initAddressStyleTwo() {
        Button addressStyleTwoButton = findViewById(R.id.showAddressSelectedView);

//        formatAddressData( datas);
//
//        final AddressSelectedView aSelectedView = new AddressSelectedBuilder(this, new OnOptionsSelectedListener() {
//            @Override
//            public void onSelectedItem(int options1, int options2, int options3) {
//                //返回的分别是三个级别的选中位置
//                String opt1tx = datas.size() > 0 ?
//                        datas.get(options1).getName() : "";
//
//                String opt2tx = options2Items.size() > 0
//                        && options2Items.get(options1).size() > 0 ?
//                        options2Items.get(options1).get(options2) : "";
//
//                String opt3tx = options2Items.size() > 0
//                        && options3Items.get(options1).size() > 0
//                        && options3Items.get(options1).get(options2).size() > 0 ?
//                        options3Items.get(options1).get(options2).get(options3) : "";
//
//                String tx = opt1tx + opt2tx + opt3tx;
//                Toast.makeText(MainActivity.this, tx, Toast.LENGTH_SHORT).show();
//            }
//        })
//                .setTitleText("地址选择")
//                .setDividerColor(Color.BLACK)
//                .build();
//        aSelectedView.setData(datas,options2Items,options3Items);

        addressStyleTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, JsonDataActivity.class));
            }
        });

    }

    private void formatAddressData(List<AddressModel> datas) {
        if (datas == null) return;

        for (int i = 0; i < datas.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < datas.get(i).getCityModels().size(); c++) {//遍历该省份的所有城市
                String cityName = datas.get(i).getCityModels().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (datas.get(i).getCityModels().get(c).getAreaModels() == null
                        || datas.get(i).getCityModels().get(c).getAreaModels().size() == 0) {
                    city_AreaList.add("");
                } else {
                    for (AddressModel.CityModel.AreaModel model : datas.get(i).getCityModels().get(c).getAreaModels()) {
                        city_AreaList.add(model.getName());
                    }
                }
//                cModel.setAreaModels();
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

    }

    private void initJson() {
        String json = getCityJson();
        try {
            JSONArray a1 = new JSONArray(json);
            for (int i = 0; i < a1.length(); i++) {
                JSONObject j1 = a1.optJSONObject(i);
                AddressModel m1 = new AddressModel();
                m1.setName(j1.optString("label"));
                m1.setValue(j1.optString("value"));
                m1.setStatus(false);
                JSONArray a2 = j1.optJSONArray("children");
                List<AddressModel.CityModel> cModels = new ArrayList<>();
                for (int j = 0; j < a2.length(); j++) {
                    JSONObject j2 = a2.optJSONObject(j);
                    AddressModel.CityModel c2 = new AddressModel.CityModel();
                    c2.setName(j2.optString("label"));
                    c2.setValue(j2.optString("value"));
                    c2.setStatus(false);
                    JSONArray a3 = j2.optJSONArray("children");
                    List<AddressModel.CityModel.AreaModel> aModels = new ArrayList<>();
                    if (a3 != null) {
                        for (int n = 0; n < a3.length(); n++) {
                            JSONObject j3 = a3.optJSONObject(n);
                            AddressModel.CityModel.AreaModel m3 = new AddressModel.CityModel.AreaModel();
                            m3.setName(j3.optString("label"));
                            m3.setValue(j3.optString("value"));
                            m3.setStatus(false);
                            aModels.add(m3);
                        }
                        c2.setAreaModels(aModels);
                        cModels.add(c2);
                    }
                }
                m1.setCityModels(cModels);
                datas.add(m1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initAddress() {
        final Button addressButton = findViewById(R.id.showAddressPickerView);

        final AddressPickerView pickerView = new AddressPickerView(MainActivity.this, datas);
        pickerView.setAddressPickerViewCallback(new AddressPickerView.AddressPickerViewCallback() {
            @Override
            public void callback(int... value) {
                i = value;
                if (value.length == 3) {
                    addressButton.setText(datas.get(value[0]).getName() + "-" + datas.get(value[0]).getCityModels().get(value[1]).getName() + "-" + datas.get(value[0]).getCityModels().get(value[1]).getAreaModels().get(value[2]).getName());
                } else {
                    addressButton.setText(datas.get(value[0]).getName() + "-" + datas.get(value[0]).getCityModels().get(value[1]).getName());
                }

            }
        });


        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerView.setSelect(i);
                pickerView.show();
            }
        });
    }

    private void initJump() {
        //toast 页面
        findViewById(R.id.jump2toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ToastActivity.class));
            }
        });

        //中间弹窗
        findViewById(R.id.jump2CenterDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DialogActivity.class));
            }
        });

        //日历控件
        findViewById(R.id.jump2CalendarView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CalendarViewActivity.class));
            }
        });

        //照片选择控件
        findViewById(R.id.jump2ImageSelector).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ImageSelectorActivity.class));
            }
        });

        //轮播控件
        findViewById(R.id.jump2BannerView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BannerViewActivity.class));
            }
        });
        //用户中心
        findViewById(R.id.jump2UserCenter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
        });
        //tab
        findViewById(R.id.jump2Tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TabActivity.class));
            }
        });
        //展示日历弹框
        findViewById(R.id.showCalendarDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this, R.style.Calendar_Dialog);
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_calendar_dialog, null);
                TextView cancelAction = view.findViewById(R.id.tvCancel);
                TextView confirmAction = view.findViewById(R.id.tvConfirm);
                TextView title = view.findViewById(R.id.tvTitle);
                DayPickerView pickerView = view.findViewById(R.id.dayPickerView);
                pickerView.setParameter(dataModel, new DatePickerController() {
                    @Override
                    public void onDayOfMonthSelected(SimpleMonthAdapter.CalendarDay calendarDay) {
                        //单选
                    }

                    @Override
                    public void onDateRangeSelected(List<SimpleMonthAdapter.CalendarDay> selectedDays) {
                        //多选
                    }

                    @Override
                    public void alertSelectedFail(FailEven even) {

                    }
                });
                cancelAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(view);
                Window dialogWindow = dialog.getWindow();
                dialogWindow.setGravity(Gravity.BOTTOM);
                WindowManager.LayoutParams params = dialogWindow.getAttributes();
                Display display = dialogWindow.getWindowManager().getDefaultDisplay();
                params.height = display.getHeight() * 7 / 8;
                dialogWindow.setAttributes(params);
                dialog.show();
            }
        });
    }

    private void initBottomListDialog() {
        final ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Item item = new Item();
            item.setTitle("第" + (i + 1) + "个item");

            items.add(item);
        }

        findViewById(R.id.bottomListDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomListDialog dialog = new BottomListDialog(MainActivity.this);
                dialog.addItemList(items, new OnItemClickListener() {
                    @Override
                    public void click(Item item) {
                        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                    }
                });
                dialog.setTitle("我是标题");
                dialog.setCancelStyle(BottomListDialog.CANCEL.BOTTOM);
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
        timer = new TimeSelectBuilder(this, new com.god2dog.wheelwidget.listener.OnTimeSelectListener() {

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

        //
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(2013, 0, 1);
        endDate.set(2020, 11, 31);

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Toast.makeText(MainActivity.this, date.toString(), Toast.LENGTH_SHORT).show();
            }
        })
                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("我是标题")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();

        Button button = findViewById(R.id.btnShowDialog);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.show();
            }
        });


    }

    private String getCityJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("region.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
