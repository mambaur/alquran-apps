package com.caraguna.alquran_apps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.caraguna.alquran_apps.R;
import com.caraguna.alquran_apps.adapters.TafsirAdapter;
import com.caraguna.alquran_apps.util.Common;
import com.caraguna.alquran_apps.util.Configuration;
import com.caraguna.alquran_apps.util.PgDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TafsirActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TafsirAdapter tafsirAdapter;
    private List<String> listData;
    private LinearLayoutManager linearLayoutManager;
    private String nomorSurat = "";

    private ProgressDialog progressDialog;

    // Interface
    private TextView txtSource, txtSubTitle;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tafsir);

        recyclerView = findViewById(R.id.rvDoa);
        txtSource = findViewById(R.id.txtSource);
        txtSubTitle = findViewById(R.id.txtSubTitle);
        btnBack = findViewById(R.id.btnBack);

        progressDialog = new ProgressDialog(this);

        txtSubTitle.setText("Tafsir "+getIntent().getStringExtra(Configuration.NAMA_SURAT));
        nomorSurat = getIntent().getStringExtra(Configuration.NOMOR_SURAT);
        getData(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData(Context context){
        PgDialog.show(progressDialog);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Configuration.baseURLDetailSurat + nomorSurat + ".json", new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("ini respon tnya "+response.toString());
                try {
                    listData = new ArrayList<>();
                    JSONArray jsonArray = response.getJSONArray("verses");
                    JSONObject data = response.getJSONObject("tafsir")
                            .getJSONObject("id")
                            .getJSONObject("kemenag")
                            .getJSONObject("text");
                    for (int i=0; i<jsonArray.length(); i++){
                        listData.add(data.getString(String.valueOf(i+1)));
                    }

                    txtSource.setText("Sumber : "+response.getJSONObject("tafsir")
                            .getJSONObject("id")
                            .getJSONObject("kemenag")
                            .getString("source"));

                    linearLayoutManager = new LinearLayoutManager(TafsirActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    tafsirAdapter = new TafsirAdapter(TafsirActivity.this, listData);
                    recyclerView.setAdapter(tafsirAdapter);
                    tafsirAdapter.notifyDataSetChanged();

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
                Common.volleyErrorHandle(TafsirActivity.this, error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
