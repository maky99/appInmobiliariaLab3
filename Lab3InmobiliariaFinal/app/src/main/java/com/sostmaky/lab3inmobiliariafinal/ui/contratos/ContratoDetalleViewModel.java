package com.sostmaky.lab3inmobiliariafinal.ui.contratos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sostmaky.lab3inmobiliariafinal.Modelo.Contrato;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContratoDetalleViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> mContrato;


    public ContratoDetalleViewModel(@NonNull Application application) {
        super(application);
        mContrato = new MutableLiveData<>();
    }

    public LiveData<Contrato> getContra() {
        if (mContrato == null){
            mContrato = new MutableLiveData<>();
        }
        return mContrato;
    }



    public void cargarContrato(Contrato contrato ) {
        // saco la fecha completa
        String fechaIni = contrato.getFecha_Inicio();
        String fechaFin = contrato.getFecha_Finalizacion();

        //formato que llega y el que quiero
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");

        try {

            Date ini = formatoEntrada.parse(fechaIni);
            String fechaFormateadaIni = formatoSalida.format(ini);

            Date fin = formatoEntrada.parse(fechaFin);
            String fechaFormateadaFin = formatoSalida.format(fin);

            // actualizo el contrato con las fechas que quiero
            contrato.setFecha_Inicio(fechaFormateadaIni);
            contrato.setFecha_Finalizacion(fechaFormateadaFin);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        mContrato.setValue(contrato);
    }


}