package com.ilsa.grassis.activites;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.CustomEditText;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Dailogs;

/**
 * Created by Ilsa on 1/3/2017.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener {

    private Context mContext;

    private RegularTextView mtxtEmail, mtxtNext;
    private CustomEditText metFirstName, metLastName, metPhoneNo, metPassword;
    private ImageView mImgFirstName, mImgLastName, mImgPhoneNo, mImgPassword;
    private ImageView mimgBackArrow;

    private int whoHasFocus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mContext = this;
        InitComponents();
        AddListener();
    }

    private void InitComponents() {

        metFirstName = (CustomEditText) findViewById(R.id.signup_et_first_name);
        metLastName = (CustomEditText) findViewById(R.id.signup_et_last_name);
        metPhoneNo = (CustomEditText) findViewById(R.id.signup_et_phone_no);
        metPassword = (CustomEditText) findViewById(R.id.signup_et_password);

        mtxtEmail = (RegularTextView) findViewById(R.id.signup_txt_email);
        mtxtNext = (RegularTextView) findViewById(R.id.signup_txt_next);

        mimgBackArrow = (ImageView) findViewById(R.id.signup_img_back_arrow);

        mImgFirstName = (ImageView) findViewById(R.id.signup_img_first_name);
        mImgLastName = (ImageView) findViewById(R.id.signup_img_last_name);
        mImgPhoneNo = (ImageView) findViewById(R.id.signup_img_phone_question);
        mImgPassword = (ImageView) findViewById(R.id.signup_img_password);
    }

    private void AddListener() {
        mImgPhoneNo.setOnClickListener(this);
        mtxtNext.setOnClickListener(this);
        mimgBackArrow.setOnClickListener(this);

        metFirstName.addTextChangedListener(this);
        metLastName.addTextChangedListener(this);
        metPhoneNo.addTextChangedListener(this);
        metPassword.addTextChangedListener(this);

        metFirstName.setOnFocusChangeListener(this);
        metLastName.setOnFocusChangeListener(this);
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
                    Dialog mBottomSheetDialog = Dailogs.CreateBottomSheet(mContext, R.style.MaterialDialogSheet, R.layout.signup_phone_dailog, true);
                    mBottomSheetDialog.show();
                }
                break;
            case R.id.signup_img_back_arrow:
                onBackPressed();
                break;
            case R.id.signup_txt_next:
                if (IsFieldValid(metFirstName.getEditableText(), Constants.SIGNUP_FIRST_NAME)) {
                    if (IsFieldValid(metLastName.getEditableText(), Constants.SIGNUP_LAST_NAME)) {
                        if (IsFieldValid(metPhoneNo.getEditableText(), Constants.SIGNUP_PHONE_VALIDATION)) {
                            if (IsFieldValid(metPassword.getEditableText(), Constants.SIGNUP_PASSWORD_VALIDATION)) {
                                Dailogs.ShowToast(mContext, "Home service is not ready yet?", Constants.LONG_DAILOG);
                                startActivity(new Intent(mContext, DispensaryActivity.class));
                            } else {
                                Dailogs.ShowToast(mContext, getString(R.string.invalid_password), Constants.SHORT_DAILOG);
                            }
                        } else {
                            Dailogs.ShowToast(mContext, getString(R.string.invalid_phone_no), Constants.SHORT_DAILOG);
                        }
                    } else {
                        Dailogs.ShowToast(mContext, getString(R.string.invalid_last_name), Constants.SHORT_DAILOG);
                    }
                } else {
                    Dailogs.ShowToast(mContext, getString(R.string.invalid_first_name), Constants.SHORT_DAILOG);
                }
                break;
        }
    }
}
