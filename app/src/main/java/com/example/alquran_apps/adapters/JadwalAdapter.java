package com.example.alquran_apps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alquran_apps.R;
import com.example.alquran_apps.models.JadwalModel;

import java.util.List;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.HolderData>{
    private LayoutInflater inflater;
    private List<JadwalModel> sholat;

    public JadwalAdapter(Context context, List<JadwalModel> sholat){
        this.inflater = LayoutInflater.from(context);
        this.sholat = sholat;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = inflater.inflate(R.layout.item_jadwal_sholat, parent, false);
        return new HolderData(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {

        holder.txtSubuh.setText(sholat.get(position).getShubuh());
        holder.txtDzuhur.setText(sholat.get(position).getDzuhur());
        holder.txtAshr.setText(sholat.get(position).getAshr());
        holder.txtMagrib.setText(sholat.get(position).getMagrib());
    }

    @Override
    public int getItemCount() {
        return sholat.size();
    }

    static class HolderData extends RecyclerView.ViewHolder{
        TextView txtSubuh, txtDzuhur, txtAshr, txtMagrib, txtIsya;

        HolderData(@NonNull View itemView) {
            super(itemView);
            txtSubuh = itemView.findViewById(R.id.txtSubuh);
            txtDzuhur = itemView.findViewById(R.id.txtDzuhur);
            txtAshr = itemView.findViewById(R.id.txtAshr);
            txtMagrib = itemView.findViewById(R.id.txtMagrib);
            txtIsya = itemView.findViewById(R.id.txtIsya);
        }
    }
}
