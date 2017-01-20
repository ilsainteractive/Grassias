package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ilsa.grassis.R;
import com.ilsa.grassis.vo.DispensoryVO;

import java.util.ArrayList;

public class DispensaryActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Context mContext;
    private GoogleMap mMap;
    private Toolbar mToolbar;
    private SearchView mSearchView;
    private Activity mActivity;
    private Toolbar toolbar;

    private ArrayList<DispensoryVO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensary);

        mContext = this;
        initToolBar();
        InitComponents();
        SyncData();
        AddListener();
    }

    public void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.signup_back_arrow);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void SyncData() {
        getDispensaryList();
    }

    private void getDispensaryList() {

        list = new ArrayList<>();

        String[] Title = {"McDonald's", "Boulevard Heights", "Gaddafi Stadium", "Vogue Towers"};
        double[] lats = {31.527486, 31.520324, 31.513488, 31.508790};
        double[] longs = {74.349402, 74.346904, 74.333494, 74.349792};
        for (int i = 0; i < longs.length; i++) {

            DispensoryVO item = new DispensoryVO();
            item.setId(i + "");
            item.setImg(i + "");
            item.setTitle(Title[i]);
            item.setDesc("Fast Food Restaurant");
            item.setLat(lats[i]);
            item.setLog(longs[i]);
            list.add(item);
        }
        //Dailogs.ShowToast(mContext, "No web service available yet!", Constants.SHORT_TIME);
    }

    private void AddListener() {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = null;
        for (int i = 0; i < list.size(); i++) {

            latLng = new LatLng(list.get(i).getLat(), list.get(i).getLog());
            MarkerOptions marker = new MarkerOptions().position(new LatLng(list.get(i).getLat(), list.get(i).getLog())).title(list.get(i).getTitle());
            marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.home_lv_bottom_icon));
            mMap.addMarker(marker);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dispensory_menu, menu);

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
