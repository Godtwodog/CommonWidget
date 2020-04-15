package com.god2dog.commonwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class ToastActivity extends AppCompatActivity {
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);


        findViewById(R.id.showCommonToast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showTextToast("普通模式弹窗",context);
            }
        });
        findViewById(R.id.showMultilineToast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showTextToast("多行弹窗多行弹窗多行弹窗多行弹窗多行弹窗多行弹窗",context);
            }
        });
        findViewById(R.id.showSingleLineToast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showTextToast("单行弹窗单行弹窗",context);
            }
        });
        findViewById(R.id.showErrorToast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showErrorToast("图片提示弹窗",context);
            }
        });
        findViewById(R.id.showSuccessToast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showSuccessToast("成功图片提示弹窗",context);
            }
        });

    }
}
