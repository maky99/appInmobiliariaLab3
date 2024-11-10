package com.sostmaky.lab3inmobiliariafinal.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sostmaky.lab3inmobiliariafinal.Modelo.Pago;
import com.sostmaky.lab3inmobiliariafinal.R;
import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentPagosBinding;

import java.util.List;

public class PagosFragment extends Fragment {

    private PagosViewModel mViewModel;
    private FragmentPagosBinding bindig;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PagosViewModel.class);

        bindig=FragmentPagosBinding.inflate(inflater,container,false);
        View root=bindig.getRoot();

        int id = getArguments().getInt("id_contrato", -1);
        if (id != -1) {
            mViewModel.pagos(id);  // Llama al método aquí
        }


        mViewModel.getPago().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                PagoAdapter adapter= new PagoAdapter(getContext(),pagos,getLayoutInflater());
                GridLayoutManager grilla=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                RecyclerView reciView= bindig.rwPagos;
                reciView.setLayoutManager(grilla);
                reciView.setAdapter(adapter);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PagosViewModel.class);
        // TODO: Use the ViewModel
    }

}