package com.example.alquran_apps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alquran_apps.R;
import com.example.alquran_apps.adapters.DetailSuratAdapter;
import com.example.alquran_apps.models.AyatModel;
import com.example.alquran_apps.util.Configuration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailSurat extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private AyatModel ayatModel;
    private List<AyatModel> listDetail;
    private String nomorSurat, audioSurat;

    // Interface
    private ImageButton btnPlay, btnPlayLayout;
    private ImageView btnBack, btnClose;
    private TextView txtNama, txtTurun, txtAsma;
    private ConstraintLayout audioLayout;
    private SeekBar seekBar;
    private TextView txtAudioStart, txtAudioEnd;

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat);

        // Initialize interface
        recyclerView = findViewById(R.id.rvDetail);
        btnPlay = findViewById(R.id.btnPlay);
        btnPlayLayout = findViewById(R.id.btnPlayLayout);
        txtAudioStart = findViewById(R.id.txtAudioStart);
        txtAudioEnd = findViewById(R.id.txtAudioEnd);
        txtAsma = findViewById(R.id.txtAsma);
        btnBack = findViewById(R.id.btnBack);
        txtNama = findViewById(R.id.namaSurat);
        txtTurun = findViewById(R.id.txtTurun);
        audioLayout = findViewById(R.id.audioLayout);
        btnClose = findViewById(R.id.btnClose);
        seekBar = findViewById(R.id.seekbar);

        // Audio player
        mediaPlayer = new MediaPlayer();
        seekBar.setMax(100);

        btnPlay.setOnClickListener(this);
        btnPlayLayout.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        nomorSurat = getIntent().getStringExtra(Configuration.NOMOR_SURAT);

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar seekbarPlayer = (SeekBar) v;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekbarPlayer.getProgress();
                mediaPlayer.seekTo(playPosition);
                txtAudioEnd.setText(milliSecondToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                seekBar.setSecondaryProgress(percent);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                seekBar.setProgress(0);
                btnPlay.setImageResource(R.drawable.ic_play);
                btnPlayLayout.setImageResource(R.drawable.ic_play);
                txtAudioStart.setText("00:00");
                txtAudioEnd.setText("00:00");
                mediaPlayer.reset();
                prepareMediaPlayer();
            }
        });
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekbar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            txtAudioStart.setText(milliSecondToTimer(currentDuration));
        }
    };

    private void prepareMediaPlayer(){
        try {
            mediaPlayer.setDataSource(audioSurat);
            mediaPlayer.prepare();
            txtAudioEnd.setText(milliSecondToTimer(mediaPlayer.getDuration()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateSeekbar(){
        if (mediaPlayer.isPlaying()){
            seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater, 1000);
        }
    }

    private String milliSecondToTimer(long milliSecond){
        String timerString = "";
        String secondString;

        int hours = (int) (milliSecond / (1000*60*60));
        int minutes = (int) (milliSecond % (1000*60*60)) / (60*1000);
        int seconds = (int) ((milliSecond % (1000*60*60)) % (60*1000) / 1000);

        if (hours > 0){
            timerString = hours + ":";
        }
        if (seconds<10){
            secondString = "0" + seconds;
        }else{
            secondString = "" + seconds;
        }

        timerString = timerString + minutes + ":" + secondString;
        return timerString;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer.isPlaying()){
            handler.removeCallbacks(updater);
            mediaPlayer.pause();
            btnPlay.setImageResource(R.drawable.ic_play);
            btnPlayLayout.setImageResource(R.drawable.ic_play);
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDetail();
    }

    void getDetail(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.baseURLDetailSurat+nomorSurat+".json", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listDetail = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("verses");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject value = jsonArray.getJSONObject(i);
                        ayatModel = new AyatModel();
                        ayatModel.setText(value.getString("text"));
                        ayatModel.setTranslation_id(value.getString("translation_id"));
                        ayatModel.setNumber(value.getInt("number"));
                        listDetail.add(ayatModel);
                    }

                    JSONArray recitations = jsonObject.getJSONArray("recitations");

                    txtNama.setText(jsonObject.getString("name"));
                    txtTurun.setText(jsonObject.getString("type"));
                    txtAsma.setText(jsonObject.getJSONObject("name_translations").getString("ar"));

                    // source title kemenag
//                    jsonObject.getJSONObject("tafsir")
//                            .getJSONObject("id")
//                            .getJSONObject("kemenag")
//                            .getString("source");
                    // isi tafsir
//                    jsonObject.getJSONObject("tafsir")
//                            .getJSONObject("id")
//                            .getJSONObject("kemenag")
//                            .getString("text");

                    audioSurat = recitations.getJSONObject(0).getString("audio_url");

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
                prepareMediaPlayer();
                audioLayout.setVisibility(View.VISIBLE);
                if (mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.ic_play);
                    btnPlayLayout.setImageResource(R.drawable.ic_play);
                }else{
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.ic_pause);
                    btnPlayLayout.setImageResource(R.drawable.ic_pause);
                    updateSeekbar();
                }
                break;
            case R.id.btnBack:
                if (mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.ic_play);
                    btnPlayLayout.setImageResource(R.drawable.ic_play);
                }
                finish();
                break;
            case R.id.btnClose:
                if (mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.ic_play);
                    btnPlayLayout.setImageResource(R.drawable.ic_play);
                }
                audioLayout.setVisibility(View.GONE);
                break;
            case R.id.btnPlayLayout:
                if (mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.ic_play);
                    btnPlayLayout.setImageResource(R.drawable.ic_play);
                }else{
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.ic_pause);
                    btnPlayLayout.setImageResource(R.drawable.ic_pause);
                    updateSeekbar();
                }
                break;
        }
    }
}
