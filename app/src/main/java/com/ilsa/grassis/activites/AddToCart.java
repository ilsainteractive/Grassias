package com.ilsa.grassis.activites;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.SwipeableItemClickListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;
import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.AddToCartAdapter;
import com.ilsa.grassis.apivo.Products;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.library.RoundedImageView;
import com.ilsa.grassis.vo.UserProducs;

import java.util.ArrayList;

public class AddToCart extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    AddToCartAdapter addToCartAdapter;
    static BoldSFTextView totlaPrice;
    private RegularTextView add_to_cart_checkOut;
    private Toolbar toolbar;
    private ImageView add_to_cart_Img_backArrow;
    private Boolean Empty;
    private RoundedImageView addToCart_toolbar_Img;
    private RegularTextView empty_cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        Empty = getIntent().getExtras().getBoolean("FALSE");
        inItToolbar();
        inItComponent();
        setViews();
        addListener();
    }

    private void inItToolbar() {
        toolbar = (Toolbar) findViewById(R.id.add_toCart_toolbar);
        add_to_cart_Img_backArrow = (ImageView) toolbar.findViewById(R.id.add_to_cart_Img_backArrow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setBackgroundColor(getResources().getColor(
                R.color.white));
        setSupportActionBar(toolbar);
    }

    private void inItComponent() {

        totlaPrice = (BoldSFTextView) findViewById(R.id.add_to_cart_bottom_totalPrie);
        add_to_cart_checkOut = (RegularTextView) findViewById(R.id.add_to_cart_bottom_checkOut);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_AddToCart);
        addToCart_toolbar_Img = (RoundedImageView) findViewById(R.id.addToCart_toolbar_Img);
        empty_cart = (RegularTextView) findViewById(R.id.empty_cart);
        empty_cart.setVisibility(View.GONE);

        if (Empty)
            loadDataIntoUserProducts(AppContoller.orderManager.getProductses());

        TotalPrices(AppContoller.orderUserProducts.getUserProducs());

        if (AppContoller.orderUserProducts.getUserProducs().size() > 0)
            init();
        else
            empty_cart.setVisibility(View.VISIBLE);

    }

    private void setViews() {
        Glide.with(AddToCart.this).load(AppContoller.userData.getUser().getAvatar().getSmall()).asBitmap().into(addToCart_toolbar_Img);

    }


    private void init() {
        addToCartAdapter = new AddToCartAdapter(getApplicationContext(), AppContoller.orderManager.getDispensary(), AppContoller.orderUserProducts.getUserProducs());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(addToCartAdapter);

        final SwipeToDismissTouchListener<RecyclerViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new RecyclerViewAdapter(recyclerView),
                        new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onPendingDismiss(RecyclerViewAdapter recyclerView, int position) {

                            }

                            @Override
                            public void onDismiss(RecyclerViewAdapter view, int position) {
                                addToCartAdapter.remove(position);
                            }
                        });
        touchListener.setDismissDelay(-1);
        recyclerView.setOnTouchListener(touchListener);
        //recyclerView.requestDisallowInterceptTouchEvent(false);

        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        recyclerView.addOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
        recyclerView.addOnItemTouchListener(new SwipeableItemClickListener(this,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getId() == R.id.txt_delete) {
                            touchListener.processPendingDismisses();
                        } else if (view.getId() == R.id.txt_undo) {
                            touchListener.undoPendingDismiss();
                        } else { // R.id.txt_data
                            // Toast.makeText(AddToCart.this, "Position " + position, LENGTH_SHORT).show();
                        }
                    }
                }));

        recyclerView.setOnTouchListener(touchListener);
    }


    private void addListener() {
        add_to_cart_checkOut.setOnClickListener(this);
        add_to_cart_Img_backArrow.setOnClickListener(this);
    }

    private static void loadDataIntoUserProducts(ArrayList<Products> products) {

        for (Products products1 : products) {

            UserProducs userProducs = new UserProducs();

            userProducs.setProduct_id(products1.getId());
            userProducs.setDispensary_id(products1.getDispensary_id());
            userProducs.setQuantity("1");
            userProducs.setPrice(Integer.toString(Integer.parseInt(products1.getPricing().get(0).getValue_cents()) / 100));
            userProducs.setName(products1.getName());

            AppContoller.orderUserProducts.getUserProducs().add(userProducs);
        }
    }

    private static void TotalPrices(ArrayList<UserProducs> products) {

        int singleProductPrice = 0;
        int total = 0;
        int quantity = 1;
        for (UserProducs products1 : products) {
            singleProductPrice = Integer.parseInt(products1.getPrice());
            quantity = Integer.parseInt(products1.getQuantity());
            total += singleProductPrice * quantity;

        }
        totlaPrice.setText(total + "");
    }

    public static void SetTotalPriceFromAdapter(int price, int operation) {
        if (operation == 0)
            totlaPrice.setText((Integer.parseInt(totlaPrice.getText().toString()) - price) + "");
        else
            totlaPrice.setText((Integer.parseInt(totlaPrice.getText().toString()) + price) + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_to_cart_bottom_checkOut:
                if (AppContoller.orderUserProducts.getUserProducs().size() > 0) {
                    Intent intent = new Intent(AddToCart.this, CheckOutActivity.class);
                    intent.putExtra("TOTALPRICE", totlaPrice.getText().toString());
                    startActivity(intent);
                } else
                    Toast.makeText(this, "Empty Cart", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_to_cart_Img_backArrow:
                finish();
                break;
        }
    }
}
