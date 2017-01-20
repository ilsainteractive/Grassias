package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.BoldSFUITextView;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.library.ThinTextView;
import com.ilsa.grassis.utils.Helper;

public class MenuItemDetailsActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;

    private Toolbar toolbar;
    private ScrollView mScrollView;
    private LinearLayout mImageLayout, mTitleLayout, mPrecentLayout, mValueBarLayout;

    private MediumTextView mtxtToolbarTitle;
    private BoldSFUITextView mtxtTtile;
    private ThinTextView mTxtSubTitle;
    private RegularTextView mtxtTHC, mtxtCBD, mtxtCBN, mtxtDesc;

    private RegularTextView mtxtIngUnit1, mtxtIngUnit2, mtxtIngUnit3, mtxtIngUnit4, mtxtIngUnit5;
    private RegularTextView mtxtIngValue1, mtxtIngValue2, mtxtIngValue3, mtxtIngValue4, mtxtIngValue5;


    private boolean isScrolled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_details_item);

        mContext = this;
        mActivity = this;
        isScrolled = false;
        initToolBar();
        InitComponents();
    }

    public void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mtxtToolbarTitle = (MediumTextView) toolbar.findViewById(R.id.toolbar_title);
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

    private void InitComponents() {

        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        //        Math.round(Helper.getFontSize(mContext.getResources(), 275)));
        //mViewPager.setLayoutParams(layoutParams);

        mImageLayout = (LinearLayout) findViewById(R.id.menu_item_details_img_layout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                Math.round(Helper.getFontSize(mContext.getResources(), 390)));
        mImageLayout.setLayoutParams(layoutParams);

        mTitleLayout = (LinearLayout) findViewById(R.id.menu_item_details_title_bar_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                Math.round(Helper.getFontSize(mContext.getResources(), 60)));
        params.setMargins(Math.round(Helper.getFontSize(mContext.getResources(), 15)), 0,
                Math.round(Helper.getFontSize(mContext.getResources(), 15)), 0);
        mTitleLayout.setLayoutParams(params);

        mPrecentLayout = (LinearLayout) findViewById(R.id.menu_item_details_precent_bar_layout);
        LinearLayout.LayoutParams paramsPrecent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                Math.round(Helper.getFontSize(mContext.getResources(), 45)));
        paramsPrecent.setMargins(Math.round(Helper.getFontSize(mContext.getResources(), 15)), 0,
                Math.round(Helper.getFontSize(mContext.getResources(), 15)), 0);
        mPrecentLayout.setLayoutParams(paramsPrecent);

        mValueBarLayout = (LinearLayout) findViewById(R.id.menu_item_details_values_bar_layout);
        LinearLayout.LayoutParams paramsValues = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                Math.round(Helper.getFontSize(mContext.getResources(), 70)));
        mValueBarLayout.setLayoutParams(paramsValues);
        mValueBarLayout.setPadding(Math.round(Helper.getFontSize(mContext.getResources(), 15)), 0,
                Math.round(Helper.getFontSize(mContext.getResources(), 15)), 0);

        mtxtTtile = (BoldSFUITextView) findViewById(R.id.menu_item_details_txt_name);
        mtxtTtile.setTextSize(Helper.getFontSize(getResources(), 6));

        mTxtSubTitle = (ThinTextView) findViewById(R.id.menu_item_details_txt_sub_name);

        mtxtTHC = (RegularTextView) findViewById(R.id.menu_item_details_txt_thc);
        mtxtCBD = (RegularTextView) findViewById(R.id.menu_item_details_txt_cbd);
        mtxtCBN = (RegularTextView) findViewById(R.id.menu_item_details_txt_cbn);

        mtxtDesc = (RegularTextView) findViewById(R.id.menu_item_details_txt_desc);
        mtxtDesc.setTextSize(Helper.getFontSize(getResources(), 5));

        mtxtTHC.setTextSize(Helper.getFontSize(getResources(), 6));
        mtxtTHC.setTypeface(mtxtTHC.getTypeface(), Typeface.BOLD);
        mtxtCBD.setTextSize(Helper.getFontSize(getResources(), 6));
        mtxtCBD.setTypeface(mtxtTHC.getTypeface(), Typeface.BOLD);
        mtxtCBN.setTextSize(Helper.getFontSize(getResources(), 6));
        mtxtCBN.setTypeface(mtxtTHC.getTypeface(), Typeface.BOLD);

        mtxtIngUnit1 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit1);
        mtxtIngUnit2 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit2);
        mtxtIngUnit3 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit3);
        mtxtIngUnit4 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit4);
        mtxtIngUnit5 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit5);

        mtxtIngUnit1.setTextSize(Helper.getFontSize(getResources(), 3.5));
        mtxtIngUnit2.setTextSize(Helper.getFontSize(getResources(), 3.5));
        mtxtIngUnit3.setTextSize(Helper.getFontSize(getResources(), 3.5));
        mtxtIngUnit4.setTextSize(Helper.getFontSize(getResources(), 3.5));
        mtxtIngUnit5.setTextSize(Helper.getFontSize(getResources(), 3.5));

        mtxtIngValue1 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit1_value);
        mtxtIngValue2 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit2_value);
        mtxtIngValue3 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit3_value);
        mtxtIngValue4 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit4_value);
        mtxtIngValue5 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit5_value);

        mtxtIngValue1.setTextSize(Helper.getFontSize(getResources(), 4.5));
        mtxtIngValue1.setTypeface(mtxtIngValue1.getTypeface(), Typeface.BOLD);
        mtxtIngValue2.setTextSize(Helper.getFontSize(getResources(), 4.5));
        mtxtIngValue2.setTypeface(mtxtIngValue2.getTypeface(), Typeface.BOLD);
        mtxtIngValue3.setTextSize(Helper.getFontSize(getResources(), 4.5));
        mtxtIngValue3.setTypeface(mtxtIngValue3.getTypeface(), Typeface.BOLD);
        mtxtIngValue4.setTextSize(Helper.getFontSize(getResources(), 4.5));
        mtxtIngValue4.setTypeface(mtxtIngValue4.getTypeface(), Typeface.BOLD);
        mtxtIngValue5.setTextSize(Helper.getFontSize(getResources(), 4.5));
        mtxtIngValue5.setTypeface(mtxtIngValue5.getTypeface(), Typeface.BOLD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isScrolled) {
            mScrollView.postDelayed(new Runnable() {
                public void run() {
                    mScrollView.fullScroll(View.FOCUS_UP);
                    isScrolled = true;
                }
            }, 200);
        }
    }

}