package com.ilsa.grassis.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.CustomEditText;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Dailogs;

/**
 * Created by Ilsa on 1/3/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private RegularTextView mTxtGetStarted, mTxtCreateAccount, mTxtSkipNow;
    private CustomEditText metUserName, metPassword;

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
                Dailogs.ShowToast(mContext, "Home service is not ready yet?", Constants.LONG_DAILOG);
                break;
            case R.id.login_txt_get_started:
                if (!metUserName.getText().toString().equalsIgnoreCase("")) { // any length validation on user or passwords add them below
                    if (!metPassword.getText().toString().equalsIgnoreCase("")) {
                        Dailogs.ShowToast(mContext, "Service is not ready yet?", Constants.LONG_DAILOG);
                    } else {
                        Dailogs.ShowToast(mContext, getString(R.string.invalid_password), Constants.SHORT_DAILOG);
                    }
                } else {
                    Dailogs.ShowToast(mContext, getString(R.string.invalid_username), Constants.SHORT_DAILOG);
                }
                break;
        }
    }
}
