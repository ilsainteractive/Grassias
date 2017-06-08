package com.ilsa.grassis.adapters;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.model.LatLng;
import com.ilsa.grassis.R;
import com.ilsa.grassis.activites.AddToCart;
import com.ilsa.grassis.activites.MenuActivity;
import com.ilsa.grassis.activites.PointBalanceActivity;
import com.ilsa.grassis.apivo.Dispensaries;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.apivo.Features;
import com.ilsa.grassis.apivo.Products;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.library.ThinTextView;
import com.ilsa.grassis.rootvo.NearByVo;
import com.ilsa.grassis.utils.CircularTextView;
import com.ilsa.grassis.utils.Dailogs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.relex.circleindicator.CircleIndicator;

/**
 * The type Menu item adapter.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private NearByVo dataList;
    private Context mContext;
    private double currentLatitude;
    private double currentLongitude;

    /**
     * Instantiates a new Menu item adapter.
     *
     * @param mContext the mContext
     * @param dataList the menu list
     */
    public HomeAdapter(Context mContext, NearByVo dataList, double currentLatitude, double currentLongitude) {
        this.dataList = dataList;
        this.mContext = mContext;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        //sorting
       /* Collections.sort(dataList.getDispensaries(), new Comparator<Dispensaries>() {
            @Override
            public int compare(Dispensaries o1, Dispensaries o2) {
                return o2.getDispensary().getId().compareTo(o1.getDispensary().getId());
            }
        });*/
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_home_lv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Dispensary dispensary = dataList.getDispensaries().get(position).getDispensary();

        LoadImages(holder, dispensary);
        setTexts(holder, dispensary);
        AddListeners(holder, dispensary, dataList);
        initViews(holder, dataList, position);
    }

    private void AddListeners(MyViewHolder mHolder, final Dispensary dispensary, final NearByVo nearByVo) {
        mHolder.mProductsDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckIfProductsExist(dispensary, nearByVo)) {
                    Intent i = new Intent(mContext, MenuActivity.class);
                    i.putExtra("dispensary_id", dispensary.getId());
                    i.putExtra("dispensary_photo", dispensary.getPhotos().get(0).getPhoto().getLarge());
                    mContext.startActivity(i);
                } else {
                    Dailogs.ShowToast(mContext, "No products found for this denpensory", Toast.LENGTH_LONG);
                }
            }
        });
    }

    private boolean CheckIfProductsExist(Dispensary dispensary, NearByVo nearByVo) {
        for (Products product : nearByVo.getProducts()) {
            if (dispensary.getId().equalsIgnoreCase(product.getDispensary_id()))
                return true;
        }
        return false;
    }

    private void LoadImages(final MyViewHolder mHolder, Dispensary dispensary) {

        if (dispensary.getPhotos().size() != 0) {
            mHolder.progressBar.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(dispensary.getPhotos().get(0).getPhoto().getLarge())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            mHolder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            mHolder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(mHolder.mDespensoryPhoto);
        }
        if (dispensary.getFeatures() != null && dispensary.getFeatures().size() > 0) {
            for (Features feature : dispensary.getFeatures()) {
                if (feature.getDeal() != null && feature.getDeal().getDeal_type() != null) {

                    mHolder.offerProgress.setVisibility(View.VISIBLE);
                    mHolder.mOfferOff.setText(feature.getDeal().getDeal_type());
                    //mHolder.mOfferNo.setText(feature.getDeal().getDeal_type().substring(0, feature.getDeal().getDeal_type().indexOf("%")));
                    //mHolder.mOfferOff.setText(feature.getDeal().getDeal_type().substring(feature.getDeal().getDeal_type().indexOf("%") + 1, feature.getDeal().getDeal_type().length()));
                    mHolder.mOfferName.setText(feature.getDeal().getTitle());

                    Glide.with(mContext).load(feature.getDeal().getBackground().getLarge())
                            .thumbnail(0.5f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    mHolder.offerProgress.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    mHolder.offerProgress.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(mHolder.mOfferPhoto);
                } else {
                    //mHolder.mOfferOff.setVisibility(View.INVISIBLE);
                    //mHolder.mOfferName.setText("No offer available.");
                }
            }
        } else {
            mHolder.mOfferOff.setVisibility(View.INVISIBLE);
            mHolder.mOfferName.setText(R.string.no_offer_ava);
        }
        Glide.with(mContext).load(dispensary.getLogo().getMedium())
                .bitmapTransform(new CropCircleTransformation(mContext))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mHolder.mDespensoryLogo);
    }

    private void setTexts(MyViewHolder mHolder, Dispensary dispensary) {
        mHolder.mDespensoryTitle.setText(dispensary.getName());
        int disInMiles = distanceOfLocation(this.currentLatitude, this.currentLongitude, dispensary.getLocation().getCoords().getLatitude(), dispensary.getLocation().getCoords().getLongitude());
        if (dispensary.getSchedule().getMon_open() != null) {

            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            switch (day) {
                case Calendar.SUNDAY:
                    mHolder.mDespensoryAddresse.setText(disInMiles
                            + " miles | OPEN till " + timeFormat24To12(dispensary.getSchedule().getSun_close().substring(dispensary.getSchedule().getSun_close().indexOf("T") + 1, dispensary.getSchedule().getSun_close().indexOf("T") + 6)));
                    break;
                case Calendar.MONDAY:
                    mHolder.mDespensoryAddresse.setText(disInMiles
                            + " miles | OPEN till " + timeFormat24To12(dispensary.getSchedule().getMon_close().substring(dispensary.getSchedule().getMon_close().indexOf("T") + 1, dispensary.getSchedule().getMon_close().indexOf("T") + 6)));
                    break;
                case Calendar.TUESDAY:
                    mHolder.mDespensoryAddresse.setText(disInMiles
                            + " miles | OPEN till " + timeFormat24To12(dispensary.getSchedule().getTue_close().substring(dispensary.getSchedule().getTue_close().indexOf("T") + 1, dispensary.getSchedule().getTue_close().indexOf("T") + 6)));
                    break;
                case Calendar.WEDNESDAY:
                    mHolder.mDespensoryAddresse.setText(disInMiles
                            + " miles | OPEN till " + timeFormat24To12(dispensary.getSchedule().getWed_close().substring(dispensary.getSchedule().getWed_close().indexOf("T") + 1, dispensary.getSchedule().getWed_close().indexOf("T") + 6)));
                    break;
                case Calendar.THURSDAY:
                    mHolder.mDespensoryAddresse.setText(disInMiles
                            + " miles | OPEN till " + timeFormat24To12(dispensary.getSchedule().getThu_close().substring(dispensary.getSchedule().getThu_close().indexOf("T") + 1, dispensary.getSchedule().getThu_close().indexOf("T") + 6)));
                    break;
                case Calendar.FRIDAY:
                    mHolder.mDespensoryAddresse.setText(disInMiles
                            + " miles | OPEN till " + timeFormat24To12(dispensary.getSchedule().getFri_close().substring(dispensary.getSchedule().getFri_close().indexOf("T") + 1, dispensary.getSchedule().getFri_close().indexOf("T") + 6)));
                    break;
                case Calendar.SATURDAY:
                    //dispensary.getLocation().getCoords().getLatitude()
                    mHolder.mDespensoryAddresse.setText(disInMiles
                            + " miles | OPEN till " + timeFormat24To12(dispensary.getSchedule().getSat_close().substring(dispensary.getSchedule().getSat_close().indexOf("T") + 1, dispensary.getSchedule().getSat_close().indexOf("T") + 6)));
                    break;
            }
        } else {
            mHolder.mDespensoryAddresse.setText(disInMiles
                    + " miles");
        }
    }

    private int distanceOfLocation(double lat1, double lon1, double lat2, double lon2) {
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lon1);

        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lon2);


        float meter = loc1.distanceTo(loc2);
        return (int) (meter * 0.00062137119);
    }

    private String timeFormat24To12(String timein24Format) {
        String timein12Format = null;
        try {

            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj = sdf.parse(timein24Format);
            timein12Format = new SimpleDateFormat("K:mm a").format(dateObj);
        } catch (Exception e) {
        }

        return timein12Format;
    }

    private void initViews(MyViewHolder mHolder, NearByVo nearByVo, int pos) {

//        ArrayList<Products> list = new ArrayList<>();
//        for (Products product : nearByVo.getProducts()) {
//            if (nearByVo.getDispensaries().get(pos).getDispensary().getId().equalsIgnoreCase(product.getDispensary_id())) {
//                list.add(product);
//            }
//        }
        ArrayList<Features> list = new ArrayList<>();
        list = dataList.getDispensaries().get(pos).getDispensary().getFeatures();

        if (list.size() > 0) {
            mHolder.mProductPagerLayout.setVisibility(View.VISIBLE);
            mHolder.mNoProductLayout.setVisibility(View.GONE);
            MenuGalleryAdapterFeatures adapter = new MenuGalleryAdapterFeatures(mContext);
            adapter.setData(list);
            mHolder.pager.setAdapter(adapter);

            mHolder.pageIndicatorView.setViewPager(mHolder.pager);
            adapter.registerDataSetObserver(mHolder.pageIndicatorView.getDataSetObserver());
        } else {
            mHolder.mNoProductLayout.setVisibility(View.VISIBLE);
            mHolder.mProductPagerLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.getDispensaries().size();
    }

    /**
     * The type My view holder.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mDespensoryPhoto;
        ImageView mDespensoryCart;
        CircularTextView total_added_item_in_cart;
        ImageView mDespensoryDetail;
        ImageView mDespensoryLogo;
        ImageView mOfferPhoto;
        ImageView mProductsDetail;

        RegularTextView mDespensoryTitle;
        RegularTextView mDespensoryAddresse;

        LinearLayout mProductPagerLayout, mNoProductLayout;
        ProgressBar progressBar, offerProgress;

        CircleIndicator pageIndicatorView;
        ViewPager pager;


        BoldSFTextView mOfferNo;
        ThinTextView mOfferOff, mOfferName;


        /**
         * Instantiates a new My view holder.
         *
         * @param view the view
         */
        public MyViewHolder(View view) {
            super(view);

            mDespensoryPhoto = (ImageView) view.findViewById(R.id.home_lv_top_section_img);
            mDespensoryCart = (ImageView) view.findViewById(R.id.home_list_box_cart);
            total_added_item_in_cart = (CircularTextView) view.findViewById(R.id.total_added_item_in_cart);
            mDespensoryDetail = (ImageView) view.findViewById(R.id.home_list_box_detail);
            mDespensoryLogo = (ImageView) view.findViewById(R.id.home_lv_bottom_section_2_img);
            mOfferPhoto = (ImageView) view.findViewById(R.id.home_lv_bottom_section_offer_img);
            mProductsDetail = (ImageView) view.findViewById(R.id.home_list_box_detail);

            mDespensoryTitle = (RegularTextView) view.findViewById(R.id.home_list_top_title);
            mDespensoryAddresse = (RegularTextView) view.findViewById(R.id.home_list_top_sub_title);
            progressBar = (ProgressBar) view.findViewById(R.id.progress);
            offerProgress = (ProgressBar) view.findViewById(R.id.progress_offer);
            pager = (ViewPager) view.findViewById(R.id.viewPager);
            mProductPagerLayout = (LinearLayout) view.findViewById(R.id.home_lv_bottom_pager);
            mNoProductLayout = (LinearLayout) view.findViewById(R.id.home_lv_bottom_pager_no_item);
            pageIndicatorView = (CircleIndicator) view.findViewById(R.id.indicator);

            mOfferNo = (BoldSFTextView) view.findViewById(R.id.home_lv_bottom_section_2_txt_per);
            mOfferOff = (ThinTextView) view.findViewById(R.id.home_lv_bottom_section_2_txt_off);
            mOfferName = (ThinTextView) view.findViewById(R.id.home_lv_bottom_section_2_txt_products);

            mDespensoryCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddToCart.class);
                    mContext.startActivity(intent);
                }
            });

            total_added_item_in_cart.setSolidColor("#f0f0f0");
            total_added_item_in_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddToCart.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}