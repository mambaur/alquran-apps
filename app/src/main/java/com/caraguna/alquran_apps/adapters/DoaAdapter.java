package com.caraguna.alquran_apps.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.caraguna.alquran_apps.R;
import com.caraguna.alquran_apps.activities.DetailDoa;
import com.caraguna.alquran_apps.models.DoaModel;
import com.caraguna.alquran_apps.util.Configuration;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;

public class DoaAdapter extends RecyclerView.Adapter<DoaAdapter.HolderData> {
    private List<DoaModel> listData;
    private LayoutInflater inflater;
    private Context context;
    private InterstitialAd interstitialAd;

    public DoaAdapter(Context context, List<DoaModel> listData, InterstitialAd interstitialAd) {
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.interstitialAd = interstitialAd;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_doa, parent, false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        holder.txtJudul.setText(listData.get(position).getJudul());
        holder.txtSubJudul.setText(listData.get(position).getSub_judul());
        Glide.with(context).load(listData.get(position).getImg()).into(holder.imgView);

        final String id = listData.get(position).getId();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else{
                    Log.d("Ad load", "Ads gagal load");
                }
                Intent intent = new Intent(context, DetailDoa.class);
                intent.putExtra(Configuration.ID_DOA, id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    static class HolderData extends RecyclerView.ViewHolder {
        TextView txtJudul, txtSubJudul;
        ImageView imgView;
        CardView cardView;

        HolderData(@NonNull View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.txtJudul);
            imgView = itemView.findViewById(R.id.imgDoaItem);
            txtSubJudul = itemView.findViewById(R.id.txtSubJudul);
            cardView = itemView.findViewById(R.id.cvDoa);
        }
    }
}
