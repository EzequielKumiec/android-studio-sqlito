package com.example.gastos;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nombre de la base de datos
    private static final String DATABASE_NAME = "gastos.db";
    // Versión de la base de datos. Cambia esto si modificas la estructura de la base de datos.
    private static final int DATABASE_VERSION = 1;

    // Sentencia SQL para crear la tabla de gastos
    private static final String SQL_CREATE_GASTOS_TABLE =
            "CREATE TABLE gastos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id_articulo INTEGER NOT NULL," +
                    "precio INTEGER NOT NULL," +
                    "descripcion TEXT NOT NULL," +
                    "fecha DATE NOT NULL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Se ejecuta al crear la base de datos por primera vez.
        db.execSQL(SQL_CREATE_GASTOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se ejecuta cuando la versión de la base de datos cambia.
        // Aquí puedes realizar operaciones como eliminar tablas o modificar la estructura.
        // Por simplicidad, en este ejemplo simplemente eliminamos y recreamos la tabla.
        db.execSQL("DROP TABLE IF EXISTS gastos");
        onCreate(db);
    }

    public long insertGasto(int idArticulo, int precio, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String fecha = dateFormat.format(new Date());

        values.put("id_articulo", idArticulo);
        values.put("precio", precio);
        values.put("descripcion", descripcion);
        values.put("fecha", fecha);

        long newRowId = db.insert("gastos", null, values);
        db.close();
        return newRowId;
    }
}