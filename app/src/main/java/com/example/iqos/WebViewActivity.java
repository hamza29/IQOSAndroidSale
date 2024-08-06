package com.example.iqos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iqos.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {
    ActivityWebViewBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityWebViewBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        ;
        WebSettings webSettings = mBinding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mBinding.webView.loadUrl(intent.getStringExtra("video_link"));


        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}