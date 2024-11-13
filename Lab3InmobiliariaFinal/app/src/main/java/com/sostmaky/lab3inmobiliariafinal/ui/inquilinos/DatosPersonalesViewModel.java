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

public class DatosPersonalesViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Inquilino> mDetalleInquilino;

    public DatosPersonalesViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<Inquilino> getDetaInmueble() {
        if (mDetalleInquilino == null) {
            mDetalleInquilino = new MutableLiveData<>();
        }
        return mDetalleInquilino;
    }

    public void detaInqui (Inquilino inquilino){



        mDetalleInquilino.setValue(inquilino);
    }



}
