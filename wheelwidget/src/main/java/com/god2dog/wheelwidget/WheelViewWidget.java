package com.god2dog.wheelwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/2
 * 描述：CommonWidget 自定义时间滚轮控件
 */
public class WheelViewWidget extends View {
    private final String TAG = "dengxs";
    //展示的item个数，默认为7个
    private int itemCount = 7;
    //滚轮中item字体大小
    private int mItemTextSize;
    //滚轮中item
    private Paint mItemTextPaint;
    //绘制间隔线
    private Paint mIndicatorPaint;
    //绘制外部item文本
    private Paint mItemOutPaint;
    //滚轮中item字体颜色
    private int mItemTextColor;
    //未选中文本颜色
    private int mItemOutTextColor;
    //间隔线颜色
    private int mIndicatorColor;
    //字体样式 默认MONOSPACE
    private Typeface mTypeface = Typeface.MONOSPACE;
    //滚轮item 适配器
    private WheelItemAdapter mAdapter;
    //item高度
    private float itemHeight;
    //item字体宽度
    private int maxTextWidth;
    //item字体高度
    private int maxTextHeight;
    //间隔倍数
    private float mItemSpceMultiplier = 1.6F;
    //偏移量
    private float CENTER_CONTENT_OFFSET = 4.0F;
    private static final float SCALE_CONTENT = 0.8F;
    //控件高度
    private int measureHeight;
    //控件宽度
    private int measureWidth;

    private static int DEFAULT_ITEM_TEXT_SIZE = 16;
    //默认文本颜色,若未设置，则取默认值
    private int DEFAULT_ITEM_TEXT_COLOR;
    //默认间隔线颜色
    private int DEFAULT_INDICATOR_COLOR;
    //默认未选择区域文本颜色
    private int DEFAULT_UNSELECTED_TEXT_COLOR;

    private int widthMeasureSpec;
    //控件半径
    private int radius;
    //上面间隔线位置坐标Y
    private float firstLineY;
    //下面间隔线位置坐标Y
    private float secondLineY;
    //文字基线位置
    private float centerY;
    //当前移动总值 Y坐标
    private float totalScrollY;

    private float previousY = 0;
    //设置控件是否循环  默认数据循环滚动
    private boolean isLoop = true;

    private int preCurrentIndex = 0;
    //初始化位置
    private int initPosition = -1;
    private int mGravity = Gravity.CENTER;
    //中间文字开始绘制的位置
    private int drawCenterContentStart = 0;
    //区域外文字开始绘制的位置
    private int drawOutContentStart = 0;
    //选中item
    private int selectedItem;
    private int textOffset;
    //设置是否为透明度渐变
    private boolean isAlphaGradient = false;


    public WheelViewWidget(Context context) {
        this(context, null);
    }

    public WheelViewWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelViewWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DEFAULT_ITEM_TEXT_SIZE = getResources().getDimensionPixelSize(R.dimen.DefaultItemTextSize);
        DEFAULT_ITEM_TEXT_COLOR = getResources().getColor(R.color.WheelItemTextColor);
        DEFAULT_INDICATOR_COLOR = getResources().getColor(R.color.IndicatorColor);
        DEFAULT_UNSELECTED_TEXT_COLOR = getResources().getColor(R.color.UnSelectedTextColor);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WheelViewWidget);
        mItemTextSize = ta.getDimensionPixelSize(R.styleable.WheelViewWidget_ItemTextSize, DEFAULT_ITEM_TEXT_SIZE);
        mItemTextColor = ta.getColor(R.styleable.WheelViewWidget_ItemTextColor, DEFAULT_ITEM_TEXT_COLOR);
        mItemOutTextColor = ta.getColor(R.styleable.WheelViewWidget_ItemOutTextColor, DEFAULT_UNSELECTED_TEXT_COLOR);
        isLoop = ta.getBoolean(R.styleable.WheelViewWidget_ItemIsLoop, true);
        mIndicatorColor = ta.getColor(R.styleable.WheelViewWidget_IndicatorColor, DEFAULT_INDICATOR_COLOR);
        mGravity = ta.getInt(R.styleable.WheelViewWidget_TextGravity, mGravity);
        ta.recycle();

        initPaints();
    }

    private void initPaints() {
        mItemTextPaint = new Paint();
        mItemTextPaint.setColor(mItemTextColor);
        mItemTextPaint.setAntiAlias(true);
        mItemTextPaint.setTextAlign(Paint.Align.CENTER);
        mItemTextPaint.setTypeface(mTypeface);
        mItemTextPaint.setTextSize(mItemTextSize);

        mItemOutPaint = new Paint();
        mItemOutPaint.setTextAlign(Paint.Align.CENTER);
        mItemOutPaint.setTypeface(mTypeface);
        mItemOutPaint.setAntiAlias(true);
        mItemOutPaint.setTextSize(mItemTextSize);
        mItemOutPaint.setColor(mItemOutTextColor);

        mIndicatorPaint = new Paint();
        mIndicatorPaint.setColor(mIndicatorColor);
        mItemTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        //测量item文字宽度和高度
        measureView();
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //进行视图的绘制
        if (mAdapter == null) {
            return;
        }

        //获取当前初始化位置position
        initPosition = Math.min(Math.max(0, initPosition), mAdapter.getItemCount() - 1);
        //滚动个数  滚动的总高度 / itemHeight 得到滚动了多少个item数量
        int change = (int) (totalScrollY / itemHeight);
        Log.i(TAG, "移动个数:" + change);
        float itemHeightOffset = totalScrollY * itemCount;

        //对角度进行计算a
        int count = 0;
        while (count < itemCount) {
            Object showText;
            //获取目标索引值
            int index = preCurrentIndex - (itemCount / 2 - count);

            try {
                preCurrentIndex = initPosition + change % mAdapter.getItemCount();
            } catch (ArithmeticException e) {

            }
            if (isLoop) {
                //如果是循环的
                if (preCurrentIndex < 0) {
                    preCurrentIndex = preCurrentIndex + mAdapter.getItemCount();
                }
                if (preCurrentIndex > mAdapter.getItemCount() - 1) {
                    preCurrentIndex = preCurrentIndex - mAdapter.getItemCount();
                }
            } else {
                if (preCurrentIndex < 0) {
                    preCurrentIndex = 0;
                }
                if (preCurrentIndex > mAdapter.getItemCount()) {
                    preCurrentIndex = mAdapter.getItemCount() - 1;
                }
            }

            //绘制两条 间隔线
            canvas.drawLine(0.0F, firstLineY, measureWidth, firstLineY, mIndicatorPaint);
            canvas.drawLine(0.0F, secondLineY, measureWidth, secondLineY, mIndicatorPaint);

            //设置控件是否循环滚动
            if (isLoop) {
                //递归获得目标位置
                index = recursionGetLoopIndex(index);
                showText = mAdapter.getItem(index);
            } else if (index < 0 || index > mAdapter.getItemCount() - 1) {
                showText = "";
            } else {
                showText = mAdapter.getItem(index);
            }
            canvas.save();
            //计算弧长
            float arc = itemHeight * count - itemHeightOffset;
            //计算弧度 弧度 = 弧长 / 半径
            float radian = arc / radius;
            //计算角度
            //顺时针90度旋转，使其处于第一和第四象限
            float angle = (float) (90D - radian / Math.PI * 180D);

            if (angle > 90 || angle < -90) {
                canvas.restore();
            } else {
                //获取 展示文本内容
                String contentText = getContentText(showText);

                float offset = (float) Math.pow(Math.abs(angle) / 90F, 2.2);
                //重新测量文本内容 以适应控件大小
                reMeasureContentText(contentText);
                //获取文本起始位置
                measureCenterContentStart(contentText);
                measureOutContentStart(contentText);

                //获得Y滚动
                float translateY = (float) (radius - Math.cos(radian) * radius - Math.sin(radian) * itemHeight / 2D);
                canvas.translate(0.0F, translateY);

                if (translateY <= firstLineY && translateY >= firstLineY - maxTextHeight) {
                    //item经过第一条线firstLineY
                    canvas.save();
                    canvas.clipRect(0, 0, measureWidth, translateY - firstLineY);
                    canvas.scale(1.0F, (float) (Math.sin(radian) * radius * SCALE_CONTENT));
                    //设置外部文字
                    setOutPaintStyle(offset, angle);
                    canvas.drawText(contentText, drawOutContentStart, maxTextHeight, mItemOutPaint);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, firstLineY - translateY, measureWidth, (int) itemHeight);
                    canvas.scale(1.0F, (float) (Math.sin(radian) * radius * 1.0F));
                    canvas.drawText(contentText, drawCenterContentStart, maxTextHeight - CENTER_CONTENT_OFFSET, mItemTextPaint);
                    canvas.restore();
                } else if (translateY <= secondLineY && translateY >= secondLineY - maxTextHeight) {
                    //item经过第二条线 secondLineY
                    canvas.save();
                    canvas.clipRect(0,secondLineY - translateY,measureWidth,itemHeight);
                    canvas.scale(1.0F, (float) (Math.sin(radian) * radius * 1.0F));
                    canvas.drawText(contentText,drawCenterContentStart,maxTextHeight - CENTER_CONTENT_OFFSET,mItemTextPaint);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0,0,measureWidth,translateY - secondLineY);
                    canvas.scale(1.0F, (float) (Math.sin(radian) * radius * SCALE_CONTENT));
                    canvas.drawText(contentText,drawOutContentStart,maxTextHeight,mItemOutPaint);
                    canvas.restore();
                } else if (translateY >= firstLineY && maxTextHeight + itemHeight <= secondLineY){

                    float Y  = maxTextHeight - CENTER_CONTENT_OFFSET;
                    canvas.drawText(contentText,drawCenterContentStart,Y,mItemTextPaint);
                    //设置选中项
                    selectedItem = preCurrentIndex - (itemCount / 2  -count);
                }else {
                    canvas.save();
                    canvas.clipRect(0,0,measureWidth,itemHeight);
                    canvas.scale(1.0F, (float) (Math.sin(radian) * radius * SCALE_CONTENT));
                    setOutPaintStyle(offset,angle);
                    canvas.drawText(contentText,drawOutContentStart + textOffset * offset ,maxTextHeight,mItemOutPaint);
                }
            }
        }
    }

    private void measureOutContentStart(String contentText) {
        Rect rect = new Rect();
        mItemOutPaint.getTextBounds(contentText, 0, contentText.length(), rect);
        switch (mGravity) {
            case Gravity.CENTER:
                drawOutContentStart = (int) ((measureWidth - rect.width()) * 0.5);
                break;
            case Gravity.LEFT:
                drawOutContentStart = 0;
                break;
            case Gravity.RIGHT:
                drawOutContentStart = measureWidth - rect.width() - (int) CENTER_CONTENT_OFFSET;
                break;
        }

    }

    private void measureCenterContentStart(String contentText) {
        Rect rect = new Rect();
        mItemTextPaint.getTextBounds(contentText, 0, contentText.length(), rect);
        switch (mGravity) {
            case Gravity.CENTER:
                drawCenterContentStart = (int) ((measureWidth - rect.width()) * 0.5);
                break;
            case Gravity.LEFT:
                drawCenterContentStart = 0;
                break;
            case Gravity.RIGHT:
                drawCenterContentStart = measureWidth - rect.width() - (int) CENTER_CONTENT_OFFSET;
                break;
        }
    }

    private void setOutPaintStyle(float offset, float angle) {
        //控制文字倾斜角度
        float DEFAULT_TEXT_ITALIC = 0.5F;
        int multiplier = 0;
        if (textOffset > 0){
            multiplier = 1;
        }else if (textOffset < 0){
            multiplier = -1;
        }
        mItemOutPaint.setTextSkewX(multiplier * (angle > 0 ? -1 : 1) * DEFAULT_TEXT_ITALIC * offset);
        int alpha = isAlphaGradient ? (int) (90F - Math.abs(angle) / 90F * 255F) : 255;
        mItemOutPaint.setAlpha(alpha);
    }

    private void reMeasureContentText(String contentText) {
        Rect rect = new Rect();
        mItemTextPaint.getTextBounds(contentText, 0, contentText.length(), rect);
        int width = rect.width();
        int size = mItemTextSize;
        while (width > measureWidth) {
            size--;
            mItemTextPaint.setTextSize(size);
            mItemTextPaint.getTextBounds(contentText, 0, contentText.length(), rect);
            width = rect.width();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                previousY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = previousY - event.getRawX();
                previousY = event.getRawY();
                totalScrollY = totalScrollY + dy;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    private void measureView() {
        if (mAdapter == null) {
            return;
        }
        measureItemTextWidthAndHeight();
        //计算控件高度 即半圆周长 可见item数目 -1 为所需item值
        float halfCircleLength = (itemCount - 1) * itemHeight;
        //有半圆周长可得 直径 即为控件高度
        measureHeight = (int) ((halfCircleLength * 2.0F) / Math.PI);
        //计算半径
        radius = (int) (halfCircleLength / 2.0F);
        //获取控件宽度
        measureWidth = MeasureSpec.getSize(widthMeasureSpec);

        //计算横线位置和中间基线
        firstLineY = (measureHeight - itemHeight) / 2.0F;
        secondLineY = (measureHeight + itemHeight) / 2.0F;

        centerY = secondLineY - (itemHeight - maxTextHeight) / 2.0F - CENTER_CONTENT_OFFSET;

        if (initPosition == -1) {
            //初始化显示的item 数据源位置
            if (isLoop) {
                //若循环，则居中显示
                initPosition = (mAdapter.getItemCount() + 1) / 2;
            } else {
                //反之，则初始位置为第一个
                initPosition = 0;
            }
        }
        preCurrentIndex = initPosition;
    }

    private void measureItemTextWidthAndHeight() {
        Rect rect = new Rect();
        for (int i = 0; i < itemCount; i++) {
            String temp = getContentText(mAdapter.getItem(i));
            mItemTextPaint.getTextBounds(temp, 0, temp.length(), rect);

            //获取最大宽度
            int textWidth = rect.width();
            if (textWidth > maxTextWidth) {
                maxTextWidth = textWidth;
            }

            mItemTextPaint.getTextBounds("星期", 0, 2, rect);
            maxTextHeight = rect.height() + 2;
            //item高度
            itemHeight = maxTextHeight * mItemSpceMultiplier;
        }
    }

    private String getContentText(Object item) {
        if (item == null) {
            return "";
        } else if (item instanceof Integer) {
            return getNumberFormat((int) item);
        } else {
            return "";
        }
    }

    //对整数进行格式化，一位数的要变为两位数
    private String getNumberFormat(int item) {
        if (item >= 0 && item < 10) {
            return "0" + item;
        } else {
            return String.valueOf(item);
        }
    }

    /**
     * @return 是否循环滚动 isLoop
     */
    public boolean isLoop() {
        return isLoop;
    }

    private int recursionGetLoopIndex(int index) {
        if (index < 0) {
            index = index + mAdapter.getItemCount();
        } else if (index > mAdapter.getItemCount() - 1) {
            index = index - mAdapter.getItemCount();
        }
        index = recursionGetLoopIndex(index);
        return index;
    }

    /**
     * 设置文本 格式
     * center 居中对齐
     * left 左对齐
     * right 右对齐
     *
     * @param mGravity
     */
    public void setGravity(int mGravity) {
        this.mGravity = mGravity;
    }

    public void setTextOffset(int textOffset) {
        this.textOffset = textOffset;
        if (textOffset != 0){
            mItemTextPaint.setTextScaleX(1.0F);
        }
    }

    public void setAlphaGradient(boolean alphaGradient) {
        isAlphaGradient = alphaGradient;
    }
}
