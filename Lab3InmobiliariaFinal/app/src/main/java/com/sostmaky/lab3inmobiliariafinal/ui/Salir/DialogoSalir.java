package com.sostmaky.lab3inmobiliariafinal.ui.Salir;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.sostmaky.lab3inmobiliariafinal.LoginActivity;
import com.sostmaky.lab3inmobiliariafinal.MenuActivity;

public class DialogoSalir {
    public static void mostrarDialogo(Context context, Fragment fragment) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmar salida")
                .setMessage("¿Desea salir? Si o no")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // borrar las credenciales almacenadas
                        SharedPreferences preferences = context.getSharedPreferences("token.xlm", Context.MODE_PRIVATE);

                        // lo lleva al  al login activity
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        fragment.startActivity(intent);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // vuelve a la activity principal
                        Intent intent = new Intent(context, MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        fragment.startActivity(intent);


                    }
                })
                .show();
    }
}
