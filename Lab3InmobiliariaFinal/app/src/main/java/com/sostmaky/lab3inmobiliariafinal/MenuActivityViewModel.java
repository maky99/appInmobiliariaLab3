package com.sostmaky.lab3inmobiliariafinal;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sostmaky.lab3inmobiliariafinal.Modelo.Propietario;
import com.sostmaky.lab3inmobiliariafinal.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Propietario> mPropietario;

    public MenuActivityViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }

    public LiveData<Propietario> getPropietario() {
        if (mPropietario==null){
            mPropietario=new MutableLiveData<>();
        }
        return mPropietario;
    }


    public void datosPersonales() {
        SharedPreferences sp = getApplication().getSharedPreferences("token.xlm", Context.MODE_PRIVATE); // Usar contexto de la aplicación
        String token = sp.getString("sp_token", null);
        String bearerToken = "Bearer " + token;

        ApiClient.Inmobiliariaservice api = ApiClient.getapiInmobiliaria();
        Call<Propietario> llamada = api.PropietarioPerfil(bearerToken);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Propietario propi = response.body();
                    mPropietario.setValue(propi);  // Actualiza LiveData con los datos del propietario
                    Log.d("DatosPersonales", "Propietario: " + propi.toString());
                } else {
                    Toast.makeText(getApplication(), "No se encontraron datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error en servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}




