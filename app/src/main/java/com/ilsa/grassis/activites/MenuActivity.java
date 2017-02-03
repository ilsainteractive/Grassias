package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.MenuAdapter;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.ExpandedRecyclerView;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.MenuItemClickListener;
import com.ilsa.grassis.library.RecyclerTouchListener;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;
import com.ilsa.grassis.vo.MenuListVO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Menu activity contains list of items.
 */
public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Activity mActivity;

    private Toolbar toolbar;
    private SearchView mSearchView;
    private ScrollView mScrollView;

    private MediumTextView mtxtToolbarTitle;
    private ImageView mTopBanner;

    private ExpandedRecyclerView recyclerView;
    private MenuAdapter mMenuAdapter;

    private List<MenuListVO> menuListVOs;

    // Dummy data for inflation
    private String[] titles = {"Exctracts", "Indica", "Sativa", "Hybrid", "Edibles", "Topicals", "Grow"};
    private int[] images = {R.mipmap.menu_lv_item_img, R.mipmap.menu_lv_item_img, R.mipmap.menu_lv_item_img,
            R.mipmap.menu_lv_item_img, R.mipmap.menu_lv_item_img, R.mipmap.menu_lv_item_img, R.mipmap.menu_lv_item_img};

    private RecyclerTouchListener listener;
    private boolean isScrolled = false;

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
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        mContext = this;
        mActivity = this;
        isScrolled = false;
        initToolBar();
        InitComponents();
        syncData();
        AddListener();
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

        mTopBanner = (ImageView) findViewById(R.id.menu_bottom_banner);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
//        LinearLayout.LayoutParams paramsTexts = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                Math.round(Helper.getFontSize(mContext.getResources(), 190)));
//        mTopBanner.setLayoutParams(paramsTexts);
        recyclerView = (ExpandedRecyclerView) findViewById(R.id.recycler_view);
        listener = new RecyclerTouchListener(mContext, recyclerView, new MenuItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(new Intent(mContext, MenuItemActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        });
        recyclerView.addOnItemTouchListener(listener);
    }


    /**
     * Syncing data from server to inflate on listview.
     */
    private void syncData() {
        menuListVOs = new ArrayList<>();
        mMenuAdapter = new MenuAdapter(mContext, menuListVOs);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mMenuAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        for (int i = 0; i < 7; i++) {
            MenuListVO listVO = new MenuListVO();
            listVO.setId(i + "");
            listVO.setTitle(titles[i]);
            listVO.setImg(images[i] + "");

            menuListVOs.add(listVO);
        }
        mMenuAdapter.notifyDataSetChanged();
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

    private void AddListener() {
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
}