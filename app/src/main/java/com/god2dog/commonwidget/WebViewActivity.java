package com.god2dog.commonwidget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    private ProgressWebView mWebView;
    private Toolbar toolbar;
    private String title;
    private String url;
    private String from;
    private String action;
    private long djtime[] = new long[2];
    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mWebView = findViewById(R.id.wv);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        preferences = getSharedPreferences(AppConfig.PREFERENCES_KEY,Activity.MODE_PRIVATE);

        if (getIntent() != null && !"".equals(getIntent().getAction())) {
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("url");
            action = getIntent().getStringExtra("action");
            from = getIntent().getStringExtra("from");
        }
        toolbar.setTitle(title);

        mWebView.setWebViewClient(new WebViewClient() {//防止调用系统的浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置背景颜色 透明
        //wv.setBackgroundColor(Color.argb(0, 0, 0, 0));
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSupportZoom(true);
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
        //开启DomStorage缓存
        webSettings.setDomStorageEnabled(true);
        mWebView.loadUrl(url);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (from != null && from.equals("advertising")) {//判断是否来自广告页面
            toNextActivity();
            finish();
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            System.arraycopy(djtime, 1, djtime, 0, djtime.length - 1);
            djtime[djtime.length - 1] = SystemClock.uptimeMillis();
            if (djtime[0] >= (SystemClock.uptimeMillis() - 1000)) {
                finish();
            } else {
                Toast.makeText(this, "再按一次返回", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }

    private void toNextActivity() {//判断去登录界面还是主界面
        Intent intent = null;
        String token  = preferences.getString(AppConfig.CACHE_TOKEN,"");
        if (TextUtils.isEmpty(token)) {
            intent = new Intent(WebViewActivity.this, LoginActivity.class);
        } else {
            intent = new Intent(WebViewActivity.this, MainActivity.class);
        }
        startActivity(intent);
    }

}
