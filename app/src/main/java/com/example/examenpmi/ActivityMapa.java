package com.example.examenpmi;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.examenpmi.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.examenpmi.databinding.ActivityMapaBinding;
import com.google.android.gms.tasks.Task;

public class ActivityMapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int latitud;
    private int longitud;

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private boolean verificarPermisosUbicacion() {
        return ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void solicitarPermisosUbicacion() {
        if (!verificarPermisosUbicacion()) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION
            );
        } else {
            obtenerUbicacionActual();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacionActual();
            } else {
                System.out.println("lol");
            }
        }
    }

    private LatLng obtenerUbicacionActual() {
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            Task<Location> locationTask = locationClient.getLastLocation();
            locationTask.addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(miUbicacion).title("Mi Ubicación"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(miUbicacion));
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        latitud = getIntent().getIntExtra("LATITUD", 0);
        longitud = getIntent().getIntExtra("LONGITUD", 0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        LatLng miUbicacion = obtenerUbicacionActual();
        if(miUbicacion != null)
        {
            mMap.addMarker(new MarkerOptions().position(miUbicacion).title("Ubicacion actual"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(miUbicacion));
        }

        // Add a marker in Sydney and move the camera
        LatLng ubiContacto = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(ubiContacto).title("Ubicacion de Contacto"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubiContacto));
    }
}