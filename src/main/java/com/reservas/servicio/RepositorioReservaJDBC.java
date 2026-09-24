package com.reservas.servicio;

import com.reservas.modelo.Estudiante;
import com.reservas.modelo.Reserva;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación EN MEMORIA (mismo criterio que RepositorioCubiculoJDBC):
 * sirve para probar el flujo completo sin base de datos real. Cambia el
 * contenido por JDBC cuando conectes MySQL — las firmas no cambian.
 */
public class RepositorioReservaJDBC implements RepositorioReserva {

    private static final List<Reserva> RESERVAS = new ArrayList<>();
    private static final Map<String, Estudiante> ESTUDIANTES = new HashMap<>();

    static {
        ESTUDIANTES.put("PL100524", new Estudiante("PL100524", "Javier Polanco", "javier@ufg.edu.sv", "PREGRADO"));
        ESTUDIANTES.put("GE100124", new Estudiante("GE100124", "Alejandro Gonzalez", "alejandro@ufg.edu.sv", "POSGRADO"));
        ESTUDIANTES.put("AR101124", new Estudiante("AR101124", "Saul Arevalo", "saul@ufg.edu.sv", "PREGRADO"));
        ESTUDIANTES.put("BR100124", new Estudiante("BR100124", "Manuel Berrios", "manuel@ufg.edu.sv", "POSGRADO"));
    }

    @Override
    public List<Reserva> buscarPorCubiculoYFecha(String codigoCubiculo, LocalDate fecha) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : RESERVAS) {
            if (r.getCubiculo().getCodigo().equals(codigoCubiculo) && r.getFecha().equals(fecha)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    @Override
    public Reserva buscarActivaPorCarne(String carne) {
        for (Reserva r : RESERVAS) {
            if (r.getEstudiante().getCarne().equals(carne) && r.getEstado().equals("CONFIRMADA")) {
                return r;
            }
        }
        return null;
    }

    @Override
    public List<Reserva> buscarPorCarne(String carne) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : RESERVAS) {
            if (r.getEstudiante().getCarne().equals(carne)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    @Override
    public Estudiante buscarEstudiante(String carne) {
        return ESTUDIANTES.get(carne);
    }

    @Override
    public void guardar(Reserva reserva) {
        RESERVAS.add(reserva);
    }
}
