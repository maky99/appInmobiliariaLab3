package com.sostmaky.lab3inmobiliariafinal.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.sostmaky.lab3inmobiliariafinal.Modelo.TipoInmueble;
import com.sostmaky.lab3inmobiliariafinal.R;
import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentInmueblesBinding;
import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentNuevoInmuebleBinding;

public class NuevoInmuebleFragment extends Fragment {

    private NuevoInmuebleViewModel mViewModel;
    private FragmentNuevoInmuebleBinding binding;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;

    private InmueblesViewModel viewModel;

    public static NuevoInmuebleFragment newInstance() {
        return new NuevoInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        InmueblesViewModel viewModel =
                new ViewModelProvider(this).get(InmueblesViewModel.class);
        mViewModel=new ViewModelProvider(this).get(NuevoInmuebleViewModel.class);
        binding = FragmentNuevoInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        abrirGaleria();

        viewModel.tipoInmuebleList();


        viewModel.getTipoInmueble().observe(getViewLifecycleOwner(),tipoInmuebles -> {
            if (tipoInmuebles != null) {
                ArrayAdapter<TipoInmueble> adapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item, tipoInmuebles);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerTipo.setAdapter(adapter);
            }
        });

        binding.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foto = mViewModel.getUriMutable().getValue() != null ? mViewModel.getUriMutable().getValue().toString() : "";
                String direccion=binding.etDireccion.getText().toString();
                String uso=binding.spUso.getSelectedItem().toString();
                double precio= Double.parseDouble(binding.etPrecio.getText().toString());
                TipoInmueble tipoSeleccionado = (TipoInmueble) binding.spinnerTipo.getSelectedItem();
                int tipo = tipoSeleccionado.getId_Tipo_Inmueble();
                int ambientes = Integer.parseInt(binding.etAmbientes.getText().toString());
                double tamano=Double.parseDouble(binding.etTamano.getText().toString());
                int bano = Integer.parseInt(binding.etBanos.getText().toString());
                int cochera=Integer.parseInt(binding.etCochera.getText().toString());
                String servicios=binding.etServicios.getText().toString();
                int patio = Integer.parseInt(binding.etPatio.getText().toString());
                boolean disponible = binding.swDisponible.isChecked();
                String condicion=binding.spCondicion.getSelectedItem().toString();
                viewModel.cargarInmuebleNuevo(foto, direccion, uso, precio, tipo, ambientes, tamano, bano, cochera,servicios, patio, disponible, condicion);
            }
        });

        viewModel.getVista().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null && aBoolean) {
                    NavController navController = Navigation.findNavController(requireView());
                    navController.navigate(R.id.action_nuevo_Inmueble_Fragment_to_nav_Inmuebles);
            }
            }
        });



     binding.btnCargImag.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            arl.launch(intent);
        }
    });
        //observer para ver la foto seleccionada
        mViewModel.getUriMutable().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                if (uri != null) {
                    binding.ivFoto.setImageURI(uri);
                }

            }
        });


        return root;
    }
    //para abrir la galeria
    private void abrirGaleria(){
        intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                mViewModel.recibirFoto(result);


            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NuevoInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }

}