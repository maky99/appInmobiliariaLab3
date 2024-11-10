package com.sostmaky.lab3inmobiliariafinal.ui.inquilinos;

import android.app.Activity;
import android.content.Context;
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
import com.sostmaky.lab3inmobiliariafinal.Modelo.Inquilino;
import com.sostmaky.lab3inmobiliariafinal.R;

import java.io.Serializable;
import java.util.List;

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.ViewHolder> {
    private Context context;
    private List<Inquilino> inquilinos;
    private LayoutInflater inflater;

    //creo el contructor
    public InquilinoAdapter(@NonNull Context context, @NonNull List<Inquilino> inquilinos, LayoutInflater inflater) {
        this.context = context;
        this.inquilinos = inquilinos;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public InquilinoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_inq, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InquilinoAdapter.ViewHolder holder, int position) {
        Inquilino inquilino = inquilinos.get(position);
        holder.tvDirecInqui.setText(inquilinos.get(position).getDireccion());
        String foto = inquilino.getFotoInmu();
        Log.d("InquilinoAdapter", "URL de foto: " + foto); // Agr
        if (foto != null && !foto.isEmpty()) {
            Glide.with(holder.ivCarInq.getContext())
                    .load(foto)
                    .placeholder(R.drawable.casadefe) // Imagen de carga
                    .error(R.drawable.casadefe) // Imagen en caso de error
                    .into(holder.ivCarInq);
        } else {
            // Si no hay imagen, usar una imagen predeterminada
            holder.ivCarInq.setImageResource(R.drawable.casadefe);
        }
        holder.itemView.findViewById(R.id.btnVerInqui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bunde=new Bundle();
                bunde.putSerializable("inquilino", inquilino);
                Navigation.findNavController(view).navigate(R.id.action_nav_Inquilinos_to_datosPersonalesFragment, bunde);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inquilinos.size();
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
