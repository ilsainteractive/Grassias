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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.HomeAdapter;
import com.ilsa.grassis.adapters.ToggleDisAdapter;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.ExpandedRecyclerView;
import com.ilsa.grassis.library.MenuItemClickListener;
import com.ilsa.grassis.library.RecyclerTouchListener;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.rootvo.NearByVo;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;

import org.json.JSONObject;

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
    private PopupWindow mpopupWindow;
    private boolean isHeartBlank = true;
    private RecyclerTouchListener listener;

    private BoldSFTextView mtxtToolbarTitle;
    private RegularTextView mtxtToolbarTitleDump;
    private ImageView heart;

    private HomeAdapter homeAdapter;
    private NearByVo searchNearByVo;
    private ProgressDialog pd;

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

    @BindView(R.id.toolbar)
    Toolbar mRelativeLayout;

    @BindView(R.id.notFoundDispensary)
    TextView notFoundText;

    TextView switch_your_favorite_dis;

    LayoutInflater inflater;
    View customView;
    ExpandedRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mContext = this;
        mActivity = this;

        initToolBar();
        InitComponents();
        AddListener();
        SyncData();
    }

    private void SyncData() {

        if (AppContoller.nearByVo == null) {
            if (Helper.checkInternetConnection(mContext)) {
                getNearByDespensories(new Location("asd"));
            } else
                Dailogs.ShowToast(mContext, getString(R.string.no_internet_msg), Constants.SHORT_TIME);
        } else {
            setAdaptorAndViews(AppContoller.nearByVo);
        }
    }

    public void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mtxtToolbarTitle = (BoldSFTextView) toolbar.findViewById(R.id.toolbar_title);
        mtxtToolbarTitleDump = (RegularTextView) toolbar.findViewById(R.id.toolbar_title_dump);
        heart = (ImageView) toolbar.findViewById(R.id.toolbar_heart);
        toolbar.setTitle("");
        if (AppContoller.userData != null)
            mtxtToolbarTitle.setText(AppContoller.userData.getUser().getUsername());
        setSupportActionBar(toolbar);
    }

    private void InitComponents() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_home);

        inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        customView = inflater.inflate(R.layout.popupwindow_listview, null);
        switch_your_favorite_dis = (TextView) customView.findViewById(R.id.switch_your_favorite_dis);
        recyclerView = (ExpandedRecyclerView) customView.findViewById(R.id.recycler_viewId);

    }

    private void AddListener() {
        // mImgDetail.setOnClickListener(this);
        mDiscover.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mDeals.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mQr.setOnClickListener(this);
        mHome.setImageResource(R.mipmap.home_icon1);
        heart.setOnClickListener(this);
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
                if (AppContoller.IsLoggedIn && AppContoller.userData != null)
                    startActivity(new Intent(mContext, ProfileActivity.class));
                else
                    Dailogs.ShowToast(mContext, "You need to sign in or sign up before continuing", Constants.SHORT_TIME);
                break;
            case R.id.home_btn_deals:
                if (AppContoller.IsLoggedIn && AppContoller.userData != null)
                    startActivity(new Intent(mContext, DealsRewardActivity.class));
                else
                    Dailogs.ShowToast(mContext, "You need to sign in or sign up before continuing", Constants.SHORT_TIME);
                break;
            case R.id.home_btn_home:
                break;
            case R.id.home_btn_qr:
                startActivity(new Intent(mContext, CodeScanner.class));
                break;
            case R.id.toolbar_heart:
                if (isHeartBlank) {

                    AppContoller.FavDispensaries.clear();
                    AppContoller.FavDispensariesIds = AppContoller.userData.getUser().getFavorites().getDispensaries();
                    for (int i = 0; i < AppContoller.FavDispensariesIds.size(); i++) {
                        String id = AppContoller.FavDispensariesIds.get(i).getDispensary_id();
                        for (int ii = 0; ii < AppContoller.nearByVo.getDispensaries().size(); ii++) {
                            if (AppContoller.nearByVo.getDispensaries().get(ii).getDispensary().getId().equalsIgnoreCase(id)) {
                                AppContoller.FavDispensaries.add(AppContoller.nearByVo.getDispensaries().get(ii).getDispensary());
                                break;
                            }
                        }
                    }

                    if (AppContoller.FavDispensaries.size() < 1) {
                        switch_your_favorite_dis.setVisibility(View.GONE);
                        Toast.makeText(this, "No liked dispensary", Toast.LENGTH_SHORT).show();
                        heart.setImageResource(R.mipmap.heart_icon_empty);
                        isHeartBlank = true;
                    } else {
                        heart.setImageResource(R.mipmap.fillheart);
                        isHeartBlank = false;
                        openPopUpWindow();
                    }

                } else {
                    isHeartBlank = true;
                    mpopupWindow.dismiss();
                    heart.setImageResource(R.mipmap.blankheart);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mpopupWindow != null && mpopupWindow.isShowing()) {
            mpopupWindow.dismiss();
            isHeartBlank = true;
            heart.setImageResource(R.mipmap.blankheart);
        } else if (heart.getVisibility() == View.GONE) {
            if (AppContoller.nearByVo != null)
                setAdaptorAndViews(AppContoller.nearByVo);

            mSearchView.onActionViewCollapsed();
            heart.setVisibility(View.VISIBLE);
            mtxtToolbarTitleDump.setVisibility(View.VISIBLE);
            mtxtToolbarTitle.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();
    }

    private void openPopUpWindow() {
        // List defined in XML ( See Below )
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        listener = new RecyclerTouchListener(mContext, recyclerView, new MenuItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(HomeActivity.this, "Toggle service issue", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(mContext, "long clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.addOnItemTouchListener(listener);

       /* AppContoller.FavDispensaries.clear();
        AppContoller.FavDispensariesIds = AppContoller.userData.getUser().getFavorites().getDispensaries();
        for (int i = 0; i < AppContoller.FavDispensariesIds.size(); i++) {
            String id = AppContoller.FavDispensariesIds.get(i).getDispensary_id();
            for (int ii = 0; ii < AppContoller.nearByVo.getDispensaries().size(); ii++) {
                if (AppContoller.nearByVo.getDispensaries().get(ii).getDispensary().getId().equalsIgnoreCase(id)) {
                    AppContoller.FavDispensaries.add(AppContoller.nearByVo.getDispensaries().get(ii).getDispensary());
                    break;
                }
            }
        }

        if (AppContoller.FavDispensaries.size() < 1)
            switch_your_favorite_dis.setVisibility(View.GONE);
        //else
        //Toast.makeText(this, "No liked dispensary", Toast.LENGTH_SHORT).show();*/
        ToggleDisAdapter adapter = new ToggleDisAdapter(mContext, AppContoller.FavDispensaries);
        recyclerView.setAdapter(adapter);

        mpopupWindow = new PopupWindow(
                customView,
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        );
        if (Build.VERSION.SDK_INT >= 21) {
            mpopupWindow.setElevation(5.0f);
        }
        mpopupWindow.showAsDropDown(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) myActionMenuItem.getActionView();


        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heart.setVisibility(View.GONE);
                mtxtToolbarTitleDump.setVisibility(View.GONE);
                mtxtToolbarTitle.setVisibility(View.GONE);
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                if (query.length() > 3) {
                    SearchWebService(query);
                }
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

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (AppContoller.nearByVo != null)
                    setAdaptorAndViews(AppContoller.nearByVo);

                heart.setVisibility(View.VISIBLE);
                mtxtToolbarTitleDump.setVisibility(View.VISIBLE);
                mtxtToolbarTitle.setVisibility(View.VISIBLE);
                return false;
            }
        });
        return true;
    }

    private void SearchWebService(String query) {

        pd = new ProgressDialog(HomeActivity.this);
        pd.setMessage(getString(R.string.searching_dispensaries));
        pd.setCancelable(false);
        pd.show();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://kushmarketing.herokuapp.com/api/search?limit=10&lat=&lng=&distance=20000&q=" + query)
                .get()
                .addHeader("accept", "application/vnd.kush_marketing.com; version=1")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "b9764130-2778-7052-4889-b48d2c5d6ad6")
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                pd.dismiss();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string().toString();
                Log.i("response", res);
                if (!response.isSuccessful()) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                JSONObject error = jsonObject.getJSONObject("error");
                                String message = error.get("message").toString();
                                Dailogs.ShowToast(mContext, message, Constants.LONG_TIME);
                                pd.dismiss();
                            } catch (Exception e) {
                                pd.dismiss();
                            }
                        }
                    });
                } else {
                    try {
                        Gson gson = new GsonBuilder().create();
                        searchNearByVo = gson.fromJson(res, NearByVo.class);

                        if (searchNearByVo.getDispensaries().size() > 0) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setAdaptorAndViews(searchNearByVo);
                                    pd.dismiss();
                                }
                            });
                        } else {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                    notFoundText.setVisibility(View.VISIBLE);
                                    mRecyclerView.setVisibility(View.GONE);
                                }
                            });
                        }

                    } catch (Exception e) {
                        pd.dismiss();
                        e.printStackTrace();
                        Log.i("problem", e.getMessage());
                    }
                }
            }
        });
    }

    private void getNearByDespensories(Location location) {

        pd = new ProgressDialog(mContext);
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
                            pd.dismiss();
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
                            e.printStackTrace();
                            Log.i("problem", e.getMessage());
                        }
                    }
                }
            });
        } catch (Exception e) {
            pd.dismiss();
            e.printStackTrace();
            Log.i("problem", e.toString());
        }
    }

    private void setAdaptorAndViews(NearByVo nearByVo) {

        notFoundText.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

        homeAdapter = new HomeAdapter(mContext, nearByVo);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(homeAdapter);

        //  mtxtToolbarTitle.setVisibility(View.VISIBLE);
        //  mtxtToolbarTitleDump.setVisibility(View.VISIBLE);
        if (AppContoller.IsLoggedIn && AppContoller.userData != null)
            mtxtToolbarTitle.setText(AppContoller.userData.getUser().getUsername());
        else
            mtxtToolbarTitle.setText("Guest");
    }

}
