package com.universidad.compras.ejecucion;

import com.universidad.compras.modelo.Solicitud;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EjecucionSolicitudTest {

    @Test
    void ejecutarReservaPresupuestoYGeneraOrden() {
        Solicitud s = new Solicitud("S-010", "ana@udes.edu.co", 3000000, "SOFTWARE", "CC-100");
        s.setEstado("APROBADA");

        PresupuestoService presupuestoService = new PresupuestoService();
        OrdenCompraService ordenCompraService = new OrdenCompraService();

        EjecutorSolicitud ejecutor = new EjecutorSolicitud(s);
        ejecutor.ejecutarOperacion(new ReservarPresupuestoCommand(presupuestoService, s));
        ejecutor.ejecutarOperacion(new GenerarOrdenCompraCommand(ordenCompraService, s, "Proveedor Tech"));

        assertEquals("EJECUTADA", s.getEstado());
    }

    @Test
    void deshacerSoloLaUltimaOperacionNoAfectaLaAnterior() {
        Solicitud s = new Solicitud("S-011", "luis@udes.edu.co", 4000000, "MATERIAL OFICINA", "CC-200");
        s.setEstado("APROBADA");

        PresupuestoService presupuestoService = new PresupuestoService();
        OrdenCompraService ordenCompraService = new OrdenCompraService();

        EjecutorSolicitud ejecutor = new EjecutorSolicitud(s);

        assertDoesNotThrow(() -> {
            ejecutor.ejecutarOperacion(new ReservarPresupuestoCommand(presupuestoService, s));
            ejecutor.ejecutarOperacion(new GenerarOrdenCompraCommand(ordenCompraService, s, "Proveedor Papel"));
            ejecutor.deshacerUltimaOperacion();
        });
    }

    @Test
    void elHistorialConservaTodasLasOperacionesNoSoloLaUltima() {
        Solicitud s = new Solicitud("S-012", "marta@udes.edu.co", 5000000, "SOFTWARE", "CC-300");
        s.setEstado("APROBADA");

        PresupuestoService presupuestoService = new PresupuestoService();
        OrdenCompraService ordenCompraService = new OrdenCompraService();

        EjecutorSolicitud ejecutor = new EjecutorSolicitud(s);

        assertDoesNotThrow(() -> {
            ejecutor.ejecutarOperacion(new ReservarPresupuestoCommand(presupuestoService, s));
            ejecutor.ejecutarOperacion(new GenerarOrdenCompraCommand(ordenCompraService, s, "Proveedor Soft"));
            assertEquals(2, ejecutor.getHistorial().size());
        });
    }
}