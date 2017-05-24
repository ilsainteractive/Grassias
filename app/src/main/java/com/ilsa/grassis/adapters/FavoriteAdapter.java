package com.ilsa.grassis.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ilsa.grassis.R;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Helper;

import java.util.ArrayList;

/**
 * The type Menu item adapter.
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

    private ArrayList<Dispensary> menuList;
    private Context mContext;
    private Activity activity;

    /**
     * Instantiates a new Menu item adapter.
     *
     * @param mContext the mContext
     * @param menuList the menu list
     */
    public FavoriteAdapter(Context mContext, ArrayList<Dispensary> menuList, Activity activity) {
        this.menuList = menuList;
        this.mContext = mContext;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lv_favorite_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Dispensary dispensary = menuList.get(position);
        holder.title.setText(dispensary.getName());

        if (dispensary.getDescription().equalsIgnoreCase("")) {
            holder.add.setVisibility(View.GONE);
        } else {
            holder.add.setText(dispensary.getDescription());
        }

        holder.timing.setText(Helper.getBoldedText("2.3 miles  |  OPEN till 8:00pm", 14, 19));
        holder.icon.setImageResource(R.mipmap.fillheart);
        Glide.with(mContext).load("")
                .thumbnail(0.5f)
                .crossFade().placeholder(R.mipmap.no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.logo);
    }


    @Override
    public int getItemCount() {
        return menuList.size();
    }

    /**
     * The type My view holder.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon, logo;
        private BoldSFTextView title;
        private RegularTextView add, timing;
        private LinearLayout linearLayout;

        /**
         * Instantiates a new My view holder.
         *
         * @param view the view
         */
        public MyViewHolder(View view) {
            super(view);
            title = (BoldSFTextView) view.findViewById(R.id.discov_map_item_lv_title);
            add = (RegularTextView) view.findViewById(R.id.discov_map_item_lv_add);
            timing = (RegularTextView) view.findViewById(R.id.discov_map_item_lv_timings);
            icon = (ImageView) view.findViewById(R.id.discov_map_item_lv_image_proceed);
            logo = (ImageView) view.findViewById(R.id.discov_map_item_lv_image);
            linearLayout = (LinearLayout) view.findViewById(R.id.mainLayout_id);
        }
    }
}