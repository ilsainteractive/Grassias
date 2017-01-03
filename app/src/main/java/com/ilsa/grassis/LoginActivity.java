package com.ilsa.grassis;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Ilsa on 1/3/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private Typeface mTypefaceBold, mTypefaceLight;
    private TextView mTxtTitle, mTxtSubTitle;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mTypefaceBold = Typeface.createFromAsset(getAssets(), "fonts/SFUITextHeavy.otf");
        mTypefaceLight = Typeface.createFromAsset(getAssets(), "fonts/SFUITextLight.otf");
        mTxtTitle = (TextView) findViewById(R.id.login_txt_title);
        mTxtTitle.setTypeface(mTypefaceBold);
    }
}
