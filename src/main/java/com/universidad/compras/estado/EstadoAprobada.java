package com.universidad.compras.estado;

public class EstadoAprobada implements EstadoSolicitud {
    @Override
    public boolean aprobar(ContextoSolicitud ctx) { return false; }

    @Override
    public boolean rechazar(ContextoSolicitud ctx) { return false; }

    @Override
    public boolean ejecutar(ContextoSolicitud ctx) {
        ctx.setEstado(new EstadoEjecutada());
        return true;
    }

    @Override
    public boolean cancelar(ContextoSolicitud ctx) {
        ctx.setEstado(new EstadoCancelada());
        return true;
    }

    @Override
    public String getNombreEstado() { return "APROBADA"; }
}