package com.example.alquran_apps.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alquran_apps.R;
import com.example.alquran_apps.util.Configuration;
import com.example.alquran_apps.util.PgDialog;

public class DonasiActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;
    private ImageView btnBack, btnLink;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi);

        webView = findViewById(R.id.webView);
        btnBack = findViewById(R.id.btnBack);
        btnLink = findViewById(R.id.btnLink);

        progressDialog = new ProgressDialog(this);

        loadURL();

        btnBack.setOnClickListener(this);
        btnLink.setOnClickListener(this);
    }

    private void loadURL(){
        PgDialog.show(progressDialog);
        try {
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new DonasiActivity.Callback());
            webView.loadUrl(Configuration.baseURLDonasi);
            PgDialog.hide(progressDialog);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLink:
                copyLink();
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {

            return false;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url == null || url.startsWith("http://") || url.startsWith("https://")) return false;

            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            } catch (Exception e) {
                Log.i("Webview", "shouldOverrideUrlLoading Exception:" + e);
                return true;
            }
        }
    }

    private void copyLink(){
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Link donasi", "https://saweria.co/alquranapps");
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Link berhasil disalin", Toast.LENGTH_SHORT).show();
    }
}
