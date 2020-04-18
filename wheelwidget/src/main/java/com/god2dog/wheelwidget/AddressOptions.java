package com.god2dog.wheelwidget;

import android.view.View;

import com.god2dog.wheelwidget.adater.ArrayOptionAdapter;
import com.god2dog.wheelwidget.listener.OnItemSelectedListener;
import com.god2dog.wheelwidget.listener.OnOptionsSelectedListener;
import com.god2dog.wheelwidget.view.WheelViewWidget;

import java.util.List;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/18
 * 描述：CommonWidget
 */
public class AddressOptions<T> {
    private View view;

    //省 一级
    private WheelViewWidget optionsProvinceView;
    //市 二级
    private WheelViewWidget optionsCityView;
    //区 三级
    private WheelViewWidget optionsAreaView;

    private List<T> mOptionsProvinceItems;
    private List<List<T>> mOptionsCityItems;
    private List<List<List<T>>> mOptionsAreaItems;

    private OnOptionsSelectedListener onOptionsSelectedListener;

    private OnItemSelectedListener provinceItemListener;
    private OnItemSelectedListener cityItemListener;

    private boolean isRestore;
    private boolean isLink;

    public AddressOptions(View view, boolean isRestoreItem) {
        this.view = view;
        this.isRestore = isRestoreItem;
        optionsProvinceView = view.findViewById(R.id.province);
        optionsCityView = view.findViewById(R.id.city);
        optionsAreaView = view.findViewById(R.id.area);
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setData(final List<T> optionProvince,
                        final List<List<T>> optionCity,
                        final List<List<List<T>>> optionArea) {
        this.mOptionsProvinceItems = optionProvince;
        this.mOptionsCityItems = optionCity;
        this.mOptionsAreaItems = optionArea;

        optionsProvinceView.setAdapter(new ArrayOptionAdapter(optionProvince));
        optionsProvinceView.setCurrentItem(0);
        if (optionCity != null) {
            optionsCityView.setAdapter(new ArrayOptionAdapter(optionCity));
        }
        optionsCityView.setCurrentItem(optionsCityView.getCurrentItem());
        if (optionArea != null) {
            optionsAreaView.setAdapter(new ArrayOptionAdapter(optionArea));
        }
        optionsAreaView.setCurrentItem(optionsCityView.getCurrentItem());

        optionsProvinceView.setIsOptions(true);
        optionsCityView.setIsOptions(true);
        optionsAreaView.setIsOptions(true);

        if (optionCity == null) {
            optionsCityView.setVisibility(View.GONE);
        } else {
            optionsCityView.setVisibility(View.VISIBLE);
        }

        if (optionArea == null) {
            optionsAreaView.setVisibility(View.GONE);
        } else {
            optionsCityView.setVisibility(View.VISIBLE);
        }

        provinceItemListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                int selectIndex = 0;
                if (mOptionsCityItems == null) {
                    //只有一级
                    if (onOptionsSelectedListener != null) {
                        onOptionsSelectedListener.onSelectedItem(optionsProvinceView.getCurrentItem(), 0, 0);
                    }
                } else {
                    if (!isRestore) {
                        selectIndex = optionsCityView.getCurrentItem();
                        selectIndex = selectIndex >= mOptionsCityItems.get(position).size() - 1 ? mOptionsCityItems.get(position).size() - 1 : selectIndex;
                    }
                    optionsCityView.setAdapter(new ArrayOptionAdapter(mOptionsCityItems.get(position)));
                    optionsCityView.setCurrentItem(selectIndex);

                    if (mOptionsAreaItems == null) {
                        if (onOptionsSelectedListener != null) {
                            onOptionsSelectedListener.onSelectedItem(position, selectIndex, 0);
                        }
                    } else {
                        cityItemListener.onItemSelected(selectIndex);
                    }
                }
            }
        };
        cityItemListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                if (mOptionsAreaItems != null) {
                    //三级联动
                    int p1SelectIndex = optionsProvinceView.getCurrentItem();
                    p1SelectIndex = p1SelectIndex >= mOptionsAreaItems.size() - 1 ? mOptionsAreaItems.size() - 1 : p1SelectIndex;
                    position = position >= mOptionsCityItems.get(p1SelectIndex).size() - 1 ? mOptionsCityItems.get(p1SelectIndex).size() - 1 : position;
                    int p3SelectIndex = 0;
                    if (!isRestore) {
                        p3SelectIndex = optionsAreaView.getCurrentItem() >= mOptionsAreaItems.get(p1SelectIndex).get(position).size() - 1 ?
                                mOptionsAreaItems.get(p1SelectIndex).get(position).size() - 1 : optionsAreaView.getCurrentItem();
                    }
                    optionsAreaView.setAdapter(new ArrayOptionAdapter(mOptionsAreaItems.get(optionsProvinceView.getCurrentItem()).get(position)));
                    optionsAreaView.setCurrentItem(p3SelectIndex);

                    if (onOptionsSelectedListener != null){
                        onOptionsSelectedListener.onSelectedItem(optionsProvinceView.getCurrentItem(),position,p3SelectIndex);
                    }
                } else {
                    //二级联动
                    if (onOptionsSelectedListener != null) {
                        onOptionsSelectedListener.onSelectedItem(optionsProvinceView.getCurrentItem(), position, 0);
                    }
                }
            }
        };

        if (optionProvince != null && isLink ){
            optionsProvinceView.setOnItemSelectedListener(provinceItemListener);
        }

        if (optionCity != null && isLink){
            optionsCityView.setOnItemSelectedListener(cityItemListener);
        }

        if (optionArea != null && isLink && onOptionsSelectedListener != null){
            optionsAreaView.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int position) {
                    onOptionsSelectedListener.onSelectedItem(optionsProvinceView.getCurrentItem(),optionsCityView.getCurrentItem(),position);
                }
            });
        }
    }

    public void setOnOptionsSelectedListener(OnOptionsSelectedListener onOptionsSelectedListener) {
        this.onOptionsSelectedListener = onOptionsSelectedListener;
    }

    public void setIsLoop(boolean isLoop){
        optionsProvinceView.setIsLoop(isLoop);
        optionsCityView.setIsLoop(isLoop);
        optionsAreaView.setIsLoop(isLoop);
    }


}
