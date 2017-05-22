package com.ilsa.grassis.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilsa.grassis.R;
import com.ilsa.grassis.activites.DiscoverActivity;
import com.ilsa.grassis.activites.DispensaryInfoActivity;
import com.ilsa.grassis.apivo.Active;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.BoldSFTextView;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.library.RegularTextView;
import com.ilsa.grassis.rootvo.FavToggleDespVO;
import com.ilsa.grassis.unknow.Dispensaries;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The type Menu item adapter.
 */
public class DiscovAdapter extends RecyclerView.Adapter<DiscovAdapter.MyViewHolder> {

    private ArrayList<Dispensary> menuList;
    private Context mContext;
    private String mType;
    private Activity activity;

    /**
     * Instantiates a new Menu item adapter.
     *
     * @param mContext the mContext
     * @param menuList the menu list
     */
    public DiscovAdapter(Context mContext, ArrayList<Dispensary> menuList, String type, DiscoverActivity discoverActivity) {
        this.menuList = menuList;
        this.mContext = mContext;
        this.mType = type;
        activity = discoverActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lv_discov_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Dispensary dispensary = menuList.get(position);
        if (dispensary.getChannels().contains(mType)) {
            holder.title.setText(dispensary.getName());
            holder.add.setText(dispensary.getDescription());
            holder.timing.setText(Helper.getBoldedText("2.3 miles  |  OPEN till 8:00pm", 14, 19));

            boolean contain = false;
            for (int i = 0; i < AppContoller.FavDispensariesIds.size(); i++) {
                if (AppContoller.FavDispensariesIds.get(i).getDispensary_id().equalsIgnoreCase(dispensary.getId())) {
                    contain = true;
                    break;
                }
            }

            if (contain)
                holder.icon.setImageResource(R.mipmap.fillheart);
            else
                holder.icon.setImageResource(R.mipmap.heart_icon_empty);


            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FavoriteHeartDispensary(dispensary.getId(), holder);
                }
            });

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DispensaryInfoActivity.class);
                    intent.putExtra("dispensary_id", dispensary.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    /**
     * The type My view holder.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private BoldSFTextView title;
        private RegularTextView add, timing;
        private LinearLayout linearLayout;

        /**
         * Instantiates a new My view holder.
         *
         * @param view the view
         */
        public MyViewHolder(View view) {
            super(view);
            title = (BoldSFTextView) view.findViewById(R.id.discov_map_item_lv_title);
            add = (RegularTextView) view.findViewById(R.id.discov_map_item_lv_add);
            timing = (RegularTextView) view.findViewById(R.id.discov_map_item_lv_timings);
            //title.setTextSize(Helper.getFontSize(mContext.getResources(), 5));
            //add.setTextSize(Helper.getFontSize(mContext.getResources(), 3.5));
            //timing.setTextSize(Helper.getFontSize(mContext.getResources(), 3.5));
            icon = (ImageView) view.findViewById(R.id.discov_map_item_lv_image_proceed);
            linearLayout = (LinearLayout) view.findViewById(R.id.mainLayout_id);
        }
    }

    private void FavoriteHeartDispensary(final String dispensaryId, final MyViewHolder holder) {

        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(mContext.getString(R.string.processing));
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
                    activity.runOnUiThread(new Runnable() {
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
                    final FavToggleDespVO favToggleDespVO = gson.fromJson(res, FavToggleDespVO.class);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (favToggleDespVO.getDispensary().getId() != null) {
                                if (favToggleDespVO.getDispensary().getState_change().equalsIgnoreCase("favorited")) {
                                    holder.icon.setImageResource(R.mipmap.fillheart);

                                    Dispensaries dispensariesLikedId = new Dispensaries();
                                    dispensariesLikedId.setDispensary_id(dispensaryId);
                                    AppContoller.FavDispensariesIds.add(dispensariesLikedId);

                                } else if (favToggleDespVO.getDispensary().getState_change().equalsIgnoreCase("unfavorited")) {
                                    holder.icon.setImageResource(R.mipmap.heart_icon_empty);

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