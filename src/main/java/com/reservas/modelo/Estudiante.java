package com.reservas.modelo;

public class Estudiante {

    private String carne;
    private String nombre;
    private String correo;
    private String tipo; // "PREGRADO" o "POSGRADO"

    public Estudiante(String carne, String nombre, String correo, String tipo) {
        this.carne = carne;
        this.nombre = nombre;
        this.correo = correo;
        this.tipo = tipo;
    }

    public String getCarne() {
        return carne;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTipo() {
        return tipo;
    }
}
