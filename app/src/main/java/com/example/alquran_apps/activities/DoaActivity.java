package com.example.alquran_apps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alquran_apps.R;
import com.example.alquran_apps.adapters.ListDoaAdapter;
import com.example.alquran_apps.models.DoaModel;
import com.example.alquran_apps.util.Common;
import com.example.alquran_apps.util.Configuration;
import com.example.alquran_apps.util.PgDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ListDoaAdapter adapter;
    private List<DoaModel> listData;
    private DoaModel doaModel;
    private ProgressDialog progressDialog;

    // Interface
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doa);

        imgBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.rvDoa);

        progressDialog = new ProgressDialog(this);

        getData(this);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData(final Context context){
        PgDialog.show(progressDialog);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Configuration.baseURLDoa, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listData = new ArrayList<>();
                    JSONArray jsonArray = response.getJSONArray("results");
                    System.out.println(jsonArray.toString());
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        doaModel = new DoaModel();
                        doaModel.setJudul(data.getString("judul"));
                        doaModel.setSub_judul(data.getString("sub_judul"));
                        doaModel.setId(data.getString("id"));
                        listData.add(doaModel);
                    }


                    System.out.println("ini adalah list data "+ listData.get(0).getSub_judul());

                    linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    adapter = new ListDoaAdapter(context, listData);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
                Common.volleyErrorHandle(DoaActivity.this, error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
