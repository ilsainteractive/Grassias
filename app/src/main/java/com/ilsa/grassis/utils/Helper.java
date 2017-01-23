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
 * The type Helper.
 */
public class Helper {

    /**
     * Jump activity with timmer.
     *
     * @param TotalTime the total time
     * @param Interval  the interval
     * @param here      the here
     * @param there     the there
     */
    public static void JumpActivityWithTimmer(int TotalTime, int Interval, final Context here, final Class<?> there) {
        new CountDownTimer(TotalTime, Interval) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                here.startActivity(new Intent(here, there));
            }
        }.start();
    }

    /**
     * Dp to px int.
     *
     * @param resource the resource
     * @param dp       the dp
     * @return the int
     */
    public static int dpToPx(Resources resource, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resource.getDisplayMetrics());
    }

    /**
     * Px to dp float.
     *
     * @param resource the resource
     * @param px       the px
     * @return the float
     */
    public static float pxToDp(Resources resource, float px) {
        return (float) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, resource.getDisplayMetrics());
    }


    /**
     * Gets font size.
     *
     * @param resource the resource
     * @param size     the size
     * @return the font size
     */
    public static float getFontSize(Resources resource, int size) {
        return size * resource.getDisplayMetrics().density;
    }

    /**
     * Gets font size.
     *
     * @param resource the resource
     * @param size     the size
     * @return the font size
     */
    public static float getFontSize(Resources resource, double size) {
        return (float) size * resource.getDisplayMetrics().density;
    }

    /**
     * Gets status bar height.
     *
     * @param context the context
     * @return the status bar height
     */
    public static int getStatusBarHeight(final Context context) {
        final Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            return resources.getDimensionPixelSize(resourceId);
        else
            return (int) Math.ceil((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? 24 : 25) * resources.getDisplayMetrics().density);
    }

    /**
     * Gets bolded text.
     *
     * @param text       the text
     * @param startIndex the start index
     * @param endIndex   the end index
     * @return the bolded text
     */
    public static SpannableStringBuilder getBoldedText(String text, int startIndex, int endIndex) {
        final SpannableStringBuilder str = new SpannableStringBuilder(text);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }
}
