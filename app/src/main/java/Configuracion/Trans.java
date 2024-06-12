package Configuracion;

public class Trans {
    public static final String NameDatabase = "DBPersonas";

    public static final String tablePersonas = "personas";
    public static final String id = "id";
    public static final String nombres = "nombres";
    public static final String apellidos = "apellidos";
    public static final String edad = "edad";
    public static final String correo = "correo";
    public static final String direccion = "direccion";

    public static final String CreateTablePersonas = "CREATE TABLE " + tablePersonas + " (" +
            id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            nombres + " TEXT, " +
            apellidos + " TEXT, " +
            edad + " INTEGER, " +
            correo + " TEXT, " +
            direccion + " TEXT)";

    public static final String DropTablePersonas = "DROP TABLE IF EXISTS " + tablePersonas;
}
