    package com.sostmaky.lab3inmobiliariafinal.ui.perfil;

    import android.os.Bundle;
    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.ViewModelProvider;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import com.sostmaky.lab3inmobiliariafinal.R;
    import com.sostmaky.lab3inmobiliariafinal.databinding.FragmentCambioContrasenaBinding;

    public class CambioContrasenaFragment extends Fragment {
        private FragmentCambioContrasenaBinding binding;
        private CambioContrasenaViewModel viewModel;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            viewModel= new ViewModelProvider(this).get(CambioContrasenaViewModel.class);
            binding=FragmentCambioContrasenaBinding.inflate(inflater,container,false);
            View root = binding.getRoot();

            binding.btnCambioContra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String contraVieja=binding.etContraVieja.getText().toString();
                    String contraNueva=binding.etContraNueva.getText().toString();
                    viewModel.cambioContrasena(contraVieja,contraNueva);
                }
            });
            return root;
        }

    }
