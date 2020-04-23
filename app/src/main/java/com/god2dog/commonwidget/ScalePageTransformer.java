package com.god2dog.commonwidget;

import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager2.widget.ViewPager2;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/23
 * 描述：CommonWidget
 */
public class ScalePageTransformer implements ViewPager2.PageTransformer {
    public static final float DEFAULT_MIN_SCALE  = 0.85F;
    public static final float DEFAULT_CENTER = 0.5F;

    private float mMinScale = DEFAULT_MIN_SCALE;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void transformPage(@NonNull View page, float position) {

        page.setElevation(-Math.abs(position));

        int pageHeight = page.getHeight();
        int pageWidth = page.getWidth();

        page.setPivotX(pageWidth / 2);
        page.setPivotY(pageHeight / 2);

        if (position < -1){
            page.setScaleX(mMinScale);
            page.setScaleY(mMinScale);
            page.setPivotX(pageWidth);
        }else if (position <= 1){
            if (position < 0){
                float scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setPivotX(pageWidth * ((1 - position) * DEFAULT_CENTER));
            }else {
                float scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setPivotX(pageWidth * ((1 - position) * DEFAULT_CENTER));
            }
        }else {
            page.setScaleX(mMinScale);
            page.setScaleY(mMinScale);
            page.setPivotX(0F);
        }
    }
}
