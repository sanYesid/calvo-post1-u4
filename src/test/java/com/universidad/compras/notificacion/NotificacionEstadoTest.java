package com.universidad.compras.notificacion;

import com.universidad.compras.modelo.Solicitud;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;

class NotificacionEstadoTest {

    @Test
    void cambiarEstadoDisparaLasTresReaccionesSinLanzarExcepcion() {
        Solicitud s = new Solicitud("S-020", "ana@udes.edu.co", 2500000, "SOFTWARE", "CC-100");
        GestorNotificacionesEstado mecanismo = new GestorNotificacionesEstado();

        assertDoesNotThrow(() -> {
            mecanismo.cambiarEstado(s, "EN APROBACION", "Solicitud enviada a evaluación");
        });
        assertEquals("EN APROBACION", s.getEstado());
    }

    @Test
    void agregarUnCuartoSuscriptorDePruebaNoRequiereModificarElMecanismo() {
        Solicitud s = new Solicitud("S-021", "pedro@udes.edu.co", 1200000, "MATERIAL OFICINA", "CC-105");
        GestorNotificacionesEstado mecanismo = new GestorNotificacionesEstado();

        AtomicBoolean cuartoSuscriptorNotificado = new AtomicBoolean(false);

        // Registro de un 4to suscriptor en tiempo de ejecución
        mecanismo.suscribir((solicitud, anterior, nuevo, detalle) -> {
            cuartoSuscriptorNotificado.set(true);
        });

        assertDoesNotThrow(() -> {
            mecanismo.cambiarEstado(s, "APROBADA", "Aprobación directa");
        });

        assertTrue(cuartoSuscriptorNotificado.get(), "El cuarto suscriptor debió ser notificado sin alterar la clase central");
    }
}