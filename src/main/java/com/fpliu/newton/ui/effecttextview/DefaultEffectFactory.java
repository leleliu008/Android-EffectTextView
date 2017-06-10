package com.fpliu.newton.ui.effecttextview;

/**
 * 默认的效果工厂
 *
 * @author 792793182@qq.com 2017-06-10.
 */
final class DefaultEffectFactory implements EffectFactory {

    @Override
    public IEffect newInstance(int effectType) {
        switch (effectType) {
            case TYPE_SCALE:
                return new Scale();
            case TYPE_EVAPORATE:
                return new Evaporate();
            case TYPE_FALL:
                return new Fall();
            case TYPE_PIXELATE:
                return new Pixelate();
            case TYPE_ANVIL:
                return new Anvil();
            case TYPE_SPARKLE:
                return new Sparkle();
            case TYPE_LINE:
                return new Line();
            case TYPE_TYPER:
                return new Typing();
            case TYPE_RAINBOW:
                return new RainBow();
            case TYPE_STROKE:
                return new Stroke();
            default:
                return null;
        }
    }
}
