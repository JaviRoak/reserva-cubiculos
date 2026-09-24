package com.reservas.modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Reserva {

    private static int contadorId = 1;

    private int id;
    private Cubiculo cubiculo;
    private Estudiante estudiante;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado; // "CONFIRMADA" o "CANCELADA"

    public Reserva(Cubiculo cubiculo, Estudiante estudiante, LocalDate fecha,
                    LocalTime horaInicio, LocalTime horaFin) {
        this.id = contadorId++;
        this.cubiculo = cubiculo;
        this.estudiante = estudiante;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = "CONFIRMADA";
    }

    public int getId() {
        return id;
    }

    public Cubiculo getCubiculo() {
        return cubiculo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void marcarCancelada() {
        this.estado = "CANCELADA";
    }

    public boolean seSolapaCon(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        return this.fecha.equals(fecha)
                && this.horaInicio.isBefore(horaFin)
                && this.horaFin.isAfter(horaInicio);
    }

    public boolean dentroDeAnticipacionPermitida(LocalDate hoy, int diasMax) {
        long dias = ChronoUnit.DAYS.between(hoy, fecha);
        return dias >= 0 && dias <= diasMax;
    }
}
