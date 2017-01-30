package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.RoundedImageView;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    }

    /**
     * Layout components initializing and bridging here.
     */
    private void InitComponents() {
//        LinearLayout.LayoutParams paramsTexts = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                Math.round(Helper.getFontSize(mContext.getResources(), 190)));
//        mTopBanner.setLayoutParams(paramsTexts);
        mtxtName.setTextSize(Helper.getFontSize(mContext.getResources(), 10));
        mtxtMemberStatus.setTextSize(Helper.getFontSize(mContext.getResources(), 5.5));
        mtxtHistory.setTextSize(Helper.getFontSize(mContext.getResources(), 5.5));
        mtxtFavorite.setTextSize(Helper.getFontSize(mContext.getResources(), 5.5));
        mtxtTermsCondition.setTextSize(Helper.getFontSize(mContext.getResources(), 5.5));
        mtxtPolicy.setTextSize(Helper.getFontSize(mContext.getResources(), 5.5));
    }


    private void AddListener() {
        mLayoutMember.setOnClickListener(this);
        mDiscover.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mDeals.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mQr.setOnClickListener(this);
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
        }
    }
}