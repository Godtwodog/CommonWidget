package com.god2dog.wheelwidget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/8
 * 描述：CommonWidget
 */
public class BaseSelectedView {
    private Context context;

    protected WheelOptions mWheelOptions;

    protected ViewGroup contentContainer;
    //根view
    private ViewGroup rootView;
    //附加dialog的根view
    private ViewGroup dialogView;

    private Dialog mDialog;

    protected View clickView;

    private int marginSpace = 0;
    private View.OnKeyListener onKeyBackListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && isShowing()) {
                dismiss();
                return true;
            }
            return false;
        }
    };
    private boolean isShowing;
    private boolean dismissing;
    private boolean isAnim = true;

    protected int animGravity = Gravity.BOTTOM;
    private Animation outAnim;
    private Animation inAnim;

    private OnDismissListener onDismissListener;
    private View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
            }
            return false;
        }
    };

    private boolean isShowing() {
//        if (isDialog()) {
            return false;
//        } else {
//            return rootView.getParent() != null || isShowing;
//        }
    }

    public BaseSelectedView(Context context) {
        this.context = context;
    }

    protected void initViews() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM);
        LayoutInflater layoutInflater = LayoutInflater.from(context);

//        if (isDialog()) {
            //对话框形式
            dialogView = (ViewGroup) layoutInflater.inflate(R.layout.layout_base_wheelview, null, false);
            //设置背景色为透明
            dialogView.setBackgroundColor(Color.TRANSPARENT);
            //加载选择器的父布局
            contentContainer = dialogView.findViewById(R.id.content_container);
            //设置左右间距
            layoutParams.leftMargin = marginSpace;
            layoutParams.rightMargin = marginSpace;
            contentContainer.setLayoutParams(layoutParams);
            //创建对话框
            createDialog();
            //为背景添加点击事件，点击控件外区域，消失
            dialogView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
//        } else {
//            //如果只是展示在屏幕的下面
//            //获取根布局
//            if (mWheelOptions.decorView == null) {
//                mWheelOptions.decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView();
//            }
//            rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_base_wheelview, mWheelOptions.decorView, false);
//            rootView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            //设置背景色
//            if (mWheelOptions.outSideColor != -1) {
//                rootView.setBackgroundColor(mWheelOptions.outSideColor);
//            }
//
//            contentContainer = rootView.findViewById(R.id.content_container);
//            contentContainer.setLayoutParams(layoutParams);
//        }
        setKeyBackCancelable(true);
    }
    protected void initEvents(){

    }

    private void setKeyBackCancelable(boolean isCancelable) {
        ViewGroup view;
//        if (isDialog()) {
            view = dialogView;
//        } else {
//            view = rootView;
//        }
        view.setFocusable(isCancelable);
        view.setFocusableInTouchMode(isCancelable);

        if (isCancelable) {
            view.setOnKeyListener(onKeyBackListener);
        } else {
            view.setOnKeyListener(null);
        }
    }

    public void dismiss() {
//        if (isDialog()) {
            dismissDialog();
//        } else {
//            if (dismissing) {
//                return;
//            }
//            if (isAnim) {
//                outAnim.setAnimationListener(new Animation.AnimationListener() {
//
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        dismissImmediately();
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//            } else {
//                dismissImmediately();
//            }
//            dismissing = true;
//        }
    }

    private void dismissImmediately() {
        mWheelOptions.decorView.post(new Runnable() {
            @Override
            public void run() {
                mWheelOptions.decorView.removeView(rootView);
                isShowing = false;
                dismissing = false;
                if (onDismissListener != null) {
                    onDismissListener.onDismiss(BaseSelectedView.this);
                }
            }
        });
    }

    public View findViewById(int id) {
        return contentContainer.findViewById(id);
    }


    private void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    private void createDialog() {
        //创建对话框
        if (dialogView != null) {
            mDialog = new Dialog(context, R.style.custom_dialog);
            mDialog.setCancelable(mWheelOptions.cancelable);
            mDialog.setContentView(dialogView);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.selected_view_scale_anim);
                dialogWindow.setGravity(Gravity.BOTTOM);
            }

            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (onDismissListener != null) {
                        onDismissListener.onDismiss(BaseSelectedView.this);
                    }
                }
            });
        }
    }

    public void showDialog() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public Dialog getDialog() {
        return mDialog;
    }

    protected boolean isDialog() {
        return false;
    }

    protected BaseSelectedView setOutsideCancelable(boolean isCancelable) {
        if (rootView != null) {
            View view = rootView.findViewById(R.id.outmost_container);
            if (isCancelable) {
                view.setOnTouchListener(onCancelableTouchListener);
            } else {
                view.setOnTouchListener(null);
            }
        }
        return this;
    }

    protected void setDialogOutsideCancelable() {
        if (mDialog != null) {
            mDialog.setCancelable(mWheelOptions.cancelable);
        }
    }

    protected void initAnim() {
        inAnim = AnimationUtils.loadAnimation(context, R.anim.wheelview_slide_in_bottom);
        outAnim = AnimationUtils.loadAnimation(context, R.anim.wheelview_slide_out_bottom);
    }

    public void setOutAnim(int res) {
        this.outAnim = AnimationUtils.loadAnimation(context, res);
    }

    public void setInAnim(int res ) {
        this.inAnim = AnimationUtils.loadAnimation(context, res);
    }

    public void show(View v, boolean isAnim) {
        this.clickView = v;
        this.isAnim = isAnim;
        show();
    }

    public void show(boolean isAnim) {
        this.isAnim = isAnim;
        show(null, isAnim);
    }

    public void show(View v) {
        this.clickView = v;
        show();
    }

    public void show() {
//        if (isDialog()) {
            showDialog();
//        } else {
//            if (isShowing()) {
//                return;
//            }
//            isShowing = true;
//            onAttached(rootView);
//            rootView.requestFocus();
//        }
    }

    private void onAttached(View view) {
        mWheelOptions.decorView.addView(view);
        if (isAnim) {
            contentContainer.startAnimation(inAnim);
        }
    }


    public ViewGroup getDialogContainerLayout() {
        return contentContainer;
    }
}
