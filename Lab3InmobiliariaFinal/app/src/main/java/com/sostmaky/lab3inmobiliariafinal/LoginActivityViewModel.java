package com.sostmaky.lab3inmobiliariafinal;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sostmaky.lab3inmobiliariafinal.Modelo.Login;
import com.sostmaky.lab3inmobiliariafinal.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Boolean> TokenValid;
    private String token;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        TokenValid=new MutableLiveData<>();

    }

    public LiveData<Boolean> getTokenValid() {
        return TokenValid;
    }


    public void LlamarLogin(String mail,String contrasena){
        Login login=new Login(mail, contrasena);
        ApiClient.Inmobiliariaservice api=ApiClient.getapiInmobiliaria();
        Call<String> llamada= api.login(login);
        //si hay llamada del lado del servidor devuelve la llamada onResponse si hay un error devuelve on Failure
        llamada.enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> llamada, Response<String> response) {
        if (response.isSuccessful()){
            String token = response.body(); // guardo el token en una variable
            //si la respuesta esta ok me devuelve
            Log.d("salida",response.body());
            //guardo el toquen en sharedpreferencces
            SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("sp_token", token);
            editor.commit();
            // Navegar a la vista de detalle de propietario
            Intent intent = new Intent(getApplication(), MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(intent);
        }else {
            Log.e("Error", "Código de error: " + response.code());
            Toast.makeText(getApplication(),"datos incorrectos",Toast.LENGTH_SHORT).show();
        }
        }
        @Override
        public void onFailure(Call<String> call, Throwable throwable) {
            Toast.makeText(getApplication(),"error servidor ",Toast.LENGTH_SHORT).show();
            Log.d("salidaError", throwable.getMessage());

        }
    });
    }



    public void mostrarDialogoOlvidoContrasena(String email) {
        if (!email.isEmpty()) {
            ApiClient.Inmobiliariaservice api = ApiClient.getapiInmobiliaria();
            Call<String> llamada = api.olvidoContrasena(email);
            llamada.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    token = response.body();
                    if (token != null && !token.isEmpty()) {
                        TokenValid.setValue(true);
                        // Log.d("salida", "Token: " + response.code());
                        //Log.d("salida", "Token: " + token);
                    } else {
                        TokenValid.setValue(false);
                        // Log.d("salida", "Token is null or empty");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                }
            });
        }else {
            Toast.makeText(context, "Por favor, ingrese su correo", Toast.LENGTH_SHORT).show();
        }
    }



    public void resetContrasena(){
        ApiClient.Inmobiliariaservice api=ApiClient.getapiInmobiliaria();
        String bearerToken = "Bearer " + token;
        Log.d("salida", "Tokencito: " + bearerToken);
        Call<String> llamada= api.resetContrasena(bearerToken);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getApplication(),"Se envio a su Correo la nueva contraseña ",Toast.LENGTH_SHORT).show();
               // Log.d("salida", "regreso: " + response.code());
                //Log.d("salida", "regreso: " + response.toString());
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
            }
        });

    }





}
