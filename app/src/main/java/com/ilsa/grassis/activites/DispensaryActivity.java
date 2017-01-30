package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ilsa.grassis.R;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.library.SFUITextBold;
import com.ilsa.grassis.library.ThinTextView;
import com.ilsa.grassis.utils.Helper;
import com.ilsa.grassis.vo.DispensaryVO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Dispensary activity contains map view of locations.
 */
public class DispensaryActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Context mContext;
    private Toolbar mToolbar;
    private Activity mActivity;

    private GoogleMap mMap;
    private SearchView mSearchView;

    @BindView(R.id.dispensary_map_btm_section)
    LinearLayout mBtmSec;

    @BindView(R.id.dispensary_map_btm_section_next)
    LinearLayout mBtmSecNext;

    @BindView(R.id.dispensary_map_btm_section_txt_next)
    RegularTextView mtxtBtmSecNext;

    @BindView(R.id.dispensary_map_btm_section_title)
    RegularTextView mtxtBtmSecTitle;

    @BindView(R.id.dispensary_map_btm_section_sub_title)
    ThinTextView mtxtBtmSecSubTitle;

    @BindView(R.id.home_lv_bottom_section_2_title)
    SFUITextBold mtxtSelecTitle;

    @BindView(R.id.home_lv_bottom_section_2_subTitle)
    RegularTextView mtxtSelecAddresse;

    private ArrayList<DispensaryVO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensary);
        ButterKnife.bind(this);

        mActivity = this;
        mContext = this;
        initToolBar();
        InitComponents();
        SyncData();
        AddListener();
    }

    /**
     * Init toolbar.
     */
    public void initToolBar() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.mipmap.signup_back_arrow);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                Math.round(Helper.getFontSize(mContext.getResources(), 90)));
        //params.alignWithParent = RelativeLayout.ALIGN_PARENT_BOTTOM;
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mBtmSec.setLayoutParams(params);

        // int padding = Math.round(Helper.getFontSize(mContext.getResources(), 19));
        int paddingTopBtm = Math.round(Helper.getFontSize(mContext.getResources(), 19));
        mBtmSecNext.setPadding(0, paddingTopBtm, 0, paddingTopBtm);
        mtxtBtmSecNext.setTextSize(Helper.getFontSize(mContext.getResources(), 5.5));
        mtxtBtmSecTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 6));
        mtxtBtmSecSubTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 3.0));

        mtxtSelecTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 6.2)); //6.5 psd
        mtxtSelecAddresse.setTextSize(Helper.getFontSize(mContext.getResources(), 3.6));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.signup_back_arrow);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * Syncing data from server to inflate on map view.
     */
    private void SyncData() {
        getDispensaryList();
    }

    private void getDispensaryList() {

        list = new ArrayList<>();

        String[] Title = {"Fairfax", "Weho West", "7000 Hollywood Blvd", "Tinhorn Flats Saloon & Grill"};
        double[] lats = {34.070973,34.083457,34.100806, 34.102972};
        double[] longs = {-118.344889,  -118.385493,-118.342434, -118.338447};
        for (int i = 0; i < longs.length; i++) {

            DispensaryVO item = new DispensaryVO();
            item.setId(i + "");
            item.setImg(i + "");
            item.setTitle(Title[i]);
            item.setDesc("Fast Food Restaurant");
            item.setLat(lats[i]);
            item.setLog(longs[i]);
            list.add(item);
        }
    }

    /**
     * Applying listeners to views.
     */
    private void AddListener() {

        mtxtBtmSecNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, HomeActivity.class));
            }
        });
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
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
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = null;
        for (int i = 0; i < list.size(); i++) {
            latLng = new LatLng(list.get(i).getLat(), list.get(i).getLog());
//            MarkerOptions marker = new MarkerOptions().position(new LatLng(list.get(i).getLat(), list.get(i).getLog()))
//                    .title(list.get(i).getTitle()).snippet(list.get(i).getDesc());
//            marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.home_lv_bottom_icon))
//                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.home_lv_bottom_icon));
            MarkerOptions marker = new MarkerOptions().position(new LatLng(list.get(i).getLat(), list.get(i).getLog()))
                    .title(list.get(i).getTitle()).snippet(list.get(i).getDesc());
            marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.home_lv_bottom_icon))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.home_lv_bottom_icon));

            Marker marker1 = mMap.addMarker(marker);
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                // Use default InfoWindow frame
                @Override
                public View getInfoWindow(Marker marker) {
                    View v = mActivity.getLayoutInflater().inflate(R.layout.coustom_marker_layout, null);
                    return v;
                }


                // Defines the contents of the InfoWindow
                @Override
                public View getInfoContents(Marker marker) {
                    //View v = mActivity.getLayoutInflater().inflate(R.layout.coustom_marker_layout, null);
                    // Getting reference to the TextView to set title
                    // TextView note = (TextView) v.findViewById(R.id.note);
                    //note.setText(marker.getTitle());
                    // Returning the view containing InfoWindow contents
                    return null;
                }
            });
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    startActivity(new Intent(mContext, DispensaryInfoActivity.class));
                }
            });
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    marker.getTag();
                    return false;
                }
            });
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
}
