package com.example.examenpmi;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examenpmi.databinding.ActivityEditarContactoBinding;
import com.example.examenpmi.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

public class ActivityEditarContacto extends AppCompatActivity {
    private DataBaseHelper dbHelper;
    ActivityEditarContactoBinding mainBinding;
    EditText edtNombreEdit, edtTelefonoEdit, edtLongitudEdit, edtLatitudedit;
    Button btnActualir, btnvolver;
    private String key_id,nombre;
    private Context context;
    //ActivityEditarContactoBinding mainBinding;
    DataBaseHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityEditarContactoBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
       // setContentView(R.layout.activity_editar_contacto);
        edtNombreEdit = findViewById(R.id.edtNombreEdit);
        edtTelefonoEdit = findViewById(R.id.edtTelefonoEdit);
        edtLongitudEdit = findViewById(R.id.edtLongitudEdit);
        edtLatitudedit = findViewById(R.id.edtLatitudedit);
        //btnActualizar = findViewById(R.id.btnActualizar);
         btnvolver = findViewById(R.id.btnvolver);

        Intent intent = getIntent();

        // Recupera el valor utilizando la clave que usaste en Activity1

        key_id = intent.getStringExtra("MENSAJE_KEY");
        nombre = intent.getStringExtra("MENSAJE__KEY");
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


        dbHelper = new DataBaseHelper(this);

        mainBinding.btnActualizar.setOnClickListener(view -> {

                Bitmap signBitmap = mainBinding.signatureViewEdit.getSignatureBitmap();
                if (signBitmap != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    signBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    if(nombre.isEmpty() || telefono.isEmpty() || latitud.isEmpty() || longitud.isEmpty() || signBitmap == null){
                        Toast.makeText(ActivityEditarContacto.this, " Todos los campos deben estar llenos", Toast.LENGTH_SHORT).show();
                    }else {

                    // Agrega el mensaje de bienvenida
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("signature", byteArray);
                    values.put("nombre", edtNombreEdit.getText().toString());
                    values.put("telefono", edtTelefonoEdit.getText().toString());
                    values.put("longitud", edtLongitudEdit.getText().toString());
                    values.put("latitud", edtLatitudedit.getText().toString());
                    int update = db.update(DataBaseHelper.tableName, values, "nombre = ?", new String[]{String.valueOf(nombre)});
                    if (update != -1) {
                        Toast.makeText(ActivityEditarContacto.this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ActivityEditarContacto.this, "Error al actualizar ", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityEditarContacto.this, ActivityListaContactos.class);
                startActivity(intent);
            }
        });
    }
}
