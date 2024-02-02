package com.example.examenalejandro2ev;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    private Button btnBuscar;
    private Button btnVolver;
    private EditText editText;
    private ElementosDataSource fuenteDatos;
    private TextView txtView;
    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnVolver = findViewById(R.id.btnVolver);
        editText = findViewById(R.id.editText);
        txtView = findViewById(R.id.txtView);
        fuenteDatos = new ElementosDataSource(this);
        fuenteDatos.abrir();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarElementos();
                contador++;
//                btnBuscar.setText("Limpiar");
//                editText.setText("");
//                txtView.setText("");
//                btnBuscar.setText("Buscar");
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchActivity.this, MainActivity.class);
                i.putExtra("contador",contador);
                startActivity(i);
            }
        });

    }


    private void mostrarElementos(Cursor cursor) {
        StringBuilder resultado = new StringBuilder("Elementos:\n");

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(ElementosSQLiteHelper.COLUMNA_ID));
            String nombre = cursor.getString(cursor.getColumnIndex(ElementosSQLiteHelper.COLUMNA_NOMBRE));
            String simbolo = cursor.getString(cursor.getColumnIndex(ElementosSQLiteHelper.COLUMNA_SIMBOLO));
            int numAtomico = cursor.getInt(cursor.getColumnIndex(ElementosSQLiteHelper.COLUMNA_NUMATOMICO));
            String estado = cursor.getString(cursor.getColumnIndex(ElementosSQLiteHelper.COLUMNA_ESTADO));

            resultado.append("ID: ").append(id).append("\n");
            resultado.append("Nombre: ").append(nombre).append("\n");
            resultado.append("Símbolo: ").append(simbolo).append("\n");
            resultado.append("Número Atómico: ").append(numAtomico).append("\n");
            resultado.append("Estado: ").append(estado).append("\n\n");
        }

        txtView.setText(resultado.toString());

        if (!cursor.isClosed()) {
            cursor.close();
        }
    }

    private void buscarElementos() {
        String nombre = editText.getText().toString();
        Cursor cursor = null;
        if (!nombre.isEmpty()) {
            long idElemento = fuenteDatos.obtenerIdDelElemento(nombre);
            if (idElemento != -1) {
                cursor = fuenteDatos.buscarElementosPorNombre(nombre);
                mostrarElementos(cursor);
                Toast.makeText(this, "Elemento actualizado con éxito", Toast.LENGTH_SHORT).show();
            } else {
                mostrarDialogoError("Error al Buscar", "No existe un elemento con ese nombre.");
            }
        } else {
            mostrarDialogoError("Nombre vacío", "El nombre no puede estar vacío.");
        }

    }

    private void mostrarDialogoError(String titulo, String mensaje) {
        new AlertDialog.Builder(this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}


