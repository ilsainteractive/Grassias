package com.ilsa.grassis.activites;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

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
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.library.ThinTextView;
import com.ilsa.grassis.vo.OrderManager;
import com.ilsa.grassis.vo.OrderUserProducts;

import android.text.TextUtils;

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
    private ThinTextView mtxtTtile, mTxtSubTitle, mTxtActionBuy;

    private ExpandedRecyclerView recyclerView;
    private MenuItemAdapter mMenuItemAdapter;

    // dummy data list vaues
    private ArrayList<Products> menuListVOs;
    private Dispensary mDispensary;

    private RecyclerTouchListener listener;
    private boolean isScrolled = false;
    private String SelectedID = "";

    private TextView emptyView;

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

    String dispensaryId;

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
        dispensaryId = getIntent().getStringExtra("dispensaryId");

        syncData(getIntent().getStringExtra("dispensaryId"), getIntent().getIntExtra("category_id", 0));
        AddListener();
    }

    private void SetTexts() {

        mtxtToolbarTitle.setText(getIntent().getStringExtra("category_title"));
        mtxtTtile.setText(mDispensary.getName());
        //mTxtSubTitle.setText(mDispensary.getName());
    }


    private void AddListener() {
        mDiscover.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mDeals.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mQr.setOnClickListener(this);
        mHome.setImageResource(R.mipmap.home_icon1);
        mTxtActionBuy.setOnClickListener(this);
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

        emptyView = (TextView) findViewById(R.id.menu_item_empty_view);
        emptyView.setVisibility(View.GONE);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        pageIndicatorView = (CircleIndicator) findViewById(R.id.indicator);

        mtxtTtile = (ThinTextView) findViewById(R.id.menu_item_title);
        mTxtSubTitle = (ThinTextView) findViewById(R.id.menu_item_sub_title);
        mTxtActionBuy = (ThinTextView) findViewById(R.id.menu_item_action_buy);

        recyclerView = (ExpandedRecyclerView) findViewById(R.id.recycler_view);
        listener = new RecyclerTouchListener(mContext, recyclerView, new MenuItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(mContext, MenuItemDetailsActivity.class);
                i.putExtra("product_id", menuListVOs.get(position).getId());
                i.putExtra("category_title", getIntent().getStringExtra("category_title"));
                i.putExtra("DISPENSARY_ID", dispensaryId);
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

        if (menuListVOs.size() > 0) {
            mMenuItemAdapter.notifyDataSetChanged();

            for (Dispensaries dispensaries : AppContoller.nearByVo.getDispensaries()) {
                if (Dispensary_id.equalsIgnoreCase(dispensaries.getDispensary().getId())) {
                    mDispensary = dispensaries.getDispensary();
                    break;
                }
            }
            SetTexts();
            MenuGalleryAdapter adapter = new MenuGalleryAdapter(mContext);
            adapter.setData(menuListVOs);
            mViewPager.setAdapter(adapter);

            pageIndicatorView.setViewPager(mViewPager);
            adapter.registerDataSetObserver(pageIndicatorView.getDataSetObserver());
        } else {
            emptyView.setVisibility(View.VISIBLE);
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

        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mtxtToolbarTitle.setVisibility(View.GONE);
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mtxtToolbarTitle.setVisibility(View.VISIBLE);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {

        if (mtxtToolbarTitle.getVisibility() == View.GONE) {
            mSearchView.onActionViewCollapsed();
            mtxtToolbarTitle.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();
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
            case R.id.menu_item_action_buy:

                //Toast.makeText(this, menuListVOs.get(mViewPager.getCurrentItem()).getId(), Toast.LENGTH_SHORT).show();
                boolean added = true;
                for (int i = 0; i < AppContoller.orderManager.getProductses().size(); i++) {
                    if (AppContoller.orderManager.getProductses().get(i).getId().equalsIgnoreCase(menuListVOs.get(mViewPager.getCurrentItem()).getId())) {
                        added = false;
                    }
                }

                if (added) {

                    if (TextUtils.isEmpty(AppContoller.orderManager.getDispensary().getId())) {
                        AppContoller.orderManager.getProductses().add(menuListVOs.get(mViewPager.getCurrentItem()));
                        AppContoller.orderManager.getDispensary().setId(dispensaryId);

                        Intent intent = new Intent(MenuItemActivity.this, AddToCart.class);
                        startActivity(intent);
                    } else if (AppContoller.orderManager.getDispensary().getId().equalsIgnoreCase(dispensaryId)) {
                        AppContoller.orderManager.getProductses().add(menuListVOs.get(mViewPager.getCurrentItem()));
                        AppContoller.orderManager.getDispensary().setId(dispensaryId);

                        Intent intent = new Intent(MenuItemActivity.this, AddToCart.class);
                        startActivity(intent);
                    } else
                        changeDispensaryDialog();
                } else
                    CartAlertDialog();
                break;
        }
    }

    private void changeDispensaryDialog() {

        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.custom_dialoge);
        dialog.setTitle("Sorry!");

        // set the custom dialog components - text, image and button
        RegularTextView checkOutItems = (RegularTextView) dialog.findViewById(R.id.checkOutItems);
        RegularTextView clearItems = (RegularTextView) dialog.findViewById(R.id.clearItems);
        RegularTextView cancelDialoge = (RegularTextView) dialog.findViewById(R.id.cancelDialoge);

        checkOutItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Intent intent = new Intent(MenuItemActivity.this, AddToCart.class);
                startActivity(intent);
            }
        });
        clearItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                AppContoller.orderUserProducts = new OrderUserProducts();
                AppContoller.orderManager = new OrderManager();

            }
        });
        cancelDialoge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    private void CartAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);

        // set title
        alertDialogBuilder.setTitle("Sorry!");

        // set dialog message
        alertDialogBuilder
                .setMessage("This Product already added in your cart")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
}