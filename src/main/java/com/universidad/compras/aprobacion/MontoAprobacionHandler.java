package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;

public class MontoAprobacionHandler extends NivelAprobacionHandler {
    private final String nombreNivel;
    private final double limiteMonto;

    public MontoAprobacionHandler(String nombreNivel, double limiteMonto) {
        this.nombreNivel = nombreNivel;
        this.limiteMonto = limiteMonto;
    }

    @Override
    public ResultadoAprobacion procesar(Solicitud solicitud) {
        if (solicitud.getMonto() <= limiteMonto) {
            solicitud.setEstado("APROBADA");
            solicitud.setNivelResolutor(nombreNivel);
            return new ResultadoAprobacion(true, nombreNivel, "Aprobada en " + nombreNivel + " por monto $" + solicitud.getMonto());
        }
        return delegarASiguiente(solicitud);
    }
}