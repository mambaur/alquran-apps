package com.caraguna.alquran_apps.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caraguna.alquran_apps.R;
import com.caraguna.alquran_apps.activities.DetailDoa;
import com.caraguna.alquran_apps.models.DoaModel;
import com.caraguna.alquran_apps.util.Configuration;

import java.util.List;

public class ListDoaAdapter extends RecyclerView.Adapter<ListDoaAdapter.HolderData> {
    private List<DoaModel> listData;
    private LayoutInflater inflater;
    private Context context;

    public ListDoaAdapter(Context context, List<DoaModel> listData) {
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_doa, parent, false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        holder.txtJudul.setText(listData.get(position).getJudul());
        holder.txtSubJudul.setText(listData.get(position).getSub_judul());

        final String id = listData.get(position).getId();
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public class HolderData extends RecyclerView.ViewHolder {
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
