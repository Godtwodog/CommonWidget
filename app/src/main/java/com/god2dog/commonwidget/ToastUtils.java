package com.god2dog.commonwidget;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/15
 * 描述：CommonWidget
 */
public class ToastUtils {
    public static void showToast(String message, Context context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(@StringRes int stringId, Context context){
        showToast(context.getString(stringId),context);
    }

    public static void showTextToast(String message,Context context){
        BaseToast toast = new BaseToast(context);
        toast.setMessage(message);
        toast.setImageVisible(false);
        toast.show();
    }

    public static void showErrorToast(String message,Context context){
        BaseToast toast = new BaseToast(context);
        toast.setMessage(message);
        toast.setImageVisible(true);
        toast.show();
    }
    public static void showSuccessToast(String message,Context context){
        BaseToast toast = new BaseToast(context);
        toast.setMessage(message);
        toast.setImageResource(R.mipmap.ic_right);
        toast.show();
    }
}
