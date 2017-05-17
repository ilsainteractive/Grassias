package com.ilsa.grassis.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.MenuGalleryAdapter;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.library.SFUITextBold;
import com.ilsa.grassis.library.ThinTextView;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

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

    @BindView(R.id.editProfile)
    MediumTextView mtxtMenuView;

    @BindView(R.id.dispensory_img)
    ImageView mDispensaryImg;

    @BindView(R.id.dispensaryInfo_txt_detail)
    ThinTextView mDescription;

    @BindView(R.id.dispensary_addresse)
    RegularTextView mAddress;

    @BindView(R.id.dispensary_phone)
    RegularTextView mPhone;

    @BindView(R.id.dispensary_mail)
    RegularTextView mMail;

    @BindView(R.id.dispensary_timing)
    RegularTextView mTimings;

    @BindView(R.id.dispensary_fb)
    ImageView mFacebook;

    @BindView(R.id.dispensary_twitter)
    ImageView mTwitter;

    @BindView(R.id.dispensary_instagram)
    ImageView mInstagarm;

    @BindView(R.id.dispensary_youtube)
    ImageView mYoutube;

    @BindView(R.id.add_to_fav)
    LinearLayout mAddToFav;

    private GoogleMap mMap;
    private SearchView mSearchView;
    private String mDispensary_id;
    private Dispensary mDispensary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensary_info);
        ButterKnife.bind(this);

        mContext = this;
        SyncData();
        initToolBar();
        InitComponents();
        initViews();
        addListener();
    }

    private void SyncData() {
        mDispensary_id = getIntent().getStringExtra("dispensary_id");

        if (AppContoller.nearByVo == null) {
            if (Helper.checkInternetConnection(mContext)) {
                //getNearByDespensories(new Location("asd"));
            } else
                Dailogs.ShowToast(mContext, getString(R.string.no_internet_msg), Constants.SHORT_TIME);
        } else {
            for (int i = 0; i < AppContoller.nearByVo.getDispensaries().size(); i++) {
                if (AppContoller.nearByVo.getDispensaries().get(i).getDispensary().getId().equalsIgnoreCase(mDispensary_id)) {
                    mDispensary = AppContoller.nearByVo.getDispensaries().get(i).getDispensary();
                    break;
                }
            }
        }
    }

    private void addListener() {

        mtxtMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MenuActivity.class));
            }
        });
        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mContext.getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/<id_here>")));
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mDispensary.getFacebook())));
                }
            }
        });
        mTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDispensary.getTwitter()));
                startActivity(browserIntent);
            }
        });
        mInstagarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDispensary.getInstagram()));
                mContext.startActivity(intent);
            }
        });
        mYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDispensary.getYoutube()));
                mContext.startActivity(intent);
            }
        });

        mAddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dailogs.ShowToast(mContext, "Favorites Api is not working", Toast.LENGTH_LONG);
            }
        });
    }

    public void initToolBar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mtxtToolbarTitle.setText(mDispensary.getName());
        if (mDispensary.getSchedule().getMon_open() != null)
            mtxtToolbarSubTitle.setText((Integer.parseInt(mDispensary.getLocation().getNearby_radius()) / 1000)
                    + " miles | OPEN till " + mDispensary.getSchedule().getMon_open().substring(mDispensary.getSchedule().getMon_open().indexOf("T") + 1, mDispensary.getSchedule().getMon_open().indexOf("T") + 6));
        else {
            mtxtToolbarSubTitle.setText((Integer.parseInt(mDispensary.getLocation().getNearby_radius()) / 1000)
                    + " miles");
        }
    }

    private void InitComponents() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        for (int i = 0; i < 5; i++) {
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

        if (mDispensary.getDescription() != null && !mDispensary.getDescription().equalsIgnoreCase(""))
            mDescription.setText(mDispensary.getDescription());
        else
            mDescription.setText("No description found.");

        if (mDispensary.getLocation().getAddress() != null && !mDispensary.getLocation().getAddress().equalsIgnoreCase(""))
            mAddress.setText(mDispensary.getLocation().getAddress());
        else
            mAddress.setText("No location found.");

        if (mDispensary.getPhone() != null && !mDispensary.getPhone().equalsIgnoreCase(""))
            mPhone.setText(mDispensary.getPhone());
        else
            mPhone.setText("No phone found.");

        if (mDispensary.getEmail() != null && !mDispensary.getEmail().equalsIgnoreCase(""))
            mMail.setText(mDispensary.getEmail());
        else
            mMail.setText("No email found.");

        if (mDispensary.getSchedule().getMon_open() != null)
            mTimings.setText(mDispensary.getSchedule().getMon_open().substring(mDispensary.getSchedule().getMon_open().indexOf("T") + 1, mDispensary.getSchedule().getMon_open().indexOf("T") + 6));
        else
            mTimings.setText("No schedule found.");

        Glide.with(mContext).
                load(mDispensary.getLogo().getSmall())
                .bitmapTransform(new CropCircleTransformation(mContext))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mDispensaryImg);
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
        if (mDispensary != null) {

            new AsyncTask<Void, Void, GlideDrawable>() {

                @Override
                protected GlideDrawable doInBackground(Void... params) {
                    try {
                        return Glide.
                                with(mContext).
                                load(mDispensary.getLogo().getSmall())
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
                    LatLng latLng = new LatLng(mDispensary.getLocation().getCoords().getLatitude(), mDispensary.getLocation().getCoords().getLongitude());

                    MarkerOptions marker = new MarkerOptions().position(latLng);
                    // Bitmap mBitmap = addBorderToBitmap(drawableToBitmap(theBitmap));
                    BitmapDescriptor markerIcon = Helper.getMarkerIconFromDrawable(theBitmap);
                    marker.icon(markerIcon);
                    mMap.addMarker(marker);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                }
            }.execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dispensory, menu);
        return true;
    }
}
