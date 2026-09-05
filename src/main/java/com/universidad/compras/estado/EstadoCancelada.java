package com.universidad.compras.estado;

public class EstadoCancelada implements EstadoSolicitud {
    @Override
    public boolean aprobar(ContextoSolicitud ctx) { return false; }
    @Override
    public boolean rechazar(ContextoSolicitud ctx) { return false; }
    @Override
    public boolean ejecutar(ContextoSolicitud ctx) { return false; }
    @Override
    public boolean cancelar(ContextoSolicitud ctx) { return false; }
    @Override
    public String getNombreEstado() { return "CANCELADA"; }
}