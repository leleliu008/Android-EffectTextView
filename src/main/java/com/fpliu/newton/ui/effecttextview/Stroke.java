package com.fpliu.newton.ui.effecttextview;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.LinearInterpolator;

/**
 * 描边效果
 * 792793182@qq.com 2017-07-10
 */
class Stroke extends AbstractEffect {

    private float progress = 0;

    private Paint linePaint;

    @Override
    protected void initVariables() {
        float lineWidth = UIUtil.dp2px(mHTextView.getContext(), 1.5f);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(mHTextView.getCurrentTextColor());
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStrokeWidth(lineWidth);
    }

    @Override
    protected void animateStart(CharSequence text) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(800);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            progress = (float) animation.getAnimatedValue();
            mHTextView.invalidate();
        });
        valueAnimator.start();
        progress = 0;
    }

    @Override
    protected void animatePrepare(CharSequence text) {
        Rect bounds = new Rect();
        mPaint.getTextBounds(mText.toString(), 0, mText.length(), bounds);
    }

    @Override
    protected void drawFrame(Canvas canvas) {
        float percent = progress;

        canvas.drawText(mText, 0, mText.length(), startX, startY, mPaint);

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        float totalLength = 2 * width + 2 * height;
        float currentLength = totalLength * percent;

        if (currentLength <= width) {
            canvas.drawLine(0, 0, currentLength, 0, linePaint);
        } else if (currentLength <= width + height) {
            canvas.drawLine(0, 0, width, 0, linePaint);
            canvas.drawLine(width, 0, width, currentLength - width, linePaint);
        } else if (currentLength <= width * 2 + height) {
            canvas.drawLine(0, 0, width, 0, linePaint);
            canvas.drawLine(width, 0, width, height, linePaint);
            canvas.drawLine(width, height, totalLength - width - currentLength, height, linePaint);
        } else if (currentLength <= totalLength) {
            canvas.drawLine(0, 0, width, 0, linePaint);
            canvas.drawLine(width, 0, width, height, linePaint);
            canvas.drawLine(width, height, 0, height, linePaint);
            canvas.drawLine(0, height, 0, totalLength - currentLength, linePaint);
        }
    }
}
