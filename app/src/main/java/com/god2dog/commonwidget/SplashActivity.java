package com.god2dog.commonwidget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 启动页面
 */
public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int DEFAULT_SECOND = 6;

    private int mTimeCutDownNum = DEFAULT_SECOND;

    private boolean isFirstStartApp = false;
    private SharedPreferences.Editor editor;
    //登录token
    private String token = "";
    private RelativeLayout mAdvertisingLayout;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int time = countNum();
            mSeconds.setText("跳过\n"+(mTimeCutDownNum-time)+"s");
            if (continueCount) {
                handler.sendMessageDelayed(handler.obtainMessage(0), 1000);
            }
        }
    };
    private int timeCount = 0;
    private boolean continueCount = true;
    private boolean isPlayAd = false;

    private ImageView mAdvertisingImage;
    private TextView mSeconds;
    private AdvertisingModel adModel;
    private TextView mAdTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        SharedPreferences preferences = getSharedPreferences(AppConfig.PREFERENCES_KEY,Activity.MODE_PRIVATE);
        editor = preferences.edit();
        isFirstStartApp = preferences.getBoolean(AppConfig.IS_FIRST_START_APP, true);
        token = preferences.getString(AppConfig.CACHE_TOKEN,"");
        mAdvertisingLayout =findViewById(R.id.rlAdvertising);
        mAdvertisingImage = findViewById(R.id.iv_advertising);
        mSeconds = findViewById(R.id.tv_seconds);
        mAdTitle = findViewById(R.id.tvAdTitle);

        //此处应为网络请求数据
        adModel = new AdvertisingModel();
        adModel.setTitle("震惊，程序员半夜起床，竟是为了这个...");
        adModel.setPlay(true);
        adModel.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587651509463&di=4fff1fae6967c77b7e23ca6188571d44&imgtype=0&src=http%3A%2F%2Fwww.yutudou.com%2Fuploads%2Fallimg%2F170626%2F1-1F626141518.jpg");
        adModel.setImageRes(R.mipmap.ic_ad);

        isPlayAd = adModel.isPlay();

        mAdvertisingImage.setOnClickListener(this);
        mSeconds.setOnClickListener(this);


        mAdvertisingLayout.setVisibility(View.INVISIBLE);

//        toNextActivity();
        handler.sendMessageDelayed(handler.obtainMessage(0),1000);
    }

    private void toNextActivity() {
        if (isFirstStartApp) {
            //第一次启动，跳转到欢迎引导页
            startActivity(new Intent(SplashActivity.this, WelcomeGuideActivity.class));
            editor.putBoolean(AppConfig.IS_FIRST_START_APP, false).apply();
            finish();
        } else {
            if (token == null || token.isEmpty()) {
                //跳转到登录页面
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            } else
                //跳转到主页
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }

    }

    private int countNum() {//数秒
        timeCount++;
        if (timeCount == 3) {//数秒，超过3秒后如果没有网络，则进入下一个页面
            if (!NetUtils.isConnected(SplashActivity.this)) {
                continueCount = false;
                toNextActivity();
                finish();
            }
            if (!isPlayAd) {//如果服务端不允许播放广告，则直接进入下一个页面
                continueCount = false;
                toNextActivity();
                finish();
            } else {//如果播放，则获取本地的图片
                mAdvertisingLayout.setVisibility(View.VISIBLE);
                mAdvertisingImage.setImageResource(adModel.getImageRes());
                mAdTitle.setText(adModel.getTitle());

            }
        }
        if (timeCount == mTimeCutDownNum) {
            continueCount = false;
            toNextActivity();
            finish();
        }
        return timeCount;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_advertising:
                continueCount = false;
                Intent intent = new Intent();
                intent.putExtra("title",adModel.getTitle());
                intent.putExtra("url",adModel.getUrl());
                intent.putExtra("from","advertising");
                intent.putExtra("action","open");
                intent.setClass(SplashActivity.this,WebViewActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_seconds:
                continueCount = false;
                toNextActivity();
                finish();
                break;
        }
    }
}
