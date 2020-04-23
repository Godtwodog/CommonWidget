package com.god2dog.commonwidget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class WelcomeGuideActivity extends AppCompatActivity {

    private Button jump;
    private Button jump2Home;
    private List<Object> views;
    private String TAG = "dengxs";
    //指示器
    private LinearLayout llIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_guide);
        jump = findViewById(R.id.btnJump);
        jump2Home = findViewById(R.id.btnJump2Home);
        llIndicator = findViewById(R.id.llIndicator);

        views = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            views.add("我是第" + (i + 1) + "个页面");
            ImageView imageView = new ImageView(WelcomeGuideActivity.this);
            llIndicator.addView(imageView);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
            params.setMargins((int) getResources().getDimension(R.dimen.x10), 0, (int) getResources().getDimension(R.dimen.x10), 0);
            imageView.setLayoutParams(params);
        }

        ViewPager2 guidePager = findViewById(R.id.viewpagerGuide);

        guidePager.setOffscreenPageLimit(1);
        RecyclerView recyclerView = (RecyclerView) guidePager.getChildAt(0);
        recyclerView.setPadding(getResources().getDimensionPixelOffset(R.dimen.page_margin) * 2, 0, getResources().getDimensionPixelOffset(R.dimen.page_margin) * 2, 0);
        recyclerView.setClipToPadding(false);
        //横向滚动
        guidePager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        //竖直滚动
//        guidePager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        //设置动画
        //动画集合
        CompositePageTransformer transformerList = new CompositePageTransformer();
        //间隔动画
        MarginPageTransformer pageTransformer = new MarginPageTransformer((int) getResources().getDimension(R.dimen.page_margin));
        //缩放动画
        ScalePageTransformer scaleTransformer = new ScalePageTransformer();

        transformerList.addTransformer(scaleTransformer);
        transformerList.addTransformer(pageTransformer);
        //设置动画
        guidePager.setPageTransformer(transformerList);
        GuidePagerAdapter adapter = new GuidePagerAdapter(WelcomeGuideActivity.this, views);

        guidePager.setAdapter(adapter);

        guidePager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ToastUtils.showTextToast((String) views.get(position), WelcomeGuideActivity.this);
                if (position == views.size() - 1 || position == views.size() - 2) {
                    if (position == views.size() - 2) {
                        jump2Home.setAlpha(0);
                        jump.setAlpha(0);
                    }
                    jump.setVisibility(View.VISIBLE);
                    jump2Home.setVisibility(View.VISIBLE);
                } else {
                    jump.setVisibility(View.GONE);
                    jump2Home.setVisibility(View.GONE);
                }
                for (int i = 0; i < llIndicator.getChildCount(); i++) {
                    ImageView child = (ImageView) llIndicator.getChildAt(i);
                    if (i == position) {
                        child.setImageResource(R.mipmap.ic_dot_selector);
                    } else {
                        child.setImageResource(R.mipmap.ic_dot_unselector);
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Log.i(TAG, "positionOffset:" + positionOffset);
                Log.i(TAG, "setAlpha:" + (positionOffset * 100 / 50 - 1));
                if (position == views.size() - 2) {
                    jump2Home.setAlpha(positionOffset <= 0.5F ? 0 : positionOffset * 100 / 50 - 1);
                    jump.setAlpha(positionOffset <= 0.5F ? 0 : positionOffset * 100 / 50 - 1);
                }
            }
        });


        jump2Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToHome();
            }
        });
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToHome();
            }
        });
    }

    private void startToHome() {
        startActivity(new Intent(WelcomeGuideActivity.this, MainActivity.class));
    }
}
