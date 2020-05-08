package com.god2dog.dialoglibrary;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
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
    //滑动布局
    private ScrollView mSVContent;

    private OnDialogClickedListener onDialogListener;

    private String title;

    private String subHead;

    private String content;

    private String cancelText;

    private String confirmText;


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
        Window dialogWindow = getWindow();
        if (dialogWindow != null){
            dialogWindow.setGravity(Gravity.CENTER);
            dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
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
            mSVContent = findViewById(R.id.svContent);

            mConfirmAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDialogListener !=null) {
                        onDialogListener.onConfirmAction(v);
                    }
                    dismiss();
                }
            });

            mCancelAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDialogListener !=null) {
                        onDialogListener.onCancelAction(v);
                    }
                    dismiss();
                }
            });
        }


    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubHead(String subHead) {
        this.subHead = subHead;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
    }

    public void setConfirmText(String confirmText) {
        this.confirmText = confirmText;
    }

    @Override
    public void show() {
        if (title != null){
            mTitle.setText(title);
            mTitle.setVisibility(View.VISIBLE);
        }else {
            mTitle.setVisibility(View.GONE);
        }

        if (subHead != null){
            mSubhead.setText(subHead);
            mSubhead.setVisibility(View.VISIBLE);
        }else {
            mSubhead.setVisibility(View.GONE);
        }

        if (content != null){
            mContent.setText(content);
            mContent.setVisibility(View.VISIBLE);
        }else {
            mContent.setVisibility(View.GONE);
        }

        int measuredHeight = mContent.getMeasuredHeight();
        if (measuredHeight >= 300){
            ViewGroup.LayoutParams params = mSVContent.getLayoutParams();
            params.height = 300;
            mSVContent.setLayoutParams(params);
        }

        if (cancelText != null){
            mCancelAction.setText(cancelText);
        }

        if (confirmText != null){
            mConfirmAction.setText(confirmText);
        }
        super.show();
    }

    public interface OnDialogClickedListener{
        void onCancelAction(View view);
        void onConfirmAction(View view);
    }
}
