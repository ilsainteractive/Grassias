package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.library.SFUITextBold;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Menu activity contains list of items.
 */
public class MemberStatusActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Activity mActivity;
    private Toolbar toolbar;
    private ScrollView mScrollView;
    private MediumTextView mtxtToolbarTitle;

    private boolean isScrolled = false;

    @BindView(R.id.member_txt_green_status)
    SFUITextBold mtxtGreen;
    @BindView(R.id.member_txt_pink_status)
    SFUITextBold mtxtPink;
    @BindView(R.id.member_txt_yellow_status)
    SFUITextBold mtxtYellow;

    @BindView(R.id.member_txt_green_status_desc)
    RegularTextView mtxtGreenDesc;
    @BindView(R.id.member_txt_green_status_get)
    RegularTextView mtxtGreenGet;
    @BindView(R.id.member_txt_green_status_get_details)
    RegularTextView mtxtGreenGetDetails;

    @BindView(R.id.member_txt_pink_status_desc)
    RegularTextView mtxtPinkDesc;
    @BindView(R.id.member_txt_pink_status_get)
    RegularTextView mtxtPinkGet;
    @BindView(R.id.member_txt_pink_status_get_details)
    RegularTextView mtxtPinkGetDetails;

    @BindView(R.id.member_txt_yellow_status_desc)
    RegularTextView mtxtYellowDesc;
    @BindView(R.id.member_txt_yellow_status_get)
    RegularTextView mtxtYellowGet;
    @BindView(R.id.member_txt_yellow_status_get_details)
    RegularTextView mtxtYellowGetDetails;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_status);
        ButterKnife.bind(this);

        mContext = this;
        mActivity = this;
        isScrolled = false;
        initToolBar();
        InitComponents();
        AddListener();
    }

    /**
     * Init toolbar.
     */
    public void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mtxtToolbarTitle = (MediumTextView) toolbar.findViewById(R.id.member_profile_status_toolbar_title);
        toolbar.setTitle("");
        mtxtToolbarTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 6));
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

        mtxtGreen.setTextSize(Helper.getFontSize(mContext.getResources(), 6));
        mtxtPink.setTextSize(Helper.getFontSize(mContext.getResources(), 6));
        mtxtYellow.setTextSize(Helper.getFontSize(mContext.getResources(), 6));

        mtxtGreenDesc.setTextSize(Helper.getFontSize(mContext.getResources(), 4.3));
        mtxtPinkDesc.setTextSize(Helper.getFontSize(mContext.getResources(), 4.3));
        mtxtYellowDesc.setTextSize(Helper.getFontSize(mContext.getResources(), 4.3));

        mtxtGreenGet.setTextSize(Helper.getFontSize(mContext.getResources(), 5));
        mtxtPinkGet.setTextSize(Helper.getFontSize(mContext.getResources(), 5));
        mtxtYellowGet.setTextSize(Helper.getFontSize(mContext.getResources(), 5));

        mtxtGreenGetDetails.setTextSize(Helper.getFontSize(mContext.getResources(), 4.3));
        mtxtPinkGetDetails.setTextSize(Helper.getFontSize(mContext.getResources(), 4.3));
        mtxtYellowGetDetails.setTextSize(Helper.getFontSize(mContext.getResources(), 4.3));
    }

    private void AddListener() {
        mDiscover.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mDeals.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mQr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_btn_dispensory:
                startActivity(new Intent(mContext, DiscoverActivity.class));
                break;
            case R.id.home_btn_profile:
                startActivity(new Intent(mContext, ProfileActivity.class));
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
}