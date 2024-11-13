package com.sostmaky.lab3inmobiliariafinal.ui.inmuebles;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Inmueble;
import com.sostmaky.lab3inmobiliariafinal.R;
import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentDetalleBinding;

public class DetalleFragment extends Fragment {
    private FragmentDetalleBinding binding;
    private InmueblesViewModel viewModel;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 100;
    private DetalleViewModel detalleViewModel;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding=FragmentDetalleBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
        viewModel = new ViewModelProvider(this).get(InmueblesViewModel.class);
        detalleViewModel=new ViewModelProvider(this).get(DetalleViewModel.class);
        Inmueble inmueble = (Inmueble) getArguments().getSerializable("informacion");
        detalleViewModel.cargaDetalle(inmueble);

        detalleViewModel.getInmueblDeta().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {

            if (inmueble != null) {
                String foto = inmueble.getFoto();
                if (foto != null) {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(requireActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                    } else {
                        cargarImagen(foto);
                    }
                } else {
                    binding.ivFoto.setImageResource(R.drawable.casadefe);
                }
                binding.tvDireccionValor.setText(inmueble.getDireccion());
                binding.tvPrecioValor.setText(inmueble.getPrecio()+"");
                binding.tvUsoValor.setText(inmueble.getUso());
                binding.tvAmbientesValor.setText(Integer.toString (inmueble.getAmbientes()));
                binding.tvTamanoValor.setText(inmueble.getTamano()+"");
                binding.tvServiciosValor.setText(inmueble.getServicios());
                binding.tvBanoValor.setText(Integer.toString(inmueble.getBano()));
                binding.tvCocheraValor.setText(Integer.toString(inmueble.getCochera()));
                binding.tvPatioValor.setText(Integer.toString(inmueble.getPatio()));
                boolean estado = inmueble.getEstado_Inmueble() == 1; // true si es 1, false si es 0
                binding.swEstado.setChecked(estado);
                // Añadir el listener para detectar cambios en el switch
                binding.swEstado.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    // Aquí manejas el cambio del switch
                    int nuevoEstado = isChecked ? 1 : 0; // Convertir el booleano en un valor entero
                    inmueble.setEstado_Inmueble(nuevoEstado); // Actualizar el modelo localmente
                    viewModel.actualizaInmuebleEstado(inmueble);
                });
            }
            }
        });

        return root;
    }
    private void cargarImagen(String fotoUri) {
        Glide.with(this)
                .load(fotoUri)
                .placeholder(R.drawable.casadefe)
                .error(R.drawable.casadefe)
                .into(binding.ivFoto);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (getArguments() != null) {
                    Inmueble inmueble = (Inmueble) getArguments().getSerializable("informacion");
                    if (inmueble != null && inmueble.getFoto() != null) {
                        cargarImagen(inmueble.getFoto());
                    }
                }
            }
        }
    }



}
