package com.sostmaky.lab3inmobiliariafinal.ui.perfil;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sostmaky.lab3inmobiliariafinal.MenuActivity;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Propietario;
import com.sostmaky.lab3inmobiliariafinal.R;
import com.sostmaky.lab3inmobiliariafinal.request.ApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private Context context;

    private MutableLiveData<Propietario> mPropietario;
    private final MutableLiveData<String> mText;
    private MutableLiveData<Uri>uriMutableLiveData;
    private Uri uri;


    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        mText = new MutableLiveData<>();
        mText.setValue("perfil");

    }
    public LiveData<Uri> getUriMutable() {
        if (uriMutableLiveData == null) {
            uriMutableLiveData = new MutableLiveData<>();
        }
        return uriMutableLiveData;
    }


    public LiveData<Propietario> getPropietario() {
        if (mPropietario==null){
            mPropietario=new MutableLiveData<>();
        }
        return mPropietario;
    }
    public LiveData<String> getText() {
        return mText;
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


    public void datosPersonales() {
        //busco en el sharedoreferences el token que guarde
        SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
        String token = sp.getString("sp_token", null);
        String bearerToken = "Bearer " + token;
        ApiClient.Inmobiliariaservice api = ApiClient.getapiInmobiliaria();
        Call<Propietario> llamada = api.PropietarioPerfil(bearerToken);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Propietario propi = response.body();
                    mPropietario.setValue(propi);
                } else {
                    Toast.makeText(context, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Toast.makeText(getApplication(),"Error en servidor ",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void editarDatos(String dni, String apellido, String nombre, String direccion, String telefono, String correo,String foto) {
        SharedPreferences sp = context.getSharedPreferences("token.xlm", 0);
        String token = sp.getString("sp_token", null);
        String bearerToken = "Bearer " + token;
        //manejo de la imagen vemos si el uri no esta vacio
        String imagenUri = "";
        Log.d("Fotito", "URI de la perfilviewmodel: " + foto);
        if (uri != null) {
            try {
                // guardamos la imagen seleccionada en el almacenamiento interno
                imagenUri = guardarImagenEnAlmacenamientoInterno(uri,dni);
            } catch (IOException e) {
                Toast.makeText(context, "Error al guardar la imagen", Toast.LENGTH_SHORT).show();
                Log.e("ImageSaveError", "Error al guardar la imagen", e);

                // obtengo imagen predeterminada desde drawable
                //imagenUri = String.valueOf(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.imagen));
            }
        } //else {
            //imagenUri = String.valueOf(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.imagen));
        //}
            //Usuario usuarioActual = MtUsus.getValue(); // usuario actual
            //if (usuarioActual != null && usuarioActual.getImagen() != null) {
               // imagenUri = usuarioActual.getImagen(); // mantengo la imagen actual
            //} else {
              //  imagenUri = "default_image_uri"; // imagen por defecto si no hay imagen
            //}
        int dn=Integer.parseInt(dni);
        Propietario propietario=new Propietario(dn,apellido,nombre,direccion,telefono,correo,imagenUri);
        ApiClient.Inmobiliariaservice api = ApiClient.getapiInmobiliaria();
        Call<Propietario> llamada=api.editarPropietarioPerfil(bearerToken,propietario);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                Log.d("responde", String.valueOf(response.isSuccessful()));
                if (response.isSuccessful()) {
                    Intent intent = new Intent(getApplication(), MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                    Toast.makeText(context, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al actualizar datos: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Toast.makeText(context,"Error en la conexión",Toast.LENGTH_SHORT).show();
                Log.e("propi", "Error en la conexión", throwable);

            }
        });








    }

    private String guardarImagenEnAlmacenamientoInterno(Uri uri,String dni) throws IOException {
        // obtener un inputstream desde el URI de la imagen seleccionada
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        // creamos un archivo para guardar la imagen en el almacenamiento interno
        File directorio = new File(context.getFilesDir(), "imagenesInmobiliaria"); // nombre de la carpeta para el almacenamiento interno
        if (!directorio.exists()) {
            directorio.mkdir(); // vemos si existe sino se crea
        }
        // le damos el nombre al archivo para la imagen
        File archivoImagen = new File(directorio, "usuario_imagen_" + dni + ".jpg");
        // guardamos la imagen en el archivo
        FileOutputStream fos = new FileOutputStream(archivoImagen);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            fos.write(buffer, 0, length);
        }
        fos.close();
        inputStream.close();
        // devolvemos la ruta del archivo
        return archivoImagen.getAbsolutePath();
    }


}