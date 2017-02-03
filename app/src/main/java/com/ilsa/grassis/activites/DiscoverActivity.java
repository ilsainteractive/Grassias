package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.fragments.DiscoverDeliveryFrag;
import com.ilsa.grassis.fragments.DiscoverMapListFrag;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Dailogs;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Discover activity.
 */
public class DiscoverActivity extends AppCompatActivity implements View.OnClickListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Context mContext;
    private Toolbar mToolbar;
    private Activity mActivity;
    /**
     * The constant IsMapView.
     */
    public static boolean IsMapView = true;
    BoldSFTextView mtxtStoreFront;
    RegularTextView mtxtDelivery;

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
        setContentView(R.layout.activity_discover);
        ButterKnife.bind(this);

        mContext = this;
        mActivity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mtxtDelivery = (RegularTextView) findViewById(R.id.toolbar_delivery);
        mtxtStoreFront = (BoldSFTextView) findViewById(R.id.toolbar_store_front);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setRippleColor(getResources().getColor(R.color.white));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsMapView) {
                    DiscoverMapListFrag.recyclerView.setVisibility(View.VISIBLE);
                    DiscoverMapListFrag.mapView.setVisibility(View.GONE);
                    fab.setImageResource(R.mipmap.fab_map);
                    IsMapView = false;
                } else {
                    DiscoverMapListFrag.recyclerView.setVisibility(View.GONE);
                    DiscoverMapListFrag.mapView.setVisibility(View.VISIBLE);
                    fab.setImageResource(R.mipmap.fab_list);
                    IsMapView = true;
                }
            }
        });


        mtxtStoreFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0, true);
                Typeface bold = Typeface.createFromAsset(mContext.getAssets(), "fonts/SFUIText-Heavy.otf");
                Typeface regular = Typeface.createFromAsset(mContext.getAssets(), "fonts/SFUIText-Regular.otf");
                mtxtDelivery.setTypeface(regular);
                mtxtStoreFront.setTypeface(bold);
            }
        });
        mtxtDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1, true);
                Typeface bold = Typeface.createFromAsset(mContext.getAssets(), "fonts/SFUIText-Heavy.otf");
                Typeface regular = Typeface.createFromAsset(mContext.getAssets(), "fonts/SFUIText-Regular.otf");
                mtxtDelivery.setTypeface(bold);
                mtxtStoreFront.setTypeface(regular);
            }
        });
        AddListener();
    }

    private void AddListener() {
        mDiscover.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mDeals.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mQr.setOnClickListener(this);
        mDiscover.setImageResource(R.mipmap.dispensary1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_btn_dispensory:
                //startActivity(new Intent(mContext, DiscoverActivity.class));
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
        getMenuInflater().inflate(R.menu.menu_discover, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        /**
         * Instantiates a new Sections pager adapter.
         *
         * @param fm the fm
         */
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                return DiscoverMapListFrag.newInstance(position + 1);
            }
            if (position == 1) {
                return DiscoverDeliveryFrag.newInstance(position + 1);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "STOREFRONT";
                case 1:
                    return "DELIVERY";
            }
            return null;
        }
    }
}
