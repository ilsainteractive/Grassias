package com.ilsa.grassis.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Helper;
import com.ilsa.grassis.vo.DealsVO;

import java.util.List;

/**
 * The type Menu item adapter.
 */
public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.MyViewHolder> {

    private List<DealsVO> menuList;
    private Context mContext;

    /**
     * Instantiates a new Menu item adapter.
     *
     * @param mContext the mContext
     * @param menuList the menu list
     */
    public RewardAdapter(Context mContext, List<DealsVO> menuList) {
        this.menuList = menuList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lv_reward_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GradientDrawable background = (GradientDrawable) holder.circle.getBackground();
        if (position % 2 == 0) {
            background.setColor(mContext.getResources().getColor(R.color.baseColor));
        } else {
            background.setColor(mContext.getResources().getColor(R.color.txt_psd_coloras));
        }
//        DealsVO movie = menuList.get(position);
//        holder.title.setText(movie.getTitle());
//        holder.Off.setText(movie.getOff());
//        holder.ViewDeal.setText("VIEW DEALS");
//        holder.ViewDeal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, DealDetailsActivity.class));
//            }
//        });
//        holder.topLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DealsRewardActivity.mtxtRewads.performClick();
//            }
//        });
//        if (position == 0) {
//            holder.icon.setImageResource(R.mipmap.deals_frag_img);
//        }
//        if (position == 1) {
//            holder.icon.setImageResource(R.mipmap.deals_frag_img2);
//        }
//        if (position == 2) {
//            holder.icon.setImageResource(R.mipmap.deals_frag_img3);
//        }
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
        private MediumTextView title;
        private BoldSFTextView subTitle;
        private RegularTextView add_stamp;
        private View circle;

        /**
         * Instantiates a new My view holder.
         *
         * @param view the view
         */
        public MyViewHolder(View view) {
            super(view);
            icon = (ImageView) view.findViewById(R.id.frag_reward_lv_img);
            title = (MediumTextView) view.findViewById(R.id.frag_reward_lv_title);
            subTitle = (BoldSFTextView) view.findViewById(R.id.frag_reward_lv_subtitle);
            add_stamp = (RegularTextView) view.findViewById(R.id.frag_reward_lv_add_stamp);
            circle = (View) view.findViewById(R.id.circle_shape);

//            title.setTextSize(Helper.getFontSize(mContext.getResources(), 5));
//            subTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 8.5));
//            add_stamp.setTextSize(Helper.getFontSize(mContext.getResources(), 4.5));

        }
    }
}