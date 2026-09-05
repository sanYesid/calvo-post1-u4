package com.universidad.compras.ejecucion;

import com.universidad.compras.modelo.Solicitud;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class EjecutorSolicitud {
    private final Solicitud solicitud;
    private final Deque<OperacionCommand> historialStack = new ArrayDeque<>();
    private final List<OperacionCommand> historialCompleto = new ArrayList<>();

    public EjecutorSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public void ejecutarOperacion(OperacionCommand comando) {
        comando.ejecutar();
        historialStack.push(comando);
        historialCompleto.add(comando);
        if ("APROBADA".equals(solicitud.getEstado())) {
            solicitud.setEstado("EJECUTADA");
        }
    }

    public void deshacerUltimaOperacion() {
        if (!historialStack.isEmpty()) {
            OperacionCommand comando = historialStack.pop();
            comando.deshacer();
        }
    }

    public List<OperacionCommand> getHistorial() {
        return new ArrayList<>(historialCompleto);
    }
}