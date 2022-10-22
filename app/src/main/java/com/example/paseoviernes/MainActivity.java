package com.example.paseoviernes;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etCodigo, etNombre, etCiudad, etCantidad;
    CheckBox cbActivo;
    String codigo, nombre, ciudad, cantidad;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCodigo = findViewById(R.id.etcodigo);
        etNombre = findViewById(R.id.etnombre);
        etCiudad = findViewById(R.id.etciudad);
        etCantidad = findViewById(R.id.etcantidad);
        cbActivo = findViewById(R.id.cbactivo);
    }

    public void adicionar(View view) {
        codigo = etCodigo.getText().toString();
        nombre = etNombre.getText().toString();
        ciudad = etCiudad.getText().toString();
        cantidad = etCantidad.getText().toString();
        if (codigo.isEmpty() || nombre.isEmpty() || ciudad.isEmpty()
                || cantidad.isEmpty()) {
            Toast.makeText(this, "Todos los datos requeridos", Toast.LENGTH_SHORT).show();
            etCodigo.requestFocus();
        } else {
            Map<String, Object> factura = new HashMap<>();
            factura.put("Codigo", codigo);
            factura.put("nombre", nombre);
            factura.put("Ciudad", ciudad);
            factura.put("Cantidad", cantidad);
            factura.put("Activo", "si");

            // Add a new document with a generated ID
                db.collection("facturas")
                .add(factura)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Datos guardados!", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error, guardando campos!", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }

    public void cancelar(View view) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        etCodigo.setText("");
        etNombre.setText("");
        etCiudad.setText("");
        etCantidad.setText("");
        cbActivo.setText("");
        etCodigo.requestFocus();
    }
}