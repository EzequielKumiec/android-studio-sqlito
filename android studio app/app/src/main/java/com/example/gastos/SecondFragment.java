package com.example.gastos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gastos.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    private DataBaseHelperArticulo dbHelper;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DataBaseHelperArticulo(getContext());

        // Agregar listener al botón para guardar el artículo
        binding.btnGuardarArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreArticulo = binding.editTextTextPersonName2.getText().toString();

                // Verificar que el nombre del artículo no esté vacío
                if (!nombreArticulo.isEmpty()) {
                    // Insertar el artículo en la base de datos
                    long newRowId = dbHelper.insertArticulo(nombreArticulo);
                    if (newRowId != -1) {
                        Toast.makeText(getContext(), "Artículo guardado exitosamente", Toast.LENGTH_SHORT).show();
                        // Limpiar el campo de nombre del artículo después de guardar
                        binding.editTextTextPersonName2.setText("");
                    } else {
                        Toast.makeText(getContext(), "Error al guardar el artículo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Por favor ingresa un nombre para el artículo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}