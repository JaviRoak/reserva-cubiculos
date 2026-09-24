# reserva-cubiculos

Proyecto Maven (packaging `war`) que implementa el diseño del Segundo Parcial
de Desarrollo de Aplicaciones Multiplataforma (UFG) — sistema de reservas de
cubículos de estudio — listo para abrir en VS Code, con la misma estructura
que el proyecto del pre-parcial (`biblioteca-prestamos`).

Esto no lo pide el examen (el examen solo pide el diseño en el documento),
pero te sirve para probar en la práctica lo que explicaste en el video y
para tenerlo como evidencia de que entiendes el flujo completo.

## Estructura

- `src/main/java/com/reservas/modelo` → `Cubiculo`, `Estudiante`, `Reserva`
- `src/main/java/com/reservas/servicio` → `IServicioReserva`, `ServicioReservaImpl`,
  y los repositorios (con una implementación **en memoria** para probar sin BD real)
- `src/main/java/com/reservas/web` → `ReservaServlet`
- `src/main/webapp` → `formulario_reserva.html` y `WEB-INF/web.xml`

## Reglas de negocio implementadas

1. Una reserva no se registra si se solapa en fecha/horario con otra reserva
   confirmada del mismo cubículo (`ServicioReservaImpl.haySolape`).
2. Un estudiante no puede tener más de una reserva activa a la vez
   (`ServicioReservaImpl.tieneReservaActiva`).
3. Anticipación máxima según tipo de estudiante: posgrado 7 días, pregrado 2
   días (`ServicioReservaImpl.anticipacionValida`).

## Opción rápida: GitHub Codespaces

1. En el repo de GitHub: botón **Code** → pestaña **Codespaces** →
   **Create codespace on main**.
2. Espera a que cargue (ya trae Java y Maven).
3. En la terminal integrada, ejecuta:
   ```
   mvn jetty:run
   ```
4. Cuando aparezca el aviso de puerto reenviado (o en la pestaña **PORTS**),
   abre el puerto **8080** — el plugin de Jetty embebido levanta la app ahí
   mismo, sin instalar Tomcat aparte, ya con el contexto
   `/reserva-cubiculos` configurado.
5. Prueba con los mismos cubículos/carnés de la sección de abajo.
6. Para detener el servidor: `Ctrl+C` en la terminal.

## Opción local: VS Code + Tomcat

1. Abre esta carpeta en VS Code (con "Extension Pack for Java" y
   "Community Server Connectors" instalados).
2. Descarga Apache Tomcat 10.x y descomprímelo en tu equipo.
3. En el panel de servidores, agrega ese Tomcat y despliega el `.war`
   que genera Maven (`mvn package`, o el propio botón de la extensión).
4. Abre `http://localhost:8080/reserva-cubiculos/` en el navegador.

También puedes usar el mismo Jetty embebido en local, sin Tomcat:
`mvn jetty:run` y abrir `http://localhost:8080/reserva-cubiculos/`.

## Datos de prueba

- Cubículos: `C001`, `C002` (Sede Central), `C003` (Sede Norte).
- Carnés:
  - `PL100524` y `AR101124` → pregrado, hasta 2 días de anticipación.
  - `GE100124` y `BR100124` → posgrado, hasta 7 días de anticipación.
- Para ver el rechazo por solape, reserva el mismo cubículo/fecha/horario
  dos veces seguidas con carnés distintos. Para ver el rechazo por reserva
  activa, intenta reservar dos veces con el mismo carné.

## Para una entrega real con MySQL

`RepositorioCubiculoJDBC` y `RepositorioReservaJDBC` aquí son una versión
**EN MEMORIA** solo para que el flujo se pueda probar sin MySQL instalado
(mismo criterio que usaba el pre-parcial). Reemplaza su contenido por JDBC
real cuando tengas la base de datos configurada — la firma de los métodos
no cambia, así que el resto del código (Servlet, Servicio) sigue funcionando
igual.
