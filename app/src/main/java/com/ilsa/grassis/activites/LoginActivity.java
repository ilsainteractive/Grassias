package com.ilsa.grassis.activites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilsa.grassis.R;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.BoldTextView;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.CustomEditText;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.rootvo.UserDataVO;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private RegularTextView mTxtGetStarted, mTxtCreateAccount, mTxtSkipNow;
    private CustomEditText metUserName, metPassword;
    private BoldTextView mTxtTitle;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;

        if (Helper.IsUserRegistered(mContext)) {
            AppContoller.IsLoggedIn = true;
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            AppContoller.IsLoggedIn = false;

            setContentView(R.layout.activity_login);
            InitComponents();
            //ActionBarConfigs();
            AddListener();
        }
    }


    private void InitComponents() {
        mTxtCreateAccount = (RegularTextView) findViewById(R.id.login_txt_create_account);
        mTxtSkipNow = (RegularTextView) findViewById(R.id.login_txt_skip_now);
        mTxtGetStarted = (RegularTextView) findViewById(R.id.login_txt_get_started);
        metUserName = (CustomEditText) findViewById(R.id.login_et_user_name);
        metPassword = (CustomEditText) findViewById(R.id.login_et_password);
        mTxtTitle = (BoldTextView) findViewById(R.id.login_txt_title);

        //mTxtTitle.setTextSize(Helper.getFontSize(getResources(), 11));
        //metUserName.setTextSize(Helper.getFontSize(getResources(), 4));
        //metPassword.setTextSize(Helper.getFontSize(getResources(), 4));
        //mTxtCreateAccount.setTextSize(Helper.getFontSize(getResources(), 4));
        //mTxtSkipNow.setTextSize(Helper.getFontSize(getResources(), 4));
        //mTxtGetStarted.setTextSize(Helper.getFontSize(getResources(), 4));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//            mTxtTitle.setLetterSpacing(.05f * getResources().getDisplayMetrics().density);
//            metUserName.setLetterSpacing(.02f * getResources().getDisplayMetrics().density);
//            metPassword.setLetterSpacing(.02f * getResources().getDisplayMetrics().density);
//            mTxtCreateAccount.setLetterSpacing(.02f * getResources().getDisplayMetrics().density);
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
                // startActivity(new Intent(mContext, HomeActivity.class));
                if (!metUserName.getText().toString().equalsIgnoreCase("")) { // any length validation on user or passwords add them below
                    if (!metPassword.getText().toString().equalsIgnoreCase("")) {
                        if (Helper.checkInternetConnection(mContext))
                            RequestLogin(metUserName.getText().toString(), metPassword.getText().toString(), mContext);
                        else
                            Dailogs.ShowToast(mContext, getString(R.string.no_internet_msg), Constants.SHORT_TIME);
                    } else {
                        Dailogs.ShowToast(mContext, getString(R.string.invalid_password), Constants.SHORT_TIME);
                    }
                } else {
                    Dailogs.ShowToast(mContext, getString(R.string.invalid_username), Constants.SHORT_TIME);
                }
                break;
        }
    }

    private void RequestLogin(String userName, String password, final Context context) {

        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage(getString(R.string.Verifying_msg));
        pd.setCancelable(false);
        pd.show();

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{  \n  \"user\": {\n    \"email\": \"" + userName + "\",\n    \"password\": \"" + password + "\"\n  }\n}");
        final Request request = new Request.Builder()
                .url("http://kushmarketing.herokuapp.com/api/login")
                .post(body)
                .addHeader("accept", "application/vnd.kush_marketing.com; version=1")
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "250b8468-1abd-d85e-7408-4a80f3f8b37c")
                .build();

        //Response response = client.newCall(request).execute();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                pd.dismiss();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                pd.dismiss();
                final String res = response.body().string().toString();
                Log.i("response", res);
                if (!response.isSuccessful()) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                JSONObject error = jsonObject.getJSONObject("error");
                                String message = error.get("message").toString();
                                Dailogs.ShowToast(mContext, message, Constants.LONG_TIME);
                            } catch (Exception e) {
                            }
                        }
                    });
                } else {
                    Gson gson = new GsonBuilder().create();
                    AppContoller.userData = gson.fromJson(res, UserDataVO.class);

                    if (AppContoller.userData.getUser() != null) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("first_name", AppContoller.userData.getUser().getFirst_name());
                        intent.putExtra("last_name", AppContoller.userData.getUser().getLast_name());
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
