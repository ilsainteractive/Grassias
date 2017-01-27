package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.BoldTextView;
import com.ilsa.grassis.library.CustomEditText;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Helper;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private RegularTextView mTxtGetStarted, mTxtCreateAccount, mTxtSkipNow;
    private CustomEditText metUserName, metPassword;
    private BoldTextView mTxtTitle;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mActivity = this;
        InitComponents();
        //ActionBarConfigs();
        AddListener();

    }

    private void InitComponents() {
        mTxtCreateAccount = (RegularTextView) findViewById(R.id.login_txt_create_account);
        mTxtSkipNow = (RegularTextView) findViewById(R.id.login_txt_skip_now);
        mTxtGetStarted = (RegularTextView) findViewById(R.id.login_txt_get_started);
        metUserName = (CustomEditText) findViewById(R.id.login_et_user_name);
        metPassword = (CustomEditText) findViewById(R.id.login_et_password);
        mTxtTitle = (BoldTextView) findViewById(R.id.login_txt_title);

        mTxtTitle.setTextSize(Helper.getFontSize(getResources(), 11));
        metUserName.setTextSize(Helper.getFontSize(getResources(), 4));
        metPassword.setTextSize(Helper.getFontSize(getResources(), 4));
        mTxtCreateAccount.setTextSize(Helper.getFontSize(getResources(), 4));
        mTxtSkipNow.setTextSize(Helper.getFontSize(getResources(), 4));
        mTxtGetStarted.setTextSize(Helper.getFontSize(getResources(), 4));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            mTxtTitle.setLetterSpacing(.05f * getResources().getDisplayMetrics().density);
            metUserName.setLetterSpacing(.02f * getResources().getDisplayMetrics().density);
            metPassword.setLetterSpacing(.02f * getResources().getDisplayMetrics().density);
            mTxtCreateAccount.setLetterSpacing(.02f * getResources().getDisplayMetrics().density);
        }
    }

    private void ActionBarConfigs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mActivity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(mActivity.getResources().getColor(R.color.transparent));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window w = mActivity.getWindow();
                w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                int statusBarHeight = Helper.getStatusBarHeight(mContext);
                View view = new View(mContext);
                view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                view.getLayoutParams().height = statusBarHeight;
                ((ViewGroup) w.getDecorView()).addView(view);
                view.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            }
        }
    }

    private void AddListener() {

        mTxtCreateAccount.setOnClickListener(this);
        mTxtSkipNow.setOnClickListener(this);
        mTxtGetStarted.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_txt_create_account:
                startActivity(new Intent(mContext, SignUpActivity.class));
                break;
            case R.id.login_txt_skip_now:
                AppContoller.IsLoggedIn = false;
                startActivity(new Intent(mContext, HomeActivity.class));
                break;
            case R.id.login_txt_get_started:
                startActivity(new Intent(mContext, HomeActivity.class));
//                if (!metUserName.getText().toString().equalsIgnoreCase("")) { // any length validation on user or passwords add them below
//                    if (!metPassword.getText().toString().equalsIgnoreCase("")) {
//                        Dailogs.ShowToast(mContext, "Service is not ready yet?", Constants.LONG_TIME);
//                    } else {
//                        Dailogs.ShowToast(mContext, getString(R.string.invalid_password), Constants.SHORT_TIME);
//                    }
//                } else {
//                    Dailogs.ShowToast(mContext, getString(R.string.invalid_username), Constants.SHORT_TIME);
//                }
                break;
        }
    }
}
