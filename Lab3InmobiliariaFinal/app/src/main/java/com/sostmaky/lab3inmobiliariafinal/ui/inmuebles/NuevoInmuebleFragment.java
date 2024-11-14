package com.sostmaky.lab3inmobiliariafinal.ui.inmuebles;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sostmaky.lab3inmobiliariafinal.Modelo.TipoInmueble;
import com.sostmaky.lab3inmobiliariafinal.R;
import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentNuevoInmuebleBinding;

public class NuevoInmuebleFragment extends Fragment {

    private NuevoInmuebleViewModel mViewModel;
    private FragmentNuevoInmuebleBinding binding;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    private static final int REQUEST_CODE = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        InmueblesViewModel viewModel=new ViewModelProvider(this).get(InmueblesViewModel.class);
        mViewModel = new ViewModelProvider(this).get(NuevoInmuebleViewModel.class);
        binding = FragmentNuevoInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Verifica permisos al principio
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            abrirGaleria(); // Llama a abrirGaleria() si ya tienes el permiso
        }

        // Configura el observador para el tipo de inmueble
        viewModel.tipoInmuebleList();
        viewModel.getTipoInmueble().observe(getViewLifecycleOwner(), tipoInmuebles -> {
            if (tipoInmuebles != null) {
                ArrayAdapter<TipoInmueble> adapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item, tipoInmuebles);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerTipo.setAdapter(adapter);
            }
        });

        // Lógica del botón Aceptar
        binding.btnAceptar.setOnClickListener(view -> {
            //String foto = mViewModel.getUriMutable().getValue() != null ? mViewModel.getUriMutable().getValue().toString() : "";
            String direccion = binding.etDireccion.getText().toString();
            String uso = binding.spUso.getSelectedItem().toString();
            double precio = Double.parseDouble(binding.etPrecio.getText().toString());
            TipoInmueble tipoSeleccionado = (TipoInmueble) binding.spinnerTipo.getSelectedItem();
            int tipo = tipoSeleccionado.getId_Tipo_Inmueble();
            int ambientes = Integer.parseInt(binding.etAmbientes.getText().toString());
            double tamano = Double.parseDouble(binding.etTamano.getText().toString());
            int bano = Integer.parseInt(binding.etBanos.getText().toString());
            int cochera = Integer.parseInt(binding.etCochera.getText().toString());
            String servicios = binding.etServicios.getText().toString();
            int patio = Integer.parseInt(binding.etPatio.getText().toString());
            boolean disponible = binding.swDisponible.isChecked();
            String condicion = binding.spCondicion.getSelectedItem().toString();
            mViewModel.cargarInmuebleNuevo(binding.ivFoto, direccion, uso, precio, tipo, ambientes, tamano, bano, cochera, servicios, patio, disponible, condicion);
        });

        // Redirección al navegar
        mViewModel.getVista().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null && aBoolean) {
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.action_nuevo_Inmueble_Fragment_to_nav_Inmuebles);
            }
        });

        // Lógica del botón de cargar imagen
        binding.btnCargImag.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                arl.launch(intent);  // Llama a la galería
            } else {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        });

        // Observador de la foto seleccionada
        mViewModel.getUriMutable().observe(getViewLifecycleOwner(), uri -> {
            if (uri != null) {
                binding.ivFoto.setImageURI(uri); // Muestra la imagen seleccionada
            }
        });

        return root;
    }

    // Para abrir la galería
    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> mViewModel.recibirFoto(result));
    }

    // Manejo de resultados de los permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirGaleria();  // Si el permiso es otorgado, abre la galería
            } else {
                Log.d("Permiso", "Permiso denegado para leer el almacenamiento");
            }
        }
    }
}
