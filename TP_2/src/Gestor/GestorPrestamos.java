package Gestor;

import Enum.EstadoRecurso;
import Enum.NivelUrgencia;
import Comparadores.ComparadorPrestamoFechaDevolucion;
import Comparadores.ComparadorPrestamoFechaEntrega;
import Comparadores.ComparadorRecursoTitulo;
import Comparadores.ComparadorUsuarioNombre;
import Excepciones.RecursoNoDisponibleException;
import Observer.PrestamoObserver;
import Observer.RecursoObserver;
import Interface.Prestable;
import Interface.RecursoDigital;
import Interface.ServicioNotificaciones;
import Prestamo.Prestamo;
import Sistema.SistemaNotificaciones;
import Sistema.SistemaPrestamos;
import Usuario.Usuario;
import Util.Input;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class GestorPrestamos implements PrestamoObserver, RecursoObserver {
    private final GestorUsuarios gestorUsuarios;
    private final GestorRecursos gestorRecursos;
    private List<Prestamo> prestamos;
    private final SistemaPrestamos sistemaPrestamos;
    private final SistemaNotificaciones sistemaNotificaciones;
    private final Input ip;
    private final Scanner sc;

    public GestorPrestamos(GestorUsuarios gestorUsuarios, GestorRecursos gestorRecursos, SistemaNotificaciones sistemaNotificaciones, int hilos) {
        this.sc = new Scanner(System.in);
        this.ip = new Input(sc);
        this.gestorUsuarios = gestorUsuarios;
        this.gestorRecursos = gestorRecursos;
        prestamos = new LinkedList<Prestamo>();
        this.sistemaPrestamos = new SistemaPrestamos(sistemaNotificaciones,hilos);
        sistemaPrestamos.agregarObservador(this);
        this.sistemaNotificaciones = sistemaNotificaciones;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    private boolean tienePrestamoActivo(Usuario usuario) {
        return prestamos.stream()
                .anyMatch(prestamo -> prestamo.getUsuario().equals(usuario) && !prestamo.isDevuelto());
    }

    public void crear() {
        System.out.println("\n--- CREAR PRESTAMO ---");
        Usuario usuario = conseguirUsuario();
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        if (tienePrestamoActivo(usuario)) {
            System.out.println("El usuario ya tiene un préstamo activo.");
            return;
        }
        RecursoDigital recurso = conseguirRecurso();
        if (recurso == null) {
            System.out.println("Recurso no encontrado.");
            return;
        }
        sistemaPrestamos.solicitarPrestamo(usuario, (Prestable) recurso);

        sistemaNotificaciones.notificarInfo("Prestamo creado con exito");
    }

    public void solicitarPrestamo(RecursoDigital recurso, Usuario usuario) {
        System.out.println("\n--- CREAR PRESTAMO ---");
        sistemaPrestamos.solicitarPrestamo(usuario, (Prestable) recurso);
    }


    public void buscar() {
        System.out.println("\n--- BUSCAR PRESTAMO ---");
        System.out.println("Introduzca los criterios de búsqueda (deje en blanco si no desea filtrar por ese criterio):");

        String nombreUsuario = ip.leerTexto("Nombre del usuario: ");
        String tituloRecurso = ip.leerTexto("Título del recurso: ");
        LocalDate fechaEntrega = ip.leerFechaOpcional("Fecha de entrega (yyyy-MM-dd HH:mm): ");
        LocalDate fechaDevolucion = ip.leerFechaOpcional("Fecha de devolución (yyyy-MM-dd HH:mm): ");

        try {
            List<Prestamo> resultados = buscarPrestamos(nombreUsuario, tituloRecurso, fechaEntrega, fechaDevolucion);
            if (resultados.isEmpty()) {
                System.out.println("No se encontraron préstamos con esos criterios.");
            } else {
                resultados.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar préstamos: " + e.getMessage());
        }
    }

    public List<Prestamo> buscarPrestamos(String nombreUsuario, String tituloRecurso, LocalDate fechaEntrega, LocalDate fechaDevolucion) {
        return prestamos.stream()
                .filter(p -> (nombreUsuario == null || nombreUsuario.isEmpty() ||
                        p.getUsuario().getNombre().equalsIgnoreCase(nombreUsuario)))
                .filter(p -> (tituloRecurso == null || tituloRecurso.isEmpty() ||
                        (p.getRecurso() instanceof RecursoDigital &&
                                ((RecursoDigital) p.getRecurso()).getTitulo().equalsIgnoreCase(tituloRecurso))))
                .filter(p -> (fechaEntrega == null ||
                        p.getFechaEntrega().toLocalDate().equals(fechaEntrega)))
                .filter(p -> (fechaDevolucion == null ||
                        p.getFechaDevolucion().toLocalDate().equals(fechaDevolucion)))
                .collect(Collectors.toList());
    }


    public void ordenar() {
        int opcion;
        do {
            System.out.println("\n--- ORDENAR DE PRESTAMOS ---");
            System.out.println("1. Ordenar por nombre del usuario de forma ascendente");
            System.out.println("2. Ordenar por nombre del usuario de forma descendente");
            System.out.println("3. Ordenar por titulo del recurso de forma ascendente");
            System.out.println("4. Ordenar por titulo del recurso de forma descendente");
            System.out.println("5. Ordenar por fecha de entrega de forma ascendente");
            System.out.println("6. Ordenar por fecha de entrega de forma descendente");
            System.out.println("7. Ordenar por fecha de devolucion de forma ascendente");
            System.out.println("8. Ordenar por fecha de devolucion de forma descendente");
            System.out.println("0. Salir");
            opcion = ip.leerEntero("Opcion: ");
            switch(opcion){
                case 0 -> System.out.println("Volviendo...");
                case 1 -> ordenarPorNombreUsuarioAsc();
                case 2 -> ordenarPorNombreUsuarioDesc();
                case 3 -> ordenarPorTituloRecursoAsc();
                case 4 -> ordenarPorTituloRecursoDesc();
                case 5 -> ordenarPorFechaEntregaAsc();
                case 6 -> ordenarPorFechaEntregaDesc();
                case 7 -> ordenarPorFechaDevolucionAsc();
                case 8 -> ordenarPorFechaDevolucionDesc();
                default -> System.out.println("Esa opción no existe. Intente de nuevo");
            }
        }while (opcion != 0);
    }

    public void ordenarPorFechaEntregaAsc() {
        prestamos.sort(new ComparadorPrestamoFechaEntrega());
        System.out.println("Ordenados por fecha de entrega ascendente.");
    }

    public void ordenarPorFechaEntregaDesc() {
        prestamos.sort(new ComparadorPrestamoFechaEntrega().reversed());
        System.out.println("Ordenados por fecha de entrega ascendente.");
    }

    public void ordenarPorFechaDevolucionAsc() {
        prestamos.sort(Collections.reverseOrder(new ComparadorPrestamoFechaDevolucion()));
        System.out.println("Ordenados por fecha de entrega descendente.");
    }

    public void ordenarPorFechaDevolucionDesc() {
        prestamos.sort(Collections.reverseOrder(new ComparadorPrestamoFechaDevolucion()));
        System.out.println("Ordenados por fecha de entrega descendente.");
    }

    public void ordenarPorNombreUsuarioAsc() {
        prestamos.sort(Comparator.comparing(Prestamo::getUsuario, new ComparadorUsuarioNombre()));
        System.out.println("Ordenados por usuario ascendente.");
    }

    public void ordenarPorNombreUsuarioDesc() {
        prestamos.sort(Comparator.comparing(Prestamo::getUsuario, new ComparadorUsuarioNombre()).reversed());
        System.out.println("Ordenados por usuario descendente.");
    }

    public void ordenarPorTituloRecursoAsc() {
        prestamos.sort(Comparator.comparing(
                p -> (RecursoDigital) p.getRecurso(),
                new ComparadorRecursoTitulo()
        ));
        System.out.println("Ordenados por título de recurso ascendente.");
    }

    public void ordenarPorTituloRecursoDesc() {
        prestamos.sort(Comparator.comparing(
                p -> (RecursoDigital) p.getRecurso(),
                new ComparadorRecursoTitulo().reversed()
        ));
        System.out.println("Ordenados por título de recurso descendente.");
    }

    public Usuario conseguirUsuario(){
        System.out.println("Ingrese el nombre o id del usuario: ");
        String input = sc.nextLine().trim();

        try {
            int id = Integer.parseInt(input);
            for (Usuario usuario : gestorUsuarios.getUsuarios().values()) {
                if (usuario.getId() == id) {
                    return usuario;
                }
            }
        } catch (NumberFormatException e) {
            for (Usuario usuario : gestorUsuarios.getUsuarios().values()) {
                if (usuario.getNombre().equalsIgnoreCase(input)) {
                    return usuario;
                }
            }
        }
        System.out.println("Usuario no encontrado.");
        return null;
    }

    public RecursoDigital conseguirRecurso() {
        System.out.println("Ingrese el título o id del recurso: ");
        String input = sc.nextLine().trim();

        try {
            int id = Integer.parseInt(input);
            for (RecursoDigital recurso : gestorRecursos.getRecursos()) {
                if (recurso.getId() == id) {
                    return recurso;
                }
            }
        } catch (NumberFormatException e) {
            for (RecursoDigital recurso : gestorRecursos.getRecursos()) {
                if (recurso.getTitulo().equalsIgnoreCase(input)) {
                    return recurso;
                }
            }
        }
        return null;
    }

    @Override
    public void actualizar(Prestamo prestamo) {
        RecursoDigital recurso = (RecursoDigital) prestamo.getRecurso();
        if (recurso.getEstado() == EstadoRecurso.PRESTADO){
            prestamos.add(prestamo);
        }
    }

    @Override
    public void actualizar(RecursoDigital recurso) {
        if (recurso.getEstado() == EstadoRecurso.PRESTADO){
            //LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(7);
            LocalDateTime nuevaFecha = LocalDateTime.now().minusDays(1);
            for (Prestamo prestamo : prestamos){
                if (prestamo.getRecurso().equals(recurso)){
                    prestamo.setFechaDevolucion(nuevaFecha);
                    sistemaNotificaciones.programarRecordatorio("Faltan 2 días para que se venza el préstamo", NivelUrgencia.WARNING, LocalDateTime.now().plusDays(5));
                }
            }
        }
    }

    public void listarPrestamos(List<Prestamo> prestamos) {
        System.out.println("\n--- LISTA DE RESERVAS ---");
        for (Prestamo prestamo : prestamos) {
            System.out.println(prestamo.toString());
        }
    }

    public void devolverPrestamo() {
        Usuario usuario = conseguirUsuario();
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        RecursoDigital recurso = conseguirRecurso();
        if (recurso == null) {
            System.out.println("Recurso no encontrado.");
            return;
        }
        List<Prestamo> prestamos = buscarPrestamos(usuario.getNombre(), recurso.getTitulo(), null, null);

        List<Prestamo> prestamosNoDevueltos = prestamos.stream()
                .filter(p -> !p.isDevuelto())
                .collect(Collectors.toList());

        if (prestamosNoDevueltos.size() == 1) {
            Prestamo prestamo = prestamosNoDevueltos.get(0);
            prestamo.setDevuelto(true);
            System.out.println("Préstamo devuelto con éxito.");
        } else if (prestamosNoDevueltos.isEmpty()) {
            System.out.println("No se encontró ningún préstamo activo para el usuario y recurso especificados.");
        } else {
            System.out.println("Se encontraron múltiples préstamos activos para el usuario y recurso especificados.");
        }
    }
    public void apagarSistema() {
        sistemaPrestamos.cerrar();
    }
}

