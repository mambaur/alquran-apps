package com.example.alquran_apps.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alquran_apps.R;
import com.example.alquran_apps.activities.DoaActivity;
import com.example.alquran_apps.adapters.DoaAdapter;
import com.example.alquran_apps.models.DoaModel;
import com.example.alquran_apps.util.Configuration;
import com.example.alquran_apps.util.PgDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoaFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DoaAdapter doaAdapter;
    private DoaModel doaModel;
    private List<DoaModel> listDoa;
    private LinearLayout txtSelengkapnya;

    private ProgressDialog progressDialog;

    public DoaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_doa, container, false);

        recyclerView = view.findViewById(R.id.rvDoa);
        txtSelengkapnya = view.findViewById(R.id.txtSelengkapnya);

        progressDialog = new ProgressDialog(view.getContext());


        getDoa(view.getContext());
        txtSelengkapnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), DoaActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getDoa(final Context context){
        PgDialog.show(progressDialog);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.baseURLDoa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listDoa = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        doaModel = new DoaModel();
                        doaModel.setId(data.getString("id"));
                        doaModel.setJudul(data.getString("judul"));
                        doaModel.setSub_judul(data.getString("sub_judul"));
                        doaModel.setImg(data.getString("img"));
                        listDoa.add(doaModel);
                    }

                    linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    doaAdapter = new DoaAdapter(context, listDoa);
                    recyclerView.setAdapter(doaAdapter);
                    doaAdapter.notifyDataSetChanged();

                    PgDialog.hide(progressDialog);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }
}
