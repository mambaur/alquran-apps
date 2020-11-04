package com.caraguna.alquran_apps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caraguna.alquran_apps.R;

import java.util.List;

public class TafsirAdapter extends RecyclerView.Adapter<TafsirAdapter.HolderData> {
    List<String> listData;
    LayoutInflater inflater;

    public TafsirAdapter(Context context, List<String> listData) {
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_doa, parent, false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        int nomor = position+1;
        holder.txtJudul.setText("Ayat ke "+nomor);
        holder.txtSubJudul.setText(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        LinearLayout linear;
        TextView txtJudul, txtSubJudul;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            linear = itemView.findViewById(R.id.linear);
            txtJudul = itemView.findViewById(R.id.txtNamaSurat);
            txtSubJudul = itemView.findViewById(R.id.txtSubSurat);
        }
    }
}
