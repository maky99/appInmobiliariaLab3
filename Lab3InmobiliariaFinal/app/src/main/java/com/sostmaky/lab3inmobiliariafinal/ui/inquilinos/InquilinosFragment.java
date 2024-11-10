package com.sostmaky.lab3inmobiliariafinal.ui.inquilinos;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sostmaky.lab3inmobiliariafinal.Modelo.Inquilino;
import com.sostmaky.lab3inmobiliariafinal.R;
import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentInmueblesBinding;
import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentInquilinosBinding;
import com.sostmaky.lab3inmobiliariafinal.ui.inmuebles.InmueblesViewModel;
import com.sostmaky.lab3inmobiliariafinal.ui.inmuebles.ListaAdapter;

import java.util.List;

public class InquilinosFragment extends Fragment {

    private FragmentInquilinosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        InquilinosViewModel inquilinosViewModel =new ViewModelProvider(this).get(InquilinosViewModel.class);

        binding = FragmentInquilinosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        inquilinosViewModel.ListInquilios();

        inquilinosViewModel.getTipoInmueble().observe(getViewLifecycleOwner(), new Observer<List<Inquilino>>() {
            @Override
            public void onChanged(List<Inquilino> inquilinos) {

               InquilinoAdapter adapter=new InquilinoAdapter(getContext(),inquilinos, getLayoutInflater());
                GridLayoutManager grilla=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                RecyclerView reciView=binding.rwListaInqui;
                reciView.setLayoutManager(grilla);
                reciView.setAdapter(adapter);

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
