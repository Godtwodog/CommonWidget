package com.god2dog.commonwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.god2dog.addresspickerview.AddressModel;
import com.god2dog.addresspickerview.AddressPickerView;
import com.god2dog.dialoglibrary.BottomDialog;
import com.god2dog.dialoglibrary.BottomListDialog;
import com.god2dog.dialoglibrary.CustomDialog;
import com.god2dog.dialoglibrary.Item;
import com.god2dog.dialoglibrary.OnItemClickListener;
import com.god2dog.wheelwidget.listener.OnTimeSelectChangeListener;
import com.god2dog.wheelwidget.listener.OnTimeSelectListener;
import com.god2dog.wheelwidget.builder.TimeSelectBuilder;
import com.god2dog.wheelwidget.view.TimeSelectedView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TimeSelectedView timer;
    private int[] i;

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

        initJump();

        initAddress();
    }

    private void initAddress() {
        final List<AddressModel> datas = new ArrayList<>();
        final Button addressButton = findViewById(R.id.showAddressPickerView);
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
        final AddressPickerView pickerView = new AddressPickerView(MainActivity.this, datas);
        pickerView.setAddressPickerViewCallbackk(new AddressPickerView.AddressPickerViewCallback() {
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
