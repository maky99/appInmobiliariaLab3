package com.sostmaky.lab3inmobiliariafinal.ui.inicio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sostmaky.lab3inmobiliariafinal.Modelo.DireInmobi;
import com.sostmaky.lab3inmobiliariafinal.R;
import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentMapsBinding;

import java.util.List;

public class MapsFragment extends Fragment {
    private FragmentMapsBinding binding;
    private MapsViewModel mapsViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mapsViewModel = new ViewModelProvider(this).get(MapsViewModel.class);
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mapsViewModel.Lugar();
        return root;

    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            mapsViewModel.getDirInmo().observe(getViewLifecycleOwner(), new Observer<List<DireInmobi>>() {
                @Override
                public void onChanged(List<DireInmobi> direInmobis) {
                    DireInmobi central = direInmobis.get(0);
                    LatLng paraZoom = new LatLng(central.getLatitud(), central.getLongitud());

                    for (DireInmobi dire : direInmobis) {
                        LatLng inmobiliarias = new LatLng(dire.getLatitud(), dire.getLongitud());
                        googleMap.addMarker(new MarkerOptions().position(inmobiliarias).title(dire.getNombre()));
                    }
                        // donde el zoom inicial y posicion
                        CameraUpdate zoomCam = CameraUpdateFactory.newLatLngZoom(paraZoom, 10); // zoom inicial mas lejano
                        googleMap.moveCamera(zoomCam);
                        // hace una animacion de zoom
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(18), 5000, null);


                }
            });


        }
    };


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

}