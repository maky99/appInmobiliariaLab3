package com.sostmaky.lab3inmobiliariafinal.ui.inmuebles;

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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Inmueble;
import com.sostmaky.lab3inmobiliariafinal.R;
import com.sostmaky.lab3inmobiliariafinal.request.ApiClient;


import java.util.List;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.Viewolder> {
    private Context context;
    private List<Inmueble> inmuebles;
    private LayoutInflater inflater;
    private int card;

    public ListaAdapter(@NonNull Context context,@NonNull List<Inmueble> inmuebles,int card, LayoutInflater inflater){
        this.context=context;
        this.inmuebles=inmuebles;
        this.inflater=inflater;
        this.card=card;

    }

    @NonNull
    @Override
    public Viewolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.card,parent,false);
        return new Viewolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewolder holder, int position) {
        Inmueble inmueble = inmuebles.get(position);
        holder.direccion.setText(inmuebles.get(position).getDireccion());
        holder.precio.setText(inmuebles.get(position).getPrecio()+"");
        String fotoUri = ApiClient.ima + inmueble.getFoto();
        if (fotoUri != null && !fotoUri.isEmpty()) {
            // Cargar la imagen usando Glide
            Glide.with(holder.itemView.getContext())
                    .load(fotoUri)
                    .placeholder(R.drawable.casadefe) // Imagen de carga
                    .error(R.drawable.casadefe) // Imagen en caso de error
                    .into(holder.fotoInmueble);
        } else {
            // Si no hay imagen, usar una imagen predeterminada
            holder.fotoInmueble.setImageResource(R.drawable.casadefe);
        }
        holder.itemView.findViewById(R.id.btMasInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bunde=new Bundle();
                bunde.putSerializable("informacion",inmueble);
                Navigation.findNavController((Activity) context,R.id.nav_host_fragment_content_menu).navigate(R.id.detalleFragment,bunde);

            }
        });


    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public class Viewolder extends RecyclerView.ViewHolder{

        ImageView fotoInmueble;
        TextView precio,direccion;

        public Viewolder(@NonNull View itemView) {
            super(itemView);
            fotoInmueble=itemView.findViewById(R.id.ivCarImagen);
            precio=itemView.findViewById(R.id.tvCarPrecio);
            direccion=itemView.findViewById(R.id.tvCarDireccion);

        }
    }

    public static class Nuevo_Inmueble_Fragment extends Fragment {

        private NuevoInmuebleViewModel mViewModel;

        public static Nuevo_Inmueble_Fragment newInstance() {
            return new Nuevo_Inmueble_Fragment();
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_nuevo_inmueble, container, false);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mViewModel = new ViewModelProvider(this).get(NuevoInmuebleViewModel.class);
            // TODO: Use the ViewModel
        }

    }

    public static class NuevoInmuebleViewModel extends ViewModel {
        // TODO: Implement the ViewModel
    }
}