package com.ilsa.grassis.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Helper;
import com.ilsa.grassis.vo.DispensaryVO;

import java.util.List;

/**
 * The type Menu item adapter.
 */
public class DiscovAdapterBackup extends RecyclerView.Adapter<DiscovAdapterBackup.MyViewHolder> {

    private List<DispensaryVO> menuList;
    private Context mContext;

    /**
     * Instantiates a new Menu item adapter.
     *
     * @param mContext the mContext
     * @param menuList the menu list
     */
    public DiscovAdapterBackup(Context mContext, List<DispensaryVO> menuList) {
        this.menuList = menuList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lv_discov_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DispensaryVO movie = menuList.get(position);
        holder.title.setText(movie.getTitle());
        holder.add.setText(movie.getDesc());
        holder.timing.setText(movie.getId());
        holder.timing.setText(Helper.getBoldedText("2.3 miles  |  OPEN till 8:00pm", 14, 19));
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
        private BoldSFTextView title;
        private RegularTextView add, timing;

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
            //title.setTextSize(Helper.getFontSize(mContext.getResources(), 5));
            //add.setTextSize(Helper.getFontSize(mContext.getResources(), 3.5));
            //timing.setTextSize(Helper.getFontSize(mContext.getResources(), 3.5));
            icon = (ImageView) view.findViewById(R.id.menu_item_lv_image);
        }
    }
}