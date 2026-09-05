package com.universidad.compras.notificacion;

import com.universidad.compras.modelo.Solicitud;

public class ObservadorDashboard implements ObservadorEstado {
    @Override
    public void alCambiarEstado(Solicitud solicitud, String estadoAnterior, String estadoNuevo, String detalle) {
        ClientesNotificacion.actualizarDashboardContabilidad(
            solicitud.getId(),
            estadoNuevo,
            solicitud.getMonto()
        );
    }
}