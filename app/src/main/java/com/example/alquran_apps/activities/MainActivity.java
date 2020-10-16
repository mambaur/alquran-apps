package com.example.alquran_apps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.alquran_apps.R;
import com.example.alquran_apps.adapters.JadwalAdapter;
import com.example.alquran_apps.models.JadwalModel;
import com.example.alquran_apps.repositories.JadwalRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> sholat;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sholat = new ArrayList<String>();
        sholat.add("Dzuhur");
        sholat.add("Magrib");
        sholat.add("Isya");

        jadwalToday();

        recyclerView = findViewById(R.id.listSholat);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new JadwalAdapter(MainActivity.this, sholat);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    void jadwalToday(){
//        List<JadwalModel> respond = JadwalRepository.getJadwalToday(this);
//        Toast.makeText(this, respond, Toast.LENGTH_SHORT).show();
    }
}
