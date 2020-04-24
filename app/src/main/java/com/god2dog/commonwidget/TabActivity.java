package com.god2dog.commonwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class TabActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private TabLayout mTab1;
    private String[] tabTitles = new String[]{"选项一","选项二","选项三","选项四","选项五","选项六","选项七"};
    private String[] tabShortTitles = new String[]{"选项一","选项二","选项三"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        mTabLayout = findViewById(R.id.tabLayout);
        mTab1 = findViewById(R.id.tab1);
        for(int i=0;i<tabShortTitles.length;i++){
            TabLayout.Tab tab = mTab1.newTab().setText(tabShortTitles[i]);
            mTab1.addTab(tab);
        }

        for(int i=0;i<tabTitles.length;i++){
            mTabLayout.addTab(mTabLayout.newTab());
            mTabLayout.getTabAt(i).setText(tabTitles[i]);
        }
    }
}
