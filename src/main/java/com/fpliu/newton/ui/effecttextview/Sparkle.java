package com.fpliu.newton.ui.effecttextview;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;

import java.util.Random;

/**
 * 闪烁效果
 * 792793182@qq.com 2017-07-10
 */
final class Sparkle extends AbstractEffect {

    private float progress = 0;

    private float charTime = 400;

    private int mostCount = 20;

    private float upDistance = 0;

    private Paint backPaint;

    private Bitmap sparkBitmap;

    @Override
    protected void initVariables() {
        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(((ColorDrawable) mHTextView.getBackground()).getColor());
        backPaint.setStyle(Paint.Style.FILL);

        sparkBitmap = BitmapFactory.decodeResource(mHTextView.getResources(), R.drawable.sparkle);
    }

    @Override
    protected void animateStart(CharSequence text) {
        int n = mText.length();
        n = n <= 0 ? 1 : n;

        long duration = (long) (charTime + charTime / mostCount * (n - 1));

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, duration).setDuration(duration);
        valueAnimator.addUpdateListener(animation -> {
            progress = (float) animation.getAnimatedValue();
            mHTextView.invalidate();
        });
        valueAnimator.start();

    }

    @Override
    protected void animatePrepare(CharSequence text) {
        Rect bounds = new Rect();
        mPaint.getTextBounds(mText.toString(), 0, mText.length(), bounds);
        upDistance = bounds.height();
    }

    @Override
    protected void drawFrame(Canvas canvas) {
        float offset = startX;
        float oldOffset = oldStartX;

        int maxLength = Math.max(mText.length(), mOldText.length());
        float percent = progress / (charTime + charTime / mostCount * (mText.length() - 1));

        mPaint.setAlpha(255);
        mPaint.setTextSize(mTextSize);

        for (int i = 0; i < maxLength; i++) {
            // draw new text
            if (i < mText.length()) {

                if (!CharacterUtils.stayHere(i, differentList)) {

                    float width = mPaint.measureText(mText.charAt(i) + "");
                    canvas.drawText(mText.charAt(i) + "", 0, 1, offset, startY, mPaint);
                    if (percent < 1) {
                        drawSparkle(canvas, offset, startY - (1 - percent) * upDistance, width);
                    }

                    canvas.drawRect(offset, startY * 1.2f - (1 - percent) * (upDistance + startY * 0.2f), offset + gaps[i], startY * 1.2f, backPaint);
                }
                offset += gaps[i];
            }
            // draw old text
            if (i < mOldText.length()) {
                //
                float pp = progress / (charTime + charTime / mostCount * (mText.length() - 1));

                mOldPaint.setTextSize(mTextSize);
                int move = CharacterUtils.needMove(i, differentList);
                if (move != -1) {
                    mOldPaint.setAlpha(255);
                    float p = pp * 2f;
                    p = p > 1 ? 1 : p;
                    float distX = CharacterUtils.getOffset(i, move, p, startX, oldStartX, gaps, oldGaps);
                    canvas.drawText(mOldText.charAt(i) + "", 0, 1, distX, startY, mOldPaint);
                } else {

                    float p = pp * 3.5f;
                    p = p > 1 ? 1 : p;
                    mOldPaint.setAlpha((int) (255 * (1 - p)));
                    canvas.drawText(mOldText.charAt(i) + "", 0, 1, oldOffset, startY, mOldPaint);
                }
                oldOffset += oldGaps[i];
            }
        }
    }

    private void drawSparkle(Canvas canvas, float offset, float startY, float width) {
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            canvas.drawBitmap(getRandomSpark(random), (float) (offset + random.nextDouble() * width), (float) (startY - random
                    .nextGaussian() * Math.sqrt(upDistance)), mPaint);
        }
    }

    private Bitmap getRandomSpark(Random random) {
        int dstWidth = random.nextInt(12) + 1;
        return Bitmap.createScaledBitmap(sparkBitmap, dstWidth, dstWidth, false);
    }
}
