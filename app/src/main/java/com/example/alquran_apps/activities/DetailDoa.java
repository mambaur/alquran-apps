package com.example.alquran_apps.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alquran_apps.R;
import com.example.alquran_apps.util.Configuration;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailDoa extends AppCompatActivity {

    private TextView txtDoa, txtTranslation, txtTitleBar;
    private String idDoa = "";
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_doa);

        txtDoa = findViewById(R.id.txtDoa);
        txtTranslation = findViewById(R.id.txtDoaTranslation);
        txtTitleBar = findViewById(R.id.txtTitlebar);
        btnBack = findViewById(R.id.btnBack);

        idDoa = getIntent().getStringExtra(Configuration.ID_DOA);
        Toast.makeText(this, idDoa, Toast.LENGTH_SHORT).show();
        getDetail();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDetail(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.baseURLDetailDoa+idDoa+".json", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject("result");
                    txtDoa.setText(Html.fromHtml(data.getString("text_ar")));
                    txtTranslation.setText(data.getString("translation"));
                    txtTitleBar.setText(data.getString("judul"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError){
                    Toast.makeText(DetailDoa.this, "Koneksi error bro!", Toast.LENGTH_SHORT).show();
                }else if(error instanceof ServerError){
                    Toast.makeText(DetailDoa.this, "Maaf bro, server sedang bermasalah!", Toast.LENGTH_SHORT).show();
                }else if(error instanceof AuthFailureError){
                    Toast.makeText(DetailDoa.this, "Maaf bro, API key kami sedang bermasalah!", Toast.LENGTH_SHORT).show();
                }else if(error instanceof ParseError){
                    Toast.makeText(DetailDoa.this, "Parsing data salah!", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NoConnectionError){
                    Toast.makeText(DetailDoa.this, "Waduh, tidak ada koneksi internet bro!", Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(DetailDoa.this, "Kelamaan nunggu bro, muat ulang aja!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        requestQueue.add(stringRequest);
    }
}
