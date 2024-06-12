package com.example.quiz2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Configuracion.Personas;
import Configuracion.PersonasDB;

public class ActivityList extends AppCompatActivity {

    private ListView listperson;
    private ArrayList<Personas> listaPersonas;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listperson = findViewById(R.id.listperson);

        // Obtener lista de personas desde la base de datos
        obtenerListaPersonas();

        // Inicializar el adaptador
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, obtenerNombresPersonas());
        listperson.setAdapter(adapter);

        listperson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Llama al método onItemClick pasando la vista, la posición y el ID del elemento
                onItemClick(parent, view, position, id);
            }
        });

        // Botón Actualizar
        Button btnActualizar = findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la persona seleccionada
                int position = listperson.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    Personas persona = listaPersonas.get(position);

                    // Iniciar la actividad para actualizar la persona
                    Intent intent = new Intent(ActivityList.this, ActualizarPersonaActivity.class);
                    intent.putExtra("persona", (CharSequence) persona);
                    startActivity(intent);
                } else {
                    Toast.makeText(ActivityList.this, "Seleccione una persona para actualizar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botón Eliminar
        Button btnEliminar = findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la persona seleccionada
                int position = listperson.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    Personas persona = listaPersonas.get(position);

                    // Eliminar la persona de la base de datos
                    PersonasDB personasDB = new PersonasDB(ActivityList.this);
                    int filasEliminadas = personasDB.eliminarPersona(persona.getId());
                    if (filasEliminadas > 0) {
                        // Si la eliminación fue exitosa, actualizar la lista de personas en la actividad
                        obtenerListaPersonas();
                        adapter.clear();
                        adapter.addAll(obtenerNombresPersonas());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(ActivityList.this, "Persona eliminada: " + persona.getNombres(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ActivityList.this, "Error al eliminar persona", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityList.this, "Seleccione una persona para eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void obtenerListaPersonas() {
        // Obtener la lista de personas desde la base de datos
        PersonasDB personasDB = new PersonasDB(this);
        listaPersonas = new ArrayList<>(personasDB.obtenerPersonas());
    }

    private ArrayList<String> obtenerNombresPersonas() {
        // Crear una lista de nombres de personas a partir de la lista de personas
        ArrayList<String> nombresPersonas = new ArrayList<>();
        for (Personas persona : listaPersonas) {
            nombresPersonas.add(persona.getNombres());
        }
        return nombresPersonas;
    }

    public void onItemClick(View view, int position) {
        // Mostrar los detalles de la persona seleccionada
        Personas personaSeleccionada = listaPersonas.get(position);
        mostrarDetallesPersona(personaSeleccionada);
    }

    private void mostrarDetallesPersona(Personas persona) {
        // Construir el mensaje con los detalles de la persona
        StringBuilder detalles = new StringBuilder();
        detalles.append("ID: ").append(persona.getId()).append("\n");
        detalles.append("Nombres: ").append(persona.getNombres()).append("\n");
        detalles.append("Apellidos: ").append(persona.getApellidos()).append("\n");
        detalles.append("Edad: ").append(persona.getEdad()).append("\n");
        detalles.append("Correo: ").append(persona.getCorreo()).append("\n");
        detalles.append("Dirección: ").append(persona.getDireccion());

        // Mostrar un Toast con los detalles de la persona
        Toast.makeText(this, detalles.toString(), Toast.LENGTH_LONG).show();
    }
}



