package com.god2dog.dialoglibrary;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/13
 * 描述：CommonWidget
 */
public class CenterDialog extends Dialog {
    private Display display;
    private int dialogStyle = 1;

    private TextView mCancelAction;
    private TextView mConfirmAction;
    //标题
    private TextView mTitle;
    //副标题
    private TextView mSubhead;
    //文本内容
    private TextView mContent;

    private OnDialogClickedListener onDialogListener;

    public void AddOnDialogListener(OnDialogClickedListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }

    public CenterDialog(@NonNull Context context , int style) {
        super(context,R.style.Center_Dialog);
        this.dialogStyle = style;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null){
            display = windowManager.getDefaultDisplay();
        }

        initDialog();
    }

    private void initDialog() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        if (dialogStyle == 1) {
            setContentView(R.layout.layout_center_dialog);
            mCancelAction = findViewById(R.id.tvCancelAction);
            mConfirmAction = findViewById(R.id.tvConfirmAction);
            mTitle = findViewById(R.id.tvMainTitle);
            mSubhead = findViewById(R.id.tvSubHead);
            mContent = findViewById(R.id.tvContent);
        }


    }

    @Override
    public void show() {

    }

    public interface OnDialogClickedListener{
        void onCancelAction(View view);
        void onConfirmAction(View view);
    }
}
