package com.example.alquran_apps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alquran_apps.R;
import com.example.alquran_apps.models.AyatModel;

import java.util.List;

public class DetailSuratAdapter extends RecyclerView.Adapter<DetailSuratAdapter.HolderData> {
    List<AyatModel> listDetail;
    LayoutInflater inflater;

    public DetailSuratAdapter(Context context, List<AyatModel> listDetail) {
        this.inflater = LayoutInflater.from(context);
        this.listDetail = listDetail;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_detail_surat, parent, false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        holder.txtAyat.setText(listDetail.get(position).getText());
        holder.txtTerjemahan.setText(listDetail.get(position).getTranslation_id());
    }

    @Override
    public int getItemCount() {
        return listDetail.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView txtAyat, txtTerjemahan;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            txtAyat = itemView.findViewById(R.id.txtAyat);
            txtTerjemahan = itemView.findViewById(R.id.txtTerjemahan);
        }
    }
}
