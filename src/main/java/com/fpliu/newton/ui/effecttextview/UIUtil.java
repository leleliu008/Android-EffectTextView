package com.fpliu.newton.ui.effecttextview;

import android.content.Context;
import android.content.res.Resources;

final class UIUtil {

    private UIUtil() {
    }

    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    public static int dp2Px(int dp) {
        return Math.round(dp * DENSITY);
    }

    public static int dp2px(Context ctx, float dpValue) {
        final float density = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }
}
