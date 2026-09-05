package com.universidad.compras.notificacion;

import com.universidad.compras.modelo.Solicitud;
import java.util.ArrayList;
import java.util.List;

public class GestorNotificacionesEstado {
    private final List<ObservadorEstado> observadores = new ArrayList<>();

    public GestorNotificacionesEstado() {
        // Registrar por defecto los 3 observadores exigidos por el dominio
        this.observadores.add(new ObservadorCorreo());
        this.observadores.add(new ObservadorDashboard());
        this.observadores.add(new ObservadorAuditoria());
    }

    public void suscribir(ObservadorEstado observador) {
        this.observadores.add(observador);
    }

    public void desuscribir(ObservadorEstado observador) {
        this.observadores.remove(observador);
    }

    public void cambiarEstado(Solicitud solicitud, String nuevoEstado, String detalle) {
        String estadoAnterior = solicitud.getEstado();
        solicitud.setEstado(nuevoEstado);
        for (ObservadorEstado obs : observadores) {
            obs.alCambiarEstado(solicitud, estadoAnterior, nuevoEstado, detalle);
        }
    }
}