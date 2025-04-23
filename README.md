[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/tc38IXJF)
# 📚 Trabajo Práctico: Sistema de Gestión de Biblioteca Digital (Java 21+)

## 📌 Objetivo General

Desarrollar un sistema de gestión de biblioteca digital que implemente los cinco principios SOLID, programación orientada a objetos, y conceptos avanzados de Java. El sistema deberá manejar diferentes tipos de recursos digitales, préstamos, reservas, y notificaciones en tiempo real.

## 👨‍🎓 Información del Alumno
- **Nombre y Apellido**: Reynier López

## 📋 Requisitos Adicionales

## Documentación del alumno

### Detalles del proyecto

1. **¿Cómo funciona el sistema?**
        Basicamente se utiliza unos menus para interacturar con el usuario, estos
        mismos son unos scanners y al recibir una respuesta se activa una 
        funcionalidad del sistema a través de un switch case.
        Se utiliza la clase gestorBibloteca como intermediario entre los distintos
        gestores de clases de persistencia y las notificaciones, alertas y creación
        de prestamos y alertas se utiliza concurrencia.

2. **¿Cómo ponerlo en funcionamiento?**
    Esta es la guía paso a paso:
    - Posicionarse en una carpeta para poner el proyecto
    - Abir la terminal o git bash si estas en windows
    - Copiar los siguientes comandos: 
    - git clone git@github.com:um-programacion-ii/programacion-2-trabajo-practico-2-Reynier124.git
    - cd cd programacion-2-trabajo-practico-2-Reynier124/
    - Abir en esa posición tu IDE o editor de codigo predilecto
    - Compilar si tu IDE no lo hace automáticamente
    - Ir en TP_2/src/main/main.java e iniciar la ejecución

### Instrucciones para probar los aspectos desarrollados

### 0. Configuración
1. **Al iniciar el programa**:
    Una vez empiezas el programa te va a pedir que tipo de servicio
    de notificaciones vas a querer, esto es obligatorio y no cambiara ninguna prueba cuál vayas a
    elegir. Además, te va a preguntar las preferencias en las notificaciones, recomendaría poner
    todas que sí o quitar info que puede ser algo molesta.
2. **Durante la ejecución**:
    Puedes modificar en todo momento las preferencias de las notificaciones
    en el menu de reportes

### 1. Gestión de Recursos
1. **Creación**: Para crear un recurso tendrías que ir al menu de recursos y elegir la primera 
    opción que sería "Crear Recurso". Ahí te va a dar elegir entre 3 categorías: libro, revista
    y audiolibro, luego va a pedir el título (No puede haber titular iguales) y un parametro extra
    dependiendo de la categoria. Una vez completados todos los datos se habrá generado el recurso.
2. **Buscar**: Para buscar un recurso tendrías que ir al menu de recursos y elegir la segunda
    opción, en esta te va a pedir todos los parametros necesarios (Todos son opcionales) y a partir
    de los parametros dados hará un filter a la lista y te devolverá los resultados.
3. **Listar Recursos**: Es tan simple como elegir la cuarta opción del menu de recursos. Además,
    se puede usar la tercera opción para ordenar internamente la lista de recursos en orden alfabetico
    ascendente o descendente. Esto también servirá para validar que buscar y crear funcionan correctamente

### 2. Gestión de Usuarios
1. **Creación**: Ir al menu de usuarios, elegir la primera opción para crear un usuario y va a
    empezar a pedir los siguientes datos: nombre (Tiene que ser único), email (Tiene que ser un
    email valido) y contraseña (Mínimo 4 caracteres). Una vez completado los datos se creará el
    usuario y llegará una notificación para confirmar que se creó correctamente.
2. **Buscar**: Ir al menu de usuarios, elegir la segunda opción y te empieza a pedir los
    parametros de búsqueda, todos son opcionales y sirven para luego hacer un filter a la lista.
    Una vez pasado todos los parametros te devolverá en pantalla los resultados

### 3. Préstamos
1. **Realizar Préstamo**: Primero es recomendable revisar la lista de recursos y sus estados y,
    a partir de ahí, elegir un recurso para hacer un préstamo. Una vez que tengas decidido el
    recurso vuelves al menu principal y luego elegis el menu de préstamos. Para crear el préstamo
    tienes que elegir la primera opción del menu y te va a pedir el nombre o id del usuario que
    quiere hacer el préstamo (Tiene que ser exacto) y luego te pide el título o id del recurso
   (También es exacto) y envía la petición a una cola para luego ser procesada. Si tienes las
    preferencias adecuadas te va a avisar que el préstamo se creó y se procesó correctamente.
2. **Devolver Recurso**: En el menu de préstamos eliges la quinta opción, te va a pedir los mismos
    datos que hiciste para crear el préstamo y va a hacer una comprobación para ver si no está devuelto.
    Si no está devuelto, se realizará la devolución. Se puede confirmar a través del listado de
    recursos o de préstamos.

### 4. Reservas
1. **Realizar Reserva**: Los pasos son similares que los de préstamo con la diferencia que es en
    el menu de reservas y que te va a pedir la prioridad con la que se trate la reserva (Valor 
    máximo es 4). Y si tienes la preferencia de info, te avisará cuándo el recurso esté disponible
    y solo es posible tener una reserva por usuario.

### 5. Reportes
1. **Ver Reportes**: Si vas al menu de reportes tienes distintas opciones como ver qué usuario es más
    activo, un historial de las notificaciones que fueron apareciendo, los recursos más reservados o
    prestados, ver los usos de las categorias y modificar tus preferencias en las notificaciones.

### 6. Alertas
1. **Verificar Alertas**: El codigo está programado para que cuándo crees un prestamo te salga inmediatamente
    una alerta para ver si quieres renovar el préstamo, si renuevas el prestamo te debería salir otra alerta
    indicando que se te paso la fecha de devolución. Obviamente no es el funcionamiento normal del programa,
    esto es solamente para probar las alertas.

## Ejemplos de pruebas
1. **Flujo completo de préstamo**:
    - Elegir sistema de notificación
    - Completar sus preferencias de notificación (En este caso todas en No)
    - Elegir la primera opción (Menu de usuarios)
    - Elegir la primera opción (Crear usuario)
    - Completar nombre
    - Completar email (Tiene que ser un string con "@")
    - Completar contraseña (Mínimo 4 caracteres)
    - Elegir la última opción (Volver)
    - Elegir segunda opción (Menu de recursos)
    - Elegir primera opción (Crear recurso)
    - Elegir categoria (En este caso, libro)
    - Completar titulo
    - Completar cantidad de páginas
    - Elegir ultima opción (Volver)
    - Elegir tercera opción (Menu de préstamos)
    - Elegir primera opción (Crear préstamo)
    - Completar con el nombre de usuario recién creado
    - Completar con el título del recurso recién creado
    - Elegir última opción
    - Elegir segunda opción (Menu de recursos)
    - Elegir cuarta opción (Listar recursos)
    - Verificar estado de recurso (Debería estar Prestado)
    - Elegir última opción (Volver)
    - Elegir tercera opción (Menu de préstamos)
    - Elegir quinta opción (Devolver préstamo)
    - Completar con el nombre de usuario recién creado
    - Completar con el título del recurso recién creado
    - Elegir cuarta opción (Listar Préstamos)
    - Verificar que el prestamo diga devuelto
    - Elegir última opción (Volver)
    - Elegir segunda opción (Menu de recursos)
    - Elegir cuarta opción (Listar recursos)
    - Verificar estado de recurso (Debería estar Disponible)

2. **Sistema de Reservas**:
    - Elegir sistema de notificación
    - Completar sus preferencias de notificación (Todas en sí)
    - Elegir la primera opción (Menu de usuarios)
    - Elegir la primera opción (Crear usuario)
    - Completar nombre
    - Completar email (Tiene que ser un string con "@")
    - Completar contraseña (Mínimo 4 caracteres)
    - Repetir procedimiento para crear otro usuario
    - Elegir la última opción (Volver)
    - Elegir segunda opción (Menu de recursos)
    - Elegir primera opción (Crear recurso)
    - Elegir categoria (En este caso, libro)
    - Completar titulo
    - Completar cantidad de páginas
    - Elegir ultima opción (Volver)
    - Elegir cuarta opción (Menu de reservas)
    - Elegir primera opción (Crear reserva)
    - Completar con el nombre de usuario recién creado
    - Completar con el título del recurso recién creado
    - Repetir procedimiento para el otro usuario
    - Elegir última opción
    - Elegir segunda opción (Menu recursos)
    - Quinta opción (Utilizado para probar)
    - Y a partir de aquí te llegarán las alertas para crear un prestamo

3. **Alertas y Notificaciones**
    - Realizar el 1 otra vez, pero con todas las preferencias activadas

## 🧩 Tecnologías y Herramientas

- Java 21+ (LTS)
- Git y GitHub
- GitHub Projects
- GitHub Issues
- GitHub Pull Requests

## 📘 Etapas del Trabajo

### Etapa 1: Diseño Base y Principios SOLID
- **SRP**: 
  - Crear clase `Usuario` con atributos básicos (nombre, ID, email)
  - Crear clase `RecursoDigital` como clase base abstracta
  - Implementar clase `GestorUsuarios` separada de `GestorRecursos`
  - Cada clase debe tener una única responsabilidad clara
  - Implementar clase `Consola` para manejar la interacción con el usuario

- **OCP**: 
  - Diseñar interfaz `RecursoDigital` con métodos comunes
  - Implementar clases concretas `Libro`, `Revista`, `Audiolibro`
  - Usar herencia para extender funcionalidad sin modificar código existente
  - Ejemplo: agregar nuevo tipo de recurso sin cambiar clases existentes
  - Implementar menú de consola extensible para nuevos tipos de recursos

- **LSP**: 
  - Asegurar que todas las subclases de `RecursoDigital` puedan usarse donde se espera `RecursoDigital`
  - Implementar métodos comunes en la clase base
  - Validar que el comportamiento sea consistente en todas las subclases
  - Crear métodos de visualización en consola para todos los tipos de recursos

- **ISP**: 
  - Crear interfaz `Prestable` para recursos que se pueden prestar
  - Crear interfaz `Renovable` para recursos que permiten renovación
  - Implementar solo las interfaces necesarias en cada clase
  - Diseñar menús de consola específicos para cada tipo de operación

- **DIP**: 
  - Crear interfaz `ServicioNotificaciones`
  - Implementar `ServicioNotificacionesEmail` y `ServicioNotificacionesSMS`
  - Usar inyección de dependencias en las clases que necesitan notificaciones
  - Implementar visualización de notificaciones en consola

### Etapa 2: Gestión de Recursos y Colecciones
- Implementar colecciones:
  - Usar `ArrayList<RecursoDigital>` para almacenar recursos
  - Usar `Map<String, Usuario>` para gestionar usuarios
  - Implementar métodos de búsqueda básicos
  - Crear menú de consola para gestión de recursos

- Crear servicios de búsqueda:
  - Implementar búsqueda por título usando Streams
  - Implementar filtrado por categoría
  - Crear comparadores personalizados para ordenamiento
  - Diseñar interfaz de consola para búsquedas con filtros

- Sistema de categorización:
  - Crear enum `CategoriaRecurso`
  - Implementar método de asignación de categorías
  - Crear búsqueda por categoría
  - Mostrar categorías disponibles en consola

- Manejo de excepciones:
  - Crear `RecursoNoDisponibleException`
  - Crear `UsuarioNoEncontradoException`
  - Implementar manejo adecuado de excepciones en los servicios
  - Mostrar mensajes de error amigables en consola

### Etapa 3: Sistema de Préstamos y Reservas
- Implementar sistema de préstamos:
  - Crear clase `Prestamo` con atributos básicos
  - Implementar lógica de préstamo y devolución
  - Manejar estados de los recursos (disponible, prestado, reservado)
  - Diseñar menú de consola para préstamos

- Sistema de reservas:
  - Crear clase `Reserva` con atributos necesarios
  - Implementar cola de reservas usando `BlockingQueue`
  - Manejar prioridad de reservas
  - Mostrar estado de reservas en consola

- Notificaciones:
  - Implementar sistema básico de notificaciones
  - Crear diferentes tipos de notificaciones
  - Usar `ExecutorService` para enviar notificaciones
  - Mostrar notificaciones en consola

- Concurrencia:
  - Implementar sincronización en operaciones de préstamo
  - Usar `synchronized` donde sea necesario
  - Manejar condiciones de carrera
  - Mostrar estado de operaciones concurrentes en consola

### Etapa 4: Reportes y Análisis
- Generar reportes básicos:
  - Implementar reporte de recursos más prestados
  - Crear reporte de usuarios más activos
  - Generar estadísticas de uso por categoría
  - Diseñar visualización de reportes en consola

- Sistema de alertas:
  - Implementar alertas por vencimiento de préstamos:
    - Crear clase `AlertaVencimiento` que monitorea fechas de devolución
    - Implementar lógica de recordatorios (1 día antes, día del vencimiento)
    - Mostrar alertas en consola con formato destacado
    - Permitir renovación desde la alerta
  
  - Crear notificaciones de disponibilidad:
    - Implementar `AlertaDisponibilidad` para recursos reservados
    - Notificar cuando un recurso reservado está disponible
    - Mostrar lista de recursos disponibles en consola
    - Permitir préstamo inmediato desde la notificación
  
  - Manejar recordatorios automáticos:
    - Implementar sistema de recordatorios periódicos
    - Crear diferentes niveles de urgencia (info, warning, error)
    - Mostrar historial de alertas en consola
    - Permitir configuración de preferencias de notificación

- Concurrencia en reportes:
  - Implementar generación de reportes en segundo plano
  - Usar `ExecutorService` para tareas asíncronas
  - Manejar concurrencia en acceso a datos
  - Mostrar progreso de generación de reportes en consola

## 📋 Detalle de Implementación

### 1. Estructura Base
```java
// Interfaces principales
public interface RecursoDigital {
    String getIdentificador();
    EstadoRecurso getEstado();
    void actualizarEstado(EstadoRecurso estado);
}

public interface Prestable {
    boolean estaDisponible();
    LocalDateTime getFechaDevolucion();
    void prestar(Usuario usuario);
}

public interface Notificable {
    void enviarNotificacion(String mensaje);
    List<Notificacion> getNotificacionesPendientes();
}

// Clase base abstracta
public abstract class RecursoBase implements RecursoDigital, Prestable {
    // Implementación común
}
```

### 2. Gestión de Biblioteca
```java
public class GestorBiblioteca {
    private final Map<String, RecursoDigital> recursos;
    private final List<Prestamo> prestamos;
    private final ExecutorService notificador;
    // Implementación de gestión
}
```

### 3. Sistema de Préstamos
```java
public class SistemaPrestamos {
    private final BlockingQueue<SolicitudPrestamo> colaSolicitudes;
    private final ExecutorService procesadorPrestamos;
    // Implementación de préstamos
}
```

## ✅ Entrega y Flujo de Trabajo con GitHub

1. **Configuración del Repositorio**
   - Proteger la rama `main`
   - Crear template de Issues y Pull Requests

2. **Project Kanban**
   - `To Do`
   - `In Progress`
   - `Code Review`
   - `Done`

3. **Milestones**
   - Etapa 1: Diseño Base
   - Etapa 2: Gestión de Recursos
   - Etapa 3: Sistema de Préstamos
   - Etapa 4: Reportes

4. **Issues y Pull Requests**
   - Crear Issues detallados para cada funcionalidad
   - Asociar cada Issue a un Milestone
   - Implementar en ramas feature
   - Revisar código antes de merge

## 📝 Ejemplo de Issue

### Título
Implementar sistema de préstamos concurrente

### Descripción
Crear el sistema de préstamos que utilice hilos y el patrón productor-consumidor para procesar solicitudes de préstamo en tiempo real.

#### Requisitos
- Implementar `BlockingQueue` para solicitudes de préstamo
- Crear procesador de solicitudes usando `ExecutorService`
- Implementar sistema de notificaciones
- Asegurar thread-safety en operaciones de préstamo

#### Criterios de Aceptación
- [ ] Sistema procesa préstamos concurrentemente
- [ ] Manejo adecuado de excepciones
- [ ] Documentación de diseño

### Labels
- `enhancement`
- `concurrency`

## ✅ Requisitos para la Entrega

- ✅ Implementación completa de todas las etapas
- ✅ Código bien documentado
- ✅ Todos los Issues cerrados
- ✅ Todos los Milestones completados
- ✅ Pull Requests revisados y aprobados
- ✅ Project actualizado

> ⏰ **Fecha de vencimiento**: 23/04/2025 a las 13:00 hs

## 📚 Recursos Adicionales

- Documentación oficial de Java 21
- Guías de estilo de código
- Ejemplos de implementación concurrente
- Patrones de diseño aplicados

## 📝 Consideraciones Éticas

### Uso de Inteligencia Artificial
El uso de herramientas de IA en este trabajo práctico debe seguir las siguientes pautas:

1. **Transparencia**
   - Documentar claramente qué partes del código fueron generadas con IA
   - Explicar las modificaciones realizadas al código generado
   - Mantener un registro de las herramientas utilizadas

2. **Aprendizaje**
   - La IA debe usarse como herramienta de aprendizaje, no como reemplazo
   - Comprender y ser capaz de explicar el código generado
   - Utilizar la IA para mejorar la comprensión de conceptos

3. **Integridad Académica**
   - El trabajo final debe reflejar tu aprendizaje y comprensión personal
   - No se permite la presentación de código generado sin comprensión
   - Debes poder explicar y defender cualquier parte del código

4. **Responsabilidad**
   - Verificar la corrección y seguridad del código generado
   - Asegurar que el código cumple con los requisitos del proyecto
   - Mantener la calidad y estándares de código establecidos

5. **Desarrollo Individual**
   - La IA puede usarse para facilitar tu proceso de aprendizaje
   - Documentar tu proceso de desarrollo y decisiones tomadas
   - Mantener un registro de tu progreso y aprendizaje

### Uso de la IA por parte del Alumno
1. **Se utilizo en los menus**
2. **Se utilizo en los inputs**
3. **Se utilizo poco en concurrencia**

### Consecuencias del Uso Inadecuado
El uso inadecuado de IA puede resultar en:
- Calificación reducida o nula
- Sanciones académicas
- Pérdida de oportunidades de aprendizaje
- Impacto negativo en tu desarrollo profesional

## 📝 Licencia

Este trabajo es parte del curso de Programación Avanzada de Ingeniería en Informática. Uso educativo únicamente.