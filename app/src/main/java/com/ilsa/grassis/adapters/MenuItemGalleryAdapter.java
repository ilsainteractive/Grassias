package com.ilsa.grassis.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ilsa.grassis.R;

import java.util.ArrayList;
import java.util.List;

public class MenuItemGalleryAdapter extends PagerAdapter {

    private List<View> viewList;
    private Context mContext;

    public MenuItemGalleryAdapter(Context mContext) {
        this.mContext = mContext;
        this.viewList = new ArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imgDisplay;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_menu_item_slider_item, container, false);

        //RegularTextView bottomTitle = (RegularTextView) v.findViewById(R.id.home_lv_bottom_txt);
        //bottomTitle.setTextSize(Helper.getFontSize(mContext.getResources(), 4.5));

        imgDisplay = (ImageView) v
                .findViewById(R.id.home_lv_bottom_img);
//        Glide.with(mContext).load(mUrls.get(position))
//                .placeholder(R.drawable.wait_image).into(imgDisplay);
        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @NonNull
    List<View> getData() {
        if (viewList == null) {
            viewList = new ArrayList<>();
        }

        return viewList;
    }

    public void setData(@Nullable List<View> list) {
        this.viewList.clear();
        if (list != null && !list.isEmpty()) {
            this.viewList.addAll(list);
        }

        notifyDataSetChanged();
    }
}