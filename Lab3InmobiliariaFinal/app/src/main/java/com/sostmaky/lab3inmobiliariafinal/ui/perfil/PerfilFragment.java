package com.sostmaky.lab3inmobiliariafinal.ui.perfil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Propietario;
import com.sostmaky.lab3inmobiliariafinal.R;
import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel viewModel;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel= new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        abrirGaleria();


        viewModel.datosPersonales();



        viewModel.getPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etDni.setText(String.valueOf(propietario.getDni()));
                binding.etApellido.setText(propietario.getApellido());
                binding.etNombre.setText(propietario.getNombre());
                binding.etDireccion.setText(propietario.getDireccion());
                binding.etTelefono.setText(propietario.getTelefono());
                binding.etMail.setText(propietario.getEmail());
                /*if (propietario.getFoto() != null ) {
                    Glide.with(getContext().getApplicationContext()) // Contexto del fragmento
                            .load(propietario.getFoto()) // URL de la imagen
                            .placeholder(R.drawable.imagen) // Imagen predeterminada mientras se carga
                            .error(R.drawable.imagen) // Imagen de error si falla la carga
                            .into(binding.ivFoto); // ImageView donde se mostrará la imagen
                } else {
                    binding.ivFoto.setImageResource(R.drawable.imagen); // Imagen predeterminada si no hay URL
                }*/

               if (propietario.getFoto() != null ){
                    binding.ivFoto.setImageURI(Uri.parse(propietario.getFoto()));
                }
            }
        });

        binding.btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Extraer todos los datos del formulario y enviarlos al ViewModel
                String dni = binding.etDni.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String nombre = binding.etNombre.getText().toString();
                String direccion = binding.etDireccion.getText().toString();
                String telefono = binding.etTelefono.getText().toString();
                String correo = binding.etMail.getText().toString();
                String foto=binding.ivFoto.toString();

                // Llamar al método del ViewModel pasando todos los datos editados
                viewModel.editarDatos(dni, apellido, nombre, direccion, telefono, correo,foto);
            }
        });
        binding.btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });

//observer para ver la foto seleccionada
        viewModel.getUriMutable().observe(getViewLifecycleOwner(), new Observer<Uri>() {
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
                viewModel.recibirFoto(result);


            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}