package com.universidad.compras.estado;

public class EstadoPendiente implements EstadoSolicitud {
    @Override
    public boolean aprobar(ContextoSolicitud ctx) {
        ctx.setEstado(new EstadoAprobada());
        return true;
    }

    @Override
    public boolean rechazar(ContextoSolicitud ctx) {
        ctx.setEstado(new EstadoRechazada());
        return true;
    }

    @Override
    public boolean ejecutar(ContextoSolicitud ctx) {
        // Inválido: no se puede ejecutar una solicitud pendiente
        return false;
    }

    @Override
    public boolean cancelar(ContextoSolicitud ctx) {
        ctx.setEstado(new EstadoCancelada());
        return true;
    }

    @Override
    public String getNombreEstado() { return "PENDIENTE"; }
}