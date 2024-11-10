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
import com.sostmaky.lab3inmobiliariafinal.Modelo.Pago;
import com.sostmaky.lab3inmobiliariafinal.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Pago>> mPago;


    public PagosViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        mPago = new MutableLiveData<>();

    }

    public LiveData<List<Pago>> getPago() {
        if (mPago == null){
            mPago = new MutableLiveData<>();
        }
        return mPago;
    }

    public void pagos(int id){
        //busco en el sharedoreferences el token que guarde
        SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
        String token = sp.getString("sp_token", null);
        String bearerToken = "Bearer " + token;
        ApiClient.Inmobiliariaservice api = ApiClient.getapiInmobiliaria();
        Call<List<Pago>> llamada= api.pagosXContrato(id,bearerToken);
        llamada.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                List<Pago> pagos=response.body();
                //Log.d("pago linea 52" , pagos.toString());
                // veo  si la lista esta vacia o null
                if (pagos == null || pagos.isEmpty()) {
                    Toast.makeText(context, "No hay realizados ", Toast.LENGTH_SHORT).show();
                } else {
                    mPago.postValue(pagos);
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable throwable) {

            }
        });


    }





}