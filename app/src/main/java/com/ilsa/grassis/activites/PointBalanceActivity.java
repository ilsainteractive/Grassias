package com.ilsa.grassis.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.PointBalanceAdapter;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.rootvo.NearByVo;
import com.ilsa.grassis.utils.Dailogs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PointBalanceActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    PointBalanceAdapter pointBalanceAdapter;

    @BindView(R.id.home_btn_dispensory)
    ImageView mDiscover;

    @BindView(R.id.home_btn_profile)
    ImageView mProfile;

    @BindView(R.id.home_btn_deals)
    ImageView mDeals;

    @BindView(R.id.home_btn_home)
    ImageView mHome;

    @BindView(R.id.home_btn_qr)
    ImageView mQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_balance);

        ButterKnife.bind(this);
        inItComponent();
        addListeners();
    }

    private void inItComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.point_balance_recycleview);
        pointBalanceAdapter = new PointBalanceAdapter(getApplicationContext(), AppContoller.nearByVo);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pointBalanceAdapter);
    }

    private void addListeners() {
        mDiscover.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mDeals.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mQr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_btn_dispensory:
                startActivity(new Intent(this, DiscoverActivity.class));
                break;
            case R.id.home_btn_profile:
                if (AppContoller.IsLoggedIn && AppContoller.userData != null)
                    startActivity(new Intent(this, ProfileActivity.class));
                else
                    Dailogs.ShowToast(this, "You need to sign in or sign up before continuing", Constants.SHORT_TIME);
                break;
            case R.id.home_btn_deals:
                if (AppContoller.IsLoggedIn && AppContoller.userData != null)
                    startActivity(new Intent(this, DealsRewardActivity.class));
                else
                    Dailogs.ShowToast(this, "You need to sign in or sign up before continuing", Constants.SHORT_TIME);
                break;
            case R.id.home_btn_home:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.home_btn_qr:
                startActivity(new Intent(this, CodeScanner.class));
                break;
        }
    }
}
