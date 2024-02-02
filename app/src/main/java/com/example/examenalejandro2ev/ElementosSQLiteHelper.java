package com.example.examenalejandro2ev;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ElementosSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLA_ELEMENTOS = "Elementos";
    public static final String COLUMNA_ID = "idElemento";
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_SIMBOLO = "simbolo";
    public static final String COLUMNA_NUMATOMICO = "numAtomico";
    public static final String COLUMNA_ESTADO = "estado";

    public static final String DATABASE_NAME = "elementos.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE =
            "CREATE TABLE " + TABLA_ELEMENTOS + " (" +
                    COLUMNA_ID + " INTEGER PRIMARY KEY," +
                    COLUMNA_NOMBRE + " TEXT," +
                    COLUMNA_SIMBOLO + " TEXT," +
                    COLUMNA_NUMATOMICO + " INTEGER," +
                    COLUMNA_ESTADO + " TEXT)";

    public ElementosSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_ELEMENTOS);
        db.execSQL(SQL_CREATE);
    }
}
