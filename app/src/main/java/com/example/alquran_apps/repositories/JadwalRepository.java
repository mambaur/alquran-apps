package com.example.alquran_apps.repositories;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alquran_apps.models.JadwalModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JadwalRepository {
    public static String baseURL = "https://raw.githubusercontent.com/lakuapik/jadwalsholatorg/master/adzan/";
    public static String getURL = "semarang/2020/10.json";
    RequestQueue requestQueue;
    List<JadwalModel> listJadwal;

    public JadwalRepository(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.listJadwal = new ArrayList<JadwalModel>();
    }

    public List<JadwalModel> getJadwalToday(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL + getURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JadwalModel jadwalModel = new JadwalModel();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0; i<jsonArray.length(); i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                System.out.println("ini hasil "+ jsonObject.getString("ashr"));
                                jadwalModel.setAshr(jsonObject.getString("ashr"));
                                System.out.println("ini adalah list jadwal model "+jadwalModel.getAshr());
                                listJadwal.add(jadwalModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Get Jadwal Error : ", error.toString());
            }
        });
        requestQueue.add(stringRequest);
        return listJadwal;
    }

    public String tes(){
        return "Percobaan berhasil";
    }
}
