package com.universidad.compras.ejecucion;

import com.universidad.compras.modelo.Solicitud;

public class GenerarOrdenCompraCommand implements OperacionCommand {
    private final OrdenCompraService ordenCompraService;
    private final Solicitud solicitud;
    private final String proveedor;
    private String numeroOrdenGenerada;

    public GenerarOrdenCompraCommand(OrdenCompraService ordenCompraService, Solicitud solicitud, String proveedor) {
        this.ordenCompraService = ordenCompraService;
        this.solicitud = solicitud;
        this.proveedor = proveedor;
    }

    @Override
    public void ejecutar() {
        this.numeroOrdenGenerada = ordenCompraService.generar(solicitud.getId(), proveedor);
    }

    @Override
    public void deshacer() {
        if (numeroOrdenGenerada != null) {
            ordenCompraService.cancelar(numeroOrdenGenerada);
            this.numeroOrdenGenerada = null;
        }
    }

    @Override
    public String getNombre() {
        return "Generar Orden de Compra";
    }
}