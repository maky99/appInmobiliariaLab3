package com.sostmaky.lab3inmobiliariafinal.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sostmaky.lab3inmobiliariafinal.Modelo.Inmueble;
import com.sostmaky.lab3inmobiliariafinal.R;
import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentInmueblesBinding;

import java.util.List;

public class InmuebleshowFragment extends Fragment {

    private FragmentInmueblesBinding binding;
    private NuevoInmuebleViewModel nuevoViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InmueblesViewModel viewModel =
                new ViewModelProvider(this).get(InmueblesViewModel.class);


        nuevoViewModel=new ViewModelProvider(this).get(NuevoInmuebleViewModel.class);
        binding = FragmentInmueblesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.btnFlotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.tipoInmuebleList();
                //Toast.makeText(getActivity(), "Botón flotante presionado", Toast.LENGTH_SHORT).show();
                // uso un NavController para navegar al nuevo fragmento
                Navigation.findNavController(view).navigate(R.id.action_nav_Inmuebles_to_nuevo_Inmueble_Fragment);
            }
        });

        viewModel.Propiedades();

        viewModel.getPropiedades().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                ListaAdapter adapter =new ListaAdapter(getContext(), inmuebles, R.layout.card,getLayoutInflater());
                RecyclerView reciView=binding.rvListado;
                GridLayoutManager grilla=new GridLayoutManager(getContext(),2, GridLayoutManager.VERTICAL, false);
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