package com.ichmal.jokiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.MyViewHolder> {

    ArrayList<Order> mList;
    Context context;

    public RiwayatAdapter(Context context, ArrayList<Order> mList){
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order = mList.get(position);
        holder.name.setText(order.getNama());
        holder.email.setText(order.getEmail());
        holder.phone.setText(order.getPhone());
        holder.tier.setText(order.getTierAkun());
        holder.tipe.setText(order.getTipeOrder());
        holder.tanggal.setText(order.getTanggal());
        holder.status.setText(order.getStatus());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, email, phone, tier, status, tipe, tanggal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_text);
            email = itemView.findViewById(R.id.email_text);
            phone = itemView.findViewById(R.id.phone_text);
            tier = itemView.findViewById(R.id.tier_text);
            tipe = itemView.findViewById(R.id.order_text);
            tanggal = itemView.findViewById(R.id.tanggal_text);
            status = itemView.findViewById(R.id.status_text);

        }
    }
}
