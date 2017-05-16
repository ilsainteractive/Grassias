package com.ilsa.grassis.activites;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ilsa.grassis.R;
import com.ilsa.grassis.utils.Helper;

@SuppressLint("ResourceAsColor")
public class HelpActivity extends AppCompatActivity {

    WebView webView;
    ProgressDialog dialog;
    ImageView iRefresh;
    String link, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        link = getIntent().getStringExtra("PATH");
        name = getIntent().getStringExtra("TITLE");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Title and subtitle
        toolbar.setTitle(name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(getResources().getColor(
                R.color.baseColor));
        //toolbar.setNavigationIcon(R.mipmap.backarrow);
        toolbar.setNavigationOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //window.setStatusBarColor(Common.darkenColor(getResources()
                    //.getColor(R.color.themeToolbarColor)));
        }*/

        webView = (WebView) findViewById(R.id.webview);
        iRefresh = (ImageView) findViewById(R.id.iRefresh);
       // iRefresh.setOnClickListener(HelpActivity.this);
        dialog = new ProgressDialog(HelpActivity.this);
        dialog.setMessage("Loading...");
        // dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(true);

        if (Helper.checkInternetConnection(getApplicationContext())) {
            dialog.show();
            webView.loadUrl(link);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return false;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    webView.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            });
        } else {
           // webView.setVisibility(View.GONE);
        }
    }
}
