package com.ilsa.grassis.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;

/**
 * Created by Ilsa on 1/3/2017.
 */

public class Helper {

    public static void JumpActivityWithTimmer(int TotalTime, int Interval, final Context here, final Class<?> there) {
        new CountDownTimer(TotalTime, Interval) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                here.startActivity(new Intent(here, there));
            }
        }.start();
    }

    public static int dpToPx(Resources resource, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resource.getDisplayMetrics());
    }

    public static float pxToDp(Resources resource, float px) {
        return (float) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, resource.getDisplayMetrics());
    }



    public static float getFontSize(Resources resource, int size) {
        return size * resource.getDisplayMetrics().density;
    }

    public static float getFontSize(Resources resource, double size) {
        return (float) size * resource.getDisplayMetrics().density;
    }

    public static int getStatusBarHeight(final Context context) {
        final Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            return resources.getDimensionPixelSize(resourceId);
        else
            return (int) Math.ceil((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? 24 : 25) * resources.getDisplayMetrics().density);
    }

    public static SpannableStringBuilder getBoldedText(String text, int startIndex, int endIndex) {
        final SpannableStringBuilder str = new SpannableStringBuilder(text);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }
}
