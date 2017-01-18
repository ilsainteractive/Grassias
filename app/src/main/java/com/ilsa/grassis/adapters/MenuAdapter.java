package com.ilsa.grassis.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.utils.Helper;
import com.ilsa.grassis.vo.MenuListVO;

import java.util.List;

/**
 * Created by Ilsa on 1/18/2017.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private List<MenuListVO> menuList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RegularTextView title;
        private ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            title = (RegularTextView) view.findViewById(R.id.menu_home_lv_title);
            title.setTextSize(Helper.getFontSize(mContext.getResources(), 6));
            imageView = (ImageView) view.findViewById(R.id.menu_home_lv_image);
        }
    }

    public MenuAdapter(Context mContext, List<MenuListVO> moviesList) {
        this.menuList = moviesList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_menu_lv_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MenuListVO movie = menuList.get(position);
        holder.title.setText(movie.getTitle());
        holder.imageView.setImageResource(R.mipmap.menu_lv_item_img);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}