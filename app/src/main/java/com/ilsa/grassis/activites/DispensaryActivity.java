package com.ilsa.grassis.activites;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilsa.grassis.R;
import com.ilsa.grassis.apivo.NearByVo;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;
import com.ilsa.grassis.vo.DispensaryVO;
import com.ilsa.grassis.vo.SignUpVO;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Dispensary activity contains map view of locations.
 */
public class DispensaryActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private Context mContext;
    private Toolbar mToolbar;
    private Activity mActivity;

    private GoogleMap mMap;
    private SearchView mSearchView;

    private String mSelectedId = "";

//    @BindView(R.id.dispensary_map_btm_section)
//    LinearLayout mBtmSec;

    //@BindView(R.id.dispensary_map_btm_section_next)
    //LinearLayout mBtmSecNext;

    @BindView(R.id.dispensary_map_btm_section_txt_next)
    RegularTextView mtxtBtmSecNext;

    //@BindView(R.id.dispensary_map_btm_section_title)
    //RegularTextView mtxtBtmSecTitle;

    //@BindView(R.id.dispensary_map_btm_section_sub_title)
    //ThinTextView mtxtBtmSecSubTitle;

    //@BindView(R.id.home_lv_bottom_section_2_title)
    //SFUITextBold mtxtSelecTitle;

    //@BindView(R.id.home_lv_bottom_section_2_subTitle)
    //RegularTextView mtxtSelecAddresse;

    private ArrayList<DispensaryVO> list;
    private NearByVo nearByVo;
    //Define a request code to send to Google Play services
    private static final int REQUEST_RUNTIME_PERMISSION = 123;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    private SignUpVO signUpVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensary);
        ButterKnife.bind(this);

        mActivity = this;
        mContext = this;
        signUpVO = (SignUpVO) getIntent().getExtras().get("SignUp_info");

        if (Helper.checkInternetConnection(mContext)) {
            if (Helper.isLocationEnabled(mContext)) {
                if (Helper.CheckPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {

                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .addApi(LocationServices.API)
                            .build();

                    // Create the LocationRequest object
                    mLocationRequest = LocationRequest.create()
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                            .setFastestInterval(1 * 1000); // 1 second, in milliseconds

                    initToolBar();
                    InitComponents();
                    AddListener();
                } else {
                    Helper.RequestPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_RUNTIME_PERMISSION);
                }
            } else {
                Helper.showSettingsAlert(mContext);
            }
        } else {
            Helper.ShowToast(mContext, getString(R.string.no_internet_msg));
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case REQUEST_RUNTIME_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    recreate();
                } else {
                    Helper.ShowToast(mContext, getString(R.string.no_permission_msg));
                }
                return;
            }
        }
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
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                Math.round(Helper.getFontSize(mContext.getResources(), 90)));
        //params.alignWithParent = RelativeLayout.ALIGN_PARENT_BOTTOM;
//        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        mBtmSec.setLayoutParams(params);

        // int padding = Math.round(Helper.getFontSize(mContext.getResources(), 19));
        //int paddingTopBtm = Math.round(Helper.getFontSize(mContext.getResources(), 19));
        //mBtmSecNext.setPadding(0, paddingTopBtm, 0, paddingTopBtm);
        //mtxtBtmSecNext.setTextSize(Helper.getFontSize(mContext.getResources(), 5.5));
        //mtxtBtmSecTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 6));
        //mtxtBtmSecSubTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 3.0));

        //mtxtSelecTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 6.2)); //6.5 psd
        //mtxtSelecAddresse.setTextSize(Helper.getFontSize(mContext.getResources(), 3.6));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.signup_back_arrow);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * Applying listeners to views.
     */
    private void AddListener() {

        mtxtBtmSecNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Helper.checkInternetConnection(mContext)) {
                    if (!mSelectedId.equalsIgnoreCase(""))
                        HitSignUpRequest();
                } else {
                    Dailogs.ShowToast(mContext, getString(R.string.no_internet_msg), Constants.SHORT_TIME);
                }
            }
        });
    }

    private void HitSignUpRequest() {
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n  \"user\": {\n    \"email\": \"" + signUpVO.getEmail() + "\",\n    \"first_name\": \"" + signUpVO.getFirst_name() + "\",\n    \"last_name\": \"" + signUpVO.getLast_name() + "\",\n    \"password\": \"" + signUpVO.getPassword() + "\",\n    \"username\": \"" + signUpVO.getFirst_name() + "\"\n  }\n}");
            Request request = new Request.Builder()
                    .url("http://kushmarketing.herokuapp.com/api/users")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("accept", "application/vnd.kush_marketing.com; version=1")
                    .addHeader("authorization", "Bearer 977fa5b9a9e56fa2b2e4cfcad7a626b760c7752a4da9dfebb87c3cd0393f4e31")
                    .addHeader("x-dispensary-id", mSelectedId)
                    .addHeader("cache-control", "no-cache")
                    //.addHeader("postman-token", "2ed679ee-9c6d-e4cc-4875-005e12e8e3b7")
                    .build();

            //Response response = client.newCall(request).execute();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Dailogs.ShowToast(mContext, getString(R.string.Something_went_wrong_ry_again), Constants.LONG_TIME);
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string().toString());
                                    JSONObject error = jsonObject.getJSONObject("error");
                                    String message = error.get("message").toString();
                                    Dailogs.ShowToast(mContext, "Email id " + message, Constants.LONG_TIME);
                                } catch (Exception e) {
                                }
                            }
                        });
                    } else {
                        try {
                            //Gson gson = new GsonBuilder().create();
                            //nearByVo = gson.fromJson(response.body().string().toString(), NearByVo.class);
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(mContext, HomeActivity.class);
                                    startActivity(i);
                                }
                            });
                        } catch (Exception e) {
                            Log.i("problem", e.toString());
                        }
                    }
                }
            });
        } catch (Exception e) {
        }
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
//        LatLng latLng = null;
//        for (int i = 0; i < list.size(); i++) {
//            latLng = new LatLng(list.get(i).getLat(), list.get(i).getLog());
////            MarkerOptions marker = new MarkerOptions().position(new LatLng(list.get(i).getLat(), list.get(i).getLog()))
////                    .title(list.get(i).getTitle()).snippet(list.get(i).getDesc());
////            marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.home_lv_bottom_icon));
//            MarkerOptions marker = new MarkerOptions().position(new LatLng(list.get(i).getLat(), list.get(i).getLog()));
//            marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.home_lv_bottom_icon));
//            Marker marker1 = mMap.addMarker(marker);
////            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
////
////                // Use default InfoWindow frame
////                @Override
////                public View getInfoWindow(Marker marker) {
////                    View v = mActivity.getLayoutInflater().inflate(R.layout.coustom_marker_layout, null);
////                    return v;
////                }
////
////
////                // Defines the contents of the InfoWindow
////                @Override
////                public View getInfoContents(Marker marker) {
////                    //View v = mActivity.getLayoutInflater().inflate(R.layout.coustom_marker_layout, null);
////                    // Getting reference to the TextView to set title
////                    // TextView note = (TextView) v.findViewById(R.id.note);
////                    //note.setText(marker.getTitle());
////                    // Returning the view containing InfoWindow contents
////                    return null;
////                }
////            });
////            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
////                @Override
////                public void onInfoWindowClick(Marker marker) {
////                    startActivity(new Intent(mContext, DispensaryInfoActivity.class));
////                }
////            });
//            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                @Override
//                public boolean onMarkerClick(Marker marker) {
//                    startActivity(new Intent(mContext, DispensaryInfoActivity.class));
//                    return false;
//                }
//            });
//        }
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Disconnect from API onPause()
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            getDispensaryList(location);
            Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }

    private void getDispensaryList(Location location) {

        list = new ArrayList<>();
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://kushmarketing.herokuapp.com/api/search?q=&limit=10&lat=" + location.getLatitude() + "&lng= " + location.getLongitude() + "&distance=20000&type=storefront")
                    .get()
                    .addHeader("accept", "application/vnd.kush_marketing.com; version=1")
                    //.addHeader("cache-control", "no-cache")
                    //.addHeader("postman-token", "cd1ee873-7298-d617-eb8a-f35ff86a84d9")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Dailogs.ShowToast(mContext, getString(R.string.Something_went_wrong_ry_again), Constants.LONG_TIME);
                            }
                        });
                    } else {
                        try {
                            Gson gson = new GsonBuilder().create();
                            nearByVo = gson.fromJson(response.body().string().toString(), NearByVo.class);
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mMap != null) {
                                        LatLng latLng = null;
                                        for (int i = 0; i < nearByVo.getDispensaries().length; i++) {
                                            latLng = new LatLng(nearByVo.getDispensaries()[i].getDispensaries().getLocation().getCoords().getLatitude(), nearByVo.getDispensaries()[i].getDispensaries().getLocation().getCoords().getLongitude());
                                            MarkerOptions marker = new MarkerOptions().position(latLng);

                                            marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_dailog_popup_img));
                                            marker.snippet(nearByVo.getDispensaries()[i].getDispensaries().getId());
                                            Marker marker1 = mMap.addMarker(marker);
                                        }
                                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                            @Override
                                            public boolean onMarkerClick(Marker marker) {

                                                marker.getTag();
                                                mSelectedId = marker.getSnippet();
                                                return false;
                                            }
                                        });
                                    }
                                }
                            });
                        } catch (Exception e) {
                            Log.i("problem", e.toString());
                        }
                    }
                }
            });


        } catch (Exception e) {
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }
}
