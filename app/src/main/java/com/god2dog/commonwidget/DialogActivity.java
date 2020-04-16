package com.god2dog.commonwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.god2dog.dialoglibrary.CenterDialog;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        findViewById(R.id.showCommonDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CenterDialog dialog = new CenterDialog(DialogActivity.this,1);
                dialog.setTitle("我是标题");
                dialog.setSubHead("我是副标题");
                dialog.setContent("我是文本内容");
                dialog.AddOnDialogListener(new CenterDialog.OnDialogClickedListener() {
                    @Override
                    public void onCancelAction(View view) {
                        ToastUtils.showToast("点击取消",DialogActivity.this);
                    }

                    @Override
                    public void onConfirmAction(View view) {
                        ToastUtils.showToast("点击确认",DialogActivity.this);
                    }
                });
                dialog.show();
            }
        });

        findViewById(R.id.showNoSubheadDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CenterDialog dialog = new CenterDialog(DialogActivity.this,1);
                dialog.setTitle("我是标题");
                dialog.setContent("我是文本内容");
                dialog.AddOnDialogListener(new CenterDialog.OnDialogClickedListener() {
                    @Override
                    public void onCancelAction(View view) {
                        ToastUtils.showToast("点击取消",DialogActivity.this);
                    }

                    @Override
                    public void onConfirmAction(View view) {
                        ToastUtils.showToast("点击确认",DialogActivity.this);
                    }
                });
                dialog.show();
            }
        });
    }
}
