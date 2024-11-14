package com.sostmaky.lab3inmobiliariafinal.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sostmaky.lab3inmobiliariafinal.Modelo.Inmueble;
import com.sostmaky.lab3inmobiliariafinal.request.ApiClient;

import java.io.ByteArrayOutputStream;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> uriMutableLiveData;
    private Uri uri;
    private Context context;
    private MutableLiveData<Boolean> mVista;


    public NuevoInmuebleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        mVista = new MutableLiveData<>();
    }

    public LiveData<Uri> getUriMutable() {
        if (uriMutableLiveData == null) {
            uriMutableLiveData = new MutableLiveData<>();
        }
        return uriMutableLiveData;
    }

    public LiveData<Boolean> getVista() {
        return mVista;
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                uri = data.getData();
                uriMutableLiveData.setValue(uri);
            }
        }
    }

    public void cargarInmuebleNuevo(ImageView imagen, String direccion, String uso, double precio, int tipo, int ambientes, double tamano, int bano, int cochera, String servicios, int patio, boolean disponible, String condicion) {

        int estado = disponible ? 1 : 0;
        if (imagen.getDrawable() instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), byteArrayOutputStream.toByteArray());

            // Crear un nombre único para la imagen usando solo la dirección (limitada en longitud)
            String nombreArchivo = generarNombreDesdeDireccion(direccion) + ".jpg";

            // Crear la parte multipart con el nombre único de la foto
            MultipartBody.Part foto = MultipartBody.Part.createFormData("foto", nombreArchivo, requestFile);

            // Creación de RequestBody para cada campo
            RequestBody Direccion = RequestBody.create(MediaType.parse("application/json"), direccion);
            RequestBody Uso = RequestBody.create(MediaType.parse("application/json"), uso);
            RequestBody Ambientes = RequestBody.create(MediaType.parse("application/json"), String.valueOf(ambientes));
            RequestBody Tamano = RequestBody.create(MediaType.parse("application/json"), String.valueOf(tamano));
            RequestBody Id_Tipo_Inmueble = RequestBody.create(MediaType.parse("application/json"), String.valueOf(tipo));
            RequestBody Servicios = RequestBody.create(MediaType.parse("application/json"), servicios);
            RequestBody Bano = RequestBody.create(MediaType.parse("application/json"), String.valueOf(bano));
            RequestBody Cochera = RequestBody.create(MediaType.parse("application/json"), String.valueOf(cochera));
            RequestBody Patio = RequestBody.create(MediaType.parse("application/json"), String.valueOf(patio));
            RequestBody Precio = RequestBody.create(MediaType.parse("application/json"), String.valueOf(precio));
            RequestBody Condicion = RequestBody.create(MediaType.parse("application/json"), condicion);
            RequestBody Estado_inmueble = RequestBody.create(MediaType.parse("application/json"), String.valueOf(estado));

            try {
                SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
                String token = sp.getString("sp_token", null);
                String bearerToken = "Bearer " + token;

                ApiClient.Inmobiliariaservice api = ApiClient.getapiInmobiliaria();
                Call<Inmueble> llamada = api.NuevoInmueble(bearerToken, Direccion, Uso, Ambientes, Tamano, Id_Tipo_Inmueble, Servicios, Bano, Cochera, Patio, Precio, Condicion, Estado_inmueble, foto);

                llamada.enqueue(new Callback<Inmueble>() {
                    @Override
                    public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                        if (response.isSuccessful()) {
                            Inmueble inmueble = response.body();
                            if (inmueble != null) {
                                Log.d("API Success", "Inmueble creado exitosamente: " + inmueble.toString());
                                Toast.makeText(context, "Inmueble creado correctamente", Toast.LENGTH_SHORT).show();
                                mVista.setValue(true);
                            }
                        } else {
                            Log.d("API Error", "Error al crear inmueble: " + response.message());
                            Toast.makeText(context, "Error al crear inmueble", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Inmueble> call, Throwable throwable) {
                        Log.d("API Failure", "Error al hacer la llamada: " + throwable.getMessage());
                        Toast.makeText(context, "Error al crear inmueble", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                Log.d("API Exception", "Error durante la llamada a la API: " + e.getMessage());
            }
        }
    }

    private String generarNombreDesdeDireccion(String direccion) {
        // Limitar la longitud del nombre del archivo y asegurarse de que es válido
        String nombreBase = direccion.replaceAll("[^a-zA-Z0-9]", "_"); // Reemplazar caracteres no válidos
        if (nombreBase.length() > 20) {
            nombreBase = nombreBase.substring(0, 20); // Limitar la longitud a 20 caracteres
        }
        return nombreBase;
    }


    // TODO: Implement the ViewModel
}