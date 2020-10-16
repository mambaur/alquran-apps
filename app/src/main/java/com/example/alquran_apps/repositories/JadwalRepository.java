package com.example.alquran_apps.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alquran_apps.models.JadwalModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JadwalRepository {
    public static List<JadwalModel> getJadwalToday(Context context){
        String url = "https://raw.githubusercontent.com/lakuapik/jadwalsholatorg/master/adzan/semarang/2020/10.json";
        List<JadwalModel> jadwal = null;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            JSONArray result = obj.getJSONArray(response);
                            System.out.println("Ini adalah obj = "+result.getJSONArray(1));
//                            JSONArray playerArray = obj.getJSONArray("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("get Jadwal", error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


        return jadwal;
    }
}
