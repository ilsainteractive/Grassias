package com.ilsa.grassis.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ilsa.grassis.R;
import com.ilsa.grassis.activites.DealDetailsActivity;
import com.ilsa.grassis.activites.DealsRewardActivity;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.rootvo.DealsVO;

/**
 * The type Menu item adapter.
 */
public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.MyViewHolder> {

    private DealsVO[] multipleDealses;
    private Context mContext;


    /**
     * Instantiates a new Menu item adapter.
     *
     * @param mContext        the mContext
     * @param multipleDealses the menu list
     */
    public DealsAdapter(Context mContext, DealsVO[] multipleDealses) {
        this.multipleDealses = multipleDealses;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lv_deals_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(multipleDealses[position].getDeal().getTitle());
        holder.Off.setText(multipleDealses[position].getDeal().getDeal_type());
        holder.progress.setVisibility(View.VISIBLE);
        Glide.with(mContext).load(multipleDealses[position].getDeal().getBackground().getLarge()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                holder.progress.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                holder.progress.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.icon);
        holder.ViewDeal.setText("VIEW DEALS");
        holder.ViewDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DealDetailsActivity.class);
                intent.putExtra("TITLE", multipleDealses[position].getDeal().getTitle());
                intent.putExtra("DEAL_TYPE", multipleDealses[position].getDeal().getDeal_type());
                intent.putExtra("PATH", multipleDealses[position].getDeal().getBackground().getLarge());
                intent.putExtra("DESCRIPTION", multipleDealses[position].getDeal().getDescription());
                mContext.startActivity(intent);
            }
        });
        holder.topLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealsRewardActivity.mtxtRewads.performClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return multipleDealses.length;
    }

    /**
     * The type My view holder.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private BoldSFTextView Off;
        private MediumTextView title;
        private RegularTextView ViewDeal;
        private LinearLayout topLayout;
        private ProgressBar progress;

        /**
         * Instantiates a new My view holder.
         *
         * @param view the view
         */
        public MyViewHolder(View view) {
            super(view);
            topLayout = (LinearLayout) view.findViewById(R.id.deals_item_lv_top_txts);
            title = (MediumTextView) view.findViewById(R.id.deals_item_lv_title);
            Off = (BoldSFTextView) view.findViewById(R.id.deals_item_lv_off);
            ViewDeal = (RegularTextView) view.findViewById(R.id.deals_item_lv_view_deals);
            //title.setTextSize(Helper.getFontSize(mContext.getResources(), 6));
            //Off.setTextSize(Helper.getFontSize(mContext.getResources(), 8.5));
            //ViewDeal.setTextSize(Helper.getFontSize(mContext.getResources(), 6));
            icon = (ImageView) view.findViewById(R.id.deals_item_lv_img);
            progress = (ProgressBar) view.findViewById(R.id.dealFrgProgress);
        }
    }
}