package com.ilsa.grassis.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ilsa.grassis.R;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.MediumTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.rootvo.GetAllRewards;

/**
 * The type Menu item adapter.
 */
public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.MyViewHolder> {

    private GetAllRewards[] getAllRewardList;
    private Context mContext;

    /**
     * Instantiates a new Menu item adapter.
     *
     * @param mContext         the mContext
     * @param getAllRewardList the menu list
     */
    public RewardAdapter(Context mContext, GetAllRewards[] getAllRewardList) {
        this.getAllRewardList = getAllRewardList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lv_reward_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

       /* GradientDrawable background = (GradientDrawable) holder.circle.getBackground();
        if (position % 2 == 0) {
            background.setColor(mContext.getResources().getColor(R.color.baseColor));
        } else {
            background.setColor(mContext.getResources().getColor(R.color.txt_psd_coloras));
        }*/
//        DealsVO movie = getAllRewardList.get(position);
        holder.title.setText(getAllRewardList[position].getProduct().getTitle());
        holder.subTitle.setText(getAllRewardList[position].getProduct().getPoints());
        holder.progressBar.setVisibility(View.VISIBLE);
        Glide.with(mContext).load(getAllRewardList[position].getProduct().getIcon().getLarge()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        }).into(holder.icon);

        holder.add_stamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                InputMethodManager inputMethodManager=(InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);


                openDialoge();
            }
        });
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

    private void openDialoge() {
        // custom dialog


        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_addstamp_dialoge);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // set the custom dialog components - text, image and button
       /* TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Android custom dialog example!");*/
        EditText editText = (EditText) dialog.findViewById(R.id.addstamp_edt);

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return getAllRewardList.length;
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
        private ProgressBar progressBar;

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
            progressBar = (ProgressBar) view.findViewById(R.id.rewardFrgProgress);

//            title.setTextSize(Helper.getFontSize(mContext.getResources(), 5));
//            subTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 8.5));
//            add_stamp.setTextSize(Helper.getFontSize(mContext.getResources(), 4.5));

        }
    }
}