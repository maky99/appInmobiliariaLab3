package com.sostmaky.lab3inmobiliariafinal;

import static android.Manifest.permission.CALL_PHONE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sostmaky.lab3inmobiliariafinal.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements SensorEventListener {
    private ActivityLoginBinding binding;
    private LoginActivityViewModel viewModel;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean isMoving = false;
    private Conectado conectado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);
        // Inicializar el SensorManager y el acelerómetro
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        solicitarPermisos();


        binding.btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creo un intent para navegar del LoginActivity a MenuActivity
                //Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                //startActivity(intent);
                //aca llamo lo que tiene el inicio con el correo y la contraseña
                viewModel.LlamarLogin(binding.etCorreo.getText().toString(), binding.etContrasena.getText().toString());
            }
        });
        //veo si en el formulario esta puesto el correo para resetar la contraseña
        binding.tvOlvidoContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = binding.etCorreo.getText().toString();
                    viewModel.mostrarDialogoOlvidoContrasena(correo);
            }
        });
        //controlo que el usuaio sea correcto y le pregunto si realmente quiere seretear
        viewModel.getTokenValid().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isValid) {
                if (isValid) {
                    // Si el token es válido, sigue como está
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Confirmación")
                            .setMessage("¿Está seguro de que desea resetear su contraseña?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    viewModel.resetContrasena();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    // si el token es false muestra un mensaje de error
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Error")
                            .setMessage("El usuario ingresado es incorrecto.")
                            .setPositiveButton("Volver", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss(); // Cierra el diálogo
                                }
                            })
                            .show();
                }
            }
        });

    }


    private void solicitarPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{CALL_PHONE}, 1000);
        }
    }
    //asi es si quiero que llame cuendo se conecte a internet con el wifi y esta la clase conectado
    private void registrarBroadcast(){
        this.conectado=new Conectado();
        registerReceiver(conectado,new IntentFilter("android.net.wifi.supplicant.CONNECTION_CHANGE"));
    }




    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // veo si el movimiento es fuerte
            if (Math.abs(x) > 2 || Math.abs(y) > 3 || Math.abs(z) > 3) {
                isMoving = true;
            } else if (isMoving) {
                // si dejo de moverse hace la llamada
              realizarLlamada();
                isMoving = false;
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void realizarLlamada() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso para realizar llamadas no concedido", Toast.LENGTH_SHORT).show();
            solicitarPermisos();
            return; // Sale si no tiene el permiso
        }

        Intent intentLlamada = new Intent(Intent.ACTION_CALL);
        intentLlamada.setData(Uri.parse("tel:" + "2664553747"));
        intentLlamada.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentLlamada);
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}

