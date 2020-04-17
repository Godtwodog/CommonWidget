package com.god2dog.commonwidget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class WelcomeGuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_guide);

        List<Object> views = new ArrayList<>();

        ViewPager2 guidePager = findViewById(R.id.viewpagerGuide);
        GuidePagerAdapter adapter = new GuidePagerAdapter(WelcomeGuideActivity.this,views);

        guidePager.setAdapter(adapter);
    }
}
