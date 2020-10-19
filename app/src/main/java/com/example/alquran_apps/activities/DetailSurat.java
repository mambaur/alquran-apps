package com.example.alquran_apps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alquran_apps.R;
import com.example.alquran_apps.adapters.DetailSuratAdapter;
import com.example.alquran_apps.models.DetailSuratModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailSurat extends AppCompatActivity implements View.OnClickListener {
    private final static String baseUrlDetail = "https://al-quran-8d642.firebaseio.com/surat/";
    private static final String NOMOR_SURAT = "no_surat";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DetailSuratModel detailSuratModel;
    private List<DetailSuratModel> listDetail;
    private String nomorSurat;

    // Interface
    private ImageButton btnPlay;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat);

        // Initialize interface
        recyclerView = findViewById(R.id.rvDetail);
        btnPlay = findViewById(R.id.btnPlay);
        btnBack = findViewById(R.id.btnBack);

        btnPlay.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        nomorSurat = getIntent().getStringExtra(NOMOR_SURAT);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDetail();
    }

    void getDetail(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrlDetail+nomorSurat+".json?print=pretty", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listDetail = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        detailSuratModel = new DetailSuratModel();
                        detailSuratModel.setAyat(jsonObject.getString("ar"));
                        detailSuratModel.setTranslate(jsonObject.getString("id"));
                        listDetail.add(detailSuratModel);
                    }

                    // RecyclerView Detail Surat
                    layoutManager = new LinearLayoutManager(DetailSurat.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);

                    adapter = new DetailSuratAdapter(DetailSurat.this, listDetail);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailSurat.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPlay:
                Toast.makeText(this, nomorSurat, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }
}
