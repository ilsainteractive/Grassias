package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
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
import android.widget.ScrollView;
import android.widget.Toast;

import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.MenuAdapter;
import com.ilsa.grassis.adapters.MenuGalleryAdapter;
import com.ilsa.grassis.library.ExpandedRecyclerView;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.MenuItemClickListener;
import com.ilsa.grassis.library.RecyclerTouchListener;
import com.ilsa.grassis.library.ThinTextView;
import com.ilsa.grassis.utils.Helper;
import com.ilsa.grassis.vo.MenuListVO;

import java.util.ArrayList;
import java.util.List;

public class MenuItemActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;

    private Toolbar toolbar;
    private SearchView mSearchView;
    private ScrollView mScrollView;

    private MediumTextView mtxtToolbarTitle;
    private ThinTextView mtxtTtile, mTxtSubTitle, mTxtAction;

    private ExpandedRecyclerView recyclerView;
    private MenuAdapter mMenuAdapter;

    private List<MenuListVO> menuListVOs;
    private String[] titles = {"Exctracts"};
    private int[] images = {R.mipmap.menu_lv_item_img};
    private RecyclerTouchListener listener;
    private boolean isScrolled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        mContext = this;
        mActivity = this;
        isScrolled = false;
        initToolBar();
        InitComponents();
        syncData();
        initViews();
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

        // mTopBanner = (ImageView) findViewById(R.id.menu_bottom_banner);
        //mScrollView = (ScrollView) findViewById(R.id.scrollView);
        // LinearLayout.LayoutParams paramsTexts = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        //        Math.round(Helper.getFontSize(mContext.getResources(), 190)));
        //mTopBanner.setLayoutParams(paramsTexts);

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
                Toast.makeText(mContext, "Clicked " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(mContext, "long clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.addOnItemTouchListener(listener);
    }

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
        for (int i = 0; i < images.length; i++) {
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
//            mScrollView.postDelayed(new Runnable() {
//                public void run() {
//                    mScrollView.fullScroll(View.FOCUS_UP);
//                    isScrolled = true;
//                }
//            }, 200);
        }
    }

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
        getMenuInflater().inflate(R.menu.home_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) myActionMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                // UserFeedback.show("SearchOnQueryTextSubmit: " + query);
//                if (!mSearchView.isIconified()) {
//                    mSearchView.setIconified(true);
//                }
//                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });
        return true;
    }
}