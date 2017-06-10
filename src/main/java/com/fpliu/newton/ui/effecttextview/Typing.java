package com.fpliu.newton.ui.effecttextview;

import android.graphics.Canvas;

/**
 * 打字效果
 * 792793182@qq.com 2017-07-10
 */
final class Typing extends AbstractEffect {

    private int currentLength;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void animateStart(CharSequence text) {
        currentLength = 0;
        mHTextView.invalidate();
    }

    @Override
    protected void animatePrepare(CharSequence text) {

    }

    @Override
    protected void drawFrame(Canvas canvas) {
        canvas.drawText(mText, 0, currentLength, startX, startY, mPaint);

        if (currentLength < mText.length()) {
            currentLength++;
            mHTextView.postInvalidateDelayed(100);
        }
    }
}
