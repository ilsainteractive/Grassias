package com.ilsa.grassis.activites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.HomeAdapter;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.rootvo.NearByVo;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Home activity.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Activity mActivity;
    private Toolbar toolbar;
    private SearchView mSearchView;

    private BoldSFTextView mtxtToolbarTitle;
    private RegularTextView mtxtToolbarTitleDump;

    //private RegularTextView mtxtListTitleTop, mtxtListSubTitleTop;
    //RegularTextView mtxtToolbarTitleDump, mtxtListViewBottomSection2Title;
    //private RegularTextView mtxtListViewBottomSection3SubTitle;
    //private BoldSFTextView mtxtListTitleBottom, mtxtLvBottomSection2Per, mtxtListViewBottomSection2SubTitle, mtxtListViewBottomSection3Title;
    //private ThinTextView mtxtLvBottomSection2Off, mtxtLvBottomSection2Products;
    //private ImageView mImgCart, mImgDetail, mImgLvTopSection;

    private HomeAdapter homeAdapter;

    //@BindView(R.id.recycler_view_home)
    RecyclerView mRecyclerView;

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

    //private LinearLayout mListViewTopSection, mListViewTopSectionText, mListViewBottomSectionPager, mListViewBottomSection2;

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
        SyncData();
        //initViews();
    }

    private void SyncData() {

        if (AppContoller.nearByVo == null) {
            getNearByDespensories(new Location("asd"));
        } else {
            setAdaptorAndViews(AppContoller.nearByVo);
        }
    }

    /**
     * Init toolbar.
     */
    public void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mtxtToolbarTitle = (BoldSFTextView) toolbar.findViewById(R.id.toolbar_title);
        mtxtToolbarTitleDump = (RegularTextView) toolbar.findViewById(R.id.toolbar_title_dump);
        toolbar.setTitle("");
        if (AppContoller.userData != null)
            mtxtToolbarTitle.setText(AppContoller.userData.getUser().getUsername());
        setSupportActionBar(toolbar);
    }

    /**
     * Layout components initializing and bridging here.
     */
    private void InitComponents() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_home);

//        mListViewTopSection = (LinearLayout) findViewById(R.id.home_lv_top_section);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 220)));
//        mListViewTopSection.setLayoutParams(params);
//
//        mImgLvTopSection = (ImageView) findViewById(R.id.home_lv_top_section_img);
//        RelativeLayout.LayoutParams topImgHieght = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 190)));
//        mImgLvTopSection.setLayoutParams(topImgHieght);
//
//        mListViewTopSectionText = (LinearLayout) findViewById(R.id.home_lv_top_section_texts_layout);
//        RelativeLayout.LayoutParams paramsTexts = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 190)));
//        mListViewTopSectionText.setLayoutParams(paramsTexts);
//
        //mtxtListTitleBottom = (BoldSFTextView) findViewById(R.id.home_lv_bottom_title);
        //LinearLayout.LayoutParams paramsBottomTitle = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 100)));
        //mtxtListTitleBottom.setLayoutParams(paramsBottomTitle);
//
//        mListViewBottomSectionPager = (LinearLayout) findViewById(R.id.home_lv_bottom_pager);
//        LinearLayout.LayoutParams paramsBottomPager = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 360)));
//        mListViewBottomSectionPager.setLayoutParams(paramsBottomPager);
//
//        mListViewBottomSection2 = (LinearLayout) findViewById(R.id.home_lv_bottom_section_2);
//        LinearLayout.LayoutParams paramsBottomsection2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(Helper.getFontSize(mContext.getResources(), 120)));
//        mListViewBottomSection2.setLayoutParams(paramsBottomsection2);

        //mtxtListViewBottomSection2Title = (RegularTextView) findViewById(R.id.home_lv_bottom_section_2_subTitle);
        //mtxtListViewBottomSection2Title.setTextSize(Helper.getFontSize(mContext.getResources(), 3.5));

        //mtxtListViewBottomSection2SubTitle = (BoldSFTextView) findViewById(R.id.home_lv_bottom_section_2_title);
        //mtxtListViewBottomSection2SubTitle.setTypeface(null, Typeface.BOLD);
        //mtxtListViewBottomSection2SubTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 5));

        // mtxtLvBottomSection2Per = (BoldSFTextView) findViewById(R.id.home_lv_bottom_section_2_txt_per);
        //mtxtLvBottomSection2Per.setTextSize(Helper.getFontSize(mContext.getResources(), 21));

        // mtxtLvBottomSection2Off = (ThinTextView) findViewById(R.id.home_lv_bottom_section_2_txt_off);
        //mtxtLvBottomSection2Off.setTextSize(Helper.getFontSize(mContext.getResources(), 21));

        //  mtxtLvBottomSection2Products = (ThinTextView) findViewById(R.id.home_lv_bottom_section_2_txt_products);
        //mtxtLvBottomSection2Products.setTextSize(Helper.getFontSize(mContext.getResources(), 10));

        // mtxtListViewBottomSection3Title = (BoldSFTextView) findViewById(R.id.home_lv_bottom_section_3_title);
        //mtxtListViewBottomSection3Title.setTextSize(Helper.getFontSize(mContext.getResources(), 5));
        //mtxtListViewBottomSection3Title.setTypeface(null, Typeface.BOLD);

        //mtxtListViewBottomSection3SubTitle = (RegularTextView) findViewById(R.id.home_lv_bottom_section_3_subTitle);
        //mtxtListViewBottomSection3SubTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 3.5));

        //below setting
        //   mtxtListTitleTop = (RegularTextView) findViewById(R.id.home_list_top_title);
        //   mtxtListSubTitleTop = (RegularTextView) findViewById(R.id.home_list_top_sub_title);

        // mtxtListTitleTop.setTextSize(Helper.getFontSize(mContext.getResources(), 10));
        //mtxtListSubTitleTop.setTextSize(Helper.getFontSize(mContext.getResources(), 4.7));
        //mtxtListTitleBottom.setTextSize(Helper.getFontSize(mContext.getResources(), 9));
        //mtxtListSubTitleTop.setText(Helper.getBoldedText("2.3 miles  |  OPEN till 8:00pm", 14, 19));

        // mImgCart = (ImageView) findViewById(R.id.home_list_box_cart);
        //  mImgDetail = (ImageView) findViewById(R.id.home_list_box_detail);
        //int padding = Math.round(Helper.getFontSize(mContext.getResources(), 19));
//        mImgCart.setPadding(padding, padding,
//                padding, padding);
//        mImgDetail.setPadding(padding, padding,
//                padding, padding);
    }

    private void AddListener() {
        // mImgDetail.setOnClickListener(this);
        mDiscover.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mDeals.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mQr.setOnClickListener(this);
        mHome.setImageResource(R.mipmap.home_icon1);
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
//        getMenuInflater().inflate(R.menu.menu_home, menu);
//
//        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
//        mSearchView = (SearchView) myActionMenuItem.getActionView();
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
////                if (!mSearchView.isIconified()) {
////                    mSearchView.setIconified(true);
////                }
////                myActionMenuItem.collapseActionView();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
        return true;
    }

    private void getNearByDespensories(Location location) {

        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getString(R.string.loading_dispensaries));
        pd.setCancelable(false);
        pd.show();
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://kushmarketing.herokuapp.com/api/search?q=&limit=10&lat=&lng=&distance=20000&type=storefront")
                    .get()
                    .addHeader("accept", "application/vnd.kush_marketing.com; version=1")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Dailogs.ShowToast(mContext, getString(R.string.something_went_wrong_ry_again), Constants.LONG_TIME);
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) {
                    if (!response.isSuccessful()) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Dailogs.ShowToast(mContext, getString(R.string.something_went_wrong_ry_again), Constants.LONG_TIME);
                            }
                        });
                    } else {
                        try {
                            Gson gson = new GsonBuilder().create();
                            AppContoller.nearByVo = gson.fromJson(response.body().string().toString(), NearByVo.class);

                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                    setAdaptorAndViews(AppContoller.nearByVo);
                                }
                            });

                        } catch (Exception e) {
                            pd.dismiss();
                            Log.i("problem", e.toString());
                        }
                    }
                }
            });
        } catch (Exception e) {
            pd.dismiss();
            Log.i("problem", e.toString());
        }
    }

    private void setAdaptorAndViews(NearByVo nearByVo) {

        homeAdapter = new HomeAdapter(mContext, nearByVo);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(homeAdapter);

        mtxtToolbarTitle.setVisibility(View.VISIBLE);
        mtxtToolbarTitleDump.setVisibility(View.VISIBLE);
        if (AppContoller.IsLoggedIn && AppContoller.userData != null)
            mtxtToolbarTitle.setText(AppContoller.userData.getUser().getUsername());
        else
            mtxtToolbarTitle.setText("Guest");
    }
}
