package com.universidad.compras.ejecucion;

public interface OperacionCommand {
    void ejecutar();
    void deshacer();
    String getNombre();
}