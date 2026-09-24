package com.reservas.servicio;

import com.reservas.modelo.Cubiculo;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementación EN MEMORIA para poder probar el flujo sin MySQL instalado.
 * La firma de los métodos no cambia, así que cuando conectes MySQL real
 * solo tienes que reemplazar el contenido de esta clase por JDBC de verdad
 * (DriverManager / PreparedStatement) — el resto del código no se toca.
 */
public class RepositorioCubiculoJDBC implements RepositorioCubiculo {

    private static final Map<String, Cubiculo> CUBICULOS = new HashMap<>();

    static {
        CUBICULOS.put("C001", new Cubiculo("C001", 4, "Sede Central", "DISPONIBLE"));
        CUBICULOS.put("C002", new Cubiculo("C002", 6, "Sede Central", "DISPONIBLE"));
        CUBICULOS.put("C003", new Cubiculo("C003", 4, "Sede Norte", "DISPONIBLE"));
    }

    @Override
    public Cubiculo buscarPorCodigo(String codigo) {
        return CUBICULOS.get(codigo);
    }
}
