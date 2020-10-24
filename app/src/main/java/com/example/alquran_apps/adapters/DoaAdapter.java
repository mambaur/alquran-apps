package com.example.alquran_apps.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alquran_apps.R;

import java.util.List;

public class DoaAdapter extends RecyclerView.Adapter<DoaAdapter.HolderData> {
    private List<String> listData;
    private LayoutInflater inflater;
    private Context context;

    public DoaAdapter(Context context, List<String> listData) {
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_doa, parent, false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        holder.txtJudul.setText(listData.get(position));
        Glide.with(context).load("https://tebuireng.online/wp-content/uploads/2018/04/Doa-Muslimaah.jpg").into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView txtJudul;
        ImageView imgView;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            txtJudul = itemView.findViewById(R.id.txtJudul);
            imgView = itemView.findViewById(R.id.imgDoaItem);
        }
    }
}
