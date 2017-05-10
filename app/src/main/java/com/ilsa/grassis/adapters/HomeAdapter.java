package com.ilsa.grassis.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ilsa.grassis.R;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.apivo.Products;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.rootvo.NearByVo;

import java.util.ArrayList;
import java.util.List;

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
        mHolder.mDespensoryAddresse.setText(dispensary.getLocation().getAddress());
    }

    private void initViews(MyViewHolder mHolder, NearByVo nearByVo, int pos) {

        ArrayList<Products> list = new ArrayList<>();
        List<View> pageList = new ArrayList<>();

        for (Products product : nearByVo.getProducts()) {
            if (nearByVo.getDispensaries().get(pos).getDispensary().getId().equalsIgnoreCase(product.getId())) {
                list.add(product);
            }
        }
        MenuGalleryAdapter adapter = new MenuGalleryAdapter(mContext);
        //adapter.setData(list);
        adapter.setData(createPageList(pageList, list));
        mHolder.pager.setAdapter(adapter);
    }

    @NonNull
    private List<View> createPageList(List<View> pageList, ArrayList<Products> list) {

        for (Products product : list) {
            pageList.add(createPageView(product));
        }
        return pageList;
    }

    @NonNull
    private View createPageView(Products product) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_home_lv_slider_item, null, false);

        RegularTextView bottomTitle = (RegularTextView) v.findViewById(R.id.home_lv_bottom_txt);
        ImageView imageView = (ImageView) v.findViewById(R.id.home_lv_bottom_img);
        final ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.progress);

        bottomTitle.setText(product.getName());
        Glide.with(mContext).load(product.getBackground().getUrl())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(imageView);
        return v;
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

        ProgressBar progressBar;

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
        }
    }
}