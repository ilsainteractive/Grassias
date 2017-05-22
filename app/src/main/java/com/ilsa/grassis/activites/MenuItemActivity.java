package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.ScrollView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.MenuGalleryAdapter;
import com.ilsa.grassis.adapters.MenuItemAdapter;
import com.ilsa.grassis.apivo.Dispensaries;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.apivo.Products;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.ExpandedRecyclerView;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.MenuItemClickListener;
import com.ilsa.grassis.library.RecyclerTouchListener;
import com.ilsa.grassis.library.ThinTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

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
    CircleIndicator pageIndicatorView;

    private MediumTextView mtxtToolbarTitle;
    private ThinTextView mtxtTtile, mTxtSubTitle, mTxtAction;

    private ExpandedRecyclerView recyclerView;
    private MenuItemAdapter mMenuItemAdapter;

    // dummy data list vaues
    private ArrayList<Products> menuListVOs;
    private Dispensary mDispensary;

    private RecyclerTouchListener listener;
    private boolean isScrolled = false;
    private String SelectedID = "";

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
        syncData(getIntent().getStringExtra("dispensaryId"), getIntent().getIntExtra("category_id", 0));
        initViews();
        SetTexts();
        AddListener();
    }

    private void SetTexts() {

        mtxtToolbarTitle.setText(getIntent().getStringExtra("category_title"));
        mtxtTtile.setText(mDispensary.getName());
        //mTxtSubTitle.setText(mDispensary.getName());
    }

    private void initViews() {
        if (menuListVOs.size() > 0) {
            //mHolder.mProductPagerLayout.setVisibility(View.VISIBLE);
            //mHolder.mNoProductLayout.setVisibility(View.GONE);
            MenuGalleryAdapter adapter = new MenuGalleryAdapter(mContext);
            adapter.setData(menuListVOs);
            mViewPager.setAdapter(adapter);

            pageIndicatorView.setViewPager(mViewPager);
            adapter.registerDataSetObserver(pageIndicatorView.getDataSetObserver());
        } else {
            //mHolder.mNoProductLayout.setVisibility(View.VISIBLE);
            //mHolder.mProductPagerLayout.setVisibility(View.GONE);
        }
    }

    private void AddListener() {
        mDiscover.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mDeals.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mQr.setOnClickListener(this);
        mHome.setImageResource(R.mipmap.home_icon1);
    }

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

    private void InitComponents() {

        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        pageIndicatorView = (CircleIndicator) findViewById(R.id.indicator);

        mtxtTtile = (ThinTextView) findViewById(R.id.menu_item_title);
        mTxtSubTitle = (ThinTextView) findViewById(R.id.menu_item_sub_title);
        mTxtAction = (ThinTextView) findViewById(R.id.menu_item_action);

        recyclerView = (ExpandedRecyclerView) findViewById(R.id.recycler_view);
        listener = new RecyclerTouchListener(mContext, recyclerView, new MenuItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(mContext, MenuItemDetailsActivity.class);
                i.putExtra("product_id", menuListVOs.get(position).getId());
                i.putExtra("category_title", getIntent().getStringExtra("category_title"));
                //i.putExtra("category_name", getIntent().getStringExtra("dispensary_title"));
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        });
        recyclerView.addOnItemTouchListener(listener);
    }

    private void syncData(String Dispensary_id, int Category_id) {
        SelectedID = Dispensary_id;
        menuListVOs = new ArrayList<>();
        mMenuItemAdapter = new MenuItemAdapter(mContext, menuListVOs);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mMenuItemAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        for (Products product : AppContoller.nearByVo.getProducts()) {
            if (Dispensary_id.equalsIgnoreCase(product.getDispensary_id()) && (Category_id + "").equalsIgnoreCase(product.getProduct_category_id())) {
                menuListVOs.add(product);
            }
        }

        mMenuItemAdapter.notifyDataSetChanged();

        for (Dispensaries dispensaries : AppContoller.nearByVo.getDispensaries()) {
            if (Dispensary_id.equalsIgnoreCase(dispensaries.getDispensary().getId())) {
                mDispensary = dispensaries.getDispensary();
                break;
            }
        }
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
                startActivity(new Intent(mContext, DealsRewardActivity.class));
                break;
        }
    }
}