package com.sostmaky.lab3inmobiliariafinal.ui.contratos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Contrato;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Inquilino;
import com.sostmaky.lab3inmobiliariafinal.R;
import com.sostmaky.lab3inmobiliariafinal.ui.inquilinos.InquilinoAdapter;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder> {
    private Context context;
    private List<Contrato> contratos;
    private LayoutInflater inflater;

    //creo el contructor
    public ContratoAdapter(Context context, List<Contrato> contratos, LayoutInflater inflater) {
        this.context = context;
        this.contratos = contratos;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ContratoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_contra, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoAdapter.ViewHolder holder, int position) {
        Contrato contrato=contratos.get(position);
        holder.tvDirecInqui.setText(contratos.get(position).getInmueble().getDireccion());
        String foto = contrato.getInmueble().getFoto();

        if (foto != null && !foto.isEmpty()) {
            Uri uriFoto = Uri.parse(foto);
            Glide.with(holder.ivCarInq.getContext())
                    .load(uriFoto)
                    .placeholder(R.drawable.casadefe) // Imagen de carga
                    .error(R.drawable.casadefe)       // Imagen en caso de error
                    .into(holder.ivCarInq);
        } else {
            // Si no hay imagen, usa una imagen predeterminada
            holder.ivCarInq.setImageResource(R.drawable.casadefe);
        }
        holder.itemView.findViewById(R.id.btnVerContra).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bunde=new Bundle();
                bunde.putSerializable("contrato", contrato);
                Navigation.findNavController(view).navigate(R.id.contrato_DetalleFragment, bunde);

            }
        });


    }

    @Override
    public int getItemCount() {
        return contratos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDirecInqui;
        ImageView ivCarInq;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDirecInqui = itemView.findViewById(R.id.tvDirecInqui);
            ivCarInq = itemView.findViewById(R.id.ivCarInq);
        }
    }
}
