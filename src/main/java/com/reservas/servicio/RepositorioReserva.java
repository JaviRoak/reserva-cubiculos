package com.reservas.servicio;

import com.reservas.modelo.Estudiante;
import com.reservas.modelo.Reserva;

import java.time.LocalDate;
import java.util.List;

public interface RepositorioReserva {
    List<Reserva> buscarPorCubiculoYFecha(String codigoCubiculo, LocalDate fecha);
    Reserva buscarActivaPorCarne(String carne);
    List<Reserva> buscarPorCarne(String carne);
    Estudiante buscarEstudiante(String carne);
    void guardar(Reserva reserva);
}
