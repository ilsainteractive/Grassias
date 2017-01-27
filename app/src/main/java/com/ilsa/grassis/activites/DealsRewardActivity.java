package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
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
import android.view.View;

import com.ilsa.grassis.R;
import com.ilsa.grassis.fragments.DealsFrag;
import com.ilsa.grassis.fragments.DiscoverMapListFrag;
import com.ilsa.grassis.fragments.RewardFrag;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.RegularTextView;

import butterknife.ButterKnife;

/**
 * Discover activity.
 */
public class DealsRewardActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    public ViewPager mViewPager;
    private Context mContext;
    private Toolbar mToolbar;
    private Activity mActivity;
    /**
     * The constant IsMapView.
     */
    public static boolean IsMapView = true;
    public static BoldSFTextView mtxtDeals;
    public static RegularTextView mtxtRewads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_rewards);
        ButterKnife.bind(this);
        mContext = this;
        mActivity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mtxtDeals = (BoldSFTextView) findViewById(R.id.toolbar_deals);
        mtxtRewads = (RegularTextView) findViewById(R.id.toolbar_rewards);

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


        mtxtDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0, true);
                Typeface bold = Typeface.createFromAsset(mContext.getAssets(), "fonts/SFUIText-Heavy.otf");
                Typeface regular = Typeface.createFromAsset(mContext.getAssets(), "fonts/SFUIText-Regular.otf");
                mtxtRewads.setTypeface(regular);
                mtxtDeals.setTypeface(bold);
            }
        });
        mtxtRewads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1, true);
                Typeface bold = Typeface.createFromAsset(mContext.getAssets(), "fonts/SFUIText-Heavy.otf");
                Typeface regular = Typeface.createFromAsset(mContext.getAssets(), "fonts/SFUIText-Regular.otf");
                mtxtRewads.setTypeface(bold);
                mtxtDeals.setTypeface(regular);
            }
        });
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
                return DealsFrag.newInstance(position + 1);
            }
            if (position == 1) {
                return RewardFrag.newInstance(position + 1);
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
                    return "DEALS";
                case 1:
                    return "REWARDS";
            }
            return null;
        }
    }
}
