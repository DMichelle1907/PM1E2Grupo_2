package com.example.examenpmi.config;

public class Firmas {
    private byte[] signature;
    private String id, nombre;
    private Integer telefono, longitud, latitud;

    public Firmas(String id, byte[] signature, String nombre,Integer longitud, Integer latitud, Integer telefono) {
        this.id = id;
        this.signature = signature;
        this.nombre = nombre;
        this.telefono = telefono;
        this.longitud = longitud;
        this.latitud = latitud;

    }

    public Firmas() {
    }


    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public Integer getLongitud() {
        return longitud;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }

    public Integer getLatitud() {
        return latitud;
    }

    public void setLatitud(Integer latitud) {
        this.latitud = latitud;
    }

}
