package com.universidad.compras.notificacion;

import com.universidad.compras.modelo.Solicitud;

public class ObservadorAuditoria implements ObservadorEstado {
    @Override
    public void alCambiarEstado(Solicitud solicitud, String estadoAnterior, String estadoNuevo, String detalle) {
        ClientesNotificacion.registrarAuditoria(
            solicitud.getId(),
            estadoNuevo,
            detalle != null ? detalle : "Cambio de estado registrado"
        );
    }
}