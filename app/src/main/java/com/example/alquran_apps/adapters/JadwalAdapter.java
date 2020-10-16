package com.example.alquran_apps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alquran_apps.R;

import java.util.List;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.HolderData>{
    private Context context;
    private List<String> sholat;

    public JadwalAdapter(Context context, List<String> sholat){
        this.context = context;
        this.sholat = sholat;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jadwal_sholat, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        String dataSholat = sholat.get(position);

        holder.txtNama.setText(dataSholat);
    }

    @Override
    public int getItemCount() {
        return sholat.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView txtNama;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.txtName);
        }
    }
}
