package com.example.alquran_apps.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alquran_apps.R;
import com.example.alquran_apps.activities.DetailSurat;
import com.example.alquran_apps.models.SuratModel;

import java.util.List;

public class SuratAdapter extends RecyclerView.Adapter<SuratAdapter.HolderData> {
    private Context context;
    private LayoutInflater inflater;
    private List<SuratModel> listSurat;
    private static final String NOMOR_SURAT = "no_surat";

    public SuratAdapter(Context context, List<SuratModel> listSurat) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.listSurat = listSurat;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_surat, parent, false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        holder.txtSurat.setText(listSurat.get(position).getNama());
        holder.txtNomor.setText(listSurat.get(position).getNomor());
        holder.txtArti.setText(listSurat.get(position).getArti());
        holder.txtAsma.setText(listSurat.get(position).getAsma());

        final String nomor = listSurat.get(position).getNomor();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailSurat.class);
                intent.putExtra(NOMOR_SURAT, nomor);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSurat.size();
    }


    static class HolderData extends RecyclerView.ViewHolder {
        TextView txtSurat, txtArti, txtAsma, txtNomor;
        CardView cardView;
        HolderData(@NonNull View itemView) {
            super(itemView);
            txtSurat = itemView.findViewById(R.id.txtSurat);
            txtArti = itemView.findViewById(R.id.txtArtiSurat);
            txtAsma = itemView.findViewById(R.id.txtAsmaSurat);
            txtNomor = itemView.findViewById(R.id.txtNomorSurat);
            cardView = itemView.findViewById(R.id.cardSurat);
        }
    }
}
