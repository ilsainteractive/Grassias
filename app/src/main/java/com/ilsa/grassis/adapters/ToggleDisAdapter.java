package com.ilsa.grassis.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ilsa.grassis.R;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.rootvo.NearByVo;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * The type Menu item adapter.
 */
public class ToggleDisAdapter extends RecyclerView.Adapter<ToggleDisAdapter.MyViewHolder> {

    private NearByVo nearByVo;
    private Context mContext;


    public ToggleDisAdapter(Context mContext, NearByVo nearByVo) {
        this.nearByVo = nearByVo;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_popup_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            Dispensary dispensary = nearByVo.getDispensaries().get(position).getDispensary();
            //Glide.with(mContext).load(multipleDealses);
            holder.name.setText(dispensary.getName());
            Glide.with(mContext).
                    load(dispensary.getLogo().getSmall())
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.icon);
        } catch (Exception e) {
        }

    }

    @Override
    public int getItemCount() {
        return nearByVo.getDispensaries().size();
    }

    /**
     * The type My view holder.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private BoldSFTextView name;


        /**
         * Instantiates a new My view holder.
         *
         * @param view the view
         */
        public MyViewHolder(View view) {
            super(view);
            icon = (ImageView) view.findViewById(R.id.popUpWindow_icon);
            name = (BoldSFTextView) view.findViewById(R.id.popUpWindow_name);
        }
    }
}