package com.sostmaky.lab3inmobiliariafinal.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sostmaky.lab3inmobiliariafinal.Modelo.Contrato;
import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentContratosBinding;
import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentInquilinosBinding;
import com.sostmaky.lab3inmobiliariafinal.ui.inquilinos.InquilinosViewModel;

import java.util.List;

public class ContratosFragment extends Fragment {
    private FragmentContratosBinding binding;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 100;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        ContratosViewModel contratosViewModel =new ViewModelProvider(this).get(ContratosViewModel.class);

        binding = FragmentContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

       contratosViewModel.contratosActivo();
       contratosViewModel.getContrato().observe(getViewLifecycleOwner(), new Observer<List<Contrato>>() {
           @Override
           public void onChanged(List<Contrato> contratoes) {
               ContratoAdapter adapter= new ContratoAdapter(getContext(),contratoes,getLayoutInflater());
               GridLayoutManager grilla=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
               RecyclerView reciView=binding.rwContratos;
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
