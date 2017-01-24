package com.ilsa.grassis.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ilsa.grassis.R;
import com.ilsa.grassis.vo.DispensaryVO;

import java.util.ArrayList;

/**
 * Created by SohailZahid on 1/24/2017.
 */

public class DiscoverMapFrag extends Fragment implements OnMapReadyCallback {

    private Context mContext;
    private Toolbar mToolbar;
    private Activity mActivity;

    private GoogleMap mMap;
    private ArrayList<DispensaryVO> list;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public DiscoverMapFrag() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DiscoverMapFrag newInstance(int sectionNumber) {
        DiscoverMapFrag fragment = new DiscoverMapFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover_map, container, false);
        getDispensaryList();
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        return rootView;
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