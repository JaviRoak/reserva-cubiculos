package com.reservas.servicio;

import com.reservas.modelo.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IServicioReserva {

    boolean haySolape(String codigoCubiculo, LocalDate fecha,
                       LocalTime horaInicio, LocalTime horaFin);

    boolean tieneReservaActiva(String carneEstudiante);

    boolean anticipacionValida(String carneEstudiante, LocalDate fecha);

    Reserva registrarReserva(String codigoCubiculo, LocalDate fecha,
                              LocalTime horaInicio, LocalTime horaFin,
                              String carneEstudiante);

    List<Reserva> listarPorEstudiante(String carneEstudiante);
}
