package com.sostmaky.lab3inmobiliariafinal.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TimeFormatException;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.sostmaky.lab3inmobiliariafinal.LoginActivity;
import com.sostmaky.lab3inmobiliariafinal.MenuActivity;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Inmueble;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Propietario;
import com.sostmaky.lab3inmobiliariafinal.Modelo.TipoInmueble;
import com.sostmaky.lab3inmobiliariafinal.R;
import com.sostmaky.lab3inmobiliariafinal.request.ApiClient;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Inmueble>> mPropiedades;
    private MutableLiveData<List<TipoInmueble>> mTipoInmueble;


    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        mTipoInmueble = new MutableLiveData<>();

    }



    public LiveData<List<Inmueble>> getPropiedades() {
        if (mPropiedades==null){
            mPropiedades=new MutableLiveData<>();
        }
        return mPropiedades;
    }
    public LiveData<List<TipoInmueble>> getTipoInmueble() {
        if (mTipoInmueble==null){
            mTipoInmueble=new MutableLiveData<>();
        }
        return mTipoInmueble;
    }

    public void Propiedades(){
        //busco en el sharedoreferences el token que guarde
        SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
        String token = sp.getString("sp_token", null);
        String bearerToken = "Bearer " + token;
        ApiClient.Inmobiliariaservice api=ApiClient.getapiInmobiliaria();
        Call<List<Inmueble>> llamada=api.Inmuebles(bearerToken);
        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    // tengo los inmuebles obtenidos en la respuesta
                    List<Inmueble> inmuebles = response.body();
                        //se los asigno al mutable
                        mPropiedades.postValue((List<Inmueble>) inmuebles);
                } else {
                    Log.e("Propiedades", "Error en la respuesta: " + response.code() + " - " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {

            }
        });
    }

    public void actualizaInmuebleEstado(Inmueble inmueble){
        SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
        String token = sp.getString("sp_token", null);
        String bearerToken = "Bearer " + token;
        ApiClient.Inmobiliariaservice api=ApiClient.getapiInmobiliaria();
        Call<String> llamada =api.actualizaInmuebleEstado(bearerToken,inmueble);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(context, "estado actualizado", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }



    public void tipoInmuebleList(){
        SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
        String token = sp.getString("sp_token", null);
        String bearerToken = "Bearer " + token;
        ApiClient.Inmobiliariaservice api=ApiClient.getapiInmobiliaria();
        Call<List<TipoInmueble>> llamada=api.tipoInmueble(bearerToken);
        llamada.enqueue(new Callback<List<TipoInmueble>>() {
            @Override
            public void onResponse(Call<List<TipoInmueble>> call, Response<List<TipoInmueble>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TipoInmueble> tipoInmuebles = response.body();

                    // Comprobar si la lista no está vacía
                    if (!tipoInmuebles.isEmpty()) {
                        Log.d("tipoInmuebleList", "Cantidad de tipos de inmueble: " + tipoInmuebles.size());
                        Log.d("tipoInmuebleList", "Cantidad de tipos de inmueble: " + tipoInmuebles.toString());

                        mTipoInmueble.postValue(tipoInmuebles);
                    } else {
                        Log.e("tipoInmuebleList", "La lista de tipos de inmueble está vacía.");
                    }
                } else {
                    Log.e("tipoInmuebleList", "Error en la respuesta: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<TipoInmueble>> call, Throwable throwable) {

            }
        });
    }

   /* public void cargarInmueblNuevo( String foto,String direccion,String uso,double precio, int tipo, int ambientes,double tamano,int bano,int cochera,String servicios,int patio, boolean disponible,String condicion ){
        Log.d("estre", direccion);
        int estado=disponible ? 1: 0;
        Inmueble inmueble=new Inmueble(direccion,uso, ambientes,tamano, tipo,servicios, bano,cochera,patio,precio,condicion,foto,estado);
        SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
        String token = sp.getString("sp_token", null);
        String bearerToken = "Bearer " + token;
        ApiClient.Inmobiliariaservice api=ApiClient.getapiInmobiliaria();
        Call<Inmueble> llamada=api.crearInmueble(bearerToken,inmueble);
        Log.d("API", "Llamando a la API para crear un inmueble");
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                     Log.d("respuesta", response.toString());
                    Toast.makeText(context, "Inmueble creado correctamente", Toast.LENGTH_SHORT).show();
                    mVista.setValue(true);
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable throwable) {
                Toast.makeText(context, "Error al crear inmueble", Toast.LENGTH_SHORT).show();

            }
        });


    }*/


    /*
    public void cargarInmuebleNuevo(String direccion, String uso) {
        Log.d("estre", direccion);

        // Crea el RequestBody solo para la dirección
        RequestBody Direccion = RequestBody.create(MediaType.parse("text/plain"), direccion);
        RequestBody Uso = RequestBody.create(MediaType.parse("text/plain"), uso);

        // Obtiene el token de SharedPreferences
        SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
        String token = sp.getString("sp_token", null);
        String bearerToken = "Bearer " + token;

        // Obtiene la instancia de la API
        ApiClient.Inmobiliariaservice api = ApiClient.getapiInmobiliaria();

        // Crea la llamada a la API solo con el parámetro de dirección
        Call<Inmueble> llamada = api.crearInmueble(bearerToken, Direccion);

        Log.d("llamo", "Llamando a la API para crear un inmueble");

        // Realiza la llamada a la API de manera asíncrona
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                Log.d("respuesta", response.toString());
                Toast.makeText(context, "Inmueble creado correctamente", Toast.LENGTH_SHORT).show();
                mVista.setValue(true);
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable throwable) {
                Toast.makeText(context, "Error al crear inmueble", Toast.LENGTH_SHORT).show();
            }
        });
    }*/









}