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
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.vo.MenuCategoriesVO;

import java.util.ArrayList;

/**
 * Menu list adapter.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private ArrayList<MenuCategoriesVO> menuList;
    private Context mContext;

    /**
     * The type My view holder.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RegularTextView title;
        private ImageView imageView;
        ProgressBar progressBar;

        /**
         * Instantiates a new My view holder.
         *
         * @param view the view
         */
        public MyViewHolder(View view) {
            super(view);
            title = (RegularTextView) view.findViewById(R.id.menu_home_lv_title);
            imageView = (ImageView) view.findViewById(R.id.menu_home_lv_image);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_offer);
        }
    }

    /**
     * Instantiates a new Menu adapter.
     *
     * @param mContext the mContext
     * @param menuList the menu list
     */
    public MenuAdapter(Context mContext, ArrayList<MenuCategoriesVO> menuList) {
        this.menuList = menuList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_menu_lv_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MenuCategoriesVO categoriesVO = menuList.get(position);
        holder.title.setText(categoriesVO.getCategoryName());
        holder.progressBar.setVisibility(View.VISIBLE);
        Glide.with(mContext).load("")
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
}