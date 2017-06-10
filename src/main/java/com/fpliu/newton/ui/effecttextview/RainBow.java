package com.fpliu.newton.ui.effecttextview;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;

/**
 * from http://wuxiaolong.me/2015/11/16/LinearGradientTextView/
 * Created by hanks on 15/12/26.
 */
final class RainBow extends AbstractEffect {

    private LinearGradient mLinearGradient;
    private Matrix mMatrix;
    private float mTranslate;
    private int dx;
    private int[] colors =
            new int[]{0xFFFF2B22, 0xFFFF7F22, 0xFFEDFF22, 0xFF22FF22, 0xFF22F4FF, 0xFF2239FF, 0xFF5400F7};

    @Override
    protected void initVariables() {
        mMatrix = new Matrix();
        dx = UIUtil.dp2Px(7);
    }

    public void setColors(int colors[]) {
        this.colors = colors;
    }

    @Override
    protected void animateStart(CharSequence text) {
        mHTextView.invalidate();
    }

    @Override
    protected void animatePrepare(CharSequence text) {
        int textWidth = (int) mPaint.measureText(mText, 0, mText.length());
        textWidth = Math.max(UIUtil.dp2Px(100), textWidth);
        if (textWidth > 0) {
            mLinearGradient = new LinearGradient(0, 0, textWidth, 0,
                    colors, null, Shader.TileMode.MIRROR);
            mPaint.setShader(mLinearGradient);
        }
    }

    @Override
    protected void drawFrame(Canvas canvas) {
        if (mMatrix != null && mLinearGradient != null) {
            mTranslate += dx;
            mMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mMatrix);
            canvas.drawText(mText, 0, mText.length(), startX, startY, mPaint);
            mHTextView.postInvalidateDelayed(100);
        }
    }
}
