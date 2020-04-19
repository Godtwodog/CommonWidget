package com.god2dog.wheelwidget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.god2dog.wheelwidget.view.BaseSelectedView;

import java.util.List;

public class AddressSelectedView<T> extends BaseSelectedView implements View.OnClickListener {
    private AddressOptions<T> addressOptions;

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";


    public AddressSelectedView(WheelOptions addressOptions) {
        super(addressOptions.context);
        this.mWheelOptions = addressOptions;
        initView(addressOptions.context);
    }

    private void initView(Context context) {
        setDialogOutsideCancelable();
        initViews();
        initAnim();
        initEvents();
        if (mWheelOptions.customListener == null) {
            LayoutInflater.from(context).inflate(mWheelOptions.layoutRes, contentContainer);

            //顶部标题
            TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
            RelativeLayout rv_top_bar = (RelativeLayout) findViewById(R.id.rvTopBar);

            //确定和取消按钮
            Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
            Button btnCancel = (Button) findViewById(R.id.btnCancel);

            btnSubmit.setTag(TAG_SUBMIT);
            btnCancel.setTag(TAG_CANCEL);
            btnSubmit.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

            //设置文字
            btnSubmit.setText(TextUtils.isEmpty(mWheelOptions.textContentSubmit) ? context.getResources().getString(R.string.wheelview_submit) : mWheelOptions.textContentSubmit);
            btnCancel.setText(TextUtils.isEmpty(mWheelOptions.textContentCancel) ? context.getResources().getString(R.string.wheelview_cancel) : mWheelOptions.textContentCancel);
            tvTitle.setText(TextUtils.isEmpty(mWheelOptions.textContentTitle) ? "" : mWheelOptions.textContentTitle);//默认为空

            //设置color
            btnSubmit.setTextColor(mWheelOptions.textColorConfirm);
            btnCancel.setTextColor(mWheelOptions.textColorCancel);
            tvTitle.setTextColor(mWheelOptions.textColorTitle);
            rv_top_bar.setBackgroundColor(mWheelOptions.bgColorTitle);

            //设置文字大小
            btnSubmit.setTextSize(mWheelOptions.textSizeConfirm);
            btnCancel.setTextSize(mWheelOptions.textSizeCancel);
            tvTitle.setTextSize(mWheelOptions.textSizeTitle);
        } else {
            mWheelOptions.customListener.customLayout(LayoutInflater.from(context).inflate(mWheelOptions.layoutRes, contentContainer));
        }

        // ----滚轮布局
        final LinearLayout optionsPicker = (LinearLayout) findViewById(R.id.selectedAddress);
        optionsPicker.setBackgroundColor(mWheelOptions.bgColorWheel);

        addressOptions = new AddressOptions<>(optionsPicker, mWheelOptions.isRestore);
        if (mWheelOptions.onOptionsSelectedListener != null) {
            addressOptions.setOptionsSelectChangeListener(mWheelOptions.onOptionsSelectedListener);
        }

        addressOptions.setTextContentSize(mWheelOptions.textContentSize);
        addressOptions.setItemsVisible(mWheelOptions.itemVisibleCount);
        addressOptions.setAlphaGradient(mWheelOptions.isAlphaGradient);
        addressOptions.setLabels(mWheelOptions.label1, mWheelOptions.label2, mWheelOptions.label3);
        addressOptions.setTextXOffset(mWheelOptions.x_offset_one, mWheelOptions.x_offset_two, mWheelOptions.x_offset_three);
        addressOptions.setCyclic(mWheelOptions.isCyclic, mWheelOptions.isCyclic, mWheelOptions.isCyclic);
        addressOptions.setTypeface(mWheelOptions.typeface);

        setOutsideCancelable(mWheelOptions.cancelable);

        addressOptions.setDividerColor(mWheelOptions.dividerColor);
        addressOptions.setLineSpacingMultiplier(mWheelOptions.lineSpacingMultiplier);
        addressOptions.setTextColorOut(mWheelOptions.textColorOut);
        addressOptions.setTextColorCenter(mWheelOptions.textColorCenter);
        addressOptions.isCenterLabel(mWheelOptions.isCenterLabel);
    }

    /**
     * 动态设置标题
     *
     * @param text 标题文本内容
     */
    public void setTitleText(String text) {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        if (tvTitle != null) {
            tvTitle.setText(text);
        }
    }

    /**
     * 设置默认选中项
     *
     * @param option1
     */
    public void setSelectOptions(int option1) {
        mWheelOptions.selectedIndex1 = option1;
        reSetCurrentItems();
    }


    public void setSelectOptions(int option1, int option2) {
        mWheelOptions.selectedIndex1 = option1;
        mWheelOptions.selectedIndex2 = option2;
        reSetCurrentItems();
    }

    public void setSelectOptions(int option1, int option2, int option3) {
        mWheelOptions.selectedIndex1 = option1;
        mWheelOptions.selectedIndex2 = option2;
        mWheelOptions.selectedIndex3 = option3;
        reSetCurrentItems();
    }

    private void reSetCurrentItems() {
        if (addressOptions != null) {
            addressOptions.setCurrentItems(mWheelOptions.selectedIndex1, mWheelOptions.selectedIndex2, mWheelOptions.selectedIndex3);
        }
    }

    public void setData(List<T> optionsItems) {
        this.setData(optionsItems, null, null);
    }

    public void setData(List<T> options1Items, List<List<T>> options2Items) {
        this.setData(options1Items, options2Items, null);
    }

    public void setData(List<T> options1Items,
                          List<List<T>> options2Items,
                          List<List<List<T>>> options3Items) {

        addressOptions.setData(options1Items, options2Items, options3Items);
        reSetCurrentItems();
    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
        } else if (tag.equals(TAG_CANCEL)) {
            if (mWheelOptions.cancelListener != null) {
                mWheelOptions.cancelListener.onClick(v);
            }
        }
        dismiss();
    }

    //抽离接口回调的方法
    public void returnData() {
        if (mWheelOptions.onOptionsSelectedChangeListener!= null) {
            int[] optionsCurrentItems = addressOptions.getCurrentItems();
            mWheelOptions.onOptionsSelectedChangeListener.onOptionsSelectedChange(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2], clickView);
        }
    }


    @Override
    public boolean isDialog() {
        return mWheelOptions.isDialog;
    }
}
