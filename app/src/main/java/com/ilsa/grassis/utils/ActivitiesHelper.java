package com.ilsa.grassis.utils;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;

/**
 * Created by Ilsa on 1/3/2017.
 */

public class ActivitiesHelper {

    public static void JumpActivityWithTimmer(int TotalTime, int Interval, final Context here, final Class<?> there) {
        new CountDownTimer(TotalTime, Interval) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                here.startActivity(new Intent(here, there));
            }
        }.start();

    }
}
