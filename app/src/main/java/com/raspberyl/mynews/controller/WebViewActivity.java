package com.raspberyl.mynews.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.raspberyl.mynews.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;
    private String mArticleUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

            mWebView = findViewById(R.id.activity_web_view);
            mArticleUrl = getIntent().getStringExtra("WebViewUrl");

            mWebView.setWebViewClient(new WebViewClient());
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadUrl(mArticleUrl);

        }

}

