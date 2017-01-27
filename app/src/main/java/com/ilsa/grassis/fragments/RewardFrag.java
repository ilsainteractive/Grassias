package com.ilsa.grassis.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilsa.grassis.R;
import com.ilsa.grassis.adapters.RewardAdapter;
import com.ilsa.grassis.library.ExpandedRecyclerView;
import com.ilsa.grassis.library.MenuItemClickListener;
import com.ilsa.grassis.library.RecyclerTouchListener;
import com.ilsa.grassis.vo.DealsVO;

import java.util.ArrayList;

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
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDispensaryList();
        rewardAdapter = new RewardAdapter(mContext, list);
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

};