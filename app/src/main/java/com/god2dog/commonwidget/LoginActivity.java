package com.god2dog.commonwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences preferences = getSharedPreferences(AppConfig.PREFERENCES_KEY,Activity.MODE_PRIVATE);
        final SharedPreferences.Editor edit = preferences.edit();

        findViewById(R.id.btnSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.putString(AppConfig.CACHE_TOKEN,"dengxs");
                edit.apply();

                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }
        });

        findViewById(R.id.btnSignOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.putString(AppConfig.CACHE_TOKEN,"");
                edit.apply();
            }
        });
    }
}
