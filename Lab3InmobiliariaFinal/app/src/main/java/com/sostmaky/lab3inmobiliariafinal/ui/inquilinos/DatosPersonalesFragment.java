package com.sostmaky.lab3inmobiliariafinal.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Inquilino;
import com.sostmaky.lab3inmobiliariafinal.R;
import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentDatosPersonalesBinding;

public class DatosPersonalesFragment extends Fragment {


    private FragmentDatosPersonalesBinding bindig;
    private DatosPersonalesViewModel viewModel;

    public static DatosPersonalesFragment newInstance() {
        return new DatosPersonalesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel =new ViewModelProvider(this).get(DatosPersonalesViewModel.class);
        bindig = FragmentDatosPersonalesBinding.inflate(inflater, container, false);

        View root = bindig.getRoot();
        Inquilino inquilino = (Inquilino) getArguments().getSerializable("inquilino");
        viewModel.detaInqui(inquilino);


        viewModel.getDetaInmueble().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
        bindig.etDni.setText(String.valueOf(inquilino.getDni()));
        bindig.etApellido.setText(inquilino.getApellido());
        bindig.etNombre.setText(inquilino.getNombre());
        bindig.etTelefono.setText(inquilino.getTelefono());
        bindig.etMail.setText(inquilino.getEmail());
        String foto = inquilino.getFotoInmu();
        ImageView ivFotoInquilino = bindig.ivFoto;
            Glide.with(requireContext())
                    .load(foto)
                    .placeholder(R.drawable.casadefe)
                    .error(R.drawable.casadefe)
                    .into(ivFotoInquilino);

            }
        });


        return root;
        //return inflater.inflate(R.layout.fragment_datos_personales, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}