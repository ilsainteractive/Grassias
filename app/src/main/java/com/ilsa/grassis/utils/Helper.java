package com.ilsa.grassis.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.CountDownTimer;
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
}
