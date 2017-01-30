package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.MenuGalleryAdapter;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.library.ThinTextView;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Home activity.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Activity mActivity;
    private Toolbar toolbar;
    private SearchView mSearchView;

    private BoldSFTextView mtxtToolbarTitle;
    private RegularTextView mtxtListTitleTop, mtxtToolbarTitleDump;
    private RegularTextView mtxtListSubTitleTop, mtxtListViewBottomSection2Title, mtxtListViewBottomSection2SubTitle;
    private RegularTextView mtxtListViewBottomSection3Title, mtxtListViewBottomSection3SubTitle;
    private BoldSFTextView mtxtListTitleBottom, mtxtLvBottomSection2Per;
    private ThinTextView mtxtLvBottomSection2Off, mtxtLvBottomSection2Products;
    private ImageView mImgCart, mImgDetail, mImgLvTopSection;

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

    private LinearLayout mListViewTopSection, mListViewTopSectionText, mListViewBottomSectionPager, mListViewBottomSection2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mContext = this;
        mActivity = this;
        initToolBar();
        //ActionBarConfigs();
        InitComponents();
        AddListener();
        initViews();
    }

    /**
     * Init toolbar.
     */
    public void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mtxtToolbarTitle = (BoldSFTextView) toolbar.findViewById(R.id.toolbar_title);
        mtxtToolbarTitleDump = (RegularTextView) toolbar.findViewById(R.id.toolbar_title_dump);
        toolbar.setTitle("");
        mtxtToolbarTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 6));
        mtxtToolbarTitleDump.setTextSize(Helper.getFontSize(mContext.getResources(), 6));
        setSupportActionBar(toolbar);
    }

    /**
     * Layout components initializing and bridging here.
     */
    private void InitComponents() {

        mListViewTopSection = (LinearLayout) findViewById(R.id.home_lv_top_section);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 220)));
        mListViewTopSection.setLayoutParams(params);

        mImgLvTopSection = (ImageView) findViewById(R.id.home_lv_top_section_img);
        RelativeLayout.LayoutParams topImgHieght = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 190)));
        mImgLvTopSection.setLayoutParams(topImgHieght);

        mListViewTopSectionText = (LinearLayout) findViewById(R.id.home_lv_top_section_texts_layout);
        RelativeLayout.LayoutParams paramsTexts = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 190)));
        mListViewTopSectionText.setLayoutParams(paramsTexts);

        mtxtListTitleBottom = (BoldSFTextView) findViewById(R.id.home_lv_bottom_title);
        LinearLayout.LayoutParams paramsBottomTitle = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 100)));
        mtxtListTitleBottom.setLayoutParams(paramsBottomTitle);

        mListViewBottomSectionPager = (LinearLayout) findViewById(R.id.home_lv_bottom_pager);
        LinearLayout.LayoutParams paramsBottomPager = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 360)));
        mListViewBottomSectionPager.setLayoutParams(paramsBottomPager);

        mListViewBottomSection2 = (LinearLayout) findViewById(R.id.home_lv_bottom_section_2);
        LinearLayout.LayoutParams paramsBottomsection2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 120)));
        mListViewBottomSection2.setLayoutParams(paramsBottomsection2);

        mtxtListViewBottomSection2Title = (RegularTextView) findViewById(R.id.home_lv_bottom_section_2_subTitle);
        mtxtListViewBottomSection2Title.setTextSize(Helper.getFontSize(mContext.getResources(), 3.5));

        mtxtListViewBottomSection2SubTitle = (RegularTextView) findViewById(R.id.home_lv_bottom_section_2_title);
        mtxtListViewBottomSection2SubTitle.setTypeface(null, Typeface.BOLD);
        mtxtListViewBottomSection2SubTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 5));

        mtxtLvBottomSection2Per = (BoldSFTextView) findViewById(R.id.home_lv_bottom_section_2_txt_per);
        mtxtLvBottomSection2Per.setTextSize(Helper.getFontSize(mContext.getResources(), 21));

        mtxtLvBottomSection2Off = (ThinTextView) findViewById(R.id.home_lv_bottom_section_2_txt_off);
        mtxtLvBottomSection2Off.setTextSize(Helper.getFontSize(mContext.getResources(), 21));

        mtxtLvBottomSection2Products = (ThinTextView) findViewById(R.id.home_lv_bottom_section_2_txt_products);
        mtxtLvBottomSection2Products.setTextSize(Helper.getFontSize(mContext.getResources(), 10));

        mtxtListViewBottomSection3Title = (RegularTextView) findViewById(R.id.home_lv_bottom_section_3_title);
        mtxtListViewBottomSection3Title.setTextSize(Helper.getFontSize(mContext.getResources(), 5));
        mtxtListViewBottomSection3Title.setTypeface(null, Typeface.BOLD);

        mtxtListViewBottomSection3Title = (RegularTextView) findViewById(R.id.home_lv_bottom_section_3_subTitle);
        mtxtListViewBottomSection3Title.setTextSize(Helper.getFontSize(mContext.getResources(), 3.5));

        //below setting
        mtxtListTitleTop = (RegularTextView) findViewById(R.id.home_list_top_title);
        mtxtListSubTitleTop = (RegularTextView) findViewById(R.id.home_list_top_sub_title);

        mtxtListTitleTop.setTextSize(Helper.getFontSize(mContext.getResources(), 10));
        mtxtListSubTitleTop.setTextSize(Helper.getFontSize(mContext.getResources(), 4.7));
        mtxtListTitleBottom.setTextSize(Helper.getFontSize(mContext.getResources(), 9));
        mtxtListSubTitleTop.setText(Helper.getBoldedText("2.3 miles  |  OPEN till 8:00pm", 14, 19));

        mImgCart = (ImageView) findViewById(R.id.home_list_box_cart);
        mImgDetail = (ImageView) findViewById(R.id.home_list_box_detail);
        int padding = Math.round(Helper.getFontSize(mContext.getResources(), 19));
        mImgCart.setPadding(padding, padding,
                padding, padding);
        mImgDetail.setPadding(padding, padding,
                padding, padding);

    }

    private void AddListener() {
        mImgDetail.setOnClickListener(this);
        mDiscover.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mDeals.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mQr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_list_box_detail:
                startActivity(new Intent(mContext, MenuActivity.class));
                break;
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
                //startActivity(new Intent(mContext, DealsRewardActivity.class));
                break;
            case R.id.home_btn_qr:
                //startActivity(new Intent(mContext, CodeScanner.class));
                Dailogs.ShowToast(mContext, "Not integrated yet", Constants.SHORT_TIME);
                break;
        }
    }


    private void initViews() {
        MenuGalleryAdapter adapter = new MenuGalleryAdapter(mContext);
        adapter.setData(createPageList());

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(adapter);
    }

    @NonNull
    private List<View> createPageList() {
        List<View> pageList = new ArrayList<>();
        pageList.add(createPageView(R.color.baseColor));
        pageList.add(createPageView(R.color.colorPrimaryDark));
        pageList.add(createPageView(R.color.login_status_color));
        pageList.add(createPageView(R.color.baseColor));
        pageList.add(createPageView(R.color.login_status_color));
        return pageList;
    }

    @NonNull
    private View createPageView(int color) {
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(color));

        return view;
    }

    /**
     * Toolbar customization
     */
    private void ActionBarConfigs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mActivity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(mActivity.getResources().getColor(R.color.lightGrey));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window w = mActivity.getWindow();
                w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                int statusBarHeight = Helper.getStatusBarHeight(mContext);
                View view = new View(mContext);
                view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                view.getLayoutParams().height = statusBarHeight;
                ((ViewGroup) w.getDecorView()).addView(view);
                view.setBackgroundColor(mContext.getResources().getColor(R.color.lightGrey));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) myActionMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                if (!mSearchView.isIconified()) {
//                    mSearchView.setIconified(true);
//                }
//                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }
}
