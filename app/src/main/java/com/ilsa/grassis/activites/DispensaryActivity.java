package com.ilsa.grassis.activites;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
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
import com.ilsa.grassis.adapters.SearchAdapter;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.library.SFUITextBold;
import com.ilsa.grassis.rootvo.NearByVo;
import com.ilsa.grassis.rootvo.UserDataVO;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;
import com.ilsa.grassis.utils.ShPrefsHelper;
import com.ilsa.grassis.vo.SignUpVO;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.ilsa.grassis.library.AppContoller.DeadActivities;
import static com.ilsa.grassis.library.AppContoller.nearByVo;

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

    @BindView(R.id.dispensary_map_btm_section_txt_next)
    RegularTextView mtxtBtmSecNext;

    @BindView(R.id.home_lv_bottom_section_2)
    LinearLayout mLayoutBottomSection;

    @BindView(R.id.home_lv_bottom_section_2_img)
    ImageView mImageSelectDispensory;

    @BindView(R.id.home_lv_bottom_section_2_title)
    SFUITextBold mtxtSelecTitle;

    @BindView(R.id.home_lv_bottom_section_2_subTitle)
    RegularTextView mtxtSelecAddresse;


    private int mapPos = -1;
    private LatLng latLng = null;

    private static final int REQUEST_RUNTIME_PERMISSION = 123;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    private SignUpVO signUpVO;
    private Menu menu;
    private List<String> items;
    private HashMap<String, Bitmap> markersBitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensary);
        ButterKnife.bind(this);

        mActivity = this;
        mContext = this;
        DeadActivities.add(this);
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

    /**
     * Init toolbar.
     */
    public void initToolBar() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.mipmap.signup_back_arrow);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Layout components initializing and bridging here.
     */
    private void InitComponents() {

        mLayoutBottomSection.setVisibility(View.INVISIBLE);
        mtxtSelecTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 6.2)); //6.5 psd
        mtxtSelecAddresse.setTextSize(Helper.getFontSize(mContext.getResources(), 3.6));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
                    else
                        Dailogs.ShowToast(mContext, getString(R.string.select_dispensary), Constants.SHORT_TIME);

                } else {
                    Dailogs.ShowToast(mContext, getString(R.string.no_internet_msg), Constants.SHORT_TIME);
                }
            }
        });
    }

    private void HitSignUpRequest() {
        try {

            final ProgressDialog pd = new ProgressDialog(mContext);
            pd.setMessage(getString(R.string.signing_up_msg));
            pd.setCancelable(false);
            pd.show();

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n  \"user\": {\n    \"email\": \"" + signUpVO.getEmail() + "\",\n    \"first_name\": \"" + signUpVO.getFirst_name() + "\",\n    \"last_name\": \"" + signUpVO.getLast_name() + "\",\n    \"password\": \"" + signUpVO.getPassword() + "\",\n    \"username\": \"" + signUpVO.getUsername() + "\"\n  }\n}");
            Request request = new Request.Builder()
                    .url("http://kushmarketing.herokuapp.com/api/users")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("accept", "application/vnd.kush_marketing.com; version=1")
                    .addHeader("authorization", "Bearer 977fa5b9a9e56fa2b2e4cfcad7a626b760c7752a4da9dfebb87c3cd0393f4e31")
                    .addHeader("x-dispensary-id", mSelectedId)
                    .addHeader("cache-control", "no-cache")
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
                public void onResponse(Call call, final Response response) throws IOException {
                    final String res = response.body().string().toString();
                    Log.i("signup respnse", res);
                    if (!response.isSuccessful()) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    pd.dismiss();
                                    JSONObject jsonObject = new JSONObject(res);
                                    JSONObject error = jsonObject.getJSONObject("error");
                                    String message = error.get("message").toString();
                                    Dailogs.ShowToast(mContext, "Email id " + message, Constants.LONG_TIME);
                                } catch (Exception e) {
                                    Log.i("problem", e.toString());
                                }
                            }
                        });
                    } else {
                        try {
                            Gson gson = new GsonBuilder().create();
                            AppContoller.userData = gson.fromJson(res, UserDataVO.class);
                            if (AppContoller.userData.getUser() != null) {
                                ShPrefsHelper.setSharedPreferenceString(mContext, Constants.USER_VO, res);
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        if (AppContoller.userData.getUser().getState_change().equalsIgnoreCase("registered")) {
                                            Intent i = new Intent(mContext, HomeActivity.class);
                                            startActivity(i);
                                            for (Activity activity : DeadActivities) {
                                                activity.finish();
                                            }
                                            Dailogs.ShowToast(mContext, getString(R.string.successfully_sign_up), Constants.LONG_TIME);
                                        } else {
                                            Dailogs.ShowToast(mContext, getString(R.string.something_went_wrong_ry_again), Constants.LONG_TIME);
                                        }
                                    }
                                });
                            }
                        } catch (Exception e) {
                            pd.dismiss();
                            Log.i("problem", e.toString());
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.i("problem", e.toString());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        this.menu = menu;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    loadHistory(query);
                    return true;
                }
            });
        }
        return true;
    }

    // History
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void loadHistory(String query) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            String[] columns = new String[]{"_id", "text"};
            Object[] temp = new Object[]{0, "default"};

            MatrixCursor cursor = new MatrixCursor(columns);
            items = new ArrayList<String>();

            for (int i = 0; i < nearByVo.getDispensaries().size(); i++) {

                if (nearByVo.getDispensaries().get(i).getDispensary().getName().toLowerCase().contains(query)) {
                    temp[0] = nearByVo.getDispensaries().get(i).getDispensary().getId();
                    temp[1] = nearByVo.getDispensaries().get(i).getDispensary().getName();
                    items.add((String) temp[1]);
                    cursor.addRow(temp);
                }
            }
            //SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            final SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
            search.setSuggestionsAdapter(new SearchAdapter(this, cursor, items));
            search.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
                @Override
                public boolean onSuggestionSelect(int position) {
                    return false;
                }

                @Override
                public boolean onSuggestionClick(int position) {
                    Cursor cursor = search.getSuggestionsAdapter().getCursor();
                    if (cursor.moveToPosition(position)) {
                        String selectedItem = cursor.getString(0);
                        for (int i = 0; i < nearByVo.getDispensaries().size(); i++) {
                            if (nearByVo.getDispensaries().get(i).getDispensary().getId().equalsIgnoreCase(selectedItem)) {
                                latLng = new LatLng(nearByVo.getDispensaries().get(i).getDispensary().getLocation().getCoords().getLatitude(), nearByVo.getDispensaries().get(i).getDispensary().getLocation().getCoords().getLongitude());
                                UpdateBannerSection(nearByVo.getDispensaries().get(i).getDispensary().getId());
                                if (mMap != null)
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
                            }
                        }
                    }
                    return false;
                }
            });
        }
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
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            } else {
                //If everything went fine lets get latitude and longitude
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
                getDispensaryList(location);
            }
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

    private void getDispensaryList(Location location) {

        try {
            final ProgressDialog pd = new ProgressDialog(mContext);
            pd.setMessage(getString(R.string.loading_dispensaries));
            pd.setCancelable(false);
            pd.show();

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
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Dailogs.ShowToast(mContext, getString(R.string.something_went_wrong_ry_again), Constants.LONG_TIME);
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
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
                            nearByVo = gson.fromJson(response.body().string().toString(), NearByVo.class);
                            try {
                                if (mMap != null) {
                                    markersBitmaps = new LinkedHashMap<String, Bitmap>();
                                    for (int i = 0; i < nearByVo.getDispensaries().size(); i++) {
                                        mapPos = i;
                                        LoadView(nearByVo.getDispensaries().get(i).getDispensary(), mapPos);
                                    }
                                    pd.dismiss();
                                }
                            } catch (Exception e) {
                                pd.dismiss();
                                Log.i("problem", e.toString());
                            }
                        } catch (Exception e) {
                            pd.dismiss();
                            Log.i("problem", e.toString());
                        }
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    private void LoadView(final Dispensary dispensary, final int mapPos) {

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    return Glide.
                            with(mContext).
                            load(dispensary.getLogo().getSmall()).asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(100, 100).get();

//                    Glide.with(mContext).load(dispensary.getLogo().getSmall()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
//                        @Override
//                        protected void setResource(Bitmap resource) {
//                            RoundedBitmapDrawable circularBitmapDrawable =
//                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                            circularBitmapDrawable.setCircular(true);
//                            imageView.setImageDrawable(circularBitmapDrawable);
//                        }
//                    });
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap theBitmap) {
                super.onPostExecute(theBitmap);

                latLng = new LatLng(dispensary.getLocation().getCoords().getLatitude(), dispensary.getLocation().getCoords().getLongitude());
                mSelectedId = dispensary.getId();

                MarkerOptions marker = new MarkerOptions().position(latLng);
                markersBitmaps.put(dispensary.getId(), theBitmap);
                Bitmap mBitmap = addBorderToBitmap(theBitmap, getColor(R.color.selected));
                marker.icon(BitmapDescriptorFactory.fromBitmap(mBitmap));
                marker.snippet(dispensary.getId());
                mMap.addMarker(marker);

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                    @Override
                    public boolean onMarkerClick(final Marker marker) {

                        mMap.clear();
                        for (int i = 0; i < nearByVo.getDispensaries().size(); i++) {
                            Dispensary dispensary1 = nearByVo.getDispensaries().get(i).getDispensary();

                            latLng = new LatLng(dispensary1.getLocation().getCoords().getLatitude(), dispensary1.getLocation().getCoords().getLongitude());
                            MarkerOptions markr = new MarkerOptions().position(latLng);

                            if (dispensary1.getId().equalsIgnoreCase(marker.getSnippet())) {
                                mSelectedId = dispensary1.getId();
                                UpdateBannerSection(mSelectedId);
                                Bitmap mBitmap = addBorderToBitmap(markersBitmaps.get(dispensary1.getId()), getColor(R.color.baseColor));
                                markr.icon(BitmapDescriptorFactory.fromBitmap(mBitmap));
                                markr.snippet(dispensary1.getId());
                                mMap.addMarker(markr);

                            } else {

                                Bitmap mBitmap = addBorderToBitmap(markersBitmaps.get(dispensary1.getId()), getColor(R.color.selected));
                                markr.icon(BitmapDescriptorFactory.fromBitmap(mBitmap));
                                markr.snippet(dispensary1.getId());
                                mMap.addMarker(markr);
                            }
                        }
                        return false;
                    }
                });

                if (mapPos == nearByVo.getDispensaries().size() - 1) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
                    UpdateBannerSection(mSelectedId);
                }
                Log.i("added", mapPos + "");
            }
        }.execute();
    }

    protected Bitmap addBorderToBitmap(Bitmap bitmap, int color) {
        if (bitmap != null) {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            int radius = Math.min(h / 2, w / 2);
            Bitmap output = Bitmap.createBitmap(w + 8, h + 8, Bitmap.Config.ARGB_8888);

            Paint p = new Paint();
            p.setAntiAlias(true);

            Canvas c = new Canvas(output);
            c.drawARGB(1, 1, 1, 1);
            p.setStyle(Paint.Style.FILL);

            c.drawCircle((w / 2) + 4, (h / 2) + 4, radius, p);

            p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            c.drawBitmap(bitmap, 4, 4, p);
            p.setXfermode(null);
            p.setStyle(Paint.Style.STROKE);
            p.setColor(color);
            //p.setColor(Color.GRAY);
            p.setStrokeWidth(3);
            c.drawCircle((w / 2) + 4, (h / 2) + 4, radius, p);

            return output;
        }
        return null;
    }

    private void UpdateBannerSection(String id) {

        for (int i = 0; i < nearByVo.getDispensaries().size(); i++) {
            if (nearByVo.getDispensaries().get(i).getDispensary().getId().equalsIgnoreCase(id)) {
                Glide.with(mContext)
                        .load(nearByVo.getDispensaries().get(i).getDispensary().getLogo().getMedium())
                        .override(200, 200).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                                return false;
                            }
                        }).placeholder(R.mipmap.flower1).into(mImageSelectDispensory);

                mtxtSelecTitle.setText(nearByVo.getDispensaries().get(i).getDispensary().getName());
                mtxtSelecAddresse.setText(nearByVo.getDispensaries().get(i).getDispensary().getLocation().getAddress());
                mLayoutBottomSection.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions,
                                           int[] grantResults) {
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
}
