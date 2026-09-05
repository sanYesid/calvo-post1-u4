package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;
import org.springframework.stereotype.Service;

@Service
public class ServicioAprobacionImpl implements ServicioAprobacion {

    @Override
    public ResultadoAprobacion evaluar(Solicitud solicitud) {
        NivelAprobacionHandler supervisor = new MontoAprobacionHandler("Supervisor de Area", 2_000_000);
        NivelAprobacionHandler gerente = new MontoAprobacionHandler("Gerente de Area", 10_000_000);
        NivelAprobacionHandler director = new MontoAprobacionHandler("Director Financiero", Double.MAX_VALUE);

        if ("INTERNACIONAL".equalsIgnoreCase(solicitud.getCategoria())) {
            NivelAprobacionHandler cumplimiento = new CumplimientoNormativoHandler();
            cumplimiento.setSiguiente(supervisor);
            supervisor.setSiguiente(gerente);
            gerente.setSiguiente(director);
            return cumplimiento.procesar(solicitud);
        } else {
            supervisor.setSiguiente(gerente);
            gerente.setSiguiente(director);
            return supervisor.procesar(solicitud);
        }
    }
}