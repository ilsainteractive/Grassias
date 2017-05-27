package com.ilsa.grassis.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ilsa.grassis.R;
import com.ilsa.grassis.activites.PointBalanceActivity;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.apivo.UserVo;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.library.ThinTextView;
import com.ilsa.grassis.rootvo.NearByVo;
import com.ilsa.grassis.rootvo.UserDataVO;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Zeeshan Ali Basbasah on 24-May-17.
 */

public class PointBalanceAdapter extends RecyclerView.Adapter<PointBalanceAdapter.MyViewHolder1> {

    private Context mContext;
    private NearByVo nearByVo;


    public PointBalanceAdapter(Context mContext, NearByVo nearByVo) {
        this.mContext = mContext;
        this.nearByVo = nearByVo;
    }

    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemvView = LayoutInflater.from(parent.getContext()).inflate(R.layout.point_balance_itemview, parent, false);
        return new MyViewHolder1(itemvView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder1 holder, int position) {
        Dispensary dispensary = nearByVo.getDispensaries().get(position).getDispensary();

        //holder.point.setText(dispensary.getName());

    }

    @Override
    public int getItemCount() {
        return nearByVo.getDispensaries().size();
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder {

        ImageView img;
        BoldSFTextView point;


        /**
         * Instantiates a new My view holder.
         *
         * @param view the view
         */
        public MyViewHolder1(View view) {
            super(view);

            img = (ImageView) view.findViewById(R.id.point_balance_img);
            point = (BoldSFTextView) view.findViewById(R.id.point_balance_txt_point);
        }
    }
}
