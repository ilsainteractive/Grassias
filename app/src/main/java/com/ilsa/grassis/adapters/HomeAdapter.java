package com.ilsa.grassis.adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ilsa.grassis.R;
import com.ilsa.grassis.apivo.Dispensaries;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.apivo.Products;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.rootvo.NearByVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import me.relex.circleindicator.CircleIndicator;

/**
 * The type Menu item adapter.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private NearByVo dataList;
    private Context mContext;

    /**
     * Instantiates a new Menu item adapter.
     *
     * @param mContext the mContext
     * @param dataList the menu list
     */
    public HomeAdapter(Context mContext, NearByVo dataList) {
        this.dataList = dataList;
        this.mContext = mContext;
        Collections.sort(dataList.getDispensaries(), new Comparator<Dispensaries>() {
            @Override
            public int compare(Dispensaries o1, Dispensaries o2) {
                return o2.getDispensary().getId().compareTo(o1.getDispensary().getId());
            }
        });
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
        initViews(holder, dataList, position);
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
    }

    private void setTexts(MyViewHolder mHolder, Dispensary dispensary) {
        mHolder.mDespensoryTitle.setText(dispensary.getName());
        if (dispensary.getSchedule().getMon_open() != null)
            mHolder.mDespensoryAddresse.setText((Integer.parseInt(dispensary.getLocation().getNearby_radius()) / 1000)
                    + " miles | OPEN till " + dispensary.getSchedule().getMon_open().substring(dispensary.getSchedule().getMon_open().indexOf("T") + 1, dispensary.getSchedule().getMon_open().indexOf("T") + 6));
        else {
            mHolder.mDespensoryAddresse.setText((Integer.parseInt(dispensary.getLocation().getNearby_radius()) / 1000)
                    + " miles");
        }
    }

    private void initViews(MyViewHolder mHolder, NearByVo nearByVo, int pos) {

        ArrayList<Products> list = new ArrayList<>();
        for (Products product : nearByVo.getProducts()) {
            if (nearByVo.getDispensaries().get(pos).getDispensary().getId().equalsIgnoreCase(product.getDispensary_id())) {
                list.add(product);
            }
        }
        if (list.size() > 0) {
            mHolder.mProductPagerLayout.setVisibility(View.VISIBLE);
            mHolder.mNoProductLayout.setVisibility(View.GONE);
            MenuGalleryAdapter adapter = new MenuGalleryAdapter(mContext);
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
        ImageView mDespensoryDetail;

        RegularTextView mDespensoryTitle;
        RegularTextView mDespensoryAddresse;

        LinearLayout mProductPagerLayout, mNoProductLayout;
        ProgressBar progressBar;

        CircleIndicator pageIndicatorView;
        ViewPager pager;

        /**
         * Instantiates a new My view holder.
         *
         * @param view the view
         */
        public MyViewHolder(View view) {
            super(view);

            mDespensoryPhoto = (ImageView) view.findViewById(R.id.home_lv_top_section_img);
            mDespensoryCart = (ImageView) view.findViewById(R.id.home_list_box_cart);
            mDespensoryDetail = (ImageView) view.findViewById(R.id.home_list_box_detail);
            mDespensoryTitle = (RegularTextView) view.findViewById(R.id.home_list_top_title);
            mDespensoryAddresse = (RegularTextView) view.findViewById(R.id.home_list_top_sub_title);
            progressBar = (ProgressBar) view.findViewById(R.id.progress);
            pager = (ViewPager) view.findViewById(R.id.viewPager);
            mProductPagerLayout = (LinearLayout) view.findViewById(R.id.home_lv_bottom_pager);
            mNoProductLayout = (LinearLayout) view.findViewById(R.id.home_lv_bottom_pager_no_item);
            pageIndicatorView = (CircleIndicator) view.findViewById(R.id.indicator);
        }
    }
}