package com.god2dog.commonwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * 启动页面
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences preferences = getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        boolean isFirstStartApp = preferences.getBoolean(AppConfig.IS_FIRST_START_APP, true);

        if (isFirstStartApp){
            //第一次启动，跳转到欢迎引导页
            startActivity(new Intent(SplashActivity.this,WelcomeGuideActivity.class));
            editor.putBoolean(AppConfig.IS_FIRST_START_APP,false).apply();
            finish();
        }else {
            //跳转到主页
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
        }
    }
}
