package com.sostmaky.lab3inmobiliariafinal.ui.inquilinos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.sostmaky.lab3inmobiliariafinal.Modelo.Inquilino;
import com.sostmaky.lab3inmobiliariafinal.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinosViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Inquilino>> mListInquilino;

    public InquilinosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<List<Inquilino>> getTipoInmueble() {
        if (mListInquilino == null) {
            mListInquilino = new MutableLiveData<>();
        }
        return mListInquilino;
    }

    public void ListInquilios() {
        //busco en el sharedoreferences el token que guarde
        SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
        String token = sp.getString("sp_token", null);
        String bearerToken = "Bearer " + token;
        ApiClient.Inmobiliariaservice api = ApiClient.getapiInmobiliaria();
        Call<List<Inquilino>> llamada = api.listInquilinos(bearerToken);
        llamada.enqueue(new Callback<List<Inquilino>>() {
            @Override
            public void onResponse(Call<List<Inquilino>> call, Response<List<Inquilino>> response) {
                List<Inquilino> inquilinos = response.body();

                if (response.isSuccessful() && inquilinos != null && !inquilinos.isEmpty()) {
                    // Si hay inquilinos, actualizar el valor
                    mListInquilino.postValue(inquilinos);
                } else {
                    // Si la lista está vacía o la respuesta no fue exitosa, mostrar un Toast
                    Toast.makeText(context, "No hay inquilinos disponibles actualmente", Toast.LENGTH_SHORT).show();
                    Log.e("ListInquilinos", "Error en la respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Inquilino>> call, Throwable throwable) {
                Log.e("ListInquilios", "Fallo en la llamada: " + throwable.getMessage());

            }
        });

    }


}
