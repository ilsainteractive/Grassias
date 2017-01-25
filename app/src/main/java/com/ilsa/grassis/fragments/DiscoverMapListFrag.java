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
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ilsa.grassis.R;
import com.ilsa.grassis.activites.DispensaryInfoActivity;
import com.ilsa.grassis.adapters.DiscovAdapter;
import com.ilsa.grassis.library.ExpandedRecyclerView;
import com.ilsa.grassis.library.MenuItemClickListener;
import com.ilsa.grassis.library.RecyclerTouchListener;
import com.ilsa.grassis.vo.DispensaryVO;

import java.util.ArrayList;

/**
 * Created by SohailZahid on 1/24/2017.
 */

public class DiscoverMapListFrag extends Fragment implements OnMapReadyCallback {

    private Context mContext;
    private Toolbar mToolbar;
    private Activity mActivity;

    private GoogleMap mMap;
    public static LinearLayout mapView;
    public static ExpandedRecyclerView recyclerView;
    private DiscovAdapter discovAdapter;
    private RecyclerTouchListener listener;

    private ArrayList<DispensaryVO> list;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public DiscoverMapListFrag() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DiscoverMapListFrag newInstance(int sectionNumber) {
        DiscoverMapListFrag fragment = new DiscoverMapListFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover_map_list, container, false);
        mContext = getContext();
        mActivity = getActivity();
        mapView = (LinearLayout) rootView.findViewById(R.id.map_view);
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        return rootView;
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
                startActivity(new Intent(mContext, DispensaryInfoActivity.class));
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

    private void getDispensaryList() {

        list = new ArrayList<>();

        String[] Title = {"McDonald's", "Boulevard Heights", "Gaddafi Stadium", "Vogue Towers"};
        double[] lats = {31.527486, 31.520324, 31.513488, 31.508790};
        double[] longs = {74.349402, 74.346904, 74.333494, 74.349792};
        for (int i = 0; i < longs.length; i++) {

            DispensaryVO item = new DispensaryVO();
            item.setId(i + "");
            item.setImg(i + "");
            item.setTitle(Title[i]);
            item.setDesc("Fast Food Restaurant");
            item.setLat(lats[i]);
            item.setLog(longs[i]);
            list.add(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = null;
        for (int i = 0; i < list.size(); i++) {
            latLng = new LatLng(list.get(i).getLat(), list.get(i).getLog());
            MarkerOptions marker = new MarkerOptions().position(new LatLng(list.get(i).getLat(), list.get(i).getLog()))
                    .title(list.get(i).getTitle()).snippet(list.get(i).getDesc());
            marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.home_lv_bottom_icon))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.home_lv_bottom_icon));
            Marker marker1 = mMap.addMarker(marker);
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    marker.getTag();
                    return false;
                }
            });
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
    }

}