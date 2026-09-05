package com.universidad.compras.estado;

public interface EstadoSolicitud {
    boolean aprobar(ContextoSolicitud contexto);
    boolean rechazar(ContextoSolicitud contexto);
    boolean ejecutar(ContextoSolicitud contexto);
    boolean cancelar(ContextoSolicitud contexto);
    String getNombreEstado();
}