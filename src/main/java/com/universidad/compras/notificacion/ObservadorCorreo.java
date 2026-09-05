package com.universidad.compras.notificacion;

import com.universidad.compras.modelo.Solicitud;

public class ObservadorCorreo implements ObservadorEstado {
    @Override
    public void alCambiarEstado(Solicitud solicitud, String estadoAnterior, String estadoNuevo, String detalle) {
        ClientesNotificacion.enviarCorreo(
            solicitud.getSolicitanteEmail(),
            "Cambio de estado en solicitud " + solicitud.getId(),
            "Su solicitud pasó de " + estadoAnterior + " a " + estadoNuevo
        );
    }
}