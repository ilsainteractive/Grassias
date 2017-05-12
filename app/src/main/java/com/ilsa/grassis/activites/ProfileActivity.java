package com.ilsa.grassis.activites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilsa.grassis.R;
import com.ilsa.grassis.apivo.UserVo;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.library.RoundedImageView;
import com.ilsa.grassis.rootvo.UserDataVO;
import com.ilsa.grassis.utils.Dailogs;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Menu activity contains list of items.
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Activity mActivity;

    private ScrollView mScrollView;
    private boolean isScrolled = false;

    @BindView(R.id.profile_txt_name)
    BoldSFTextView mtxtName;
    @BindView(R.id.profile_image)
    RoundedImageView imgName;
    @BindView(R.id.profile_txt_member_status)
    MediumTextView mtxtMemberStatus;
    @BindView(R.id.profile_txt_history)
    MediumTextView mtxtHistory;
    @BindView(R.id.profile_txt_favorite)
    MediumTextView mtxtFavorite;
    @BindView(R.id.profile_txt_trem_condition)
    MediumTextView mtxtTermsCondition;
    @BindView(R.id.profile_txt_policy)
    MediumTextView mtxtPolicy;
    @BindView(R.id.profile_member_layout)
    LinearLayout mLayoutMember;

    //
    @BindView(R.id.home_btn_dispensory)
    ImageView mDiscover;

    @BindView(R.id.home_btn_profile)
    ImageView mProfile;

    @BindView(R.id.home_btn_deals)
    ImageView mDeals;

    @BindView(R.id.home_btn_home)
    ImageView mHome;

    @BindView(R.id.home_btn_qr)
    ImageView mQr;

    @BindView(R.id.logOut)
    RegularTextView logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        mContext = this;
        mActivity = this;
        isScrolled = false;
        InitComponents();
        AddListener();
        GetProfileInfo();
    }

    private void GetProfileInfo() {

        final ProgressDialog pd = new ProgressDialog(ProfileActivity.this);
        pd.setMessage(getString(R.string.Verifying_msg));
        pd.setCancelable(false);
        pd.show();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://kushmarketing.herokuapp.com/api/users/me")
                .get()
                .addHeader("accept", "application/vnd.kush_marketing.com; version=1")
                .addHeader("authorization", "Bearer ea71aa91e2d8c7c05254c8aea9211bc4dcbb945f72a187088f86cf4a3a45ef0b")
                .addHeader("x-client-email", "zeeshan@gmail.com")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "448e7c10-320d-0b72-899c-1adc5a8785fc")
                .build();

        //  Response response = client.newCall(request).execute();
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
                    UserVo userVo = gson.fromJson(res, UserVo.class);

                    if (userVo != null) {

                    }
                }
            }
        });
    }

    /**
     * Layout components initializing and bridging here.
     */
    private void InitComponents() {
        // mtxtMemberStatus.setTextSize(Helper.getFontSize(mContext.getResources(), 5.5));
        // mtxtTermsCondition.setTextSize(Helper.getFontSize(mContext.getResources(), 5.5));
    }


    private void AddListener() {
        mLayoutMember.setOnClickListener(this);
        mDiscover.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mDeals.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mQr.setOnClickListener(this);
        logOut.setOnClickListener(this);
        mProfile.setImageResource(R.mipmap.profile_icon1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_member_layout:
                startActivity(new Intent(mContext, MemberStatusActivity.class));
                break;
            case R.id.home_btn_dispensory:
                startActivity(new Intent(mContext, DiscoverActivity.class));
                break;
            case R.id.home_btn_profile:
//                startActivity(new Intent(mContext, ProfileActivity.class));
                break;
            case R.id.home_btn_deals:
                startActivity(new Intent(mContext, DealsRewardActivity.class));
                break;
            case R.id.home_btn_home:
                startActivity(new Intent(mContext, HomeActivity.class));
                break;
            case R.id.home_btn_qr:
                //startActivity(new Intent(mContext, DealsRewardActivity.class));
                Dailogs.ShowToast(mContext, "QR Scan is not integrated.", Constants.SHORT_TIME);
                break;
            case R.id.logOut:
                LogOut();
                break;
        }
    }

    private void LogOut() {

        final ProgressDialog pd = new ProgressDialog(ProfileActivity.this);
        pd.setMessage(getString(R.string.Logging_Out));
        pd.setCancelable(false);
        pd.show();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://kushmarketing.herokuapp.com/api/logout")
                .get()
                .addHeader("accept", "application/vnd.kush_marketing.com; version=1")
                .addHeader("authorization", "Bearer "+ AppContoller.userData.getUser().getAccess_token())
                .addHeader("x-client-email", AppContoller.userData.getUser().getEmail())
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "a9ca61ff-909c-766a-1862-46503cabbe4e")
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

                }
            }
        });
    }

}