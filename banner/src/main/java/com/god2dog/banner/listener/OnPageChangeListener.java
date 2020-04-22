package com.god2dog.banner.listener;

import androidx.annotation.Px;
import androidx.viewpager2.widget.ViewPager2;


public interface OnPageChangeListener {

    void onPageScrolled(int position, float positionOffset, @Px int positionOffsetPixels);


    void onPageSelected(int position);


    void onPageScrollStateChanged(@ViewPager2.ScrollState int state);
}
