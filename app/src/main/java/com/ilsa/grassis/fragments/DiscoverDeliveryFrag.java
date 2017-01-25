package com.ilsa.grassis.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilsa.grassis.R;
import com.ilsa.grassis.activites.DiscoverActivity;

/**
 * Created by SohailZahid on 1/24/2017.
 */

public class DiscoverDeliveryFrag extends Fragment {

    private Context mContext;
    private Toolbar mToolbar;
    private Activity mActivity;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public DiscoverDeliveryFrag() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DiscoverDeliveryFrag newInstance(int sectionNumber) {
        DiscoverDeliveryFrag fragment = new DiscoverDeliveryFrag();
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
        View rootView = inflater.inflate(R.layout.fragment_discover_delivery, container, false);
        return rootView;
    }

}