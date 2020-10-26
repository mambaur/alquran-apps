package com.example.alquran_apps.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.alquran_apps.R;
import com.example.alquran_apps.util.Configuration;
import com.example.alquran_apps.util.PgDialog;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private ImageView btnBack;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);
        btnBack = findViewById(R.id.btnBack);

        progressDialog = new ProgressDialog(this);

        loadURL();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadURL(){
        PgDialog.show(progressDialog);
        try {
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new Callback());
            webView.loadUrl(Configuration.webViewURL);
            PgDialog.hide(progressDialog);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }
}
