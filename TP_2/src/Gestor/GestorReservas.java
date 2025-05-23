package Gestor;

import Enum.EstadoRecurso;
import Comparadores.ComparadorRecursoTitulo;
import Comparadores.ComparadorUsuarioNombre;
import Excepciones.RecursoNoDisponibleException;
import Interface.Prestable;
import Interface.RecursoDigital;
import Interface.ServicioNotificaciones;
import Observer.ReservaObserver;
import Reserva.Reservas;
import Sistema.SistemaDisponibilidad;
import Sistema.SistemaNotificaciones;
import Sistema.SistemaReservas;
import Usuario.Usuario;
import Util.Input;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;

public class GestorReservas implements ReservaObserver {
    private final GestorUsuarios gestorUsuarios;
    private final GestorRecursos gestorRecursos;
    private List<Reservas> reservas;
    private final Input ip;
    private final Scanner sc;
    private final SistemaReservas sistemaReservas;
    private final SistemaDisponibilidad sistemaDisponibilidad;
    private final GestorPrestamos gestorPrestamos;
    private final SistemaNotificaciones sistemaNotificaciones;

    public GestorReservas(GestorUsuarios gestorUsuarios, GestorRecursos gestorRecursos, SistemaNotificaciones sistemaNotificaciones, GestorPrestamos gestorPrestamos, int hilos) {
        this.sc = new Scanner(System.in);
        this.ip = new Input(sc);
        reservas = new LinkedList<>();
        this.sistemaNotificaciones = sistemaNotificaciones;
        this.gestorUsuarios = gestorUsuarios;
        this.gestorRecursos = gestorRecursos;
        this.gestorPrestamos = gestorPrestamos;
        this.sistemaDisponibilidad = new SistemaDisponibilidad(sistemaNotificaciones, gestorPrestamos, this);
        sistemaReservas = new SistemaReservas(sistemaNotificaciones, sistemaDisponibilidad,hilos);
        sistemaReservas.agregarObservador(this);

    }

    public List<Reservas> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reservas> reservas) {
        this.reservas = reservas;
    }

    public SistemaReservas getSistemaReservas() {
        return sistemaReservas;
    }

    public SistemaDisponibilidad getSistemaDisponibilidad() {
        return sistemaDisponibilidad;
    }

    public void crear() {
        System.out.println("\n--- CREAR RESERVAS ---");
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
        int prioridad = ip.leerPrioridad("Ingrese la prioridad: ");
        try{
            sistemaReservas.agregarReserva(usuario, (Prestable) recurso, prioridad);
        } catch (RecursoNoDisponibleException e) {
            System.out.println(e.getMessage());
        }
        sistemaNotificaciones.notificarInfo("Reserva creada con exito");
    }

    public void buscar() {
        System.out.println("\n--- BUSCAR RESERVAS ---");
        System.out.println("Introduzca los criterios de búsqueda (deje en blanco si no desea filtrar por ese criterio):");

        String nombreUsuario = ip.leerTexto("Nombre del usuario: ");
        String tituloRecurso = ip.leerTexto("Título del recurso: ");

        try {
            List<Reservas> resultados = buscarReservas(nombreUsuario, tituloRecurso);
            if (resultados.isEmpty()) {
                System.out.println("No se encontraron reservas con esos criterios.");
            } else {
                resultados.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar reservas: " + e.getMessage());
        }
    }

    public List<Reservas> buscarReservas(String nombreUsuario, String tituloRecurso) {
        return reservas.stream()
                .filter(p -> (nombreUsuario == null || nombreUsuario.isEmpty() ||
                        p.getUsuario().getNombre().equalsIgnoreCase(nombreUsuario)))
                .filter(p -> (tituloRecurso == null || tituloRecurso.isEmpty() ||
                        (p.getRecurso() instanceof RecursoDigital &&
                                ((RecursoDigital) p.getRecurso()).getTitulo().equalsIgnoreCase(tituloRecurso)))).toList();
    }

    public void ordenar() {
        int opcion;
        do {
            System.out.println("\n--- ORDENAR DE RESERVAS ---");
            System.out.println("1. Ordenar por nombre del usuario de forma ascendente");
            System.out.println("2. Ordenar por nombre del usuario de forma descendente");
            System.out.println("3. Ordenar por titulo del recurso de forma ascendente");
            System.out.println("4. Ordenar por titulo del recurso de forma descendente");
            System.out.println("0. Salir");
            opcion = ip.leerEntero("Opcion: ");
            switch(opcion){
                case 0 -> System.out.println("Volviendo...");
                case 1 -> ordenarPorNombreUsuarioAsc();
                case 2 -> ordenarPorNombreUsuarioDesc();
                case 3 -> ordenarPorTituloRecursoAsc();
                case 4 -> ordenarPorTituloRecursoDesc();
                default -> System.out.println("Esa opción no existe. Intente de nuevo");
            }
        }while (opcion != 0);
    }

    public void ordenarPorNombreUsuarioAsc() {
        reservas.sort(Comparator.comparing(Reservas::getUsuario, new ComparadorUsuarioNombre()));
        System.out.println("Ordenados por usuario ascendente.");
    }

    public void ordenarPorNombreUsuarioDesc() {
        reservas.sort(Comparator.comparing(Reservas::getUsuario, new ComparadorUsuarioNombre()).reversed());
        System.out.println("Ordenados por usuario descendente.");
    }

    public void ordenarPorTituloRecursoAsc() {
        reservas.sort(Comparator.comparing(
                p -> (RecursoDigital) p.getRecurso(),
                new ComparadorRecursoTitulo()
        ));
        System.out.println("Ordenados por título de recurso ascendente.");
    }

    public void ordenarPorTituloRecursoDesc() {
        reservas.sort(Comparator.comparing(
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

        System.out.println("Recurso no encontrado.");
        return null;
    }

    public void listarReservas(List<Reservas> reservas) {
        System.out.println("\n--- LISTA DE RESERVAS ---");
        for (Reservas reserva : reservas) {
            System.out.println(reserva.toString());
        }
    }

    @Override
    public void actualizar(Reservas reserva) {
        reservas.add(reserva);
    }

    public void eliminarReserva(Reservas reserva) {
        List<Reservas> reservas = buscarReservas(reserva.getUsuario().getNombre(), ((RecursoDigital) reserva.getRecurso()).getTitulo());
        if (reservas.size() > 1){
            reservas.remove(reserva);
        } else if (reservas.size() == 1) {
            ((RecursoDigital) reserva.getRecurso()).setEstado(EstadoRecurso.DISPONIBLE);
        }else {
            System.out.println("Ha ocurrido un error");
        }
    }


    public void apagarSistema(){
        sistemaReservas.cerrar();
    }
}
