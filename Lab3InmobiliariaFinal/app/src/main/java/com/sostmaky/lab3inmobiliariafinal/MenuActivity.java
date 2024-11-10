package com.sostmaky.lab3inmobiliariafinal;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.sostmaky.lab3inmobiliariafinal.Modelo.Propietario;
import com.sostmaky.lab3inmobiliariafinal.databinding.ActivityMenuBinding;
import com.sostmaky.lab3inmobiliariafinal.ui.perfil.PerfilViewModel;
import android.Manifest;
import android.Manifest.permission.*;
import java.io.File;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;
    private MenuActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel= new ViewModelProvider(this).get(MenuActivityViewModel.class);

        viewModel.datosPersonales();

        setSupportActionBar(binding.appBarMenu.toolbar);
       /* binding.appBarMenu.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });*/
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        // Configurar el NavController
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_Perfil,R.id.nav_Inmuebles,R.id.nav_Inquilinos,R.id.nav_Contrato,R.id.nav_Salir,R.id.mapsFragment,R.id.nav_Contrasena)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // *** Aquí comienzas a personalizar el nav_header_menu.xml ***
        // Obtener la vista del header
        View headerView = navigationView.getHeaderView(0);

        viewModel.getPropietario().observe(this, new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                if (propietario != null) {
                    View headerView = navigationView.getHeaderView(0);
                    // Acceder a los elementos dentro del nav_header_menu.xml
                    TextView navUserName = headerView.findViewById(R.id.tvNav_Nombre);
                    TextView navUserEmail = headerView.findViewById(R.id.tvNav_Correo);
                    ImageView navUserImage = headerView.findViewById(R.id.ivNav_Imagen);

                    // Asignar los valores del propietario
                    navUserName.setText(propietario.getApellido() + ", " + propietario.getNombre());
                    navUserEmail.setText(propietario.getEmail());
                    // Aquí podrías cargar la imagen del propietario en navUserImage si tienes una URL o recurso
                    String fotoPath = propietario.getFoto(); // Ruta local de la foto
                    File imgFile = new File(fotoPath);
                    if (imgFile.exists()) {
                        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        navUserImage.setImageBitmap(bitmap);
                    } else {
                        // Manejar el caso donde la imagen no existe
                        navUserImage.setImageResource(R.drawable.imagen); // Imagen en caso de error
                    }

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void checkPermission() { if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100); }  }
}