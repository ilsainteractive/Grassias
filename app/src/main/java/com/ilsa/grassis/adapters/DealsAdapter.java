package com.ilsa.grassis.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ilsa.grassis.R;
import com.ilsa.grassis.activites.DealDetailsActivity;
import com.ilsa.grassis.activites.DealsRewardActivity;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Helper;
import com.ilsa.grassis.vo.DealsVO;

import java.util.List;

/**
 * The type Menu item adapter.
 */
public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.MyViewHolder> {

    private List<DealsVO> menuList;
    private Context mContext;

    /**
     * Instantiates a new Menu item adapter.
     *
     * @param mContext the mContext
     * @param menuList the menu list
     */
    public DealsAdapter(Context mContext, List<DealsVO> menuList) {
        this.menuList = menuList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lv_deals_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DealsVO movie = menuList.get(position);
        holder.title.setText(movie.getTitle());
        holder.Off.setText(movie.getOff());
        holder.ViewDeal.setText("VIEW DEALS");
        holder.ViewDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, DealDetailsActivity.class));
            }
        });
        holder.topLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealsRewardActivity.mtxtRewads.performClick();
            }
        });
        if (position == 0) {
            holder.icon.setImageResource(R.mipmap.deals_frag_img);
        }
        if (position == 1) {
            holder.icon.setImageResource(R.mipmap.deals_frag_img2);
        }
        if (position == 2) {
            holder.icon.setImageResource(R.mipmap.deals_frag_img3);
        }
    }

    @Override
    public int getItemCount() {
        return menuList.size();
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
        }
    }
}