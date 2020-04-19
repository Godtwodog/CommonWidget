package com.god2dog.wheelwidget;

import android.graphics.Typeface;
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


    public void setTextContentSize(int textSize) {
        optionsProvinceView.setTextSize(textSize);
        optionsCityView.setTextSize(textSize);
        optionsAreaView.setTextSize(textSize);
    }

    private void setLineSpacingMultiplier() {

    }

    /**
     * 设置选项的单位
     *
     * @param label1 单位
     * @param label2 单位
     * @param label3 单位
     */
    public void setLabels(String label1, String label2, String label3) {
        if (label1 != null) {
            optionsProvinceView.setLabel(label1);
        }
        if (label2 != null) {
            optionsCityView.setLabel(label2);
        }
        if (label3 != null) {
            optionsAreaView.setLabel(label3);
        }
    }

    /**
     * 设置x轴偏移量
     */
    public void setTextXOffset(int x_offset_one, int x_offset_two, int x_offset_three) {
        optionsProvinceView.setTextXOffset(x_offset_one);
        optionsCityView.setTextXOffset(x_offset_two);
        optionsAreaView.setTextXOffset(x_offset_three);
    }
    /**
     * 设置字体样式
     *
     * @param font 系统提供的几种样式
     */
    public void setTypeface(Typeface font) {
        optionsProvinceView.setTypeface(font);
        optionsCityView.setTypeface(font);
        optionsAreaView.setTypeface(font);
    }

    /**
     * 分别设置第一二三级是否循环滚动
     *
     * @param cyclic1,cyclic2,cyclic3 是否循环
     */
    public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        optionsProvinceView.setIsLoop(cyclic1);
        optionsCityView.setIsLoop(cyclic2);
        optionsAreaView.setIsLoop(cyclic3);
    }

    /**
     * 返回当前选中的结果对应的位置数组 因为支持三级联动效果，分三个级别索引，0，1，2。
     * 在快速滑动未停止时，点击确定按钮，会进行判断，如果匹配数据越界，则设为0，防止index出错导致崩溃。
     *
     * @return 索引数组
     */
    public int[] getCurrentItems() {
        int[] currentItems = new int[3];
        currentItems[0] = optionsProvinceView.getCurrentItem();

        if (mOptionsCityItems != null && mOptionsCityItems.size() > 0) {//非空判断
            currentItems[1] = optionsCityView.getCurrentItem() > (mOptionsCityItems.get(currentItems[0]).size() - 1) ? 0 : optionsCityView.getCurrentItem();
        } else {
            currentItems[1] = optionsCityView.getCurrentItem();
        }

        if (mOptionsAreaItems != null && mOptionsAreaItems.size() > 0) {//非空判断
            currentItems[2] = optionsAreaView.getCurrentItem() > (mOptionsAreaItems.get(currentItems[0]).get(currentItems[1]).size() - 1) ? 0 : optionsAreaView.getCurrentItem();
        } else {
            currentItems[2] = optionsAreaView.getCurrentItem();
        }

        return currentItems;
    }

    public void setCurrentItems(int option1, int option2, int option3) {
        if (isLink) {
            itemSelected(option1, option2, option3);
        } else {
            optionsProvinceView.setCurrentItem(option1);
            optionsCityView.setCurrentItem(option2);
            optionsAreaView.setCurrentItem(option3);
        }
    }

    private void itemSelected(int opt1Select, int opt2Select, int opt3Select) {
        if (mOptionsProvinceItems != null) {
            optionsProvinceView.setCurrentItem(opt1Select);
        }
        if (mOptionsCityItems != null) {
            optionsCityView.setAdapter(new ArrayOptionAdapter(mOptionsCityItems.get(opt1Select)));
            optionsCityView.setCurrentItem(opt2Select);
        }
        if (mOptionsAreaItems != null) {
            optionsAreaView.setAdapter(new ArrayOptionAdapter(mOptionsAreaItems.get(opt1Select).get(opt2Select)));
            optionsAreaView.setCurrentItem(opt3Select);
        }
    }

    /**
     * 设置间距倍数,但是只能在1.2-4.0f之间
     *
     * @param lineSpacingMultiplier
     */
    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        optionsProvinceView.setItemSpaceMultiplier(lineSpacingMultiplier);
        optionsCityView.setItemSpaceMultiplier(lineSpacingMultiplier);
        optionsAreaView.setItemSpaceMultiplier(lineSpacingMultiplier);
    }

    /**
     * 设置分割线的颜色
     *
     * @param dividerColor
     */
    public void setDividerColor(int dividerColor) {
        optionsProvinceView.setDividerColor(dividerColor);
        optionsCityView.setDividerColor(dividerColor);
        optionsAreaView.setDividerColor(dividerColor);
    }


    /**
     * 设置分割线之间的文字的颜色
     *
     * @param textColorCenter
     */
    public void setTextColorCenter(int textColorCenter) {
        optionsProvinceView.setTextColorCenter(textColorCenter);
        optionsCityView.setTextColorCenter(textColorCenter);
        optionsAreaView.setTextColorCenter(textColorCenter);
    }

    /**
     * 设置分割线以外文字的颜色
     *
     * @param textColorOut
     */
    public void setTextColorOut(int textColorOut) {
        optionsProvinceView.setTextColorOut(textColorOut);
        optionsCityView.setTextColorOut(textColorOut);
        optionsAreaView.setTextColorOut(textColorOut);
    }

    /**
     * Label 是否只显示中间选中项的
     *
     * @param isCenterLabel
     */
    public void isCenterLabel(boolean isCenterLabel) {
        optionsProvinceView.isCenterLabel(isCenterLabel);
        optionsCityView.isCenterLabel(isCenterLabel);
        optionsAreaView.isCenterLabel(isCenterLabel);
    }

    public void setOptionsSelectChangeListener(OnOptionsSelectedListener optionsSelectChangeListener) {
        this.onOptionsSelectedListener = optionsSelectChangeListener;
    }

    public void setLinkage(boolean linkage) {
        this.isLink = linkage;
    }

    /**
     * 设置最大可见数目
     *
     * @param itemsVisible 建议设置为 3 ~ 9之间。
     */
    public void setItemsVisible(int itemsVisible) {
        optionsProvinceView.setItemVisibleCount(itemsVisible);
        optionsCityView.setItemVisibleCount(itemsVisible);
        optionsAreaView.setItemVisibleCount(itemsVisible);
    }

    public void setAlphaGradient(boolean isAlphaGradient) {
        optionsProvinceView.setAlphaGradient(isAlphaGradient);
        optionsCityView.setAlphaGradient(isAlphaGradient);
        optionsAreaView.setAlphaGradient(isAlphaGradient);
    }

}
