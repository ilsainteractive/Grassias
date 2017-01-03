package com.ilsa.grassis;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ilsa.grassis.utils.ActivitiesHelper;

public class SplashActivity extends AppCompatActivity {

    private Typeface mTypefaceTitle, mTypefaceSubTitle;
    private TextView mTxtTitle, mTxtSubTitle;
    private Context mContext;
    private int mTotalTime = 2000; // 2 seconds
    private int mTimeInterval = 1000; // 1 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContext = this;
        mTypefaceTitle = Typeface.createFromAsset(getAssets(), "fonts/SFUITextHeavy.otf");
        mTypefaceSubTitle = Typeface.createFromAsset(getAssets(), "fonts/SFUITextLight.otf");
        mTxtTitle = (TextView) findViewById(R.id.splash_txt_title);
        mTxtSubTitle = (TextView) findViewById(R.id.splash_txt_sub_title);
        mTxtTitle.setTypeface(mTypefaceTitle);
        mTxtSubTitle.setTypeface(mTypefaceSubTitle);
        ActivitiesHelper.JumpActivityWithTimmer(mTotalTime, mTimeInterval, mContext, LoginActivity.class);
    }
}
