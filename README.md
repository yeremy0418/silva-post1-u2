# Creational Patterns - Singleton + Factory Method

## Descripcion del proyecto
Este proyecto Maven implementa dos patrones creacionales en Java dentro del paquete `com.patrones.u2`:

- Singleton (variante `enum`) para centralizar el historial de notificaciones en una sola instancia global.
- Factory Method con registro dinamico (`Supplier<Notifier>`) para crear notificadores por tipo sin acoplar el codigo cliente a clases concretas.

La solucion simula el envio de notificaciones por distintos canales (EMAIL, SMS, PUSH) y permite agregar nuevos canales en tiempo de ejecucion (por ejemplo, SLACK).

## Estructura del proyecto
Proyecto Maven estandar:

- `src/main/java/`
- `pom.xml`
- `README.md`

Archivos principales implementados:

- `src/main/java/com/patrones/u2/NotificationLogger.java`
- `src/main/java/com/patrones/u2/Notifier.java`
- `src/main/java/com/patrones/u2/EmailNotifier.java`
- `src/main/java/com/patrones/u2/SmsNotifier.java`
- `src/main/java/com/patrones/u2/PushNotifier.java`
- `src/main/java/com/patrones/u2/NotifierFactory.java`
- `src/main/java/com/patrones/u2/Main.java`

## Analisis de patrones implementados

### 1) Singleton (enum): `NotificationLogger`
Problema que resuelve:
- Evitar multiples objetos de logging con estados distintos.
- Garantizar un punto unico de registro para todas las notificaciones de la sesion.

Como se implementa:
- Se usa `public enum NotificationLogger { INSTANCE; }`.
- La JVM garantiza una sola instancia, serializacion segura y thread-safety por diseno.
- El logger conserva en memoria una lista de entradas y expone metodos para consultar e imprimir historial.

Beneficio en este proyecto:
- Todos los canales escriben en el mismo historial, permitiendo auditoria centralizada.

### 2) Factory Method con registro dinamico: `NotifierFactory`
Problema que resuelve:
- Evitar `if/else` o `switch` extensos para crear notificadores.
- Reducir acoplamiento entre cliente y clases concretas.
- Facilitar extension de canales sin modificar logica existente (OCP).

Como se implementa:
- `NotifierFactory` usa un `Map<String, Supplier<Notifier>>` como registro.
- `create(type)` busca el `Supplier` y crea el notificador solicitado.
- `register(type, factory)` agrega nuevos tipos en tiempo de ejecucion.
- Si el tipo no existe, lanza `IllegalArgumentException` con mensaje descriptivo y tipos disponibles.

Beneficio en este proyecto:
- Se agrego `slack` sin editar la logica de `create`, solo registrando una nueva entrada.

## Instrucciones de ejecucion

### Requisitos
- JDK 8 o superior.
- Maven instalado (`mvn -v`).
- En este entorno se uso Git Bash para ejecutar comandos.

### Ejecutar demo principal
Desde la raiz del proyecto:

```bash
mvn -Dmaven.compiler.source=8 -Dmaven.compiler.target=8 compile exec:java -Dexec.mainClass=com.patrones.u2.Main
```

### Ejecutar pruebas

```bash
mvn -Dmaven.compiler.source=8 -Dmaven.compiler.target=8 test
```

## Captura de salida de `Main`
Salida obtenida en consola al ejecutar la demo:

```text
=== Demo: Singleton + Factory Method ===

Misma instancia: true
[2026-03-17 22:15:45] [EMAIL] -> cliente@mail.com: Su pedido #1001 fue confirmado
[2026-03-17 22:15:45] [SMS] -> +57-300-0000001: Pedido #1001 confirmado
[2026-03-17 22:15:45] [PUSH] -> device-token-abc123: Nuevo pedido listo para enviar
[2026-03-17 22:15:45] [SLACK] -> #pedidos: Pedido #1001 procesado

=== Historial de Notificaciones ===
[2026-03-17 22:15:45] [EMAIL] -> cliente@mail.com: Su pedido #1001 fue confirmado
[2026-03-17 22:15:45] [SMS] -> +57-300-0000001: Pedido #1001 confirmado
[2026-03-17 22:15:45] [PUSH] -> device-token-abc123: Nuevo pedido listo para enviar
[2026-03-17 22:15:45] [SLACK] -> #pedidos: Pedido #1001 procesado
Total: 4 notificaciones
```

## Conclusiones
- Singleton asegura consistencia del estado compartido del logger.
- Factory Method reduce acoplamiento y mejora extensibilidad del sistema de notificaciones.
- La combinacion de ambos patrones permite una arquitectura limpia, escalable y facil de mantener.
