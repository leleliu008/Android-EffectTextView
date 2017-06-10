package com.fpliu.newton.ui.effecttextview;

import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * 效果接口
 * <p>
 * 792793182@qq.com 2017-07-10
 */
public interface IEffect {

    void init(EffectTextView effectTextView, AttributeSet attrs, int defStyle);

    void animateText(CharSequence text);

    void onDraw(Canvas canvas);

    void reset(CharSequence text);
}
