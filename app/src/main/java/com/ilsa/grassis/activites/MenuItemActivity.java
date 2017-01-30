package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.MenuItemAdapter;
import com.ilsa.grassis.adapters.MenuItemGalleryAdapter;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.ExpandedRecyclerView;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.MenuItemClickListener;
import com.ilsa.grassis.library.RecyclerTouchListener;
import com.ilsa.grassis.library.ThinTextView;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;
import com.ilsa.grassis.vo.MenuListVO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Menu item activity contains details about selected item from menu.
 */
public class MenuItemActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Activity mActivity;

    private Toolbar toolbar;
    private SearchView mSearchView;
    private ScrollView mScrollView;
    private ViewPager mViewPager;

    private MediumTextView mtxtToolbarTitle;
    private ThinTextView mtxtTtile, mTxtSubTitle, mTxtAction;

    private ExpandedRecyclerView recyclerView;
    private MenuItemAdapter mMenuAdapter;

    // dummy data list vaues
    private List<MenuListVO> menuListVOs;
    private String[] titles = {"Hindu Kush", "Mango Kush", "Death Star", "Hindu Kush", "Mango Kush", "Death Star", "Hindu Kush", "Mango Kush", "Death Star"};
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
        setContentView(R.layout.activity_menu_item);
        ButterKnife.bind(this);

        mContext = this;
        mActivity = this;
        isScrolled = false;
        initToolBar();
        InitComponents();
        syncData();
        initViews();
        AddListener();
    }

    private void initViews() {
        MenuItemGalleryAdapter adapter = new MenuItemGalleryAdapter(mContext);
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

    private void AddListener() {
        mDiscover.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mDeals.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mQr.setOnClickListener(this);
    }

    /**
     * Init toolbar.
     */
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

    /**
     * Layout components initializing and bridging here.
     */
    private void InitComponents() {

        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                Math.round(Helper.getFontSize(mContext.getResources(), 275)));
        mViewPager.setLayoutParams(layoutParams);
        mtxtTtile = (ThinTextView) findViewById(R.id.menu_item_title);
        mTxtSubTitle = (ThinTextView) findViewById(R.id.menu_item_sub_title);
        mTxtAction = (ThinTextView) findViewById(R.id.menu_item_action);

        mtxtTtile.setTextSize(Helper.getFontSize(getResources(), 10));
        mTxtSubTitle.setTextSize(Helper.getFontSize(getResources(), 4));
        mTxtAction.setTextSize(Helper.getFontSize(getResources(), 4));

        recyclerView = (ExpandedRecyclerView) findViewById(R.id.recycler_view);
        listener = new RecyclerTouchListener(mContext, recyclerView, new MenuItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(new Intent(mContext, MenuItemDetailsActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(mContext, "long clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.addOnItemTouchListener(listener);
    }

    /**
     * Syncing data from server to inflate on listview.
     */
    private void syncData() {
        menuListVOs = new ArrayList<>();
        mMenuAdapter = new MenuItemAdapter(mContext, menuListVOs);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mMenuAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        for (int i = 0; i < titles.length; i++) {
            MenuListVO listVO = new MenuListVO();
            listVO.setId(i + "");
            listVO.setTitle(titles[i]);
            listVO.setImg(titles[i] + "");

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