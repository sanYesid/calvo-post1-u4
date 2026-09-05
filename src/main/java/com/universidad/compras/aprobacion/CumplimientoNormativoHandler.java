package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;

public class CumplimientoNormativoHandler extends NivelAprobacionHandler {
    private static final String NOMBRE_NIVEL = "Revisor de Cumplimiento Normativo";

    @Override
    public ResultadoAprobacion procesar(Solicitud solicitud) {
        if ("INTERNACIONAL".equalsIgnoreCase(solicitud.getCategoria())) {
            solicitud.setEstado("APROBADA");
            solicitud.setNivelResolutor(NOMBRE_NIVEL);
            return new ResultadoAprobacion(true, NOMBRE_NIVEL, "Aprobado por cumplimiento normativo internacional");
        }
        return delegarASiguiente(solicitud);
    }
}