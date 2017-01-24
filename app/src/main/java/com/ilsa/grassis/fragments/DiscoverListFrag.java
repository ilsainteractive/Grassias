package com.ilsa.grassis.fragments;

import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilsa.grassis.R;
import com.ilsa.grassis.activites.MenuItemDetailsActivity;
import com.ilsa.grassis.adapters.DiscovAdapter;
import com.ilsa.grassis.library.ExpandedRecyclerView;
import com.ilsa.grassis.library.MenuItemClickListener;
import com.ilsa.grassis.library.RecyclerTouchListener;
import com.ilsa.grassis.vo.DispensaryVO;

import java.util.ArrayList;

/**
 * Created by SohailZahid on 1/24/2017.
 */

public class DiscoverListFrag extends Fragment {

    private Context mContext;
    private Toolbar mToolbar;
    private Activity mActivity;

    private ExpandedRecyclerView recyclerView;
    private DiscovAdapter discovAdapter;
    private ArrayList<DispensaryVO> list;

    private RecyclerTouchListener listener;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public DiscoverListFrag() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DiscoverListFrag newInstance(int sectionNumber) {
        DiscoverListFrag fragment = new DiscoverListFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        mActivity = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_discover_list, container, false);
        return rootView;
    }

    private void getDispensaryList() {

        list = new ArrayList<>();

        String[] Title = {"McDonald's", "Boulevard Heights", "Gaddafi Stadium", "Vogue Towers"};
        double[] lats = {31.527486, 31.520324, 31.513488, 31.508790};
        double[] longs = {74.349402, 74.346904, 74.333494, 74.349792};
        for (int i = 0; i < longs.length; i++) {

            DispensaryVO item = new DispensaryVO();
            item.setId("2.3m  |  OPEN till 8:00pm");
            item.setImg(i + "");
            item.setTitle("Malibu Community Collective");
            item.setDesc("22523 Pacific Coast Hwy, Malibu, CA 90265");
            item.setLat(lats[i]);
            item.setLog(longs[i]);
            list.add(item);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDispensaryList();
        discovAdapter = new DiscovAdapter(mContext, list);
        recyclerView = (ExpandedRecyclerView) view.findViewById(R.id.recycler_view);
        listener = new RecyclerTouchListener(mContext, recyclerView, new MenuItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(new Intent(mContext, MenuItemDetailsActivity.class));
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
        recyclerView.setAdapter(discovAdapter);
        recyclerView.setNestedScrollingEnabled(false);

    }
}