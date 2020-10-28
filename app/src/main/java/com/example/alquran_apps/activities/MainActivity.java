package com.example.alquran_apps.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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
import com.example.alquran_apps.BuildConfig;
import com.example.alquran_apps.R;
import com.example.alquran_apps.adapters.JadwalAdapter;
import com.example.alquran_apps.adapters.SuratAdapter;
import com.example.alquran_apps.fragments.DoaFragment;
import com.example.alquran_apps.models.JadwalModel;
import com.example.alquran_apps.models.SuratModel;
import com.example.alquran_apps.util.Common;
import com.example.alquran_apps.util.Configuration;
import com.example.alquran_apps.util.PgDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener, LocationListener {

    private String getURLJadwal = "/2020/10";
    private String cityEndpoint = "jakartautara";
    private String day = "1";
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
    private boolean isGetJadwalLoaded = true;
    private boolean isGetSuratLoaded = true;

    private ProgressDialog progressDialog;

    // Interface
    private ImageButton btnMenu;
    private ImageView drawMenu;
    private DrawerLayout drawer;
    private NavigationView nav;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    private TextView txtLocation, txtBulan;

    // AutoCompleteTextView
    private AutoCompleteTextView ACtv;

    // Location
    LocationManager locationManager;

    // Admob
    private AdView adView;
//    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Interface
        recyclerViewSurat = findViewById(R.id.listSurat);
        recyclerViewJadwal = findViewById(R.id.listSholat);
        nestedScrollView = findViewById(R.id.nestedScroll);
        btnMenu = findViewById(R.id.btnMenu);
        txtLocation = findViewById(R.id.txtLocation);
        txtBulan = findViewById(R.id.txtBulan);
        progressBar = findViewById(R.id.pgBar);
        drawMenu = findViewById(R.id.menu_icon);
        ACtv = findViewById(R.id.ACtv);
        drawer = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav_view);
        adView = findViewById(R.id.adView);

        listSurat = new ArrayList<>();
        progressDialog = new ProgressDialog(this);

        // Load admob
        MobileAds.initialize(this);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

//        interstitialAd = new InterstitialAd(this);
//        interstitialAd.setAdUnitId("ca-app-pub-2465007971338713/8995335849");
//        interstitialAd.loadAd(new AdRequest.Builder().build());

//        ca-app-pub-2465007971338713/8995335849

        // Get date month now
        getMonth();

        // Permission for location
        grantPermission();
        checkPermission();
        getLocation();

        getJadwal();
        getSurat();

        // AutoCompleteTextView
        ArrayAdapter<String> ACadapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Configuration.SURAT);
        ACtv.setAdapter(ACadapter);
        ACtv.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
        ACtv.setDropDownVerticalOffset(8);
        ACtv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = ACtv.getText().toString();
                String[] parts = text.split(" ");
                String lastWord = parts[parts.length - 1];
                Intent intent = new Intent(MainActivity.this, DetailSurat.class);
                intent.putExtra(Configuration.NOMOR_SURAT, lastWord);
                startActivity(intent);
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

        // Nested Scroll view load more
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight()))
                            && scrollY > oldScrollY) {
                        currentItems = layoutManagerSurat.getChildCount();
                        totalItems = layoutManagerSurat.getItemCount();
                        scrollOutItems = layoutManagerSurat.findFirstVisibleItemPosition();
                        if (isScrolling && (currentItems + scrollOutItems) >= totalItems) {
                            isScrolling = false;
                            getSurat();
                            isScrolling = true;
                        }
                    }
                }
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.doaContainer, new DoaFragment()).commit();
    }

    private void getMonth(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        DateFormat urlMonth = new SimpleDateFormat("/yyyy/MM");
        DateFormat dayFormat = new SimpleDateFormat("dd");

        day = dayFormat.format(date);
        getURLJadwal = urlMonth.format(date);
        String month = dateFormat.format(date);
        txtBulan.setText(month);
    }

    private void grantPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }

    private void checkPermission() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!gpsEnabled && !networkEnabled) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_help)
                    .setTitle("Enable GPS Service")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Cancel", null)
                    .show();
        }
    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void getJadwal(){
        PgDialog.show(progressDialog);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.baseURLJadwal + cityEndpoint + getURLJadwal+".json",
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
                                jadwalModel.setTanggal(jsonObject.getString("tanggal"));
                                sholat.add(jadwalModel);
                            }

                            // Jadwal Sholat Recyclerview
                            layoutManagerJadwal = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//                            layoutManagerJadwal.scrollToPositionWithOffset(3, 1);
                            recyclerViewJadwal.setLayoutManager(layoutManagerJadwal);

                            recyclerViewJadwal.getLayoutManager().scrollToPosition(Integer.parseInt(day) - 1);

                            adapterJadwal = new JadwalAdapter(MainActivity.this, sholat);
                            recyclerViewJadwal.setAdapter(adapterJadwal);
                            adapterJadwal.notifyDataSetChanged();
                            if (!isGetSuratLoaded){
                                PgDialog.hide(progressDialog);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            PgDialog.hide(progressDialog);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PgDialog.hide(progressDialog);
                Common.volleyErrorHandle(MainActivity.this, error);
            }
        });
        requestQueue.add(stringRequest);
    }

    void getSurat(){
        if (listSurat.size() <= 110 && listSurat.size() >= 10){
            progressBar.setVisibility(View.VISIBLE);
        }
        isGetSuratLoaded = false;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.baseURLSurat,
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
                                    suratModel.setKeterangan(jsonObject.getString("keterangan"));
                                    suratModel.setAudio(jsonObject.getString("audio"));
                                    suratModel.setType(jsonObject.getString("type"));
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

                                progressBar.setVisibility(View.GONE);
                                PgDialog.hide(progressDialog);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            PgDialog.hide(progressDialog);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PgDialog.hide(progressDialog);
                Common.volleyErrorHandle(MainActivity.this, error);
            }
        });
        requestQueue.add(stringRequest);
    }

    private void shareApps(){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareMessage = "\nCobain aplikasi ini, keren banget bro!\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Alquran");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Pilih salah satu"));
        }catch (Exception e){
            e.printStackTrace();
        }
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
                            isGetSuratLoaded = true;
                            getJadwal();
                            getSurat();
                            break;
                        case R.id.share:
//                            if (interstitialAd.isLoaded()){
//                                interstitialAd.show();
//                            }else{
//                                Log.d("Ad load", "Ads gagal load");
//                            }
                            shareApps();
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
                Intent intent = new Intent(MainActivity.this, CompassActivity.class);
                startActivity(intent);
                break;
            case R.id.menu2:
                Intent intentMap = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intentMap);
                break;
            case R.id.menu3:
                Intent intentRating = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
                startActivity(intentRating);
                break;
            case R.id.menu4:
                Intent intentWeb = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(intentWeb);
                break;
            case R.id.menu5:
                Intent intentDonasi = new Intent(MainActivity.this, DonasiActivity.class);
                startActivity(intentDonasi);
                break;
            case R.id.menu6:
                Intent intentCovid = new Intent(MainActivity.this, CovidActivity.class);
                startActivity(intentCovid);
                break;
            case R.id.menu7:
                alertVersion();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.LEFT);
        return true;
    }

    private void alertVersion(){
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_help)
                .setTitle("Versi Aplikasi")
                .setMessage("Alquran v0.1")
                .setCancelable(true)
                .setPositiveButton("OK", null)
                .show();
    }

    // Handle location sholat jadwal
    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            String[] city = addresses.get(0).getSubAdminArea().split(" ", 2);
            String kota = city[1];
            txtLocation.setText(kota);

            cityEndpoint = kota.replaceAll("\\s+", "").toLowerCase();
            if (isGetJadwalLoaded){
                getJadwal();
            }
            isGetJadwalLoaded = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
