package com.fpliu.newton.ui.effecttextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 有效果的TextView
 * 792793182@qq.com 2017-07-10
 */
public class EffectTextView extends TextView {

    private static ArrayList<EffectFactory> effectFactories = new ArrayList<>();

    static {
        effectFactories.add(new DefaultEffectFactory());
    }

    private AttributeSet attrs;

    private int defStyle;

    private int effectType;

    private IEffect effect;

    public EffectTextView(Context context) {
        super(context);
        init(null, 0);
    }

    public EffectTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public EffectTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        this.attrs = attrs;
        this.defStyle = defStyle;

        // Get the attributes array
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HTextView);
        effectType = typedArray.getInt(R.styleable.HTextView_effectType, 0);
        final String fontAsset = typedArray.getString(R.styleable.HTextView_fontAsset);

        if (!this.isInEditMode()) {
            // Set custom typeface
            if (fontAsset != null && !fontAsset.trim().isEmpty()) {
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), fontAsset));
            }
        }

        typedArray.recycle();

        setEffectType(effectType);
    }

    /**
     * 添加效果工厂，便于使用者扩展
     *
     * @param effectFactory 效果工厂
     */
    public static void addEffectFactory(EffectFactory effectFactory) {
        if (effectFactory != null) {
            effectFactories.add(effectFactory);
        }
    }

    /**
     * @param effectType 效果类型
     */
    public void setEffectType(int effectType) {
        IEffect effect = null;
        //顺序查找，找到一个就不找了，所以，顺序很重要
        for (int i = 0; i < effectFactories.size(); i++) {
            effect = effectFactories.get(i).newInstance(effectType);
            if (effect != null) {
                this.effectType = effectType;
                this.effect = effect;
                break;
            }
        }
        if (effect == null) {
            this.effectType = EffectFactory.TYPE_NO_EFFECT;
            this.effect = null;
        } else {
            effect.init(this, attrs, defStyle);
        }
    }

    public void noEffect() {
        setEffectType(EffectFactory.TYPE_NO_EFFECT);
        invalidate();
    }

    public void animateText(CharSequence text) {
        if (effect != null) {
            effect.animateText(text);
        }
    }

    public void animateText(CharSequence text, int colors[]) {
        if (effect != null) {
            if (effect instanceof RainBow) {
                ((RainBow) effect).setColors(colors);
            }
            effect.animateText(text);
        }
    }

    public void reset(CharSequence text) {
        if (effect != null) {
            effect.reset(text);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (effect == null) {
            super.onDraw(canvas);
        } else {
            effect.onDraw(canvas);
        }
    }

    @Override
    public void setTextColor(int color) {
        //Check for RainbowText. Do not alter color if on that type due to paint conflicts
        if (effectType != EffectFactory.TYPE_RAINBOW) {
            super.setTextColor(color);
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState state = new SavedState(superState);
        state.effectType = effectType;
        return state;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(state);
        effectType = ss.effectType;
    }

    public static class SavedState extends BaseSavedState {

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        int effectType;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            effectType = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(effectType);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }

}
