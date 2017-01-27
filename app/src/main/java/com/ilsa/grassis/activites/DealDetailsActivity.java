package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Helper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Menu activity contains list of items.
 */
public class DealDetailsActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_details);
        ButterKnife.bind(this);

        mContext = this;
        mActivity = this;
        initToolBar();
        InitComponents();
    }

    /**
     * Init toolbar.
     */
    public void initToolBar() {
        toolbar.setTitle("");
        mtxtToolbarTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 6));
        mtxtToolbarSubTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 10));
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

        mtxtDetails.setTextSize(Helper.getFontSize(mContext.getResources(), 5.3));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dispensory, menu);
        return true;
    }
}