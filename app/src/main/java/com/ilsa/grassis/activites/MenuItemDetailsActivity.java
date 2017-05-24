package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ilsa.grassis.R;
import com.ilsa.grassis.apivo.Products;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.library.SFUITextBold;
import com.ilsa.grassis.library.ThinTextView;

/**
 * Menu item details activity contain detail about single entity selected from menu list .
 */
public class MenuItemDetailsActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;

    private Toolbar toolbar;
    private ScrollView mScrollView;
    private LinearLayout mImageLayout, mTitleLayout, mPrecentLayout, mValueBarLayout, mPricingLayout;

    private MediumTextView mtxtToolbarTitle;
    private SFUITextBold mtxtTtile;
    private ThinTextView mTxtSubTitle;
    private MediumTextView mtxtTHC, mtxtCBD, mtxtCBN;

    private RegularTextView mtxtIngUnit1, mtxtIngUnit2, mtxtIngUnit3, mtxtIngUnit4, mtxtIngUnit5, mtxtDesc;
    private RegularTextView mtxtIngValue1, mtxtIngValue2, mtxtIngValue3, mtxtIngValue4, mtxtIngValue5;

    private boolean isScrolled = false;
    private Products product;
    private ImageView mProductImage;
    private ProgressBar progress;
    private HorizontalScrollView horizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_details_item);

        mContext = this;
        mActivity = this;
        isScrolled = false;
        initToolBar();
        if (getIntent().getStringExtra("product_id") != null)
            syncData(getIntent().getStringExtra("product_id"));
        InitComponents();
        SetValues();
    }

    private void SetValues() {
        progress.setVisibility(View.VISIBLE);
        Glide.with(mContext).load(getIntent().getStringExtra("dispensary_photo"))
                .thumbnail(0.5f)
                .crossFade().placeholder(R.mipmap.no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        mProductImage.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(mProductImage);
        mtxtToolbarTitle.setText(product.getName());
        mtxtTtile.setText(product.getName());
        mTxtSubTitle.setText(getIntent().getStringExtra("category_title"));
        if (product.getThc() != null)
            mtxtTHC.setText("THC: " + product.getThc());
        else
            mtxtTHC.setText("THC: ---%");
        if (product.getCbd() != null)
            mtxtCBD.setText("CBD: " + product.getCbd());
        else
            mtxtCBD.setText("CBD: ---%");
        if (product.getCbn() != null)
            mtxtCBN.setText("CBN: " + product.getCbn());
        else
            mtxtCBN.setText("CBN: ---%");
        mtxtDesc.setText(product.getDescription());
        AddPricingScroll(product);
    }

    private void AddPricingScroll(Products product) {

        LayoutInflater linf = (LayoutInflater) getApplicationContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < product.getPricing().size(); i++) {

            View layout = linf.inflate(R.layout.lv_item_pricing, null, false);
            RegularTextView unit = (RegularTextView) layout.findViewById(R.id.menu_item_details_txt_ingredeants_unit4);
            RegularTextView unit_values = (RegularTextView) layout.findViewById(R.id.menu_item_details_txt_ingredeants_unit4_value);

            unit.setText(product.getPricing().get(i).getAmount());
            unit_values.setText(product.getPricing().get(i).getValue_cents());
            mPricingLayout.addView(layout);
        }
    }

    private void syncData(String product_id) {

        for (Products product : AppContoller.nearByVo.getProducts()) {
            if (product_id.equalsIgnoreCase(product.getId())) {
                this.product = product;
                break;
            }
        }
    }

    /**
     * Init toolbar.
     */
    public void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mtxtToolbarTitle = (MediumTextView) toolbar.findViewById(R.id.toolbar_title);
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

        mPricingLayout = (LinearLayout) findViewById(R.id.my_prining);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.hsv);
        progress = (ProgressBar) findViewById(R.id.progress_detail);
        mProductImage = (ImageView) findViewById(R.id.menu_item_details_img);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mImageLayout = (LinearLayout) findViewById(R.id.menu_item_details_img_layout);
        mTitleLayout = (LinearLayout) findViewById(R.id.menu_item_details_title_bar_layout);
        mPrecentLayout = (LinearLayout) findViewById(R.id.menu_item_details_precent_bar_layout);
        mValueBarLayout = (LinearLayout) findViewById(R.id.menu_item_details_values_bar_layout);

        mtxtTtile = (SFUITextBold) findViewById(R.id.menu_item_details_txt_name);
        mTxtSubTitle = (ThinTextView) findViewById(R.id.menu_item_details_txt_sub_name);

        mtxtTHC = (MediumTextView) findViewById(R.id.menu_item_details_txt_thc);
        mtxtCBD = (MediumTextView) findViewById(R.id.menu_item_details_txt_cbd);
        mtxtCBN = (MediumTextView) findViewById(R.id.menu_item_details_txt_cbn);

        mtxtDesc = (RegularTextView) findViewById(R.id.menu_item_details_txt_desc);

        mtxtIngUnit1 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit1);
        mtxtIngUnit2 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit2);
        mtxtIngUnit3 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit3);
        mtxtIngUnit4 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit4);
        mtxtIngUnit5 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit5);

//        mtxtIngUnit1.setTextSize(Helper.getFontSize(getResources(), 3.5));
//        mtxtIngUnit2.setTextSize(Helper.getFontSize(getResources(), 3.5));
//        mtxtIngUnit3.setTextSize(Helper.getFontSize(getResources(), 3.5));
//        mtxtIngUnit4.setTextSize(Helper.getFontSize(getResources(), 3.5));
//        mtxtIngUnit5.setTextSize(Helper.getFontSize(getResources(), 3.5));

        mtxtIngValue1 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit1_value);
        mtxtIngValue2 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit2_value);
        mtxtIngValue3 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit3_value);
        mtxtIngValue4 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit4_value);
        mtxtIngValue5 = (RegularTextView) findViewById(R.id.menu_item_details_txt_ingredeants_unit5_value);

//        mtxtIngValue1.setTextSize(Helper.getFontSize(getResources(), 4.5));
//        mtxtIngValue1.setTypeface(mtxtIngValue1.getTypeface(), Typeface.BOLD);
//        mtxtIngValue2.setTextSize(Helper.getFontSize(getResources(), 4.5));
//        mtxtIngValue2.setTypeface(mtxtIngValue2.getTypeface(), Typeface.BOLD);
//        mtxtIngValue3.setTextSize(Helper.getFontSize(getResources(), 4.5));
//        mtxtIngValue3.setTypeface(mtxtIngValue3.getTypeface(), Typeface.BOLD);
//        mtxtIngValue4.setTextSize(Helper.getFontSize(getResources(), 4.5));
//        mtxtIngValue4.setTypeface(mtxtIngValue4.getTypeface(), Typeface.BOLD);
//        mtxtIngValue5.setTextSize(Helper.getFontSize(getResources(), 4.5));
//        mtxtIngValue5.setTypeface(mtxtIngValue5.getTypeface(), Typeface.BOLD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isScrolled) {
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.fullScroll(View.FOCUS_UP);
                    isScrolled = true;
                }
            });
        }
    }

}