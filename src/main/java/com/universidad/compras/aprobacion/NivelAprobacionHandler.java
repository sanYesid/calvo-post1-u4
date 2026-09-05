package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;

public abstract class NivelAprobacionHandler {
    protected NivelAprobacionHandler siguiente;

    public NivelAprobacionHandler setSiguiente(NivelAprobacionHandler siguiente) {
        this.siguiente = siguiente;
        return siguiente;
    }

    public abstract ResultadoAprobacion procesar(Solicitud solicitud);

    protected ResultadoAprobacion delegarASiguiente(Solicitud solicitud) {
        if (siguiente != null) {
            return siguiente.procesar(solicitud);
        }
        solicitud.setEstado("RECHAZADA");
        solicitud.setNivelResolutor("Sin Resolutor");
        return new ResultadoAprobacion(false, "Sin Resolutor", "Ningún nivel pudo procesar la solicitud");
    }
}