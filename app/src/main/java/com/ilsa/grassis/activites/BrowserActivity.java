package com.ilsa.grassis.activites;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.ilsa.grassis.R;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;

@SuppressLint("ResourceAsColor")
public class BrowserActivity extends AppCompatActivity {

    WebView webView;
    ProgressDialog dialog;
    ImageView reload;
    String link, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        link = getIntent().getStringExtra("PATH");
        title = getIntent().getStringExtra("TITLE");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Title and subtitle
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setBackgroundColor(getResources().getColor(
                R.color.white));
        //toolbar.setNavigationIcon(R.mipmap.backarrow);
        toolbar.setNavigationOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setSupportActionBar(toolbar);

        webView = (WebView) findViewById(R.id.webview);
        reload = (ImageView) findViewById(R.id.reload);
        reload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(link);
            }
        });
        // iRefresh.setOnClickListener(BrowserActivity.this);
        dialog = new ProgressDialog(BrowserActivity.this);
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
                    reload.setVisibility(View.GONE);
                    view.loadUrl(url);
                    return false;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    webView.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    reload.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Dailogs.ShowToast(this, getString(R.string.no_internet_msg), Constants.SHORT_TIME);
        }
    }
}
