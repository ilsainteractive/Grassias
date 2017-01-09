package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.BoldTextView;
import com.ilsa.grassis.library.UltraThinTextView;
import com.ilsa.grassis.utils.Helper;

public class SplashActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private int mTotalTime = 2000; // 2 seconds
    private int mTimeInterval = 1000; // 1 seconds
    private BoldTextView mTxtTitle;
    private UltraThinTextView mTxtSubTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContext = this;
        mActivity = this;
        //ActionBarConfigs();
        InitComponents();

        new CountDownTimer(mTotalTime, mTimeInterval) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                finish();
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
            }
        }.start();
    }

    private void InitComponents() {

        mTxtTitle = (BoldTextView) findViewById(R.id.splash_txt_title);
        mTxtTitle.setTextSize(Helper.getFontSize(getResources(), 10));
        mTxtSubTitle = (UltraThinTextView) findViewById(R.id.splash_txt_subtitle);
        mTxtSubTitle.setTextSize(Helper.getFontSize(getResources(), 6));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            mTxtTitle.setLetterSpacing(.05f * getResources().getDisplayMetrics().density);
            mTxtSubTitle.setLetterSpacing(.02f * getResources().getDisplayMetrics().density);
        }
    }

    private void ActionBarConfigs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mActivity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(mActivity.getResources().getColor(R.color.baseColor));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window w = mActivity.getWindow();
                w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                int statusBarHeight = Helper.getStatusBarHeight(mContext);
                View view = new View(mContext);
                view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                view.getLayoutParams().height = statusBarHeight;
                ((ViewGroup) w.getDecorView()).addView(view);
                view.setBackgroundColor(mContext.getResources().getColor(R.color.baseColor));
            }
        }
    }

    void progCreatedLayout() {//
//        ImageView imageView = new ImageView(mContext);
//        imageView.setImageResource(R.mipmap.flower);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins(0, Helper.pxToDp(mContext.getResources(), 600f), 0, 0);
//        imageView.setLayoutParams(layoutParams);
//        mainLayout.addView(imageView);
//
//        BoldTextView Title = new BoldTextView(mContext);
//        Title.setText(getString(R.string.app_name_txt));
//        Title.setTextSize(TypedValue.COMPLEX_UNIT_PX, 100);
//        Title.setPadding(Helper.pxToDp(mContext.getResources(), 53f), Helper.pxToDp(mContext.getResources(), 80f), Helper.pxToDp(mContext.getResources(), 53f), 0);
//        Title.setGravity(Gravity.CENTER);
//        Title.setTextColor(getResources().getColor(R.color.white));
//        Title.setLetterSpacing(.3f);
//
//        LinearLayout.LayoutParams titlePrams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        Title.setLayoutParams(titlePrams);
//        mainLayout.addView(Title);
//
//        UltraThinTextView SunTitle = new UltraThinTextView(mContext);
//        SunTitle.setText(getString(R.string.splash_sub_title));
//        SunTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, 65);
//        SunTitle.setPadding(Helper.dpToPx(mContext.getResources(), 20), Helper.pxToDp(mContext.getResources(), 120), Helper.dpToPx(mContext.getResources(), 20), 0);
//        SunTitle.setGravity(Gravity.CENTER | Gravity.LEFT);
//        SunTitle.setTextColor(getResources().getColor(R.color.white));
//        SunTitle.setLetterSpacing(.1f);
//        SunTitle.setLineSpacing(0, 1f);
//
//        LinearLayout.LayoutParams SubTitlePrams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        Title.setLayoutParams(SubTitlePrams);
//        mainLayout.addView(SunTitle);}
    }
}
