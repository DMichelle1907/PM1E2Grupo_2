package com.example.examenpmi.config;

import android.content.Context;
import android.content.Intent;

import com.example.examenpmi.ActivityMapa;

public class LaunchActivityMapa {
    public void launchMapActivity(Context context, int latitud, int longitud)
    {
        Intent intent = new Intent(context, ActivityMapa.class);
        intent.putExtra("LATITUD", latitud);
        intent.putExtra("LONGITUD", longitud);
        context.startActivity(intent);
    }
}
