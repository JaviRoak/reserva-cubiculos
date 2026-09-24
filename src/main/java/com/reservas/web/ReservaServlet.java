package com.reservas.web;

import com.reservas.modelo.Reserva;
import com.reservas.servicio.IServicioReserva;
import com.reservas.servicio.RepositorioCubiculoJDBC;
import com.reservas.servicio.RepositorioReservaJDBC;
import com.reservas.servicio.ServicioReservaImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@WebServlet("/reservas")
public class ReservaServlet extends HttpServlet {

    private IServicioReserva servicio;

    @Override
    public void init() {
        this.servicio = new ServicioReservaImpl(
                new RepositorioCubiculoJDBC(), new RepositorioReservaJDBC());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String codCubiculo = req.getParameter("codigoCubiculo");
        String carne = req.getParameter("carne");
        String fechaStr = req.getParameter("fecha");
        String horaInicioStr = req.getParameter("horaInicio");
        String horaFinStr = req.getParameter("horaFin");
        res.setContentType("application/json");

        if (codCubiculo == null || codCubiculo.isBlank()
                || carne == null || carne.isBlank()
                || fechaStr == null || horaInicioStr == null || horaFinStr == null) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().println("{\"error\":\"Datos incompletos\"}");
            return;
        }

        LocalDate fecha;
        LocalTime hIni, hFin;
        try {
            fecha = LocalDate.parse(fechaStr);
            hIni = LocalTime.parse(horaInicioStr);
            hFin = LocalTime.parse(horaFinStr);
        } catch (DateTimeParseException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().println("{\"error\":\"Formato de fecha/hora inválido\"}");
            return;
        }

        if (!hIni.isBefore(hFin)) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().println("{\"error\":\"La hora de inicio debe ser antes que la hora de fin\"}");
            return;
        }

        try {
            Reserva reserva = servicio.registrarReserva(codCubiculo, fecha, hIni, hFin, carne);
            res.setStatus(HttpServletResponse.SC_OK);
            res.getWriter().println(
                    "{\"idReserva\":\"" + reserva.getId() + "\",\"fechaLimiteAviso\":\"reserva confirmada\"}");
        } catch (IllegalStateException e) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            res.getWriter().println("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().println("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    public void destroy() {
    }
}
