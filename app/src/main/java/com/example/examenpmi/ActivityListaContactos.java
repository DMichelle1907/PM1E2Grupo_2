package com.example.examenpmi;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.examenpmi.config.Firmas;
import com.example.examenpmi.config.LaunchActivityMapa;
import com.example.examenpmi.config.ListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ActivityListaContactos extends AppCompatActivity {
    LaunchActivityMapa abrirMapa;
    ListView listView;
    List<Firmas> Firmas = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    ListAdapter mAdapter;
    DataBaseHelper conexion;
    FloatingActionButton btnRegresar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        conexion = new DataBaseHelper(this);

        btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Vuelve a la actividad anterior al presionar el botón
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        obtenerTabla();
        mAdapter = new ListAdapter(this,Firmas);
        listView.setAdapter(mAdapter);

        ListView listView = findViewById(R.id.listView);
        //En este segmento se crea el prompt para ir a la Latitud/Longitud
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long id) {
                Firmas contactoSeleccionado = Firmas.get(posicion);

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
            });
    }

    private void obtenerTabla() {

        SQLiteDatabase db = conexion.getReadableDatabase();
        Firmas firmas = null;
        //Cursor de base de datos
        Cursor cursor = db.rawQuery(DataBaseHelper.SelectTable,null);

        //Recorremos el cursor
        while (cursor.moveToNext()){
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


}