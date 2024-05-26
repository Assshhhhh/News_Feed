package com.example.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.newsfeed.databinding.ActivityNewsFullBinding;

public class NewsFullActivity extends AppCompatActivity {

    private ActivityNewsFullBinding binding;
    private String news_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewsFullBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        news_url = getIntent().getStringExtra("url");

        WebSettings webSettings = binding.newsWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.newsWebview.setWebViewClient(new WebViewClient());
        binding.newsWebview.loadUrl(news_url);

    }

    @Override
    public void onBackPressed() {
        if(binding.newsWebview.canGoBack())
            binding.newsWebview.goBack();
        else
            super.onBackPressed();
    }
}