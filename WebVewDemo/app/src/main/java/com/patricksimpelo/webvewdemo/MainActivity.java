package com.patricksimpelo.webvewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        //Starts the web client IN the app rather than redirecting to chrome
        webView.setWebViewClient(new WebViewClient());

        //webView.loadUrl("http://www.google.com/");

        //Load data (e.g. html created within app
        webView.loadData("<html><body><h1>Hi There!</h1><p>This is the in app website</p></body></html>", "text/html", "UTF-8");

    }
}
