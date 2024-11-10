package com.sostmaky.lab3inmobiliariafinal.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.sostmaky.lab3inmobiliariafinal.MenuActivity;
import com.sostmaky.lab3inmobiliariafinal.Modelo.LoginCambio;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Propietario;
import com.sostmaky.lab3inmobiliariafinal.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambioContrasenaViewModel extends AndroidViewModel {
    private Context context;

    public CambioContrasenaViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();

    }

    public void cambioContrasena(String contraVieja,String contraNueva){
        //busco en el sharedoreferences el token que guarde
        SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
        String token = sp.getString("sp_token", null);
        String bearerToken = "Bearer " + token;

        LoginCambio logCambi=new LoginCambio(contraVieja,contraNueva);

        ApiClient.Inmobiliariaservice api =ApiClient.getapiInmobiliaria();
        Call<Propietario> llamada =api.editarPropietarioContra(bearerToken,logCambi);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(getApplication(), MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                    Toast.makeText(context, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        // Leer el cuerpo de error como String
                        String errorResponse = response.errorBody().string();
                        Toast.makeText(context, "Error al actualizar la contraseña: " + errorResponse, Toast.LENGTH_LONG).show();
                        Log.d("API", "Error en respuesta: " + errorResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("API", "Error al procesar el cuerpo de la respuesta de error");
                    }
                }


            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Toast.makeText(context,"Error en la conexión",Toast.LENGTH_SHORT).show();

            }
        });





    }





}
