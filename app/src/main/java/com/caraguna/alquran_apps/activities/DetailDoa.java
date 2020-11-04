package com.caraguna.alquran_apps.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.caraguna.alquran_apps.R;
import com.caraguna.alquran_apps.util.Common;
import com.caraguna.alquran_apps.util.Configuration;
import com.caraguna.alquran_apps.util.PgDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailDoa extends AppCompatActivity {

    private TextView txtDoa, txtTranslation, txtTitleBar, txtKeterangan;
    private String idDoa = "";
    private ImageView btnBack, imgDoa;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_doa);

        txtDoa = findViewById(R.id.txtDoa);
        txtTranslation = findViewById(R.id.txtDoaTranslation);
        txtTitleBar = findViewById(R.id.txtTitlebar);
        txtKeterangan = findViewById(R.id.keterangan);
        imgDoa = findViewById(R.id.imgDoa);
        btnBack = findViewById(R.id.btnBack);

        progressDialog = new ProgressDialog(this);

        idDoa = getIntent().getStringExtra(Configuration.ID_DOA);
        getDetail();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDetail(){
        PgDialog.show(progressDialog);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, Configuration.baseURLDetailDoa+idDoa+".json", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("result");

                    String text_arab = data.getString("text_ar");
                    txtDoa.setText(text_arab);

                    Spanned translationSpanned = Html.fromHtml(data.getString("translation"));
                    txtTranslation.setText(translationSpanned);
                    txtTitleBar.setText(data.getString("judul"));
                    Glide.with(DetailDoa.this).load(data.getString("img")).into(imgDoa);

                    Spanned htmlSpanned = Html.fromHtml(data.getString("keterangan"));
                    txtKeterangan.setText(htmlSpanned);
                    PgDialog.hide(progressDialog);
                } catch (JSONException e) {
                    PgDialog.hide(progressDialog);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PgDialog.hide(progressDialog);
                Common.volleyErrorHandle(DetailDoa.this, error);
            }
        });
        requestQueue.add(stringRequest);
    }
}
