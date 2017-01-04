package com.ilsa.grassis.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.ilsa.grassis.R;

public class SplashActivity extends AppCompatActivity {

    private Context mContext;
    private int mTotalTime = 2000; // 2 seconds
    private int mTimeInterval = 1000; // 1 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContext = this;
        //ActivitiesHelper.JumpActivityWithTimmer(mTotalTime, mTimeInterval, mContext, LoginActivity.class);
        new CountDownTimer(mTotalTime, mTimeInterval) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                finish();
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
            }
        }.start();
    }
}
