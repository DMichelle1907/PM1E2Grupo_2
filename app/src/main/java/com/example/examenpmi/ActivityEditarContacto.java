package com.example.examenpmi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examenpmi.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

public class ActivityEditarContacto extends AppCompatActivity {
    private DataBaseHelper dbHelper;
    EditText edtNombreEdit, edtTelefonoEdit, edtLongitudEdit, edtLatitudedit;
    Button btnActualizar, btnEliminar;
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_contacto);
        edtNombreEdit = findViewById(R.id.edtNombreEdit);
        edtTelefonoEdit = findViewById(R.id.edtTelefonoEdit);
        edtLongitudEdit = findViewById(R.id.edtLongitudEdit);
        edtLatitudedit = findViewById(R.id.edtLatitudedit);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        Intent intent = getIntent();

        // Recupera el valor utilizando la clave que usaste en Activity1
        String id = intent.getStringExtra("MENSAJE_KEY");
        String nombre = intent.getStringExtra("MENSAJE__KEY");
        String telefono = intent.getStringExtra("MENSAJE___KEY");
        String longitud = intent.getStringExtra("MENSAJE____KEY");
        String latitud = intent.getStringExtra("MENSAJE_____KEY");

        // Puedes hacer lo que quieras con el valor, por ejemplo, mostrarlo en un TextView
        edtNombreEdit = findViewById(R.id.edtNombreEdit);
        edtNombreEdit.setText(nombre);

        edtTelefonoEdit = findViewById(R.id.edtTelefonoEdit);
        edtTelefonoEdit.setText(telefono);

        edtLongitudEdit = findViewById(R.id.edtLongitudEdit);
        edtLongitudEdit.setText(longitud);

        edtLatitudedit = findViewById(R.id.edtLatitudedit);
        edtLatitudedit.setText(latitud);


        btnActualizar.setOnClickListener(view -> {
            Bitmap signBitmap = mainBinding.signatureView.getSignatureBitmap();
            if (signBitmap != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                signBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                // Aquí debes insertar el byte array en la base de datos utilizando SQLiteOpenHelper o algún otro método de acceso a la base de datos
                // Por ejemplo:

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("signature", byteArray);
                values.put("nombre", edtNombreEdit.getText().toString());
                values.put("telefono", edtTelefonoEdit.getText().toString());
                values.put("longitud", edtLongitudEdit.getText().toString());
                values.put("latitud", edtLatitudedit.getText().toString());
                String whereClause = "telefono = ?";
                String[] whereArgs = {edtTelefonoEdit.getText().toString()};

                long result = db.update(DataBaseHelper.tableName, values, whereClause, whereArgs);

                if (result != -1) {
                    Toast.makeText(ActivityEditarContacto.this, "Firma guardada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityEditarContacto.this, "Error al guardar la firma", Toast.LENGTH_SHORT).show();
                }
            }

        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}