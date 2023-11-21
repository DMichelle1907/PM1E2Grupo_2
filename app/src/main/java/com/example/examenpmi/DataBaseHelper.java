package com.example.examenpmi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ExamenIIP";
    private static final int DATABASE_VERSION = 1;
    public static  final String tableName = "firmas";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String SelectTable= "SELECT * FROM " + tableName;
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Define la sentencia SQL para crear la tabla
        String createTableQuery = "CREATE TABLE firmas(id INTEGER PRIMARY KEY AUTOINCREMENT, signature BLOB, nombre TEXT, telefono INTEGER, longitud INTEGER, latitud INTEGER)";

        // Ejecuta la sentencia SQL para crear la tabla
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Verifica la versión antigua y la versión nueva de la base de datos
        if (oldVersion < 2) {
            // Si la versión antigua es menor que 2, agrega una nueva columna
            String alterTableQuery = "ALTER TABLE " + tableName + " ADD COLUMN nuevaColumna TEXT";
            db.execSQL(alterTableQuery);
        }

        // Puedes agregar más bloques condicionales para otras actualizaciones según sea necesario
        // if (oldVersion < 3) { ... }

        // Actualiza la versión de la base de datos
        // Esto garantiza que el método onUpgrade no se volverá a llamar hasta que la versión de la base de datos cambie nuevamente
        db.setVersion(newVersion);
    }

    public SQLiteDatabase onUpgrade() {

        return null;
    }
}
