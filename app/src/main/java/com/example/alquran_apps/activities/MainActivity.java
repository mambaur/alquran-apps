package com.example.alquran_apps.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alquran_apps.R;
import com.example.alquran_apps.adapters.JadwalAdapter;
import com.example.alquran_apps.adapters.SuratAdapter;
import com.example.alquran_apps.models.JadwalModel;
import com.example.alquran_apps.models.SuratModel;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public static String baseURLSurat = "https://al-quran-8d642.firebaseio.com/data.json?print=pretty";
    public static String baseURLJadwal = "https://raw.githubusercontent.com/lakuapik/jadwalsholatorg/master/adzan/";
    public static String getURLJadwal = "semarang/2020/10.json";
    private List<JadwalModel> sholat;
    private List<SuratModel> listSurat;
    private RecyclerView recyclerViewJadwal, recyclerViewSurat;
    private RecyclerView.Adapter adapterJadwal, adapterSurat;
    private LinearLayoutManager layoutManagerJadwal, layoutManagerSurat;
    private SuratModel suratModel;
    private JadwalModel jadwalModel;
    private Boolean isScrolling = true;
    private int currentItems, totalItems, scrollOutItems;
    private int loadData = 0;

    // Interface
    ImageButton btnMenu;
    ImageView drawMenu;
    DrawerLayout drawer;
    NavigationView nav;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;

    // AutoCompleteTextView
    AutoCompleteTextView ACtv;
    private static final String[] COUNTRIES = new String[] {"Indonesia", "Malaysia", "Maluku", "Jepang", "Thailand", "China"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Interface
        recyclerViewSurat = findViewById(R.id.listSurat);
        recyclerViewJadwal = findViewById(R.id.listSholat);
        nestedScrollView = findViewById(R.id.nestedScroll);
        btnMenu = findViewById(R.id.btnMenu);
        progressBar = findViewById(R.id.pgBar);
        drawMenu = findViewById(R.id.menu_icon);
        ACtv = findViewById(R.id.ACtv);
        drawer = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav_view);

        listSurat = new ArrayList<>();

        getJadwal();
        getSurat();

        // AutoCompleteTextView
        ArrayAdapter<String> ACadapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        ACtv.setAdapter(ACadapter);
        ACtv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, ACtv.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Draw Menu
        drawMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RtlHardcoded")
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        nav.setNavigationItemSelectedListener(this);

        btnMenu.setOnClickListener(this);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount()-1) != null){
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight()))
                            && scrollY > oldScrollY){
                        currentItems = layoutManagerSurat.getChildCount();
                        totalItems = layoutManagerSurat.getItemCount();
                        scrollOutItems = layoutManagerSurat.findFirstVisibleItemPosition();
                        if (isScrolling && (currentItems + scrollOutItems) >= totalItems){
                            isScrolling = false;
                            progressBar.setVisibility(View.VISIBLE);
                            getSurat();
                            progressBar.setVisibility(View.GONE);
                            isScrolling = true;
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getJadwal();
//        getSurat();
    }

    void getJadwal(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURLJadwal + getURLJadwal,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            sholat = new ArrayList<>();
                            for (int i=0; i<jsonArray.length(); i++){
                                jadwalModel = new JadwalModel();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                jadwalModel.setAshr(jsonObject.getString("ashr"));
                                jadwalModel.setDzuhur(jsonObject.getString("dzuhur"));
                                jadwalModel.setIsya(jsonObject.getString("isya"));
                                jadwalModel.setShubuh(jsonObject.getString("shubuh"));
                                jadwalModel.setMagrib(jsonObject.getString("magrib"));
                                sholat.add(jadwalModel);
                            }

                            // Jadwal Sholat Recyclerview
                            layoutManagerJadwal = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            recyclerViewJadwal.setLayoutManager(layoutManagerJadwal);

                            adapterJadwal = new JadwalAdapter(MainActivity.this, sholat);
                            recyclerViewJadwal.setAdapter(adapterJadwal);
                            adapterJadwal.notifyDataSetChanged();
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
    }

    void getSurat(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURLSurat,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            if (jsonArray.length() >= listSurat.size()){
                                for (int i = loadData; i<loadData+10; i++){
                                    suratModel = new SuratModel();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    suratModel.setNama(jsonObject.getString("nama"));
                                    suratModel.setAsma(jsonObject.getString("asma"));
                                    suratModel.setArti(jsonObject.getString("arti"));
                                    suratModel.setNomor(jsonObject.getString("nomor"));
                                    listSurat.add(suratModel);
                                    if (jsonArray.length() <= listSurat.size()){
                                        break;
                                    }
                                }
                                loadData = listSurat.size();

                                // Surat recycler view
                                layoutManagerSurat = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                                recyclerViewSurat.setLayoutManager(layoutManagerSurat);

                                adapterSurat = new SuratAdapter(MainActivity.this, listSurat);
                                recyclerViewSurat.setAdapter(adapterSurat);
                                adapterSurat.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            System.out.println("ini errornya woy"+ e.toString());
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Get Surat Error : ", error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnMenu) {
            PopupMenu popupMenu = new PopupMenu(this, btnMenu);
            popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.muatUlang:
                            getJadwal();
                            getSurat();
                            break;
                        case R.id.detail:
                            Intent intent = new Intent(MainActivity.this, DetailSurat.class);
                            startActivity(intent);
                            break;
                    }
                    return true;
                }
            });
            popupMenu.show();
        }
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:
                Toast.makeText(this, "Menu Kompas", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu2:
                Toast.makeText(this, "Menu Rating", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu3:
                Toast.makeText(this, "Menu Tentang", Toast.LENGTH_SHORT).show();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.LEFT);
        return true;
    }
}
