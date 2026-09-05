package com.universidad.compras.estado;

import com.universidad.compras.modelo.Solicitud;

public class ContextoSolicitud {
    private final Solicitud solicitud;
    private EstadoSolicitud estadoActual;

    public ContextoSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
        // Mapear estado inicial según el String de la entidad
        switch (solicitud.getEstado()) {
            case "APROBADA" -> this.estadoActual = new EstadoAprobada();
            case "RECHAZADA" -> this.estadoActual = new EstadoRechazada();
            case "EJECUTADA" -> this.estadoActual = new EstadoEjecutada();
            case "CANCELADA" -> this.estadoActual = new EstadoCancelada();
            default -> this.estadoActual = new EstadoPendiente();
        }
    }

    public void setEstado(EstadoSolicitud nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.solicitud.setEstado(nuevoEstado.getNombreEstado());
    }

    public EstadoSolicitud getEstadoActual() { return estadoActual; }
    public Solicitud getSolicitud() { return solicitud; }

    public boolean aprobar() { return estadoActual.aprobar(this); }
    public boolean rechazar() { return estadoActual.rechazar(this); }
    public boolean ejecutar() { return estadoActual.ejecutar(this); }
    public boolean cancelar() { return estadoActual.cancelar(this); }
}