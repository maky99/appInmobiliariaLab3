package com.sostmaky.lab3inmobiliariafinal.ui.Salir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class SalirFragment extends Fragment {
    private SalirViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(this).get(SalirViewModel.class);

        viewModel.mostrarDialogo(getContext(),this);

        return null;
    }
}

