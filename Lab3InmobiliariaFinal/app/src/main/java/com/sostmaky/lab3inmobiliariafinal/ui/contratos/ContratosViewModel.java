package com.sostmaky.lab3inmobiliariafinal.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sostmaky.lab3inmobiliariafinal.Modelo.Contrato;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Inmueble;
import com.sostmaky.lab3inmobiliariafinal.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Contrato>> mContrato;



    public ContratosViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }

    public LiveData<List<Contrato>> getContrato() {
        if (mContrato==null){
            mContrato=new MutableLiveData<>();
        }
        return mContrato;
    }

    public void contratosActivo(){
        //busco en el sharedoreferences el token que guarde
        SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
        String token = sp.getString("sp_token", null);
        String bearerToken = "Bearer " + token;
        ApiClient.Inmobiliariaservice api = ApiClient.getapiInmobiliaria();
        Call<List<Contrato>> llamada = api.contratosActivos(bearerToken);
        llamada.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                List<Contrato> contrato= response.body();
                //Log.d("pepe", contrato.toString());
                if (contrato != null && !contrato.isEmpty()) {
                    // si hay contratos los mando al mutable
                    mContrato.postValue(contrato);
                } else {
                    // si no hay le informo al usuario
                    Toast.makeText(context, "No hay contratos activos actualmente", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable throwable) {

            }
        });

    }



}
