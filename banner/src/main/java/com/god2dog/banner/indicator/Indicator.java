package com.god2dog.banner.indicator;

import android.view.View;

import androidx.annotation.NonNull;

import com.god2dog.banner.config.IndicatorConfig;
import com.god2dog.banner.listener.OnPageChangeListener;


public interface Indicator extends OnPageChangeListener {
    @NonNull
    View getIndicatorView();

    IndicatorConfig getIndicatorConfig();

    void onPageChanged(int count, int currentPosition);

}
