package com.sostmaky.lab3inmobiliariafinal.ui.contratos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sostmaky.lab3inmobiliariafinal.Modelo.Contrato;
import com.sostmaky.lab3inmobiliariafinal.R;
import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentContratoDetalleBinding;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Contrato_DetalleFragment extends Fragment {

    private ContratoDetalleViewModel mViewModel;
    private FragmentContratoDetalleBinding binding;
    private PagosViewModel pagosViewModel;

    public static Contrato_DetalleFragment newInstance() {
        return new Contrato_DetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        pagosViewModel = new ViewModelProvider(this).get(PagosViewModel.class);
        binding = FragmentContratoDetalleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Contrato contrato = (Contrato) getArguments().getSerializable("contrato");
        binding.etIdContrato.setText(String.valueOf(contrato.getId_Contrato()));
        // saco la fecha completa
        String fechaFin = contrato.getFecha_Finalizacion();
        String fechaIni = contrato.getFecha_Inicio();
        //Log.d("fecha", fechaFin);
        //formato que llega y el que quiero
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date ini = formatoEntrada.parse(fechaIni);
            String fechalistIni = formatoSalida.format(ini);
            binding.etFechInici.setText(fechalistIni);

            Date fin = formatoEntrada.parse(fechaFin);
            String fechalistFin = formatoSalida.format(fin);
            binding.etFechFina.setText(fechalistFin);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        binding.etInmueDirecc.setText(contrato.getInmueble().getDireccion());
        String nombreCompleto = contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido();
        binding.etNombInqui.setText(nombreCompleto);
        //para darle el formato moneda
        double precio = contrato.getInmueble().getPrecio();
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
        binding.etMonto.setText(formatoMoneda.format(precio));
        String foto = contrato.getInmueble().getFoto();
        ImageView ivFotoInquilino = binding.ivFoto;
        Glide.with(this)
                .load(foto)
                .placeholder(R.drawable.casadefe)
                .error(R.drawable.casadefe)
                .into(ivFotoInquilino);

        binding.btnPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idContrato = contrato.getId_Contrato();
                Bundle bundle = new Bundle();
                bundle.putInt("id_contrato", idContrato);
                Navigation.findNavController(view).navigate(R.id.pagosFragment, bundle);

            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContratoDetalleViewModel.class);
        // TODO: Use the ViewModel
    }

}