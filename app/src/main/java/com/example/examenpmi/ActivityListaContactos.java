package com.example.examenpmi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.examenpmi.config.LaunchActivityMapa;
import com.example.examenpmi.config.ListAdapter;
import com.example.examenpmi.config.Firmas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityListaContactos extends AppCompatActivity {
    LaunchActivityMapa abrirMapa;
    ListView listView;
    List<Firmas> Firmas = new ArrayList<>();
    ListAdapter mAdapter;
    DataBaseHelper conexion;
    FloatingActionButton btnRegresar;
    private int id1;
    String nombre, Contacto, longitud, latitud;
    Button btnActualizar, btnEliminar;
    private int selectedItemPosition = -1;
    private static final long DOUBLE_CLICK_TIME_THRESHOLD = 1000; // En milisegundos

    private long lastClickTime;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);
        lastClickTime = SystemClock.elapsedRealtime();
        conexion = new DataBaseHelper(this);
        btnActualizar = findViewById(R.id.btnActualizarContacto);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnEliminar = findViewById(R.id.btnEliminarContacto);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Vuelve a la actividad anterior al presionar el botón
            }
        });
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre1 = nombre;
                String contacto1 = Contacto;
                String longitud1 = longitud;
                String latitud1 = latitud;


                // Crea un Intent
                Intent intent = new Intent(ActivityListaContactos.this, ActivityEditarContacto.class);
                // Añade el valor al Intent con una clave
                intent.putExtra("MENSAJE_KEY", id1);
                intent.putExtra("MENSAJE__KEY", nombre1);
                intent.putExtra("MENSAJE___KEY", contacto1);
                intent.putExtra("MENSAJE____KEY", longitud1);
                intent.putExtra("MENSAJE_____KEY", latitud1);


                // Inicia la segunda actividad
                startActivity(intent);
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = conexion.getWritableDatabase();

                int result = db.delete(DataBaseHelper.tableName, "telefono=?", new String[]{Contacto});


                if (result > 0) {
                    Toast.makeText(ActivityListaContactos.this, "Contacto seleccionado correctamente " + nombre + Contacto, Toast.LENGTH_SHORT).show();
                    // Puedes realizar acciones adicionales después de eliminar todos los contactos si es necesario
                    recreate();
                } else {
                    Toast.makeText(ActivityListaContactos.this, "Error al eliminar el contacto", Toast.LENGTH_SHORT).show();
                    recreate();
                }
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        obtenerTabla();
        mAdapter = new ListAdapter(this, Firmas);
        listView.setAdapter(mAdapter);

        //En este segmento se crea el prompt para ir a la Latitud/Longitud
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long id) {

                Firmas contactoSeleccionado = Firmas.get(posicion);
                if (selectedItemPosition == -1) {

                    id1 = Integer.valueOf(contactoSeleccionado.getId());
                    Log.d("ID_DEBUG", "ID value: " + id1);
                    nombre = String.valueOf(contactoSeleccionado.getNombre());
                    Contacto = String.valueOf(contactoSeleccionado.getTelefono());
                    longitud = String.valueOf(contactoSeleccionado.getLongitud());
                    latitud = String.valueOf(contactoSeleccionado.getLatitud());
                    // Primer toque, actualizar la posición seleccionada
                    selectedItemPosition = posicion;
                    Toast.makeText(ActivityListaContactos.this, "Contacto seleccionado correctamente " + nombre + Contacto, Toast.LENGTH_SHORT).show();
                    // Reiniciar la posición seleccionada


                } else if (selectedItemPosition == posicion) {
                    int latitud = contactoSeleccionado.getLatitud();
                    int longitud = contactoSeleccionado.getLongitud();
                    String nomnbre = contactoSeleccionado.getNombre();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityListaContactos.this);
                    builder.setTitle("Acción");
                    builder.setMessage("¿Deseas ir a la ubicacion de " + nomnbre + "?");

                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Intent intent = new Intent(MainActivity.this, ActivityCall.class);
                            LaunchActivityMapa abrirMapa = new LaunchActivityMapa();

                            abrirMapa.launchMapActivity(ActivityListaContactos.this, latitud, longitud);
                        }
                    });

                    // Agregar botón "No"
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Acciones a realizar cuando se hace clic en "No"
                            dialog.dismiss(); // Cerrar el diálogo
                        }
                    });
                    // Mostrar el diálogo
                    AlertDialog dialog = builder.create();
                    dialog.show();



                }

            }
        });

    }

    private void obtenerTabla() {

        SQLiteDatabase db = conexion.getReadableDatabase();
        Firmas firmas = null;
        //Cursor de base de datos
        Cursor cursor = db.rawQuery(DataBaseHelper.SelectTable, null);

        //Recorremos el cursor
        while (cursor.moveToNext()) {
            firmas = new Firmas();
            firmas.setId(cursor.getString(0));
            firmas.setNombre(cursor.getString(2));
            firmas.setTelefono(cursor.getInt(3));
            firmas.setLongitud(cursor.getInt(4));
            firmas.setLatitud(cursor.getInt(5));
            Firmas.add(firmas);
        }
        cursor.close();
    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}