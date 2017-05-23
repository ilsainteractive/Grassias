package com.ilsa.grassis.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilsa.grassis.R;
import com.ilsa.grassis.activites.DispensaryInfoActivity;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.rootvo.FavToggleDespVO;
import com.ilsa.grassis.unknow.Dispensaries;
import com.ilsa.grassis.utils.Dailogs;

import org.json.JSONObject;

import java.io.IOException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FormFragment extends Fragment {

    Dispensary dispensary;
    Marker marker;
    ImageView heart;
    boolean blank = true;

    public FormFragment() {
    }

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
        boolean contain = false;
        for (int i = 0; i < AppContoller.FavDispensariesIds.size(); i++) {
            if (AppContoller.FavDispensariesIds.get(i).getDispensary_id().equalsIgnoreCase(dispensary.getId())) {
                contain = true;
                break;
            }
        }

        if (contain)
            heart.setImageResource(R.mipmap.fillheart);
        else
            heart.setImageResource(R.mipmap.heart_icon_empty);

/*

        if (AppContoller.FavDispensariesIds.contains(dispensary.getId()))
            heart.setImageResource(R.mipmap.fillheart);
        else
            heart.setImageResource(R.mipmap.heart_icon_empty);
*/


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
                        if (AppContoller.IsLoggedIn && AppContoller.userData != null)
                            FavoriteHeartDispensary(dispensary.getId());
                        else
                            Dailogs.ShowToast(getContext(), "You need to sign in or sign up before continuing", Constants.SHORT_TIME);
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

    private void FavoriteHeartDispensary(final String dispensaryId) {

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.Verifying_msg));
        pd.setCancelable(false);
        pd.show();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://kushmarketing.herokuapp.com/api/dispensary/" + dispensaryId + "/toggle_favorite")
                .get()
                .addHeader("accept", "application/vnd.kush_marketing.com; version=1")
                .addHeader("authorization", "Bearer " + AppContoller.userData.getUser().getAccess_token())
                .addHeader("x-client-email", AppContoller.userData.getUser().getEmail())
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "fd724e2e-add7-e5fd-2f92-e38aaa653b93")
                .build();

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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                JSONObject error = jsonObject.getJSONObject("error");
                                String message = error.get("message").toString();
                                Dailogs.ShowToast(getContext(), message, Constants.LONG_TIME);
                            } catch (Exception e) {
                            }
                        }
                    });
                } else {
                    Gson gson = new GsonBuilder().create();
                    final FavToggleDespVO favToggleDespVO = gson.fromJson(res, FavToggleDespVO.class);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (favToggleDespVO.getDispensary().getId() != null) {
                                if (favToggleDespVO.getDispensary().getState_change().equalsIgnoreCase("favorited")) {
                                    heart.setImageResource(R.mipmap.fillheart);

                                    Dispensaries dispensariesLikedId = new Dispensaries();
                                    dispensariesLikedId.setDispensary_id(dispensaryId);
                                    AppContoller.FavDispensariesIds.add(dispensariesLikedId);

                                } else if (favToggleDespVO.getDispensary().getState_change().equalsIgnoreCase("unfavorited")) {
                                    heart.setImageResource(R.mipmap.heart_icon_empty);

                                    for (int i = 0; i < AppContoller.FavDispensariesIds.size(); i++) {
                                        if (AppContoller.FavDispensariesIds.get(i).getDispensary_id().equalsIgnoreCase(dispensaryId)) {
                                            AppContoller.FavDispensariesIds.remove(i);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}
