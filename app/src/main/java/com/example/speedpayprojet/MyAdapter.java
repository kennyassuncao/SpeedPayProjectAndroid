package com.example.speedpayprojet;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Historico> list;

    public MyAdapter(Context context, ArrayList<Historico> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Historico historico =list.get(position);
        holder.data.setText(historico.getData());
        holder.tipoTransasao.setText(historico.getTipoTransasao());
        holder.valorTrans.setText(historico.getValorTrans());
        holder.saldo.setText(historico.getSaldo());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView data,tipoTransasao,valorTrans,saldo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            data=itemView.findViewById(R.id.tvdata);
            tipoTransasao=itemView.findViewById(R.id.tvtipoTransasao);
            valorTrans=itemView.findViewById(R.id.tvvalorTrans);
            saldo=itemView.findViewById(R.id.tvsaldo);

        }
    }
}
