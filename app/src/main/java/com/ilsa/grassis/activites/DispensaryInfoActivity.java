package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.MenuGalleryAdapter;
import com.ilsa.grassis.library.SFUITextBold;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Dispensary info activity.
 */
public class DispensaryInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Context mContext;
    private Activity mActivity;

    /**
     * The M toolbar.
     */
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    /**
     * The M map view.
     */
    @BindView(R.id.dispensaryInfo_map_view)
    FrameLayout mMapView;

    /**
     * The Mtxt toolbar title.
     */
    @BindView(R.id.dispensary_info_toolbar_title)
    SFUITextBold mtxtToolbarTitle;

    /**
     * The Mtxt toolbar sub title.
     */
    @BindView(R.id.dispensary_info_toolbar_subtitle)
    RegularTextView mtxtToolbarSubTitle;

    /**
     * The Scroll layout.
     */
    @BindView(R.id.horizontal_scroll_layout)
    LinearLayout scroll_layout;

    @BindView(R.id.dispensary_info_toolbar_img_back)
    ImageView back;
    /**
     * The Pager layout.
     */
    @BindView(R.id.viewPager)
    ViewPager pager_layout;

    @BindView(R.id.dispensaryInfo_txt_menu_view)
    MediumTextView mtxtMenuView;

    private GoogleMap mMap;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensary_info);
        ButterKnife.bind(this);

        mContext = this;
        initToolBar();
        InitComponents();
        initViews();
        addListener();
    }

    private void addListener() {

        mtxtMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MenuActivity.class));
            }
        });
    }

    /**
     * Init toolbar.
     */
    public void initToolBar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }

    /**
     * Layout components initializing and bridging here.
     */
    private void InitComponents() {

//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                Math.round(Helper.getFontSize(mContext.getResources(), 90)));
//        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        mBtmSec.setLayoutParams(params);

        // int padding = Math.round(Helper.getFontSize(mContext.getResources(), 19));
        //int paddingTopBtm = Math.round(Helper.getFontSize(mContext.getResources(), 19));
        // mBtmSecNext.setPadding(0, paddingTopBtm, 0, paddingTopBtm);
        // mtxtSelecTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 6.2)); //6.5 psd

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        for (int i = 0; i < 10; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setPadding(2, 2, 2, 2);
            imageView.setImageBitmap(BitmapFactory.decodeResource(
                    getResources(), R.mipmap.discovry_info_imge_slider));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(Math.round(Helper.getFontSize(mContext.getResources(), 150)),
                    Math.round(Helper.getFontSize(mContext.getResources(), 150)));
            imageView.setLayoutParams(params1);
            scroll_layout.addView(imageView);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void initViews() {
        MenuGalleryAdapter adapter = new MenuGalleryAdapter(mContext);
        //adapter.setData(createPageList());

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        LatLng latLng = null;
//        for (int i = 0; i < list.size(); i++) {
//            latLng = new LatLng(list.get(i).getLat(), list.get(i).getLog());
//            MarkerOptions marker = new MarkerOptions().position(new LatLng(list.get(i).getLat(), list.get(i).getLog()))
//                    .title(list.get(i).getTitle()).snippet(list.get(i).getDesc());
//            marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.home_lv_bottom_icon))
//                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.home_lv_bottom_icon));
//            Marker marker1 = mMap.addMarker(marker);
//            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                @Override
//                public boolean onMarkerClick(Marker marker) {
//
//                    marker.getTag();
//                    return false;
//                }
//            });
//        }
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dispensory, menu);
        return true;
    }
}
