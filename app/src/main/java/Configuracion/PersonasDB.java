package Configuracion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PersonasDB {

    private static final String TAG = "PersonasDB";

    private SQLiteConexion conexion;

    public PersonasDB(Context context) {
        conexion = new SQLiteConexion(context, Trans.NameDatabase, null, 1);
    }

    public long insertarPersona(Personas persona) {
        SQLiteDatabase db = null;
        try {
            db = conexion.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Trans.nombres, persona.getNombres());
            values.put(Trans.apellidos, persona.getApellidos());
            values.put(Trans.edad, persona.getEdad());
            values.put(Trans.correo, persona.getCorreo());
            values.put(Trans.direccion, persona.getDireccion());
            return db.insertOrThrow(Trans.tablePersonas, null, values);
        } catch (SQLiteException e) {
            Log.e(TAG, "Error al insertar persona: " + e.getMessage());
            return -1; // Retorna -1 en caso de error
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public int actualizarPersona(Personas persona) {
        SQLiteDatabase db = null;
        try {
            db = conexion.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Trans.nombres, persona.getNombres());
            values.put(Trans.apellidos, persona.getApellidos());
            values.put(Trans.edad, persona.getEdad());
            values.put(Trans.correo, persona.getCorreo());
            values.put(Trans.direccion, persona.getDireccion());
            return db.update(Trans.tablePersonas, values, Trans.id + " = ?", new String[]{String.valueOf(persona.getId())});
        } catch (SQLiteException e) {
            Log.e(TAG, "Error al actualizar persona: " + e.getMessage());
            return 0; // Retorna 0 en caso de error
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public int eliminarPersona(int id) {
        SQLiteDatabase db = null;
        try {
            db = conexion.getWritableDatabase();
            return db.delete(Trans.tablePersonas, Trans.id + " = ?", new String[]{String.valueOf(id)});
        } catch (SQLiteException e) {
            Log.e(TAG, "Error al eliminar persona: " + e.getMessage());
            return 0; // Retorna 0 en caso de error
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public List<Personas> obtenerPersonas() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<Personas> listaPersonas = new ArrayList<>();
        try {
            db = conexion.getReadableDatabase();
            cursor = db.query(Trans.tablePersonas, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Personas persona = new Personas();
                persona.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Trans.id)));
                persona.setNombres(cursor.getString(cursor.getColumnIndexOrThrow(Trans.nombres)));
                persona.setApellidos(cursor.getString(cursor.getColumnIndexOrThrow(Trans.apellidos)));
                persona.setEdad(cursor.getInt(cursor.getColumnIndexOrThrow(Trans.edad)));
                persona.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow(Trans.correo)));
                persona.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow(Trans.direccion)));
                listaPersonas.add(persona);
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "Error al obtener personas: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return listaPersonas;
    }
}
