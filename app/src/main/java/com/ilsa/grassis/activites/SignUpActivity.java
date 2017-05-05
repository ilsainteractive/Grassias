package com.ilsa.grassis.activites;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.CustomEditText;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;
import com.ilsa.grassis.vo.SignUpVO;

/**
 * Sign up activity.
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher,
        View.OnFocusChangeListener {

    private Context mContext;
    private Activity mActivity;
    private Toolbar toolbar;

    private RegularTextView mtxtNext;
    private CustomEditText mtxtEmail, metFirstName, metLastName, metUserName, metPhoneNo, metPassword;
    private ImageView mImgFirstName, mImgLastName, mImgUserName, mImgPhoneNo, mImgPassword;
    private LinearLayout mTopLayout, mFirstNameLayout, mLastNameLayout,
            mEmailLayout, mPhoneNoLayout, mPasswordLayout, mNextLayout;

    private int whoHasFocus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mContext = this;
        mActivity = this;
        initToolBar();
        InitComponents();
        AddListener();
    }

    /**
     * Init toolbar.
     */
    public void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.signup_back_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Layout components initializing and bridging here.
     */
    private void InitComponents() {

//        mTopLayout = (LinearLayout) findViewById(R.id.signup_top_layout);
//        LinearLayout.LayoutParams paramsTopLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 100)));
//        mTopLayout.setLayoutParams(paramsTopLayout);
//
//        mFirstNameLayout = (LinearLayout) findViewById(R.id.signup_first_name_layout);
//        LinearLayout.LayoutParams paramsFirstName = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 80)));
//        mFirstNameLayout.setLayoutParams(paramsFirstName);
//
//        mLastNameLayout = (LinearLayout) findViewById(R.id.signup_last_name_layout);
//        LinearLayout.LayoutParams paramslastName = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 80)));
//        mLastNameLayout.setLayoutParams(paramslastName);
//
//        mEmailLayout = (LinearLayout) findViewById(R.id.signup_email_layout);
//        LinearLayout.LayoutParams paramsEmail = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 90)));
//        mEmailLayout.setLayoutParams(paramsEmail);
//
//        mPhoneNoLayout = (LinearLayout) findViewById(R.id.signup_phone_no_layout);
//        LinearLayout.LayoutParams paramsPhoneNo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 80)));
//        mPhoneNoLayout.setLayoutParams(paramsPhoneNo);
//
//        mPasswordLayout = (LinearLayout) findViewById(R.id.signup_password_layout);
//        LinearLayout.LayoutParams paramsPassword = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 80)));
//        mPasswordLayout.setLayoutParams(paramsPassword);
//
//        mNextLayout = (LinearLayout) findViewById(R.id.signup_next_layout);
//        LinearLayout.LayoutParams paramsNext = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 150)));
//        mNextLayout.setLayoutParams(paramsNext);

        metFirstName = (CustomEditText) findViewById(R.id.signup_et_first_name);
        metLastName = (CustomEditText) findViewById(R.id.signup_et_last_name);
        metUserName = (CustomEditText) findViewById(R.id.signup_et_user_name);
        metPhoneNo = (CustomEditText) findViewById(R.id.signup_et_phone_no);
        metPassword = (CustomEditText) findViewById(R.id.signup_et_password);

        mtxtEmail = (CustomEditText) findViewById(R.id.signup_txt_email);
        mtxtNext = (RegularTextView) findViewById(R.id.signup_txt_next);

        mImgFirstName = (ImageView) findViewById(R.id.signup_img_first_name);
        mImgLastName = (ImageView) findViewById(R.id.signup_img_last_name);
        mImgUserName = (ImageView) findViewById(R.id.signup_img_user_name);
        mImgPhoneNo = (ImageView) findViewById(R.id.signup_img_phone_question);
        mImgPassword = (ImageView) findViewById(R.id.signup_img_password);
    }

    /**
     * Applying listeners to views.
     */
    private void AddListener() {
        mImgPhoneNo.setOnClickListener(this);
        mtxtNext.setOnClickListener(this);

        metFirstName.addTextChangedListener(this);
        metLastName.addTextChangedListener(this);
        metUserName.addTextChangedListener(this);
        metPhoneNo.addTextChangedListener(this);
        metPassword.addTextChangedListener(this);

        metFirstName.setOnFocusChangeListener(this);
        metLastName.setOnFocusChangeListener(this);
        metUserName.setOnFocusChangeListener(this);
        metPhoneNo.setOnFocusChangeListener(this);
        metPassword.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.signup_et_first_name:
                whoHasFocus = Constants.SIGNUP_FIRST_NAME;
                break;
            case R.id.signup_et_last_name:
                whoHasFocus = Constants.SIGNUP_LAST_NAME;
                break;
            case R.id.signup_et_user_name:
                whoHasFocus = Constants.SIGNUP_USER_NAME;
                break;
            case R.id.signup_et_phone_no:
                whoHasFocus = Constants.SIGNUP_PHONE_VALIDATION;
                break;
            case R.id.signup_et_password:
                whoHasFocus = Constants.SIGNUP_PASSWORD_VALIDATION;
                break;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        switch (whoHasFocus) {
            case Constants.SIGNUP_FIRST_NAME:
                if (IsFieldValid(s, whoHasFocus)) {
                    mImgFirstName.setVisibility(View.VISIBLE);
                } else {
                    mImgFirstName.setVisibility(View.GONE);
                }
                break;
            case Constants.SIGNUP_LAST_NAME:
                if (IsFieldValid(s, whoHasFocus)) {
                    mImgLastName.setVisibility(View.VISIBLE);
                } else {
                    mImgLastName.setVisibility(View.GONE);
                }
                break;
            case Constants.SIGNUP_USER_NAME:
                if (IsFieldValid(s, whoHasFocus)) {
                    mImgUserName.setVisibility(View.VISIBLE);
                } else {
                    mImgUserName.setVisibility(View.GONE);
                }
                break;
            case Constants.SIGNUP_PHONE_VALIDATION:
                if (IsFieldValid(s, whoHasFocus)) {
                    mImgPhoneNo.setImageResource(R.mipmap.signup_tick);
                } else {
                    mImgPhoneNo.setImageResource(R.mipmap.signup_phone_question_mark);
                }
                break;
            case Constants.SIGNUP_PASSWORD_VALIDATION:
                if (IsFieldValid(s, whoHasFocus)) {
                    mImgPassword.setVisibility(View.VISIBLE);
                } else {
                    mImgPassword.setVisibility(View.GONE);
                }
                break;
        }
    }

    private boolean IsFieldValid(Editable editable, int whoHasFocus) {
        switch (whoHasFocus) {
            case Constants.SIGNUP_FIRST_NAME:
                if (!editable.toString().equalsIgnoreCase("")) {
                    if (editable.length() > Constants.FIELD_VALIDATION_LENGTH_MIN && editable.length() < Constants.FIELD_VALIDATION_LENGTH_MAX)
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
            case Constants.SIGNUP_LAST_NAME:
                if (!editable.toString().equalsIgnoreCase("")) {
                    if (editable.length() > Constants.FIELD_VALIDATION_LENGTH_MIN && editable.length() < Constants.FIELD_VALIDATION_LENGTH_MAX)
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
            case Constants.SIGNUP_USER_NAME:
                if (!editable.toString().equalsIgnoreCase("")) {
                    if (editable.length() > Constants.FIELD_PASSWORD_VALIDATION_LENGTH_MIN && editable.length() < Constants.FIELD_PASSWORD_VALIDATION_LENGTH_MAX)
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
            case Constants.SIGNUP_PHONE_VALIDATION:
                if (!editable.toString().equalsIgnoreCase("")) {
                    if (editable.length() > Constants.FIELD_PHONE_VALIDATION_LENGTH_MIN && editable.length() < Constants.FIELD_PHONE_VALIDATION_LENGTH_MAX)
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
            case Constants.SIGNUP_PASSWORD_VALIDATION:
                if (!editable.toString().equalsIgnoreCase("")) {
                    if (editable.length() > Constants.FIELD_PASSWORD_VALIDATION_LENGTH_MIN && editable.length() < Constants.FIELD_PASSWORD_VALIDATION_LENGTH_MAX)
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_img_phone_question:
                if (IsFieldValid(metPhoneNo.getEditableText(), Constants.SIGNUP_PHONE_VALIDATION)) {
                } else {
                    Dialog mBottomSheetDialog = Dailogs.CreateBottomSheet(mContext, R.style.MaterialDialogSheet, R.layout.dailog_signup_phone, true);
                    RegularTextView textView = (RegularTextView) mBottomSheetDialog.findViewById(R.id.msg);
                    textView.setTextSize(Helper.getFontSize(mContext.getResources(), 5.7));
                    mBottomSheetDialog.show();
                }
                break;
            case R.id.signup_txt_next:
                if (IsFieldValid(metFirstName.getEditableText(), Constants.SIGNUP_FIRST_NAME)) {
                    if (IsFieldValid(metLastName.getEditableText(), Constants.SIGNUP_LAST_NAME)) {
                        if (IsFieldValid(metUserName.getEditableText(), Constants.SIGNUP_USER_NAME)) {
                            if (IsFieldValid(metPhoneNo.getEditableText(), Constants.SIGNUP_PHONE_VALIDATION)) {
                                if (IsFieldValid(metPassword.getEditableText(), Constants.SIGNUP_PASSWORD_VALIDATION)) {
                                    SigingUpOnServer(mContext, "users", metFirstName.getText().toString(),
                                            metLastName.getText().toString(),metUserName.getText().toString(), mtxtEmail.getText().toString(), metPhoneNo.getText().toString(),
                                            metPassword.getText().toString());
                                } else {
                                    Dailogs.ShowToast(mContext, getString(R.string.invalid_password), Constants.SHORT_TIME);
                                }
                            } else {
                                Dailogs.ShowToast(mContext, getString(R.string.invalid_phone_no), Constants.SHORT_TIME);
                            }
                        } else {
                            Dailogs.ShowToast(mContext, getString(R.string.invalid_user_name), Constants.SHORT_TIME);
                        }
                    } else {
                        Dailogs.ShowToast(mContext, getString(R.string.invalid_last_name), Constants.SHORT_TIME);
                    }
                } else {
                    Dailogs.ShowToast(mContext, getString(R.string.invalid_first_name), Constants.SHORT_TIME);
                }
                break;
        }
    }

    public void SigingUpOnServer(Context context, String service, String firstName, String LastName,String UserName, String email, String phone, String password) {

        SignUpVO signUpVO = new SignUpVO();
        signUpVO.setEmail(email);
        signUpVO.setFirst_name(firstName);
        signUpVO.setLast_name(LastName);
        signUpVO.setPassword(password);
        signUpVO.setUsername(UserName);

        Intent intent = new Intent(context, DispensaryActivity.class);
        intent.putExtra("SignUp_info", signUpVO);
        startActivity(intent);
    }
}
