package com.example.quiz2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import Configuracion.Personas;
import Configuracion.PersonasDB;

public class ActualizarPersonaActivity extends AppCompatActivity {

    private EditText etNombres, etApellidos, etEdad, etCorreo, etDireccion;
    private Button btnActualizar;
    private Personas persona;
    private PersonasDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_persona);

        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etEdad = findViewById(R.id.etEdad);
        etCorreo = findViewById(R.id.etCorreo);
        etDireccion = findViewById(R.id.etDireccion);
        btnActualizar = findViewById(R.id.btnActualizar);

        db = new PersonasDB(this);

        persona = (Personas) getIntent().getSerializableExtra("persona");

        mostrarDatosPersona(persona);

        btnActualizar.setOnClickListener(v -> actualizarPersona());
    }

    private void mostrarDatosPersona(Personas persona) {
        etNombres.setText(persona.getNombres());
        etApellidos.setText(persona.getApellidos());
        etEdad.setText(String.valueOf(persona.getEdad()));
        etCorreo.setText(persona.getCorreo());
        etDireccion.setText(persona.getDireccion());
    }

    private void actualizarPersona() {
        String nuevosNombres = etNombres.getText().toString();
        String nuevosApellidos = etApellidos.getText().toString();
        int nuevaEdad = Integer.parseInt(etEdad.getText().toString());
        String nuevoCorreo = etCorreo.getText().toString();
        String nuevaDireccion = etDireccion.getText().toString();

        persona.setNombres(nuevosNombres);
        persona.setApellidos(nuevosApellidos);
        persona.setEdad(nuevaEdad);
        persona.setCorreo(nuevoCorreo);
        persona.setDireccion(nuevaDireccion);

        int filasActualizadas = db.actualizarPersona(persona);

        if (filasActualizadas > 0) {
            Toast.makeText(this, "Datos de persona actualizados", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar datos de persona", Toast.LENGTH_SHORT).show();
        }
    }
}
