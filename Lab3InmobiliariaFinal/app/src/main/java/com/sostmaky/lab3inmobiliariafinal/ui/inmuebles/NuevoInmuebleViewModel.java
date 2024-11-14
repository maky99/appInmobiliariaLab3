package com.sostmaky.lab3inmobiliariafinal.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
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

        // Creación de RequestBody para cada campo
        RequestBody Direccion = RequestBody.create(MediaType.parse("text/plain"), direccion);
        RequestBody Uso = RequestBody.create(MediaType.parse("text/plain"), uso);
        RequestBody Ambientes = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ambientes));
        RequestBody Tamano = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(tamano));
        RequestBody Id_Tipo_Inmueble = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(tipo));
        RequestBody Servicios = RequestBody.create(MediaType.parse("text/plain"), servicios);
        RequestBody Bano = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bano));
        RequestBody Cochera = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(cochera));
        RequestBody Patio = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(patio));
        RequestBody Precio = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(precio));
        RequestBody Condicion = RequestBody.create(MediaType.parse("text/plain"), condicion);
        RequestBody Estado_inmueble = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(estado));

        // Manejo de la imagen como MultipartBody.Part
        MultipartBody.Part archivoFotoPart = null;
        if (foto != null && !foto.isEmpty()) {
            Uri fotoUri = Uri.parse(foto);
            File savedFile = saveImageToAppFolder(fotoUri);

            if (savedFile != null && savedFile.exists()) {
                String mimeType = context.getContentResolver().getType(fotoUri);
                RequestBody requestBody = RequestBody.create(MediaType.parse(mimeType != null ? mimeType : "image/*"), savedFile);
                archivoFotoPart = MultipartBody.Part.createFormData("archivoFoto", savedFile.getName(), requestBody);

                Log.d("cargarInmuebleNuevo", "archivoFoto: " + savedFile.getName() + ", mimeType: " + mimeType);
            } else {
                Log.d("API Error", "No se pudo guardar o acceder al archivo de la imagen.");
            }
        } else {
            Log.d("cargarInmuebleNuevo", "No se proporcionó ninguna foto.");
        }

        try {
            SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
            String token = sp.getString("sp_token", null);
            String bearerToken = "Bearer " + token;

            ApiClient.Inmobiliariaservice api = ApiClient.getapiInmobiliaria();
            Call<Inmueble> llamada = api.NuevoInmueble(bearerToken, Direccion, Uso, Ambientes, Tamano, Id_Tipo_Inmueble, Servicios, Bano, Cochera, Patio, Precio, Condicion, Estado_inmueble, archivoFotoPart);

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
            Log.d("API Exception", "Error durante la llamada a la API: " + e.getMessage());
        }
    }

    private File saveImageToAppFolder(Uri imageUri) {
        // Carpeta dentro del almacenamiento interno de la app
        File directory = new File(context.getFilesDir(), "InmueblesFotos");
        if (!directory.exists()) {
            directory.mkdir(); // Crea la carpeta si no existe
        }

        String fileName = System.currentTimeMillis() + ".jpg"; // Nombre único para la imagen
        File file = new File(directory, fileName);

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            Log.e("FileSave", "Error al guardar la imagen", e);
            return null;
        }

        return file;
    }


    // TODO: Implement the ViewModel
}