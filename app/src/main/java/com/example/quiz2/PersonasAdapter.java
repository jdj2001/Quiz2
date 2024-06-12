package com.example.quiz2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Configuracion.Personas;

public class PersonasAdapter extends RecyclerView.Adapter<PersonasAdapter.PersonasViewHolder> {

    List<Personas> personasList;
    private int selectedPosition = RecyclerView.NO_POSITION; // Variable para almacenar la posición seleccionada

    public PersonasAdapter(List<Personas> personasList) {
        this.personasList = personasList;
    }

    @NonNull
    @Override
    public PersonasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_persona, parent, false);
        return new PersonasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonasViewHolder holder, int position) {
        Personas persona = personasList.get(holder.getAdapterPosition());
        holder.tvNombres.setText(persona.getNombres());
        holder.tvApellidos.setText(persona.getApellidos());
        holder.tvEdad.setText(String.valueOf(persona.getEdad()));
        holder.tvCorreo.setText(persona.getCorreo());
        holder.tvDireccion.setText(persona.getDireccion());

        // Configurar la selección de elementos
        holder.itemView.setSelected(selectedPosition == position);
        holder.itemView.setOnClickListener(v -> {
            if (selectedPosition != holder.getAdapterPosition()) {
                notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
            }
        });
    }


    @Override
    public int getItemCount() {
        return personasList.size();
    }

    public static class PersonasViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombres, tvApellidos, tvEdad, tvCorreo, tvDireccion;

        public PersonasViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombres = itemView.findViewById(R.id.tvNombres);
            tvApellidos = itemView.findViewById(R.id.tvApellidos);
            tvEdad = itemView.findViewById(R.id.tvEdad);
            tvCorreo = itemView.findViewById(R.id.tvCorreo);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
        }
    }

    public void setPersonas(List<Personas> personasList) {
        this.personasList = personasList;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

}
