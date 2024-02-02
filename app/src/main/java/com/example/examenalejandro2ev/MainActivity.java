package com.example.examenalejandro2ev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnSalir;
    private Button btnConsultar;
    private Button btnQuimico;
    private TextView txtConsultas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSalir = findViewById(R.id.btnSalir);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnQuimico = findViewById(R.id.btnQuimico);
        txtConsultas = findViewById(R.id.txtConsultas);


        //Al ya haberlos insertado una vez, lo comento.
//
//        // Abrimos la base de datos "elementos.db" en modo de escritura
//        ElementosSQLiteHelper usdbh = new ElementosSQLiteHelper(this);
//        SQLiteDatabase db = usdbh.getWritableDatabase();
//
//        // Si hemos abierto correctamente la base de datos
//        if (db != null) {
//            insertarElemento(db, "HELIO", "He", 2, "GAS");
//            insertarElemento(db, "HIERRO", "Fe", 26, "SOLIDO");
//            insertarElemento(db, "MERCURIO", "Hg", 80, "LIQUIDO");
//        }
//
//        // Cerramos la base de datos
//        db.close();

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("INFORMACIÓN")
                        .setMessage("Hasta la próxima!")
                        .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inicia SearchActivity esperando un resultado
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(i, 1);
            }
        });


        btnQuimico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void insertarElemento(SQLiteDatabase db, String nombre, String simbolo, int numAtomico, String estado) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("simbolo", simbolo);
        valores.put("numAtomico", numAtomico);
        valores.put("estado", estado);
        db.insert("Elementos", null, valores);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            int contador = data.getIntExtra("contador", 0);
            txtConsultas.setText("Consultas totales: " + contador);
        }
    }

}
