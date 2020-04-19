package com.god2dog.addresspickerview;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.god2dog.basecode.AddressModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/16
 * 描述：CommonWidget
 * <p>
 * 仿京东地址选择控件
 */
public class AddressPickerView {
    private Context context;

    private Dialog mDialog;

    private RelativeLayout mTitleBar;
    private TextView mTitle;
    private ImageView mClose;
    private TabLayout mAreaTab;
    private ViewPager viewPager;
    private View dialogView;

    private LayoutInflater layoutInflater;

    private List<View> viewList;

    private List<String> tabNameList;
    //省
    private RecyclerView pRecyclerView;
    //市
    private RecyclerView cRecyclerView;
    //区
    private RecyclerView aRecyclerView;

    private ViewPagerAdapter mPagerAdapter;
    private ProvinceAdapter mProvinceAdapter;
    private CityAdapter mCityAdapter;
    private AreaAdapter mAreaAdapter;

    private List<AddressModel> addressModels;
    private List<AddressModel.CityModel> cityModels;
    private List<AddressModel.CityModel.AreaModel> areaModels;

    private int pSelectedIndex = -1;
    private int cSelectedIndex = -1;
    private int aSelectedIndex = -1;

    private int pOldIndex = -1;
    private int cOldIndex = -1;
    private int aOldIndex = -1;

    private boolean isCreate = false;
    private AddressPickerViewCallback addressPickerViewCallback;
    private  Display display;

    public AddressPickerView(Context context, List<AddressModel> addressModels) {
        this.context = context;
        this.addressModels = addressModels;
        layoutInflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView(context);
    }

    private void initView(Context context) {
        isCreate = true;
        mDialog = new Dialog(context, R.style.Dialog);
        Window dialogWindow = mDialog.getWindow();
        dialogView = layoutInflater.inflate(R.layout.layout_address_dialog, null);
        mTitleBar = dialogView.findViewById(R.id.rlTitleBar);
        mTitle = dialogView.findViewById(R.id.tvTitle);
        mClose = dialogView.findViewById(R.id.ivClose);
        viewPager = dialogView.findViewById(R.id.viewpager);
        mAreaTab = dialogView.findViewById(R.id.tlTabLayout);

        dialogWindow.getDecorView().setPadding(0,0,0,0);
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = display.getHeight() * 2 / 3;
        dialogWindow.setAttributes(params);
        mDialog.setContentView(dialogView);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        View provinceView = layoutInflater.inflate(R.layout.layout_recyclerview, null, false);
        View cityView = layoutInflater.inflate(R.layout.layout_recyclerview, null, false);
        View areaView = layoutInflater.inflate(R.layout.layout_recyclerview, null, false);

        pRecyclerView = provinceView.findViewById(R.id.recyclerView);
        cRecyclerView = cityView.findViewById(R.id.recyclerView);
        aRecyclerView = areaView.findViewById(R.id.recyclerView);

        viewList = new ArrayList<>();
        viewList.add(provinceView);
        viewList.add(cityView);
        viewList.add(areaView);

        mPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(mPagerAdapter);
        mAreaTab.setupWithViewPager(viewPager);

        mProvinceAdapter = new ProvinceAdapter(addressModels, context);
        pRecyclerView.setAdapter(mProvinceAdapter);
        pRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mProvinceAdapter.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
                cityModels.clear();
                areaModels.clear();
                addressModels.get(position).setStatus(true);
                pSelectedIndex = position;
                if (pOldIndex != -1 && pOldIndex != pSelectedIndex) {
                    addressModels.get(pOldIndex).setStatus(false);
                    Log.e("AreaPickerView", "清空");
                }
                if (position != pOldIndex) {
                    if (cOldIndex != -1) {
                        addressModels.get(pOldIndex).getCityModels().get(cOldIndex).setStatus(false);
                    }
                    if (aOldIndex != -1) {
                        addressModels.get(pOldIndex).getCityModels().get(cOldIndex).getAreaModels().get(aOldIndex).setStatus(false);
                    }
                    cOldIndex = -1;
                    aOldIndex = -1;
                }
                cityModels.addAll(addressModels.get(position).getCityModels());
               notifyDataSetChanged();
                tabNameList.set(0, addressModels.get(position).getName());
                if (tabNameList.size() == 1) {
                    tabNameList.add("请选择");
                } else if (tabNameList.size() > 1) {
                    if (position != pOldIndex) {
                        tabNameList.set(1, "请选择");
                        if (tabNameList.size() == 3) {
                            tabNameList.remove(2);
                        }
                    }
                }
                mAreaTab.setupWithViewPager(viewPager);
                mPagerAdapter.notifyDataSetChanged();
                mAreaTab.getTabAt(1).select();
                pOldIndex = pSelectedIndex;
            }
        });

        cityModels = new ArrayList<>();
        mCityAdapter = new CityAdapter(cityModels, context);
        cRecyclerView.setAdapter(mCityAdapter);
        cRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mCityAdapter.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
                areaModels.clear();
                cityModels.get(position).setStatus(true);
                cSelectedIndex = position;
                if (cOldIndex != -1 && cOldIndex != cSelectedIndex) {
                    addressModels.get(pOldIndex).getCityModels().get(cOldIndex).setStatus(false);
                }
                if (position != cOldIndex) {
                    if (aOldIndex != -1 && cityModels.get(position).getAreaModels() != null) {
                        addressModels.get(pOldIndex).getCityModels().get(cOldIndex).getAreaModels().get(aOldIndex).setStatus(false);
                    }
                    aOldIndex = -1;
                }
                cOldIndex = cSelectedIndex;
                if (cityModels.get(position).getAreaModels() != null) {
                    areaModels.addAll(cityModels.get(position).getAreaModels());
                    mCityAdapter.notifyDataSetChanged();
                    mAreaAdapter.notifyDataSetChanged();
                    tabNameList.set(1, cityModels.get(position).getName());
                    if (tabNameList.size() == 2) {
                        tabNameList.add("请选择");
                    } else if (tabNameList.size() == 3) {
                        tabNameList.set(2, "请选择");
                    }
                    mAreaTab.setupWithViewPager(viewPager);
                    mPagerAdapter.notifyDataSetChanged();
                    mAreaTab.getTabAt(2).select();
                } else {
                    aOldIndex = -1;
                    mCityAdapter.notifyDataSetChanged();
                    mAreaAdapter.notifyDataSetChanged();
                    tabNameList.set(1, cityModels.get(position).getName());
                    mAreaTab.setupWithViewPager(viewPager);
                    mPagerAdapter.notifyDataSetChanged();
                    dismiss();
                    addressPickerViewCallback.callback(pSelectedIndex, cSelectedIndex);
                }
            }
        });

        areaModels = new ArrayList<>();
        mAreaAdapter = new AreaAdapter(areaModels, context);
        aRecyclerView.setAdapter(mAreaAdapter);
        aRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAreaAdapter.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
                tabNameList.set(2, areaModels.get(position).getName());
                mAreaTab.setupWithViewPager(viewPager);
                mPagerAdapter.notifyDataSetChanged();
                areaModels.get(position).setStatus(true);
                aSelectedIndex = position;
                if (aOldIndex != -1 && aOldIndex != position) {
                    areaModels.get(aOldIndex).setStatus(false);
                }
                aOldIndex = aSelectedIndex;
                mAreaAdapter.notifyDataSetChanged();
                dismiss();
                addressPickerViewCallback.callback(pSelectedIndex, cSelectedIndex, aSelectedIndex);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    pRecyclerView.scrollToPosition(pOldIndex == -1 ? 0 : pOldIndex);
                } else if (position == 1) {
                    cRecyclerView.scrollToPosition(cOldIndex == -1 ? 0 : cOldIndex);
                } else if (position == 2) {
                    aRecyclerView.scrollToPosition(aOldIndex == -1 ? 0 : aOldIndex);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void setSelect(int... value) {
        tabNameList = new ArrayList<>();
        if (value == null) {
            tabNameList.add("请选择");
            if (isCreate) {
                mAreaTab.setupWithViewPager(viewPager);
                mPagerAdapter.notifyDataSetChanged();
                mAreaTab.getTabAt(0).select();

                if (pSelectedIndex != -1) {
                    addressModels.get(pSelectedIndex).setStatus(false);
                }
                if (cSelectedIndex != -1) {
                    addressModels.get(pSelectedIndex).getCityModels().get(cSelectedIndex).setStatus(false);
                }
                cityModels.clear();
                areaModels.clear();
                notifyDataSetChanged();
            }
            return;
        }

        if (value.length == 3) {
            tabNameList.add(addressModels.get(value[0]).getName());
            tabNameList.add(addressModels.get(value[0]).getCityModels().get(value[1]).getName());
            tabNameList.add(addressModels.get(value[0]).getCityModels().get(value[1]).getAreaModels().get(value[2]).getName());
            mAreaTab.setupWithViewPager(viewPager);
            mPagerAdapter.notifyDataSetChanged();

            mAreaTab.getTabAt(value.length - 1).select();
            if (pSelectedIndex != -1) {
                addressModels.get(pSelectedIndex).setStatus(false);
            }
            if (cSelectedIndex != -1) {
                addressModels.get(pSelectedIndex).getCityModels().get(cSelectedIndex).setStatus(false);
            }
            addressModels.get(value[0]).setStatus(true);
            addressModels.get(value[0]).getCityModels().get(value[1]).setStatus(true);
            addressModels.get(value[0]).getCityModels().get(value[1]).getAreaModels().get(value[2]).setStatus(true);

            cityModels.clear();
            cityModels.addAll(addressModels.get(value[0]).getCityModels());
            areaModels.clear();
            areaModels.addAll(addressModels.get(value[0]).getCityModels().get(value[1]).getAreaModels());
            notifyDataSetChanged();
            pOldIndex = value[0];
            cOldIndex = value[1];
            aOldIndex = value[2];
            aRecyclerView.scrollToPosition(aOldIndex == -1 ? 0 : aOldIndex);
        }

        if (value.length == 2) {
            tabNameList.add(addressModels.get(value[0]).getName());
            tabNameList.add(addressModels.get(value[0]).getCityModels().get(value[1]).getName());
            mAreaTab.setupWithViewPager(viewPager);
            mPagerAdapter.notifyDataSetChanged();

            mAreaTab.getTabAt(value.length - 1).select();
            addressModels.get(pSelectedIndex).setStatus(false);
            addressModels.get(pSelectedIndex).getCityModels().get(cSelectedIndex).setStatus(false);
            addressModels.get(value[0]).setStatus(true);
            addressModels.get(value[0]).getCityModels().get(value[1]).setStatus(true);

            cityModels.clear();
            cityModels.addAll(addressModels.get(value[0]).getCityModels());
            mProvinceAdapter.notifyDataSetChanged();
            mCityAdapter.notifyDataSetChanged();
            pOldIndex = value[0];
            cOldIndex = value[1];
            aOldIndex = -1;
            cRecyclerView.scrollToPosition(cOldIndex == -1 ? 0 : cOldIndex);
        }
    }


    public interface AddressPickerViewCallback {
        void callback(int... value);
    }

    public void setAddressPickerViewCallbackk(AddressPickerViewCallback addressPickerViewCallback) {
        this.addressPickerViewCallback = addressPickerViewCallback;
    }

    private void notifyDataSetChanged() {
        mProvinceAdapter.notifyDataSetChanged();
        mCityAdapter.notifyDataSetChanged();
        mAreaAdapter.notifyDataSetChanged();
    }

    public void show() {

        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return tabNameList== null ? 0 : tabNameList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabNameList.get(position);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(viewList.get(position));
        }
    }
}
