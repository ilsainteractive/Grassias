package com.ilsa.grassis.activites;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.AddToCartAdapter;
import com.ilsa.grassis.adapters.PointBalanceAdapter;
import com.ilsa.grassis.apivo.Products;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.vo.OrderUserProducts;
import com.ilsa.grassis.vo.UserProducs;

import java.util.ArrayList;

public class AddToCart extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    AddToCartAdapter addToCartAdapter;
    static BoldSFTextView totlaPrice;
    private RegularTextView add_to_cart_checkOut;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        inItToolbar();
        inItComponent();
        addListener();
    }

    private void inItToolbar() {
        toolbar = (Toolbar) findViewById(R.id.add_toCart_toolbar);
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
        TotalPricesOfAllProducts(AppContoller.orderManager.getProductses());
        addToCartAdapter = new AddToCartAdapter(getApplicationContext(), AppContoller.orderManager.getDispensary(), AppContoller.orderManager.getProductses());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(addToCartAdapter);


    }

    private void addListener() {
        add_to_cart_checkOut.setOnClickListener(this);
    }

    private static void TotalPricesOfAllProducts(ArrayList<Products> products) {

        AppContoller.orderUserProducts.getUserProducs().clear();
        int total = 0;
        for (Products products1 : products) {
            total += Integer.parseInt(products1.getPricing().get(0).getValue_cents()) / 100;

            UserProducs userProducs = new UserProducs();

            userProducs.setProduct_id(products1.getId());
            userProducs.setDispensary_id(products1.getDispensary_id());
            userProducs.setQuantity("1");
            userProducs.setPrice(Integer.toString(Integer.parseInt(products1.getPricing().get(0).getValue_cents()) / 100));

            AppContoller.orderUserProducts.getUserProducs().add(userProducs);
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
                Intent intent = new Intent(AddToCart.this, CheckOutActivity.class);
                intent.putExtra("TOTALPRICE", totlaPrice.getText().toString());
                startActivity(intent);
                break;
        }
    }
}
