package com.sostmaky.lab3inmobiliariafinal.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sostmaky.lab3inmobiliariafinal.Modelo.Inmueble;
import com.sostmaky.lab3inmobiliariafinal.request.ApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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


    public void cargarInmuebleNuevo(String foto, String direccion, String uso, double precio, int tipo, int ambientes, double tamano, int bano, int cochera, String servicios, int patio, boolean disponible, String condicion) {
        Log.d("cargarInmuebleNuevo", "direccion: " + direccion);
        Log.d("cargarInmuebleNuevo", "uso: " + uso);
        Log.d("cargarInmuebleNuevo", "ambientes: " + ambientes);
        Log.d("cargarInmuebleNuevo", "tamano: " + tamano);
        Log.d("cargarInmuebleNuevo", "tipo: " + tipo);
        Log.d("cargarInmuebleNuevo", "servicios: " + servicios);
        Log.d("cargarInmuebleNuevo", "bano: " + bano);
        Log.d("cargarInmuebleNuevo", "cochera: " + cochera);
        Log.d("cargarInmuebleNuevo", "patio: " + patio);
        Log.d("cargarInmuebleNuevo", "precio: " + precio);
        Log.d("cargarInmuebleNuevo", "condicion: " + condicion);
        Log.d("cargarInmuebleNuevo", "estado: " + disponible);


        int estado = disponible ? 1 : 0;

        // Crea los RequestBody con tipo MIME text/plain
        RequestBody direccionPart = RequestBody.create(MediaType.parse("text/plain"), direccion);
        RequestBody usoPart = RequestBody.create(MediaType.parse("text/plain"), uso);
        RequestBody ambientesPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ambientes));
        RequestBody tamanoPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(tamano));
        RequestBody id_Tipo_InmueblePart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(tipo));
        RequestBody serviciosPart = RequestBody.create(MediaType.parse("text/plain"), servicios);
        RequestBody banoPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bano));
        RequestBody cocheraPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(cochera));
        RequestBody patioPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(patio));
        RequestBody precioPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(precio));
        RequestBody condicionPart = RequestBody.create(MediaType.parse("text/plain"), condicion);
        RequestBody estadoPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(estado));

        // Convierte la foto a MultipartBody.Part si es necesario
        MultipartBody.Part archivoFotoPart = null;
        if (foto != null && !foto.isEmpty()) {
            Uri fotoUri = Uri.parse(foto); // Asume que "foto" es una URI
            File file = getFileFromUri(fotoUri); // Obtiene el archivo desde la URI

            if (file != null && file.exists()) {
                String mimeType = context.getContentResolver().getType(fotoUri);
                RequestBody requestBody = RequestBody.create(MediaType.parse(mimeType != null ? mimeType : "image/jpeg"), file);
                archivoFotoPart = MultipartBody.Part.createFormData("archivoFoto", file.getName(), requestBody);

                // Log para confirmar el nombre y tipo MIME del archivo
                Log.d("cargarInmuebleNuevo", "archivoFoto: " + file.getName() + ", mimeType: " + mimeType);
            } else {
                Log.d("API Error", "No se puede acceder al archivo de la imagen.");
            }
        } else {
            Log.d("cargarInmuebleNuevo", "No se proporcionó ninguna foto.");
        }

        try {
            SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
            String token = sp.getString("sp_token", null);
            String bearerToken = "Bearer " + token;

            ApiClient.Inmobiliariaservice api = ApiClient.getapiInmobiliaria();

            // Llamada asíncrona a la API
            Call<Inmueble> llamada = api.NuevoInmueble(bearerToken, direccionPart, usoPart, ambientesPart, tamanoPart, id_Tipo_InmueblePart, serviciosPart, banoPart, cocheraPart, patioPart, precioPart, condicionPart, estadoPart, archivoFotoPart);

            llamada.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if (response.isSuccessful()) {
                        Log.d("API Success", "Inmueble creado exitosamente: " + response.body().toString());
                        Toast.makeText(context, "Inmueble creado correctamente", Toast.LENGTH_SHORT).show();
                        mVista.setValue(true);
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
            Log.d("API Exception", "Error durante la llamada a la API: " + e.getMessage() + e.toString());
        }
    }


    public File getFileFromUri(Uri uri) {
        File tempFile = null;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                tempFile = new File(context.getCacheDir(), "temp_image.jpg");
                OutputStream outputStream = new FileOutputStream(tempFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempFile;
    }


    // TODO: Implement the ViewModel
}