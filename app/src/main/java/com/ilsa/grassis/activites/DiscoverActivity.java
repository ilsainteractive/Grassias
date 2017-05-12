package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.DiscovAdapter;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.ExpandedRecyclerView;
import com.ilsa.grassis.library.MenuItemClickListener;
import com.ilsa.grassis.library.RecyclerTouchListener;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.rootvo.NearByVo;
import com.ilsa.grassis.utils.Dailogs;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Discover activity.
 */
public class DiscoverActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private Context mContext;
    private Toolbar mToolbar;
    private Activity mActivity;
    /**
     * The constant IsMapView.
     */
    public static boolean IsMapView = true;
    BoldSFTextView mtxtStoreFront;
    RegularTextView mtxtDelivery;

    @BindView(R.id.home_btn_dispensory)
    ImageView mDiscover;

    GoogleMap mMap;
    private LatLng latLng = null;
    private String mSelectedId = "";

    @BindView(R.id.home_btn_profile)
    ImageView mProfile;

    @BindView(R.id.home_btn_deals)
    ImageView mDeals;

    @BindView(R.id.home_btn_home)
    ImageView mHome;

    @BindView(R.id.home_btn_qr)
    ImageView mQr;

    @BindView(R.id.map_view)
    LinearLayout mapView;

    @BindView(R.id.recycler_view)

    ExpandedRecyclerView recyclerView;
    private DiscovAdapter discovAdapter;
    private RecyclerTouchListener listener;

    private ArrayList<Dispensary> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        ButterKnife.bind(this);

        mContext = this;
        mActivity = this;

        ToolbarSettings();
        InitComponents();
        AddListener();
        SyncData();
    }

    private void SyncData() {

        if (AppContoller.nearByVo == null) {
            //getNearByDespensories(new Location("asd"));
        } else {
            setMapAndList(AppContoller.nearByVo, Constants.STORE_FRONT);
        }
    }

    void AddingMarkers(ArrayList<Dispensary> mData, String type) {

        int mapPos = -1;
        if (mMap != null) {
            for (int i = 0; i < mData.size(); i++) {
                mapPos = i;
                Dispensary dispensary = mData.get(i);
                if (dispensary.getChannels() != null && dispensary.getChannels().size() > 0) {
                    if (dispensary.getChannels().contains(type)) {
                        LoadViews2(dispensary, mapPos);
                    }
                }
            }
        }
    }

    private void LoadViews2(final Dispensary dispensary, final int mapPos) {

        new AsyncTask<Void, Void, GlideDrawable>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected GlideDrawable doInBackground(Void... params) {
                try {
                    return Glide.
                            with(mContext).
                            load(dispensary.getLogo().getSmall())
                            .bitmapTransform(new CropCircleTransformation(mContext))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(100, 100).get();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(GlideDrawable theBitmap) {
                super.onPostExecute(theBitmap);
                latLng = new LatLng(dispensary.getLocation().getCoords().getLatitude(), dispensary.getLocation().getCoords().getLongitude());
                mSelectedId = dispensary.getId();
                MarkerOptions marker = new MarkerOptions().position(latLng);
                // Bitmap mBitmap = addBorderToBitmap(drawableToBitmap(theBitmap));
                BitmapDescriptor markerIcon = getMarkerIconFromDrawable(theBitmap);
                marker.icon(markerIcon);
                marker.snippet(dispensary.getId());
                ArrayList<Marker> markers = new ArrayList<Marker>();
                markers.add(mMap.addMarker(marker));
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(final Marker marker) {
                        mSelectedId = marker.getSnippet();
//                        try {
//                            addBorderArroundMarker(mSelectedId);
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        return false;
                    }
                });
                if (mapPos == mData.size() - 1) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
                    Log.i("added33", mapPos + "");
                }
                Log.i("added", mapPos + "");
            }
        }.execute();
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void setMapAndList(NearByVo nearByVo, String type) {
        mData = new ArrayList<>();
        for (int i = 0; i < nearByVo.getDispensaries().size(); i++) {
            Dispensary dispensary = nearByVo.getDispensaries().get(i).getDispensary();
            if (dispensary.getChannels() != null && dispensary.getChannels().size() > 0) {
                if (dispensary.getChannels().contains(type)) {
                    mData.add(dispensary);
                }
            }
        }
        if (mData.size() > 0) {
            AddingMarkers(mData, type);
            AddAdaptor(mData, type);
        } else {
            Dailogs.ShowToast(mContext, "no item found", Toast.LENGTH_LONG);
        }

    }

    private void AddAdaptor(ArrayList<Dispensary> mData, String type) {
        discovAdapter = new DiscovAdapter(mContext, mData, type);
        recyclerView = (ExpandedRecyclerView) findViewById(R.id.recycler_view);
        listener = new RecyclerTouchListener(mContext, recyclerView, new MenuItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(new Intent(mContext, DispensaryInfoActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(mContext, "long clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.addOnItemTouchListener(listener);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(discovAdapter);
        recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (AppContoller.nearByVo != null)
            setMapAndList(AppContoller.nearByVo, Constants.STORE_FRONT);
    }

    private void ToolbarSettings() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void InitComponents() {

        SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

        mtxtDelivery = (RegularTextView) findViewById(R.id.toolbar_delivery);
        mtxtStoreFront = (BoldSFTextView) findViewById(R.id.toolbar_store_front);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setRippleColor(getResources().getColor(R.color.white));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsMapView) {
                    recyclerView.setVisibility(View.VISIBLE);
                    mapView.setVisibility(View.GONE);
                    fab.setImageResource(R.mipmap.fab_map);
                    IsMapView = false;
                } else {
                    recyclerView.setVisibility(View.GONE);
                    mapView.setVisibility(View.VISIBLE);
                    fab.setImageResource(R.mipmap.fab_list);
                    IsMapView = true;
                }
            }
        });


        mtxtStoreFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mViewPager.setCurrentItem(0, true);
                Typeface bold = Typeface.createFromAsset(mContext.getAssets(), "fonts/SFUIText-Heavy.otf");
                Typeface regular = Typeface.createFromAsset(mContext.getAssets(), "fonts/SFUIText-Regular.otf");
                mtxtDelivery.setTypeface(regular);
                mtxtStoreFront.setTypeface(bold);
                mMap.clear();
                setMapAndList(AppContoller.nearByVo, Constants.STORE_FRONT);
            }
        });

        mtxtDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mViewPager.setCurrentItem(1, true);
                Typeface bold = Typeface.createFromAsset(mContext.getAssets(), "fonts/SFUIText-Heavy.otf");
                Typeface regular = Typeface.createFromAsset(mContext.getAssets(), "fonts/SFUIText-Regular.otf");
                mtxtDelivery.setTypeface(bold);
                mtxtStoreFront.setTypeface(regular);
                mMap.clear();
                setMapAndList(AppContoller.nearByVo, Constants.DELIVERY);

            }
        });
    }

    private void AddListener() {
        mDiscover.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mDeals.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mQr.setOnClickListener(this);
        mDiscover.setImageResource(R.mipmap.dispensary1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_btn_dispensory:
                //startActivity(new Intent(mContext, DiscoverActivity.class));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_discover, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
