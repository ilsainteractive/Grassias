package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
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

import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.MenuGalleryAdapter;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private Toolbar toolbar;
    private SearchView mSearchView;

    private BoldSFTextView mtxtToolbarTitle;

    private RegularTextView mtxtListTitleTop, mtxtToolbarTitleDump;
    private RegularTextView mtxtListSubTitleTop;
    private BoldSFTextView mtxtListTitleBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mContext = this;
        mActivity = this;
        initToolBar();
        //ActionBarConfigs();
        InitComponents();
        initViews();
    }

    private void InitComponents() {

        mtxtListTitleTop = (RegularTextView) findViewById(R.id.home_list_top_title);
        mtxtListSubTitleTop = (RegularTextView) findViewById(R.id.home_list_top_sub_title);
        mtxtListTitleBottom = (BoldSFTextView) findViewById(R.id.home_lv_bottom_title);
        mtxtListTitleTop.setTextSize(Helper.getFontSize(mContext.getResources(), 10));
        mtxtListTitleBottom.setTextSize(Helper.getFontSize(mContext.getResources(), 9));
        mtxtListSubTitleTop.setText(Helper.getBoldedText("2.3 miles  |  OPEN till 8:00pm", 14, 19));
    }

    public void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mtxtToolbarTitle = (BoldSFTextView) toolbar.findViewById(R.id.toolbar_title);
        mtxtToolbarTitleDump = (RegularTextView) toolbar.findViewById(R.id.toolbar_title_dump);
        toolbar.setTitle("");
        String s = "Hi, " + "Cheryl.";
        //mtxtToolbarTitle.setText(Helper.getBoldedText(s, 3, s.length()));
        mtxtToolbarTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 6));
        mtxtToolbarTitleDump.setTextSize(Helper.getFontSize(mContext.getResources(), 6));
        setSupportActionBar(toolbar);
    }

    private void initViews() {
        MenuGalleryAdapter adapter = new MenuGalleryAdapter();
        adapter.setData(createPageList());

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(adapter);
    }

    @NonNull
    private List<View> createPageList() {
        List<View> pageList = new ArrayList<>();
        pageList.add(createPageView(R.color.white));
        pageList.add(createPageView(R.color.baseColor));
        pageList.add(createPageView(R.color.colorPrimaryDark));
        pageList.add(createPageView(R.color.login_status_color));

        return pageList;
    }

    @NonNull
    private View createPageView(int color) {
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(color));

        return view;
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
