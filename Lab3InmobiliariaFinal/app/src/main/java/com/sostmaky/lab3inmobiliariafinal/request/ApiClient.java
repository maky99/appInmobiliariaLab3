package com.sostmaky.lab3inmobiliariafinal.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Contrato;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Inmueble;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Inquilino;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Login;
import com.sostmaky.lab3inmobiliariafinal.Modelo.LoginCambio;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Pago;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Propietario;
import com.sostmaky.lab3inmobiliariafinal.Modelo.TipoInmueble;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class ApiClient {

   //public static final  String URLBASE="http://192.168.0.16:5027/api/";
   public static final  String URLBASE="http://192.168.1.106:5027/api/";
 // public static final  String URLBASE="http://10.31.240.54:5027/api/";
    //public static final  String URLBASE="http://192.168.0.18:5027/api/";


    public static Inmobiliariaservice getapiInmobiliaria(){

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    return retrofit.create(Inmobiliariaservice.class);
    }

    public interface Inmobiliariaservice
    {
        @POST("propietarios/Ingreso")
        Call<String> login(@Body Login login);

        @GET("propietarios/miPerfil")
        Call<Propietario> PropietarioPerfil(@Header("Authorization") String token);

        @POST("propietarios/editarPerfil")
        Call<Propietario> editarPropietarioPerfil(@Header("Authorization") String token, @Body Propietario propietario);

        @POST("propietarios/editarContrasena")
        Call<Propietario> editarPropietarioContra(@Header("Authorization") String token, @Body LoginCambio cambio);

        @FormUrlEncoded
        @POST("propietarios/email")
        Call<String> olvidoContrasena(@Field("email") String email);

        @GET("propietarios/token")
        Call<String> resetContrasena(@Header("Authorization") String token);

        @GET("inmueble/inmuebles")
        Call<List<Inmueble>> Inmuebles(@Header("Authorization") String token);

        @PUT("inmueble/actualiEstado")
        Call<String> actualizaInmuebleEstado(@Header("Authorization") String token, @Body Inmueble inmueble);

        @GET("Tipo/tipoInmueble")
        Call<List<TipoInmueble>>tipoInmueble(@Header("Authorization") String token);

        @POST("inmueble/nuevoInmueble")
        Call<Inmueble> crearInmueble(@Header("Authorization") String token,@Body Inmueble inmueble
        );

        @GET("Inquilinos/inquilinos")
        Call<List<Inquilino>> listInquilinos(@Header("Authorization") String token);

        @GET("Contrato/contratos")
        Call<List<Contrato>> contratosActivos(@Header("Authorization") String token);

        @GET("pago/pagos/{id}")
        Call<List<Pago>> pagosXContrato(@Path("id") int id, @Header("Authorization") String token);





    }
}