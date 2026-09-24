package com.reservas.servicio;

import com.reservas.modelo.Cubiculo;
import com.reservas.modelo.Estudiante;
import com.reservas.modelo.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ServicioReservaImpl implements IServicioReserva {

    private RepositorioCubiculo repoCubiculo;
    private RepositorioReserva repoReserva;

    public ServicioReservaImpl(RepositorioCubiculo repoCubiculo, RepositorioReserva repoReserva) {
        this.repoCubiculo = repoCubiculo;
        this.repoReserva = repoReserva;
    }

    @Override
    public boolean haySolape(String codCubiculo, LocalDate fecha, LocalTime hIni, LocalTime hFin) {
        for (Reserva r : repoReserva.buscarPorCubiculoYFecha(codCubiculo, fecha)) {
            if (r.getEstado().equals("CONFIRMADA")
                    && hIni.isBefore(r.getHoraFin()) && hFin.isAfter(r.getHoraInicio()))
                return true;
        }
        return false;
    }

    @Override
    public boolean tieneReservaActiva(String carne) {
        return repoReserva.buscarActivaPorCarne(carne) != null;
    }

    @Override
    public boolean anticipacionValida(String carne, LocalDate fecha) {
        Estudiante estudiante = repoReserva.buscarEstudiante(carne);
        if (estudiante == null) return false;
        int diasMax = estudiante.getTipo().equalsIgnoreCase("POSGRADO") ? 7 : 2;
        long dias = ChronoUnit.DAYS.between(LocalDate.now(), fecha);
        return dias >= 0 && dias <= diasMax;
    }

    @Override
    public Reserva registrarReserva(String codCubiculo, LocalDate fecha,
                                     LocalTime hIni, LocalTime hFin, String carne) {
        Estudiante estudiante = repoReserva.buscarEstudiante(carne);
        if (estudiante == null)
            throw new IllegalArgumentException("Estudiante no encontrado");

        Cubiculo cubiculo = repoCubiculo.buscarPorCodigo(codCubiculo);
        if (cubiculo == null)
            throw new IllegalArgumentException("Cubículo no encontrado");

        if (!anticipacionValida(carne, fecha))
            throw new IllegalStateException("Fuera de la ventana de anticipación permitida");
        if (tieneReservaActiva(carne))
            throw new IllegalStateException("Ya tiene una reserva activa");
        if (haySolape(codCubiculo, fecha, hIni, hFin))
            throw new IllegalStateException("Cubículo ya reservado en esa franja");

        Reserva reserva = new Reserva(cubiculo, estudiante, fecha, hIni, hFin);
        repoReserva.guardar(reserva);
        return reserva;
    }

    @Override
    public List<Reserva> listarPorEstudiante(String carne) {
        return repoReserva.buscarPorCarne(carne);
    }
}
