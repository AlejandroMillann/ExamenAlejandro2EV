package com.example.examenalejandro2ev;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ElementosDataSource {

    private SQLiteDatabase baseDeDatos;
    private ElementosSQLiteHelper ayudanteBD;

    public ElementosDataSource(Context contexto) {
        ayudanteBD = new ElementosSQLiteHelper(contexto);
    }

    public void abrir() throws SQLException {
        baseDeDatos = ayudanteBD.getWritableDatabase();
    }

    public void cerrar() {
        ayudanteBD.close();
    }

    public long insertarElemento(String nombre, String simbolo, int numAtomico, String estado) {
        ContentValues valores = new ContentValues();
        valores.put(ElementosSQLiteHelper.COLUMNA_NOMBRE, nombre);
        valores.put(ElementosSQLiteHelper.COLUMNA_SIMBOLO, simbolo);
        valores.put(ElementosSQLiteHelper.COLUMNA_NUMATOMICO, numAtomico);
        valores.put(ElementosSQLiteHelper.COLUMNA_ESTADO, estado);
        return baseDeDatos.insert(ElementosSQLiteHelper.TABLA_ELEMENTOS, null, valores);
    }

    public void actualizarElemento(long idElemento, String nombre, String simbolo, int numAtomico, String estado) {
        ContentValues valores = new ContentValues();
        valores.put(ElementosSQLiteHelper.COLUMNA_NOMBRE, nombre);
        valores.put(ElementosSQLiteHelper.COLUMNA_SIMBOLO, simbolo);
        valores.put(ElementosSQLiteHelper.COLUMNA_NUMATOMICO, numAtomico);
        valores.put(ElementosSQLiteHelper.COLUMNA_ESTADO, estado);

        String condicion = ElementosSQLiteHelper.COLUMNA_NOMBRE + " = ?";
        String[] argumentos = { String.valueOf(nombre) };

        baseDeDatos.update(ElementosSQLiteHelper.TABLA_ELEMENTOS, valores, condicion, argumentos);
    }

    public void eliminarElemento(long idElemento) {
        String condicion = ElementosSQLiteHelper.COLUMNA_ID + " = ?";
        String[] argumentos = { String.valueOf(idElemento) };

        baseDeDatos.delete(ElementosSQLiteHelper.TABLA_ELEMENTOS, condicion, argumentos);
    }

    public Cursor obtenerTodosLosElementos() {
        String[] todasLasColumnas = {
                ElementosSQLiteHelper.COLUMNA_ID,
                ElementosSQLiteHelper.COLUMNA_NOMBRE,
                ElementosSQLiteHelper.COLUMNA_SIMBOLO,
                ElementosSQLiteHelper.COLUMNA_NUMATOMICO,
                ElementosSQLiteHelper.COLUMNA_ESTADO
        };
        return baseDeDatos.query(ElementosSQLiteHelper.TABLA_ELEMENTOS, todasLasColumnas,
                null, null, null, null, null);
    }

    public Cursor buscarElementosPorNombre(String nombre) {
        String[] todasLasColumnas = {
                ElementosSQLiteHelper.COLUMNA_ID,
                ElementosSQLiteHelper.COLUMNA_NOMBRE,
                ElementosSQLiteHelper.COLUMNA_SIMBOLO,
                ElementosSQLiteHelper.COLUMNA_NUMATOMICO,
                ElementosSQLiteHelper.COLUMNA_ESTADO
        };
        String seleccion = ElementosSQLiteHelper.COLUMNA_NOMBRE + " LIKE ?";
        String[] argumentosSeleccion = {"%" + nombre + "%"};
        return baseDeDatos.query(ElementosSQLiteHelper.TABLA_ELEMENTOS, todasLasColumnas,
                seleccion, argumentosSeleccion, null, null, null);
    }

    public long obtenerIdDelElemento(String nombre) {
        long id = -1;

        String[] columnas = {ElementosSQLiteHelper.COLUMNA_ID};
        String seleccion = ElementosSQLiteHelper.COLUMNA_NOMBRE + " = ?";
        String[] seleccionArgs = {nombre};

        Cursor cursor = baseDeDatos.query(
                ElementosSQLiteHelper.TABLA_ELEMENTOS,
                columnas,
                seleccion,
                seleccionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getLong(cursor.getColumnIndex(ElementosSQLiteHelper.COLUMNA_ID));
            cursor.close();
        }

        return id;
    }

    public String obtenerEstadoDelElemento(long idElemento) {
        String estado = null;

        String[] columnas = {ElementosSQLiteHelper.COLUMNA_ESTADO};
        String seleccion = ElementosSQLiteHelper.COLUMNA_ID + " = ?";
        String[] seleccionArgs = {String.valueOf(idElemento)};

        Cursor cursor = baseDeDatos.query(
                ElementosSQLiteHelper.TABLA_ELEMENTOS,
                columnas,
                seleccion,
                seleccionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            estado = cursor.getString(cursor.getColumnIndex(ElementosSQLiteHelper.COLUMNA_ESTADO));
            cursor.close();
        }

        return estado;
    }
}

