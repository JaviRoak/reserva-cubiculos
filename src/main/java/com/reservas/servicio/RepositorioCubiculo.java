package com.reservas.servicio;

import com.reservas.modelo.Cubiculo;

public interface RepositorioCubiculo {
    Cubiculo buscarPorCodigo(String codigo);
}
