package com.example.alquran_apps.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alquran_apps.R;
import com.example.alquran_apps.util.Common;
import com.example.alquran_apps.util.Configuration;
import com.example.alquran_apps.util.PgDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CovidActivity extends AppCompatActivity {

    private TextView txtTotalPositif, txtTotalSembuh, txtTotalDirawat, txtTotalMeninggal;
    private TextView txtNowPositif, txtNowSembuh, txtNowDirawat, txtNowMeninggal, txtDate;

    private ProgressDialog progressDialog;

    // Interface
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);

        txtTotalPositif = findViewById(R.id.totalPositif);
        txtTotalSembuh = findViewById(R.id.totalSembuh);
        txtTotalDirawat = findViewById(R.id.totalRawat);
        txtTotalMeninggal = findViewById(R.id.totalMeninggal);
        txtNowPositif = findViewById(R.id.nowPositif);
        txtNowSembuh = findViewById(R.id.nowSembuh);
        txtNowDirawat = findViewById(R.id.nowDirawat);
        txtNowMeninggal = findViewById(R.id.nowMeninggal);
        txtDate = findViewById(R.id.txtDate);
        btnBack = findViewById(R.id.btnBack);

        progressDialog = new ProgressDialog(this);

        getDataCovid();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDataCovid(){
        PgDialog.show(progressDialog);
        RequestQueue requestQueue = Volley.newRequestQueue(CovidActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Configuration.baseURLCovid, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject dataNow = response.getJSONObject("update").getJSONObject("penambahan");
                    JSONObject dataTotal = response.getJSONObject("update").getJSONObject("total");

                    // today
                    txtNowPositif.setText(formatNumber(dataNow.getInt("jumlah_positif")));
                    txtNowSembuh.setText(formatNumber(dataNow.getInt("jumlah_sembuh")));
                    txtNowDirawat.setText(formatNumber(dataNow.getInt("jumlah_dirawat")));
                    txtNowMeninggal.setText(formatNumber(dataNow.getInt("jumlah_meninggal")));
                    txtDate.setText(dataNow.getString("tanggal"));

                    // total
                    txtTotalPositif.setText(formatNumber(dataTotal.getInt("jumlah_positif")));
                    txtTotalSembuh.setText(formatNumber(dataTotal.getInt("jumlah_sembuh")));
                    txtTotalDirawat.setText(formatNumber(dataTotal.getInt("jumlah_dirawat")));
                    txtTotalMeninggal.setText(formatNumber(dataTotal.getInt("jumlah_meninggal")));

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
                Common.volleyErrorHandle(CovidActivity.this, error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private String formatNumber(int number){
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        return numberFormat.format(number);
    }
}
