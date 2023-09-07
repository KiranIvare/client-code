package com.qubitons.clientcode;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        String clientURL = getIntent().getStringExtra("client_url");

        webView = findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);

        // Set a WebViewClient to handle loading URLs within the WebView
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(clientURL);
    }
}
