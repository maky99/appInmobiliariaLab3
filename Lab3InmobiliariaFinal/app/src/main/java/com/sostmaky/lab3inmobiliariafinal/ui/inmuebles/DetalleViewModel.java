package com.sostmaky.lab3inmobiliariafinal.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sostmaky.lab3inmobiliariafinal.Modelo.Inmueble;
import com.sostmaky.lab3inmobiliariafinal.Modelo.TipoInmueble;
import com.sostmaky.lab3inmobiliariafinal.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Inmueble> mInmueble;



    public DetalleViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        mInmueble = new MutableLiveData<>();

    }



    public LiveData<Inmueble> getInmueblDeta() {
        if (mInmueble == null){
            mInmueble = new MutableLiveData<>();
        }
        return mInmueble;
    }


   public void cargaDetalle(Inmueble inmueble){

       mInmueble.setValue(inmueble);


    }





}