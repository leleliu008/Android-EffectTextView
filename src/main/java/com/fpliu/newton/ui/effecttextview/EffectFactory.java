package com.fpliu.newton.ui.effecttextview;

/**
 * 效果工厂接口，如果要实现自己的效果，需要实现此接口，然后调用{@link EffectTextView#addEffectFactory(EffectFactory)} ()}
 *
 * @author 792793182@qq.com 2017-06-10.
 */
public interface EffectFactory {

    int TYPE_SCALE = 0;

    int TYPE_EVAPORATE = 1;

    int TYPE_FALL = 2;

    int TYPE_PIXELATE = 3;

    int TYPE_ANVIL = 4;

    int TYPE_SPARKLE = 5;

    int TYPE_LINE = 6;

    int TYPE_TYPER = 7;

    int TYPE_RAINBOW = 8;

    int TYPE_STROKE = 9;

    /**
     * 效果工厂，生产效果实例
     *
     * @param effectType 效果类型，0-99是预留的，不要使用，自己定义的效果，从100开始。
     */
    IEffect newInstance(int effectType);
}
