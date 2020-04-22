package com.god2dog.commonwidget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.god2dog.banner.Banner;
import com.god2dog.banner.config.BannerConfig;
import com.god2dog.banner.config.IndicatorConfig;
import com.god2dog.banner.indicator.CircleIndicator;
import com.god2dog.banner.indicator.RoundLinesIndicator;
import com.god2dog.banner.listener.OnBannerListener;
import com.god2dog.banner.listener.OnPageChangeListener;
import com.god2dog.banner.transformer.ZoomOutPageTransformer;
import com.god2dog.banner.util.BannerUtils;
import com.god2dog.commonwidget.adapter.BannerImageAdapter;
import com.god2dog.commonwidget.adapter.ImageNetAdapter;
import com.god2dog.commonwidget.adapter.ImageTitleAdapter;
import com.god2dog.commonwidget.adapter.ImageTitleNumAdapter;
import com.god2dog.commonwidget.adapter.TopLineAdapter;
import com.google.android.material.snackbar.Snackbar;

public class BannerViewActivity extends AppCompatActivity implements OnPageChangeListener, View.OnClickListener {

    private Banner banner;

    private Banner banner2;

    private SwipeRefreshLayout refresh;

    private RoundLinesIndicator indicator;
    private String TAG ="dengxs";

    private Button styleImage;
    private Button styleImageTitle;
    private Button styleImageTitleNum;
    private Button styleNetImage;
    private Button changeIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_view);

        banner = findViewById(R.id.banner);
        banner2 = findViewById(R.id.banner2);
        refresh = findViewById(R.id.swipeRefresh);
        indicator = findViewById(R.id.indicator);
        styleImage = findViewById(R.id.style_image);
        styleImageTitle = findViewById(R.id.style_image_title);
        styleImageTitleNum = findViewById(R.id.style_image_title_num);
        styleNetImage = findViewById(R.id.style_net_image);
        changeIndicator = findViewById(R.id.change_indicator);

        styleImage.setOnClickListener(this);
        styleImageTitle.setOnClickListener(this);
        styleImageTitleNum.setOnClickListener(this);
        styleNetImage.setOnClickListener(this);
        changeIndicator.setOnClickListener(this);

        //设置适配器
        banner.setAdapter(new BannerImageAdapter(DataBean.getTestData()));
        //设置指示器
        banner.setIndicator(new CircleIndicator(this));
        //设置点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
            }
        });
        //添加切换监听
        banner.addOnPageChangeListener(this);
        //圆角
        banner.setBannerRound(BannerUtils.dp2px(5));

        //添加画廊效果，可以参考我给的参数自己调试(不要和其他PageTransformer同时使用)
//        banner.setBannerGalleryEffect(25, 40, 0.14f);

        //设置组合PageTransformer
//        banner.addPageTransformer(new ZoomOutPageTransformer());
//        banner.addPageTransformer(new DepthPageTransformer());


        //实现1号店和淘宝头条类似的效果
        banner2.setAdapter(new TopLineAdapter(DataBean.getTestData2()))
                .setOrientation(Banner.VERTICAL)
                .setPageTransformer(new ZoomOutPageTransformer())
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object data, int position) {
                        Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
                    }
                });


        //和下拉刷新配套使用
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                         @Override
                                         public void onRefresh() {
                                             new Handler().postDelayed(new Runnable() {
                                                 @Override
                                                 public void run() {
                                                     refresh.setRefreshing(false);
                                                     //给banner重新设置数据
                                                     banner.setDatas(DataBean.getTestData2());
                                                 }
                                             }, 3000);
                                         }
                                     }
                //模拟网络请求需要3秒，请求完成，设置setRefreshing 为false

        );

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.e(TAG, "onPageSelected:" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    /**
     * 如果你需要考虑更好的体验，可以这么操作
     */
    @Override
    protected void onStart() {
        super.onStart();
        banner.start();
        banner2.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stop();
        banner2.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        banner.destroy();
        banner2.destroy();
    }

    @Override
    public void onClick(View view) {
        indicator.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.style_image:
                refresh.setEnabled(true);
                banner.setAdapter(new BannerImageAdapter(DataBean.getTestData()));
                banner.setIndicator(new CircleIndicator(this));
                banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
                break;
            case R.id.style_image_title:
                refresh.setEnabled(true);
                banner.setAdapter(new ImageTitleAdapter(DataBean.getTestData()));
                banner.setIndicator(new CircleIndicator(this));
                banner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
                banner.setIndicatorMargins(new IndicatorConfig.Margins(0, 0,
                        BannerConfig.INDICATOR_MARGIN, (int) BannerUtils.dp2px(12)));
                break;
            case R.id.style_image_title_num:
                refresh.setEnabled(true);
                banner.setAdapter(new ImageTitleNumAdapter(DataBean.getTestData()));
                banner.removeIndicator();
                break;
            case R.id.style_net_image:
                refresh.setEnabled(false);
                banner.setAdapter(new ImageNetAdapter(DataBean.getTestData3()));
                banner.setIndicator(new RoundLinesIndicator(this));
                banner.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
                break;
            case R.id.change_indicator:
                indicator.setVisibility(View.VISIBLE);
                //在布局文件中使用指示器，这样更灵活
                banner.setIndicator(indicator, false);
                banner.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
                break;
        }
    }
}
