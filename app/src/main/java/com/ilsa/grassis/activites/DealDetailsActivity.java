package com.ilsa.grassis.activites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilsa.grassis.R;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.rootvo.UserDataVO;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.ShPrefsHelper;

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
public class DealDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Activity mActivity;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ScrollView mScrollView;

    @BindView(R.id.deals_details_toolbar_title)
    MediumTextView mtxtToolbarTitle;

    @BindView(R.id.dispensary_info_toolbar_subtitle)
    BoldSFTextView mtxtToolbarSubTitle;

    @BindView(R.id.deal_detail_txt_details)
    RegularTextView mtxtDetails;

    @BindView(R.id.deal_details_img)
    ImageView mTopBanner;
    @BindView(R.id.dealDetailProgress)
    ProgressBar progressBar;
    @BindView(R.id.regularTextView2)
    RegularTextView redeem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_details);
        ButterKnife.bind(this);

        mContext = this;
        mActivity = this;
        initToolBar();
        InitComponents();
        addListeners();

    }

    /**
     * Init toolbar.
     */
    public void initToolBar() {
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
        mtxtToolbarTitle.setText(getIntent().getStringExtra("TITLE"));
        mtxtToolbarSubTitle.setText(getIntent().getStringExtra("DEAL_TYPE"));
        progressBar.setVisibility(View.VISIBLE);

        Glide.with(mContext).load(getIntent().getStringExtra("PATH")).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        }).into(mTopBanner);
        mtxtDetails.setText(getIntent().getStringExtra("DESCRIPTION"));
    }

    private void addListeners()
    {
        redeem.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dispensory, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.regularTextView2:
                RedeemWebService();
                break;
        }
    }

    private void RedeemWebService() {

        final ProgressDialog pd = new ProgressDialog(DealDetailsActivity.this);
        pd.setMessage(getString(R.string.Verifying_msg));
        pd.setCancelable(false);
        pd.show();

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{  \n  \"user\": {\n    \"email\": \"" + "zeeali@gmail.com" + "\",\n    \"password\": \"" + "bonjour0156" + "\"\n  }\n}");
        Request request = new Request.Builder()
                .url("http://kushmarketing.herokuapp.com/point_reward_products/1/redeem")
                .post(body)
                .addHeader("client_id", "{{uid}}")
                .addHeader("client_secret", "{{secret}}")
                .addHeader("grant_type", "public")
                .addHeader("accept", "application/vnd.kush_marketing.com; version=1")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "9a6d253c-a917-c4f1-02de-597c3297af40")
                .build();


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