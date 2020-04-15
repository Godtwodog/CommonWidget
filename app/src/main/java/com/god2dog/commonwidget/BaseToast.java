package com.god2dog.commonwidget;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/15
 * 描述：CommonWidget
 */
public class BaseToast {

    private Toast mToast;
    private ImageView imageView;
    private TextView textView;
    private View view;

    private String message;
    private int resId = -1;
    private boolean isImageVisible = false;
    private int gravity = Gravity.CENTER;
    private int duration = Toast.LENGTH_SHORT;

    public BaseToast(Context context) {
        init(context);
    }

    private void init(Context context) {
        mToast = new Toast(context);
        view = LayoutInflater.from(context).inflate(R.layout.layout_base_toast,null);
        imageView = view.findViewById(R.id.iv_image);
        textView = view.findViewById(R.id.tv_message);
    }

    public void show(){
        textView.setText(message);

        if (resId >0){
            imageView.setImageResource(resId);
            isImageVisible= true;
        }
        if (isImageVisible){
            imageView.setVisibility(View.VISIBLE);
        }else {
            imageView.setVisibility(View.GONE);
        }
        mToast.setDuration(duration);
        mToast.setGravity(gravity,0,0);
        mToast.setView(view);
        mToast.show();
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setMessage(@NonNull String message) {
        this.message = message;
    }

    public void setImageResource(@DrawableRes int resId) {
        this.resId = resId;
    }

    public void setImageVisible(boolean imageVisible) {
        isImageVisible = imageVisible;
    }
}
