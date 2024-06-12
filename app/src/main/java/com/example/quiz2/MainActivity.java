package com.example.quiz2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Configuracion.Personas;
import Configuracion.PersonasDB;

public class MainActivity extends AppCompatActivity {

    private EditText etNombres, etApellidos, etEdad, etCorreo, etDireccion;
    private Button btnGuardar, btnMostrar;
    private PersonasDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new PersonasDB(this);

        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etEdad = findViewById(R.id.etEdad);
        etCorreo = findViewById(R.id.etCorreo);
        etDireccion = findViewById(R.id.etDireccion);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnMostrar = findViewById(R.id.btnMostrar);

        btnGuardar.setOnClickListener(v -> guardarPersona());
        btnMostrar.setOnClickListener(v -> mostrarListaPersonas());
    }

    private void guardarPersona() {
        Personas persona = new Personas();
        persona.setNombres(etNombres.getText().toString());
        persona.setApellidos(etApellidos.getText().toString());
        persona.setEdad(Integer.parseInt(etEdad.getText().toString()));
        persona.setCorreo(etCorreo.getText().toString());
        persona.setDireccion(etDireccion.getText().toString());

        long id = db.insertarPersona(persona);
        if (id > 0) {
            Toast.makeText(MainActivity.this, "Persona guardada con éxito", Toast.LENGTH_SHORT).show();
            limpiarCampos();

            // Después de insertar una nueva persona, iniciar ActivityList para mostrar la lista actualizada
            mostrarListaPersonas();
        } else {
            Toast.makeText(MainActivity.this, "Error al guardar persona", Toast.LENGTH_SHORT).show();
        }
    }


    private void mostrarListaPersonas() {
        Intent intent = new Intent(MainActivity.this, ActivityList.class);
        startActivity(intent);
    }

    private void limpiarCampos() {
        etNombres.setText("");
        etApellidos.setText("");
        etEdad.setText("");
        etCorreo.setText("");
        etDireccion.setText("");
    }
}
