package com.reservas.modelo;

public class Cubiculo {

    private String codigo;
    private int capacidad;
    private String sede;
    private String estado;

    public Cubiculo(String codigo, int capacidad, String sede, String estado) {
        this.codigo = codigo;
        this.capacidad = capacidad;
        this.sede = sede;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public String getSede() {
        return sede;
    }

    public String getEstado() {
        return estado;
    }
}
