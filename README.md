# calvo-post1-u4
Post-contenido — Patrones de Comportamiento aplicados al backend de ComprasUDES

---

## Cómo ejecutar
```
$ mvn clean package
$ mvn spring-boot:run
$ mvn test
```

---

### Necesidad-Aprobación por niveles jerárquicos    
**Patrón Aplicado:** Chain of Responsibility.

**Justificación Técnica:** Permite organizar los niveles de aprobación (Supervisor, Gerente, Director Financiero) como una cadena flexible de manejadores desacoplados. La solicitud avanza por la cadena hasta que un nivel la aprueba o rechaza. Además, permite anteponer el nivel Revisor de Cumplimiento Normativo para solicitudes de categoría INTERNACIONAL de forma dinámica y sin modificar el código del cliente (ControladorSolicitudes).    

**Patrón Descartado:** Command. Se descartó porque la necesidad central no radica en encapsular operaciones como objetos ejecutables/reversibles, sino en encadenar una serie de decisores independientes donde cada uno decide si resuelve la petición o la delega al siguiente.  

### Necesidad 2-Ejecución reversible de solicitudes aprobadas  
**Patrón Aplicado:** Command.

**Justificación Técnica:** Encapsula las operaciones discretas sobre los servicios de bajo nivel (PresupuestoService.reservar/liberar y OrdenCompraService.generar/cancelar) en objetos OperacionCommand con sus métodos ejecutar() y deshacer(). Esto permite mantener un historial ordenado y consultable de todas las operaciones realizadas sobre una solicitud y revertir acciones de forma independiente.

**Patrón Descartado:** Chain of Responsibility. Se descartó porque en este contexto no hay decisores evaluando si delegan o resuelven una petición entrante; hay un conjunto de acciones discretas que el sistema debe ejecutar, almacenar en historial e inspeccionar o revertir a voluntad.
