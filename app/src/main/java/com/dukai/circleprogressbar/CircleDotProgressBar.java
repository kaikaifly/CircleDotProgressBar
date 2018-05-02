package com.dukai.circleprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * ===================
 * 作   者: 杜凯
 * 创建日期: 2018/4/28
 * 描   述:
 * 参   考：
 * https://blog.csdn.net/lmj623565791/article/details/24529807
 * https://blog.csdn.net/wangchunlei123/article/details/50478913
 * https://blog.csdn.net/qq_28872867/article/details/51915989
 * ===================
 */

public class CircleDotProgressBar extends View {
    private int diameter;
    private int firstColor;
    private int strokeWidth;
    private int dotCount;
    private int currentCount = 3;
    private int secondColor;
    private int gapSize;
    private int midTextSize;
    private int midTextColor;
    private Paint circlePaint;
    private RectF rectF;
    private Paint textPaint;

    public CircleDotProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public CircleDotProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleDotProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.circleProgressBar);
        strokeWidth = typedArray.getInteger(R.styleable.circleProgressBar_strokeWidth, 5);
        firstColor = typedArray.getColor(R.styleable.circleProgressBar_firstColor, Color.parseColor("#ffffff"));
        secondColor = typedArray.getColor(R.styleable.circleProgressBar_secondColor, Color.parseColor("#ffffff"));
        dotCount = typedArray.getColor(R.styleable.circleProgressBar_dotCount, 10);
        gapSize = typedArray.getColor(R.styleable.circleProgressBar_gapSize, 10);
        midTextSize = typedArray.getColor(R.styleable.circleProgressBar_midTextSize, 10);
        midTextColor = typedArray.getColor(R.styleable.circleProgressBar_midTextColor, Color.parseColor("#000000"));

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(strokeWidth);
//        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        circlePaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setTextSize(midTextSize);
        textPaint.setColor(midTextColor);

        rectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }

        if (width > height) {
            diameter = width;
            setMeasuredDimension(diameter, diameter);
        } else {
            diameter = height;
            setMeasuredDimension(diameter, diameter);
        }

        rectF.set(strokeWidth, strokeWidth
                , getMeasuredWidth() - strokeWidth, getMeasuredHeight() - strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float dotSize = (360 * 1.0f - dotCount * gapSize) / dotCount;
//        circlePaint.setColor(Color.parseColor("#33aaaa"));
//        for (int i = 0; i < dotCount; i++) {
//            canvas.drawArc(rectF, 90 + i * (dotSize + gapSize), dotSize, false, circlePaint);
//        }
        circlePaint.setColor(firstColor);
        for (int i = 0; i < dotCount; i++) {
            canvas.drawArc(rectF, 90 + i * (dotSize + gapSize), dotSize, false, circlePaint);
        }

        circlePaint.setColor(secondColor);
        for (int i = 0; i < currentCount; i++) {
            canvas.drawArc(rectF, 90 + i * (dotSize + gapSize), dotSize, false, circlePaint);
        }
        drawText(canvas);
    }

    public void setCurrentCount(int currentCount) {
        if (currentCount > dotCount && currentCount < 0) {
            return;
        }
        this.currentCount = currentCount;
        postInvalidate();
    }

    public int getDotCount() {
        return dotCount;
    }

    private void drawText(Canvas canvas) {
        float textWidth = textPaint.measureText(currentCount * 100 / dotCount + "%");
        int textHeight = (int) Math.ceil(textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent + 2);
        canvas.drawText(currentCount * 100 / dotCount + "%", getWidth() / 2 - textWidth / 2, getHeight() / 2 + textHeight / 4, textPaint);
    }
}
