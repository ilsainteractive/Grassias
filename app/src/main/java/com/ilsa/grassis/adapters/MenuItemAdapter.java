package com.ilsa.grassis.adapters;

import android.content.Context;
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
import com.ilsa.grassis.apivo.Products;
import com.ilsa.grassis.library.RegularTextView;

import java.util.List;

/**
 * The type Menu item adapter.
 */
public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {

    private List<Products> menuList;
    private Context mContext;

    /**
     * Instantiates a new Menu item adapter.
     *
     * @param mContext the mContext
     * @param menuList the menu list
     */
    public MenuItemAdapter(Context mContext, List<Products> menuList) {
        this.menuList = menuList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_menu_item_lv_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Products product = menuList.get(position);
        holder.title.setText(product.getName());
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.imageView.setVisibility(View.GONE);
        Glide.with(mContext).load(menuList.get(position).getBackground().getBackground().getUrl())
                .thumbnail(0.5f)
                .crossFade().placeholder(R.mipmap.no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    /**
     * The type My view holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private RegularTextView title;
        private ImageView imageView, proceed;
        ProgressBar progressBar;

        /**
         * Instantiates a new My view holder.
         *
         * @param view the view
         */
        public ViewHolder(View view) {
            super(view);
            title = (RegularTextView) view.findViewById(R.id.menu_item_lv_txt);
            imageView = (ImageView) view.findViewById(R.id.menu_item_lv_image);
            proceed = (ImageView) view.findViewById(R.id.menu_item_lv_image_proceed);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_product);
        }
    }
}