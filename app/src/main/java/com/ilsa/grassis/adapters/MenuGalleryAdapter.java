package com.ilsa.grassis.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.ilsa.grassis.apivo.Products;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.RegularTextView;

import java.util.ArrayList;

/**
 * Menu gallery view for adapter items screen shots.
 */
public class MenuGalleryAdapter extends PagerAdapter {

    private ArrayList<Products> viewList;
    private Context mContext;

    /**
     * Instantiates a new Menu gallery adapter.
     *
     * @param mContext the mContext
     */
    public MenuGalleryAdapter(Context mContext) {
        this.mContext = mContext;
        this.viewList = new ArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Products product = viewList.get(position);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_home_lv_slider_item, container, false);

        BoldSFTextView bottomTitle = (BoldSFTextView) v.findViewById(R.id.home_lv_bottom_txt);
        RegularTextView bottomTitlePrice = (RegularTextView) v.findViewById(R.id.home_lv_bottom_txt_price);
        ImageView imageView = (ImageView) v.findViewById(R.id.home_lv_bottom_img);
        final ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.progress);

        bottomTitle.setText(product.getName());
        bottomTitlePrice.setText(product.getPricing().get(0).getAmount() + " "
                + product.getPricing().get(0).getValue_cents() + " "
                + product.getPricing().get(0).getValue_currency());
        Glide.with(mContext).load(product.getBackground().getBackground().getUrl())
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
        ((ViewPager) container).addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /**
     * Sets data.
     *
     * @param list the list
     */
    public void setData(@Nullable ArrayList<Products> list) {
        this.viewList.clear();
        if (list != null && !list.isEmpty()) {
            this.viewList.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    @NonNull
    ArrayList<Products> getData() {
        if (viewList == null) {
            viewList = new ArrayList<>();
        }
        return viewList;
    }
}