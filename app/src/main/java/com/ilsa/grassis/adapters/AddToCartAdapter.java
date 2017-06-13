package com.ilsa.grassis.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.location.Location;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ilsa.grassis.R;
import com.ilsa.grassis.activites.AddToCart;
import com.ilsa.grassis.activites.MenuActivity;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.apivo.Features;
import com.ilsa.grassis.apivo.Product;
import com.ilsa.grassis.apivo.Products;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.library.ThinTextView;
import com.ilsa.grassis.rootvo.NearByVo;
import com.ilsa.grassis.utils.CircularTextView;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.vo.UserProducs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Zeeshan Ali Basbasah on 01-Jun-17.
 */

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartAdapter.MyViewHolder> {

    private Context mContext;
    private Dispensary dispensary;
    private ArrayList<UserProducs> productses;

    public AddToCartAdapter(Context mContext, Dispensary dispensary, ArrayList<UserProducs> products) {

        this.mContext = mContext;
        this.dispensary = dispensary;
        this.productses = products;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_to_cart_item, parent, false);
        return new MyViewHolder(itemView);
    }

    public void remove(int position) {
        productses.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        // Glide.with(mContext).load(productses.get(position).getBackground().getBackground().getSmall()).into(holder.itmImg);
        holder.itemName.setText(productses.get(position).getName());
        // holder.itemName.setText("zeehaf lkjsfkl lksjflk slkfjlksa lskdfjklsd sdlkfjslkdf kjflks sdkfjslk kfjsdlkf ksdjfkls");
        holder.changeablePrice = Integer.parseInt(productses.get(position).getPrice());

        holder.price.setText("$" + Integer.parseInt(productses.get(position).getPrice()));
        holder.quantity.setText(productses.get(position).getQuantity() + "");
        holder.addcart_Txt_Price.setText(Integer.parseInt(productses.get(position).getPrice()) + "");
        holder.addcart_Txt_quantity.setText(productses.get(position).getQuantity() + "");

        holder.quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.first_layout.setVisibility(View.GONE);
                holder.second_layout.setVisibility(View.VISIBLE);
            }
        });

        holder.itmImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.first_layout.setVisibility(View.VISIBLE);
                holder.second_layout.setVisibility(View.GONE);
                holder.quantity.setText(productses.get(position).getQuantity() + "");

            }
        });

        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productses.get(position).setQuantity((Integer.parseInt(productses.get(position).getQuantity()) + 1) + "");
                holder.addcart_Txt_quantity.setText(productses.get(position).getQuantity());
                holder.addcart_Txt_Price.setText((Integer.parseInt(productses.get(position).getPrice()) * Integer.parseInt(productses.get(position).getQuantity())) + "");

                AddToCart.SetTotalPriceFromAdapter(Integer.parseInt(productses.get(position).getPrice()), 1);
                // addProductsIn_UserProducts(productses.get(position).getId());

            }
        });

        holder.Decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.parseInt(productses.get(position).getQuantity()) > 1) {
                    productses.get(position).setQuantity((Integer.parseInt(productses.get(position).getQuantity()) - 1) + "");
                    holder.addcart_Txt_quantity.setText(productses.get(position).getQuantity());
                    holder.addcart_Txt_Price.setText((Integer.parseInt(productses.get(position).getPrice()) * Integer.parseInt(productses.get(position).getQuantity())) + "");

                    AddToCart.SetTotalPriceFromAdapter(Integer.parseInt(productses.get(position).getPrice()), 0);
                    // MinusProductsIn_UserProducts(productses.get(position).getId());
                }

            }
        });

        //productses.get(position).getBackground().getBackground().getSmall();
        //holder.points.setText(productses.get(position).getPricing().);
    }

    @Override
    public int getItemCount() {
        return productses.size();
    }


    private void addProductsIn_UserProducts(String product_id) {

        for (int i = 0; i < AppContoller.orderUserProducts.getUserProducs().size(); i++) {
            if (AppContoller.orderUserProducts.getUserProducs().get(i).getProduct_id().equalsIgnoreCase(product_id)) {
                AppContoller.orderUserProducts.getUserProducs().get(i).setQuantity(Integer.toString(Integer.parseInt(AppContoller.orderUserProducts.getUserProducs().get(i).getQuantity()) + 1));
            }
        }
    }

    private void MinusProductsIn_UserProducts(String product_id) {

        for (int i = 0; i < AppContoller.orderUserProducts.getUserProducs().size(); i++) {
            if (AppContoller.orderUserProducts.getUserProducs().get(i).getProduct_id().equalsIgnoreCase(product_id)) {
                AppContoller.orderUserProducts.getUserProducs().get(i).setQuantity(Integer.toString(Integer.parseInt(AppContoller.orderUserProducts.getUserProducs().get(i).getQuantity()) - 1));
            }
        }
    }

    /**
     * The type My view holder.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView itmImg;
        private TextView itemName;
        private TextView price;
        private TextView points;
        private CircularTextView quantity;
        private RelativeLayout first_layout;
        private RelativeLayout second_layout;
        private CircularTextView Decrement;
        private BoldSFTextView addcart_Txt_Price;
        private TextView addcart_Txt_quantity;
        private CircularTextView increment;
        private int changeablePrice;
        private int quantityCounter = 1;
        private int totlaPriceOfAllProducts = 0;
        private int fixprice;
        private int totalOneItemPrice;


        public MyViewHolder(View view) {
            super(view);

            itmImg = (ImageView) view.findViewById(R.id.add_to_cart_Img_itmImg);
            itemName = (TextView) view.findViewById(R.id.add_to_cart_Txt_itemName);
            price = (TextView) view.findViewById(R.id.add_to_cart_Txt_price);
            points = (TextView) view.findViewById(R.id.add_to_cart_Txt_points);
            quantity = (CircularTextView) view.findViewById(R.id.circularTextView);
            first_layout = (RelativeLayout) view.findViewById(R.id.first_layout);
            second_layout = (RelativeLayout) view.findViewById(R.id.second_layout);
            Decrement = (CircularTextView) view.findViewById(R.id.circularTextView_Decrement);
            addcart_Txt_Price = (BoldSFTextView) view.findViewById(R.id.addcart_Txt_Price);
            addcart_Txt_quantity = (TextView) view.findViewById(R.id.addcart_Txt_quantity);
            increment = (CircularTextView) view.findViewById(R.id.circularTextView_Increment);

            quantity.setSolidColor("#f0f0f0");
            Decrement.setSolidColor("#f0f0f0");
            increment.setSolidColor("#f0f0f0");
        }
    }
}
