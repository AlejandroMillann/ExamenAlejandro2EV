package com.example.examenalejandro2ev;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdministrationActivity extends AppCompatActivity {

    private Button btnInsertar;
    private Button btnModificar;
    private Button btnVolver;
    private Button btnBorrar;
    private EditText txtNombre;
    private EditText txtSimbolo;
    private EditText txtNumAtomico;
    private EditText txtEstado;
    private ElementosDataSource fuenteDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);
        btnInsertar = findViewById(R.id.botonInsertar);
        btnVolver = findViewById(R.id.botonVolver);
        btnModificar = findViewById(R.id.botonModificar);
        btnBorrar = findViewById(R.id.botonBorrar);
        txtNombre = findViewById(R.id.nombre);
        txtSimbolo = findViewById(R.id.simbolo);
        txtNumAtomico = findViewById(R.id.numAtomico);
        txtEstado = findViewById(R.id.estado);
        fuenteDatos = new ElementosDataSource(this);
        fuenteDatos.abrir();

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarElemento();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarElemento();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarElemento();
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdministrationActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void insertarElemento() {
        String nombre = txtNombre.getText().toString();
        String simbolo = txtSimbolo.getText().toString();
        int numAtomico = Integer.parseInt(txtNumAtomico.getText().toString());
        String estado = txtEstado.getText().toString();

        if (!nombre.isEmpty() && !simbolo.isEmpty() && numAtomico != 0 && !estado.isEmpty()) {
            if (fuenteDatos.obtenerIdDelElemento(nombre) == -1) {
                fuenteDatos.insertarElemento(nombre, simbolo, numAtomico, estado);
                Toast.makeText(this, "Elemento agregado con éxito", Toast.LENGTH_SHORT).show();
            } else {
                mostrarDialogoError("Error al insertar", "Ya existe un elemento con el mismo nombre.");
            }
        } else {
            mostrarDialogoError("Campos vacíos", "Por favor, complete todos los campos.");
        }
    }

    private void modificarElemento() {
        String nombre = txtNombre.getText().toString();
        String simbolo = txtSimbolo.getText().toString();
        int numAtomico = Integer.parseInt(txtNumAtomico.getText().toString());
        String estado = txtEstado.getText().toString();

        if (!nombre.isEmpty()) {
            long idElemento = fuenteDatos.obtenerIdDelElemento(nombre);
            if (idElemento != -1) {
                fuenteDatos.actualizarElemento(idElemento, nombre, simbolo, numAtomico, estado);
                Toast.makeText(this, "Elemento actualizado con éxito", Toast.LENGTH_SHORT).show();
            } else {
                mostrarDialogoError("Error al modificar", "No existe un elemento con ese nombre.");
            }
        } else {
            mostrarDialogoError("Nombre vacío", "El nombre no puede estar vacío.");
        }
    }

    private void eliminarElemento() {
        String nombre = txtNombre.getText().toString();
        long idElemento = fuenteDatos.obtenerIdDelElemento(nombre);

        if (idElemento != -1) {
            fuenteDatos.eliminarElemento(idElemento);
            Toast.makeText(this, "Elemento eliminado con éxito", Toast.LENGTH_SHORT).show();
        } else {
            mostrarDialogoError("Error al borrar", "No existe un elemento con ese nombre.");
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
