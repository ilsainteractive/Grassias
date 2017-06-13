package com.ilsa.grassis.activites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ilsa.grassis.R;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.library.RoundedImageView;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;
import com.ilsa.grassis.vo.Order;
import com.ilsa.grassis.vo.OrderManager;
import com.ilsa.grassis.vo.OrderUserProducts;
import com.ilsa.grassis.vo.UserProducs;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener {

    String totlaPrice;
    BoldSFTextView checkOut_totalPrie;
    private RegularTextView toolbarTitle;
    private Toolbar toolbar;
    private Activity mActivity;
    private Context mContext;
    private Spinner spinner;
    private LinearLayout scroll_layout;
    private ArrayList<UserProducs> userProducs;
    private EditText deliveryInstruction;
    private TextView pickUpAddress;
    private TextView checkOut_Txt_pickUp;
    private TextView checkOut_Txt_delivery;
    private RegularTextView placeOrder;
    private RoundedImageView addToCart_toolbar_Img;
    private boolean delivery = false;
    private RelativeLayout static_address_pickup;
    private PlacesAutocompleteTextView placesAutocompleteTextView;
    private String delivery_selected_place = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        mActivity = this;
        mContext = this;

        totlaPrice = getIntent().getStringExtra("TOTALPRICE");
        inItToolbar();
        inItComponent();
        addListeners();
        setViews();
        setPickupOrDelivery();

    }

    public void showDropDown(View view) {
        spinner.performClick();
    }

    private void inItToolbar() {
        toolbar = (Toolbar) findViewById(R.id.checkOut_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setBackgroundColor(getResources().getColor(
                R.color.white));
        setSupportActionBar(toolbar);
    }

    private void inItComponent() {
        checkOut_totalPrie = (BoldSFTextView) findViewById(R.id.checkOut_bottom_totalPrie);
        toolbarTitle = (RegularTextView) toolbar.findViewById(R.id.toolbar_title_dump);
        spinner = (Spinner) findViewById(R.id.spinner);
        scroll_layout = (LinearLayout) findViewById(R.id.checkout_horizontal_scroll_layout);
        deliveryInstruction = (EditText) findViewById(R.id.checkout_deliveryInstruction);
        pickUpAddress = (RegularTextView) findViewById(R.id.checkOut_Txt_pickUpAddress);
        checkOut_Txt_pickUp = (TextView) findViewById(R.id.checkOut_Txt_pickUp);
        checkOut_Txt_delivery = (TextView) findViewById(R.id.checkOut_Txt_delivery);
        placeOrder = (RegularTextView) findViewById(R.id.checkOut_bottom_placeOrder);
        addToCart_toolbar_Img = (RoundedImageView) findViewById(R.id.addToCart_toolbar_Img);
        placesAutocompleteTextView = (PlacesAutocompleteTextView) findViewById(R.id.places_autocomplete);
        static_address_pickup = (RelativeLayout) findViewById(R.id.static_address_pickup);

        deliveryInstruction.setFocusable(false);
        deliveryInstruction.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });

        Location location = new Location("");
        location.setLatitude(HomeActivity.currentLatitude);
        location.setLongitude(HomeActivity.currentLongitude);

        placesAutocompleteTextView.setCurrentLocation(location);
        Long radius = 200L;
        placesAutocompleteTextView.setRadiusMeters(radius);
        Location location2 = placesAutocompleteTextView.getCurrentLocation();

        placesAutocompleteTextView.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {
                        delivery_selected_place = place.description;
                    }
                }
        );
    }

    private void addListeners() {

        checkOut_Txt_pickUp.setOnClickListener(this);
        checkOut_Txt_delivery.setOnClickListener(this);
        placeOrder.setOnClickListener(this);
    }


    private void setViews() {
        toolbarTitle.setText("Checkout");
        checkOut_totalPrie.setText(totlaPrice);
        Glide.with(mContext).load(AppContoller.userData.getUser().getAvatar().getSmall()).asBitmap().into(addToCart_toolbar_Img);

        List<String> paymentMathod = new ArrayList<String>();
        paymentMathod.add("Cash Only");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paymentMathod);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setPadding(2, 2, 2, 2);
            imageView.setImageBitmap(BitmapFactory.decodeResource(
                    getResources(), R.mipmap.discovry_info_imge_slider));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(Math.round(Helper.getFontSize(mContext.getResources(), 150)),
                    Math.round(Helper.getFontSize(mContext.getResources(), 150)));
            imageView.setLayoutParams(params1);

            scroll_layout.addView(imageView);
        }

    }

    private void setPickupOrDelivery() {

        for (int i = 0; i < AppContoller.nearByVo.getDispensaries().size(); i++) {
            if (AppContoller.nearByVo.getDispensaries().get(i).getDispensary().getId().equalsIgnoreCase(AppContoller.orderUserProducts.getUserProducs().get(0).getDispensary_id())) {
                if (AppContoller.nearByVo.getDispensaries().get(i).getDispensary().getChannels().size() == 1) {
                    if (AppContoller.nearByVo.getDispensaries().get(i).getDispensary().getChannels().get(0).equalsIgnoreCase("delivery")) {
                        delivery = true;
                        checkOut_Txt_pickUp.setTypeface(null, Typeface.NORMAL);
                        checkOut_Txt_delivery.setTypeface(null, Typeface.BOLD);
                        checkOut_Txt_pickUp.setClickable(false);
                        checkOut_Txt_pickUp.setTextColor(Color.GRAY);
                        placesAutocompleteTextView.setVisibility(View.VISIBLE);
                        static_address_pickup.setVisibility(View.GONE);
                    } else if (AppContoller.nearByVo.getDispensaries().get(i).getDispensary().getChannels().get(0).equalsIgnoreCase("storefront")) {
                        delivery = false;
                        checkOut_Txt_pickUp.setTypeface(null, Typeface.BOLD);
                        checkOut_Txt_delivery.setTypeface(null, Typeface.NORMAL);
                        checkOut_Txt_delivery.setClickable(false);
                        checkOut_Txt_delivery.setTextColor(Color.GRAY);
                        placesAutocompleteTextView.setVisibility(View.GONE);
                        static_address_pickup.setVisibility(View.VISIBLE);
                    }
                } else if (AppContoller.nearByVo.getDispensaries().get(i).getDispensary().getChannels().size() == 2) {
                    delivery = true;
                    checkOut_Txt_pickUp.setTypeface(null, Typeface.NORMAL);
                    checkOut_Txt_delivery.setTypeface(null, Typeface.BOLD);
                    placesAutocompleteTextView.setVisibility(View.VISIBLE);
                    static_address_pickup.setVisibility(View.GONE);

                } else {
                    delivery = false;
                    checkOut_Txt_pickUp.setTypeface(null, Typeface.BOLD);
                    checkOut_Txt_delivery.setTypeface(null, Typeface.NORMAL);
                    checkOut_Txt_delivery.setClickable(false);
                    checkOut_Txt_delivery.setTextColor(Color.GRAY);
                    placesAutocompleteTextView.setVisibility(View.GONE);
                    static_address_pickup.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void placeOrderWebService() {

        OrderUserProducts orderUserProducts = AppContoller.orderUserProducts;
        //
        final ProgressDialog pd = new ProgressDialog(CheckOutActivity.this);
        pd.setMessage("Wait...");
        pd.setCancelable(false);
        pd.show();

        OkHttpClient client = new OkHttpClient();

        okhttp3.FormBody.Builder formBody = new okhttp3.FormBody.Builder();

        for (int i = 0; i < AppContoller.orderUserProducts.getUserProducs().size(); i++) {
            formBody.add("data[products][" + i + "][product_id]", AppContoller.orderUserProducts.getUserProducs().get(i).getProduct_id());
            formBody.add("data[products][" + i + "][dispensary_id]", AppContoller.orderUserProducts.getUserProducs().get(i).getDispensary_id());
            formBody.add("data[products][" + i + "][quantity]", AppContoller.orderUserProducts.getUserProducs().get(i).getQuantity());
            formBody.add("data[products][" + i + "][price]", AppContoller.orderUserProducts.getUserProducs().get(i).getPrice());

        }
        formBody.add("data[order][user_id]", AppContoller.orderUserProducts.getOrder().getUser_id());
        formBody.add("data[order][user_name]", AppContoller.orderUserProducts.getOrder().getUser_name());
        formBody.add("data[order][actual_amount]", AppContoller.orderUserProducts.getOrder().getActual_amount());
        formBody.add("data[order][tax]", AppContoller.orderUserProducts.getOrder().getTax());
        formBody.add("data[order][total_amount]", AppContoller.orderUserProducts.getOrder().getTotal_amount());
        formBody.add("data[order][delivery_instructions]", AppContoller.orderUserProducts.getOrder().getDelivery_instructions());
        formBody.add("data[order][payment_method]", AppContoller.orderUserProducts.getOrder().getPayment_method());
        formBody.add("data[order][address]", AppContoller.orderUserProducts.getOrder().getAddress());
        formBody.add("data[order][dispensary_id]", AppContoller.orderUserProducts.getOrder().getDispensary_id());
        formBody.add("data[order][order_type]", AppContoller.orderUserProducts.getOrder().getOrder_type());


        Request request = new Request.Builder()
                .url("http://grassias.ilsainteractive.net/placeOrder")
                .post(formBody.build())
                .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "2c29a909-bc13-b08f-d529-54dc544d30b0")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                pd.dismiss();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string().toString();
                Log.i("response", res);
                if (!response.isSuccessful()) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                pd.dismiss();
                                JSONObject jsonObject = new JSONObject(res);
                                JSONObject error = jsonObject.getJSONObject("error");
                                String message = error.get("message").toString();
                                Dailogs.ShowToast(mContext, message, Constants.LONG_TIME);
                            } catch (Exception e) {
                            }
                        }
                    });
                } else {
                    pd.dismiss();
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                pd.dismiss();
                                JSONObject jsonObject = new JSONObject(res);
                                String success = jsonObject.get("message").toString();
                                Dailogs.ShowToast(mContext, success, Constants.LONG_TIME);

                                AppContoller.orderUserProducts = new OrderUserProducts();
                                AppContoller.orderManager = new OrderManager();
                                Intent intent = new Intent(CheckOutActivity.this, HomeActivity.class);
                                finish();
                                startActivity(intent);

                            } catch (Exception e) {
                            }
                        }
                    });
                }
            }
        });
    }

    private void getUserInformation() {
        AppContoller.orderUserProducts.getOrder().setUser_id(AppContoller.userData.getUser().getId());
        AppContoller.orderUserProducts.getOrder().setUser_name(AppContoller.userData.getUser().getUsername());
        AppContoller.orderUserProducts.getOrder().setActual_amount(totlaPrice);
        AppContoller.orderUserProducts.getOrder().setTax("0");
        AppContoller.orderUserProducts.getOrder().setTotal_amount(totlaPrice);
        AppContoller.orderUserProducts.getOrder().setDelivery_instructions(deliveryInstruction.getText().toString());
        AppContoller.orderUserProducts.getOrder().setPayment_method(spinner.getSelectedItem().toString());
        AppContoller.orderUserProducts.getOrder().setDispensary_id(AppContoller.orderUserProducts.getUserProducs().get(0).getDispensary_id());

        if (delivery) {
            AppContoller.orderUserProducts.getOrder().setOrder_type("delivery");
            AppContoller.orderUserProducts.getOrder().setAddress(delivery_selected_place);
        } else {
            AppContoller.orderUserProducts.getOrder().setOrder_type("pickup");
            AppContoller.orderUserProducts.getOrder().setAddress(pickUpAddress.getText().toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkOut_Txt_pickUp:
                checkOut_Txt_pickUp.setTypeface(null, Typeface.BOLD);
                checkOut_Txt_delivery.setTypeface(null, Typeface.NORMAL);
                delivery = false;
                static_address_pickup.setVisibility(View.VISIBLE);
                placesAutocompleteTextView.setVisibility(View.GONE);
                break;
            case R.id.checkOut_Txt_delivery:
                checkOut_Txt_pickUp.setTypeface(null, Typeface.NORMAL);
                checkOut_Txt_delivery.setTypeface(null, Typeface.BOLD);
                delivery = true;
                static_address_pickup.setVisibility(View.GONE);
                placesAutocompleteTextView.setVisibility(View.VISIBLE);
                break;
            case R.id.checkOut_bottom_placeOrder:

                boolean callWebService = true;
                if (delivery) {
                    if (delivery_selected_place.equalsIgnoreCase(""))
                        callWebService = false;

                }

                if (callWebService) {
                    if (!deliveryInstruction.getText().toString().equalsIgnoreCase("")) {
                        getUserInformation();
                        placeOrderWebService();
                    } else
                        Toast.makeText(this, "Deliviery Insturction feild empty", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(this, "Pick Address", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
