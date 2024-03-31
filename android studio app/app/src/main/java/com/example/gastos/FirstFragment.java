package com.example.gastos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gastos.databinding.FragmentFirstBinding;

import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private DatabaseHelper dbHelper;

    private DataBaseHelperArticulo dbHelperArt;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DatabaseHelper(getContext());
        dbHelperArt = new DataBaseHelperArticulo(getContext());
        // Obtener los artículos de la base de datos y cargarlos en el ComboBox
        cargarArticulosEnComboBox();

        // Agregar listener al botón para guardar el gasto
        binding.btnGuardarGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String precioStr = binding.editTextTextPersonName.getText().toString();
                int precio = 0;
                try {
                    precio = Integer.parseInt(precioStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Por favor ingresa un precio válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                String descripcion = binding.editTextTextPersonName3.getText().toString();
                String nombreArticulo = binding.spinner.getSelectedItem().toString();
                int idArticulo = obtenerIdArticulo(nombreArticulo);

                // Insertar el gasto en la base de datos
                long newRowId = dbHelper.insertGasto(idArticulo, precio, descripcion);
                if (newRowId != -1) {
                    Toast.makeText(getContext(), "Gasto guardado exitosamente", Toast.LENGTH_SHORT).show();
                    // Limpiar los campos después de guardar
                    binding.editTextTextPersonName.setText("");
                    binding.editTextTextPersonName3.setText("");
                } else {
                    Toast.makeText(getContext(), "Error al guardar el gasto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnIrAArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Método para cargar los artículos en el ComboBox
    private void cargarArticulosEnComboBox() {
        List<String> listaArticulos = dbHelperArt.obtenerArticulos();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaArticulos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
    }

    // Método para obtener el ID del artículo seleccionado en el ComboBox
    private int obtenerIdArticulo(String nombreArticulo) {
        // Aquí deberías implementar la lógica para obtener el ID del artículo desde la base de datos
        // Podrías hacer una consulta a la base de datos o utilizar un Map para mapear nombres de artículos a IDs
        // En este ejemplo, asumimos que tienes un método obtenerIdArticulo() en tu DatabaseHelper
        return dbHelperArt.obtenerIdArticulo(nombreArticulo);
    }

}