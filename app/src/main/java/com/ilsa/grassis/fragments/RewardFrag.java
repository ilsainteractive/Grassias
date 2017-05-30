package com.ilsa.grassis.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilsa.grassis.R;
import com.ilsa.grassis.activites.HomeActivity;
import com.ilsa.grassis.activites.LoginActivity;
import com.ilsa.grassis.adapters.RewardAdapter;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.ExpandedRecyclerView;
import com.ilsa.grassis.library.MenuItemClickListener;
import com.ilsa.grassis.library.RecyclerTouchListener;
import com.ilsa.grassis.rootvo.GetAllRewards;
import com.ilsa.grassis.rootvo.UserDataVO;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;
import com.ilsa.grassis.vo.DealsVO;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by SohailZahid on 1/24/2017.
 */

public class RewardFrag extends Fragment {

    private Context mContext;
    private Toolbar mToolbar;
    private Activity mActivity;

    public static ExpandedRecyclerView recyclerView;
    private RewardAdapter rewardAdapter;
    private RecyclerTouchListener listener;

    private ArrayList<DealsVO> list;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public RewardFrag() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RewardFrag newInstance(int sectionNumber) {
        RewardFrag fragment = new RewardFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reward_list, container, false);
        mContext = getContext();
        mActivity = getActivity();
        if (Helper.checkInternetConnection(mContext)) {
            GetRewardsFromWeb();
        } else
            Dailogs.ShowToast(mContext, getString(R.string.no_internet_msg), Constants.SHORT_TIME);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // getDispensaryList();
        //rewardAdapter = new RewardAdapter(mContext, list);
        recyclerView = (ExpandedRecyclerView) view.findViewById(R.id.recycler_view);
        listener = new RecyclerTouchListener(mContext, recyclerView, new MenuItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(mContext, "long clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.addOnItemTouchListener(listener);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rewardAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void getDispensaryList() {

        list = new ArrayList<>();

        String[] Title = {"KIVA BARS", "TRAIN WRECK", "FLOWERS"};
        String[] off = {"30% OFF", "NEW SHIPMENT", "$100 OUNCES"};
        for (int i = 0; i < Title.length; i++) {

            DealsVO item = new DealsVO();
            item.setId(i + "");
            item.setImage(i + "");
            item.setTitle(Title[i]);
            item.setOff(off[i]);
            list.add(item);
        }
    }

    private void GetRewardsFromWeb() {

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.getting_Rewards));
        pd.setCancelable(false);
        pd.show();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://kushmarketing.herokuapp.com/api/point_reward_products")
                .get()
                .addHeader("accept", "application/vnd.kush_marketing.com; version=1")
                .addHeader("authorization", "Bearer 68c34da0ea5f5b4bbf2ca6c7cc302a79b91c442931163e6486896db7cefe2c41")
                .addHeader("x-dispensary-id", "1")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "1e7e835d-3dbf-2770-84ec-55f5946581ae")
                .build();

        // Response response = client.newCall(request).execute();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                pd.dismiss();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                pd.dismiss();
                final String res = response.body().string().toString();
                Log.i("response", res);
                if (!response.isSuccessful()) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                JSONObject error = jsonObject.getJSONObject("error");
                                String message = error.get("message").toString();
                                Dailogs.ShowToast(mContext, message, Constants.LONG_TIME);
                            } catch (Exception e) {
                            }
                        }
                    });
                } else {
                    Gson gson = new GsonBuilder().create();
                    GetAllRewards[] getAllRewardses = gson.fromJson(res, GetAllRewards[].class);

                    rewardAdapter = new RewardAdapter(mContext, getAllRewardses,mActivity);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(rewardAdapter);
                        }
                    });
                }
            }
        });
    }
}