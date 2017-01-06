package com.ilsa.grassis.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.BoldTextView;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.CustomEditText;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;

/**
 * Created by Ilsa on 1/3/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private RegularTextView mTxtGetStarted, mTxtCreateAccount, mTxtSkipNow;
    private CustomEditText metUserName, metPassword;
    private BoldTextView mTxtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        InitComponents();
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
                Dailogs.ShowToast(mContext, "Home service is not ready yet?", Constants.LONG_TIME);
                break;
            case R.id.login_txt_get_started:
                if (!metUserName.getText().toString().equalsIgnoreCase("")) { // any length validation on user or passwords add them below
                    if (!metPassword.getText().toString().equalsIgnoreCase("")) {
                        Dailogs.ShowToast(mContext, "Service is not ready yet?", Constants.LONG_TIME);
                    } else {
                        Dailogs.ShowToast(mContext, getString(R.string.invalid_password), Constants.SHORT_TIME);
                    }
                } else {
                    Dailogs.ShowToast(mContext, getString(R.string.invalid_username), Constants.SHORT_TIME);
                }
                break;
        }
    }
}
