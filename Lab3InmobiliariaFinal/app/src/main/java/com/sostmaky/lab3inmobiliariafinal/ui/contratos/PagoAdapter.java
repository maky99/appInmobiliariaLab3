package com.sostmaky.lab3inmobiliariafinal.ui.contratos;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sostmaky.lab3inmobiliariafinal.Modelo.Contrato;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Pago;
import com.sostmaky.lab3inmobiliariafinal.R;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.ViewHolder> {
    private Context context;
    private List<Pago> pagos;
    private LayoutInflater inflater;

    //creo el constructor


    public PagoAdapter(Context context, List<Pago> pagos, LayoutInflater inflater) {
        this.context = context;
        this.pagos = pagos;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public PagoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_pagos, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoAdapter.ViewHolder holder, int position) {
        Pago pago = pagos.get(position);
        holder.tvTicket.setText(String.valueOf(pago.getId_Pago()));
        String fechaPago = pago.getFecha();
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date fech = formatoEntrada.parse(fechaPago);
            String fechaP = formatoSalida.format(fech);
            holder.tvFechaPago.setText(fechaP);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        holder.tvIdContrato.setText(String.valueOf(pago.getId_Contrato()));
        NumberFormat fotrmaMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
        double importe = pago.getImporte();
        holder.tvImporte.setText(fotrmaMoneda.format(importe));
        int estado = pago.getEstado_Pago();
        String estadoTexto = (estado == 1) ? "Pagado" : "Anulado";
        holder.tvEstadoPago.setText(estadoTexto);

    }

    @Override
    public int getItemCount() {
        return pagos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTicket, tvFechaPago, tvIdContrato, tvImporte, tvEstadoPago;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTicket = itemView.findViewById(R.id.tvTicket);
            tvFechaPago = itemView.findViewById(R.id.tvFechaPago);
            tvIdContrato = itemView.findViewById(R.id.tvIdContrato);
            tvImporte = itemView.findViewById(R.id.tvImporte);
            tvEstadoPago = itemView.findViewById(R.id.tvEstadoPago);

        }
    }
}
