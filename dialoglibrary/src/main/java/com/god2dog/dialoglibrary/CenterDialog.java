package com.god2dog.dialoglibrary;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/13
 * 描述：CommonWidget
 */
public class CenterDialog extends Dialog {
    private Display display;
    public CenterDialog(@NonNull Context context) {
        super(context,R.style.Center_Dialog);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null){
            display = windowManager.getDefaultDisplay();
        }

        initDialog();
    }

    private void initDialog() {
        setContentView(R.layout.layout_center_dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }
}
