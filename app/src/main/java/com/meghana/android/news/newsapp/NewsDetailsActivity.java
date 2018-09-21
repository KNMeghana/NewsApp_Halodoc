package com.meghana.android.news.newsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details);

        WebView webView = findViewById(R.id.webview);

        String urlToDisplay = getIntent().getStringExtra("url");


        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        if(!urlToDisplay.contains("https"))
            urlToDisplay = urlToDisplay.replace("http", "https");

        Log.d("NewsDetailsActiviyt", "Url opening : " + urlToDisplay);
        webView.loadUrl(urlToDisplay);


    }
}
