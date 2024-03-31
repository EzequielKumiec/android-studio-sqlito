package com.example.gastos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperArticulo extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "articulos.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ARTICULOS_TABLE =
            "CREATE TABLE articulos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL)";

    public DataBaseHelperArticulo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ARTICULOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS articulos");
        onCreate(db);
    }

    public long insertArticulo(String nombreArticulo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombreArticulo);
        long newRowId = db.insert("articulos", null, values);
        db.close();
        return newRowId;
    }

    public List<String> obtenerArticulos() {
        List<String> listaArticulos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nombre FROM articulos", null);
        if (cursor.moveToFirst()) {
            do {
                listaArticulos.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaArticulos;
    }

    public int obtenerIdArticulo(String nombreArticulo) {
        int idArticulo = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM articulos WHERE nombre = ?", new String[]{nombreArticulo});
        if (cursor.moveToFirst()) {
            idArticulo = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return idArticulo;
    }
}
