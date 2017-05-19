package com.ilsa.grassis.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.model.Marker;
import com.ilsa.grassis.R;
import com.ilsa.grassis.activites.DispensaryInfoActivity;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.RegularTextView;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class FormFragment extends Fragment {

    Dispensary dispensary;
    Marker marker;
    ImageView heart;
    boolean blank = true;

    public FormFragment(Dispensary dispensary, Marker marker) {
        this.dispensary = dispensary;
        this.marker = marker;
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.info_window_layout, container, false);
        BoldSFTextView infoTitle = (BoldSFTextView) view.findViewById(R.id.dispensary_name);
        RegularTextView infoaddress = (RegularTextView) view.findViewById(R.id.dispensary_add);
        ImageView profile_image = (ImageView) view.findViewById(R.id.profile_image);
        heart = (ImageView) view.findViewById(R.id.like);

        Glide.with(getContext()).
                load(this.dispensary.getLogo().getSmall())
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profile_image);

        infoTitle.setText(this.dispensary.getName());
        infoaddress.setText(this.dispensary.getLocation().getAddress());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.like:
                        if (blank) {
                            heart.setImageResource(R.mipmap.fillheart);
                            blank=false;
                        } else {
                            heart.setImageResource(R.mipmap.heart_icon_empty);
                            blank=true;
                        }
                        Toast.makeText(getContext(), "Liked", Toast.LENGTH_SHORT).show();
                        // DeleteAlarm(marker);
                        break;
                    case R.id.infoWindowMainLayoutId:
                        Intent intent = new Intent(getContext(), DispensaryInfoActivity.class);
                        String id = marker.getId();
                        intent.putExtra("dispensary_id", marker.getSnippet());
                        startActivity(intent);
                        // DeleteAlarm(marker);
                        break;
                }
            }
        };

        heart.setOnClickListener(onClickListener);
        view.findViewById(R.id.infoWindowMainLayoutId).setOnClickListener(onClickListener);
        /*view.findViewById(R.id.deleteIcon).setOnClickListener(onClickListener);*/
    }
}
