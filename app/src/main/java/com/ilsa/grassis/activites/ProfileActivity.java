package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.RoundedImageView;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_member_layout:
                startActivity(new Intent(mContext, MemberStatusActivity.class));
                break;
        }
    }
}