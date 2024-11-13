package com.sostmaky.lab3inmobiliariafinal.ui.inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.sostmaky.lab3inmobiliariafinal.Modelo.DireInmobi;

import java.util.ArrayList;
import java.util.List;

public class MapsViewModel extends AndroidViewModel {

    private MutableLiveData<List<DireInmobi>> mutDireInmo;

    public MapsViewModel(@NonNull Application application) {
        super(application);
        mutDireInmo = new MutableLiveData<>();
    }



    public MutableLiveData<List<DireInmobi>> getDirInmo(){
        if (mutDireInmo==null){
            mutDireInmo=new MutableLiveData<>();
        }
        return mutDireInmo;
    }


    public void Lugar(){
        ArrayList<DireInmobi>sucursales=new ArrayList<>();
        sucursales.add(new DireInmobi( "San Luis",-33.302051, -66.336932));
        //sucursales.add(new DireInmobi( "San Luis",-33.303401, -66.3366408));



        mutDireInmo.setValue(sucursales);
    }







}
