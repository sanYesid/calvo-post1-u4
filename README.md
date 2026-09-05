# Post-contenido — Unidad 4: Patrones de Comportamiento en ComprasUDES

## Descripción
Repositorio del post-contenido de la Unidad 4 de Patrones de Diseño
de Software. Un único proyecto Spring Boot (compras-comportamiento)
que resuelve cuatro necesidades reales del backend de ComprasUDES,
el sistema interno de solicitudes de compra corporativas: aprobación
por niveles jerárquicos, ejecución reversible de solicitudes
aprobadas, notificaciones ante cambios de estado y reglas de
transición según el estado actual de la solicitud.

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

### Necesidad-Ejecución reversible de solicitudes aprobadas

**Patrón Aplicado:** Command.    
**Justificación Técnica:** Encapsula las operaciones discretas sobre los servicios de bajo nivel (PresupuestoService.reservar/liberar y OrdenCompraService.generar/cancelar) en objetos OperacionCommand con sus métodos ejecutar() y deshacer(). El invocador (EjecutorSolicitud) mantiene un historial ordenado y consultable de todas las operaciones realizadas sobre una solicitud —no solo la última— y permite revertir cada operación de forma independiente sin afectar a las demás.
**Patrón Descartado:** Chain of Responsibility. Se descartó porque en este contexto no hay decisores evaluando si delegan o resuelven una petición entrante; hay un conjunto de acciones discretas que el sistema debe ejecutar, almacenar en historial e inspeccionar o revertir a voluntad.
**Nota de diseño adicional:** al deshacer una operación, EjecutorSolicitud también notifica, a través del mecanismo de la Necesidad 3, un cambio de estado a CANCELADA sobre la solicitud completa. Esto es una extensión propia, no un requerimiento de la guía.


### Necesidad 3 — Notificaciones ante cambio de estado

**Patrón Aplicado:** Observer.    
**Justificación Técnica:** Ofrece un mecanismo de publicación/suscripción desacoplado donde la entidad `Solicitud` o el gestor notifica el cambio de estado, y los suscriptores (`ObservadorCorreo`, `ObservadorDashboard`, `ObservadorAuditoria`) reaccionan automáticamente. Permite registrar nuevos observadores en tiempo de ejecución sin tocar el núcleo del sistema.   
**Patrón Descartado:** State. Se descartó porque la necesidad no busca modificar el comportamiento o las reglas permitidas de la solicitud, sino notificar a módulos externos e independientes sobre un evento ya ocurrido.    

### Necesidad 4 — Reglas de transición según el estado     
**Patrón Aplicado:** State.    
**Justificación Técnica:** Elimina la dispersión de bloques `if/else` condicionales basados en `s.getEstado()`. Encapsula la validez de las operaciones (`aprobar`, `rechazar`, `ejecutar`, `cancelar`) en clases concretas (`EstadoPendiente`, `EstadoAprobada`, etc.), garantizando que la propia entidad transicione de estado y rechace operaciones inválidas.       
**Patrón Descartado:** Strategy. Aunque comparten una estructura UML similar, se descartó porque Strategy implica que un cliente externo escoge e inyecta la estrategia deseada en cada llamada, mientras que en **State** es el propio objeto el que cambia de estado internamente como resultado de realizar operaciones válidas.    

---

## Herramientas utilizadas
- Java 17, Spring Boot 3.2, Apache Maven, JUnit 5
- VS Code o IntelliJ IDEA, Git, GitHub

---


## Conclusiones 
Distinguir Chain of Responsibility de Command resultó más intuitivo que Observer de State, porque las dos primeras se diferencian por el tipo de problema (una petición que se resuelve una sola vez frente a operaciones que deben quedar en un historial reversible), mientras que Observer y State comparten el mismo disparador —un cambio de estado— y solo se distinguen por quién es el responsable de decidir ese cambio: un conjunto de terceros que reacciona ante un evento ya ocurrido, o el propio objeto que valida y transiciona su comportamiento internamente. La comparación más sutil fue State contra Strategy, porque ambas comparten casi la misma estructura UML y la diferencia está en la intención: en Strategy un cliente externo inyecta el comportamiento en cada llamada, mientras que en State es la propia solicitud quien decide y cambia su comportamiento válido según su historia. Más allá de la elección de patrones, el mayor aprendizaje práctico fue de integración: implementar cada necesidad de forma aislada no bastaba, porque Chain of Responsibility y Command  tenían que terminar disparando el Observer de la Necesidad 3, y conectar ese flujo real —sin romper el cableado de beans de Spring— tomó varias iteraciones. En general, el laboratorio dejó claro que reconocer un patrón por su forma no es suficiente; lo que realmente distingue una solución correcta es identificar qué actor toma la decisión y en qué momento del ciclo de vida del sistema ocurre.