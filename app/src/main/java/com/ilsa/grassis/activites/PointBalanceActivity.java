package com.ilsa.grassis.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.PointBalanceAdapter;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.rootvo.NearByVo;

import butterknife.BindView;

public class PointBalanceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PointBalanceAdapter pointBalanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_balance);

        inItComponent();
        syncData();
    }

    private void inItComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.point_balance_recycleview);
        pointBalanceAdapter = new PointBalanceAdapter(getApplicationContext(), AppContoller.nearByVo);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pointBalanceAdapter);
    }

    private void syncData() {


    }
}
