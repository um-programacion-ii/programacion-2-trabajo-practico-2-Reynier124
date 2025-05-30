package Main;

import Excepciones.RecursoNoDisponibleException;
import Excepciones.UsuarioNoEncontradoException;
import Gestor.GestorBiblioteca;
import Interface.ServicioNotificaciones;
import Interface.RecursoDigital;
import Gestor.GestorRecursos;
import Gestor.GestorUsuarios;
import Util.Input;
import Util.ReporteGenerator;
import Util.ServicioNotificacionesEmail;
import Util.ServicioNotificacionesSMS;

import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Consola {
    private Scanner scanner;
    private Input ip;
    private final GestorBiblioteca gestorBiblioteca;
    private final ReporteGenerator reporteGenerator;
    private Lock lock = new ReentrantLock();

    public Consola(int hilos) {
        scanner = new Scanner(System.in);
        ip = new Input(scanner);
        gestorBiblioteca = new GestorBiblioteca(scanner, preguntarServicioNotifaciones(), hilos);
        reporteGenerator = new ReporteGenerator();
    }

    public ServicioNotificaciones preguntarServicioNotifaciones(){
        System.out.println("¿Qué servicios de notificaciones le gustaría tener?");
        System.out.println("1. Email");
        System.out.println("2. SMS");
        int decision;
        do {
            decision = ip.leerEntero("Seleccione una opcion: ");
            switch (decision){
                case 1 -> {
                    return new ServicioNotificacionesEmail();
                }
                case 2 -> {
                    return new ServicioNotificacionesSMS();
                }
                default -> System.out.println("Opcion invalida. Intente de nuevo");
            }
        }while (decision != 1 && decision !=2);
        return null;
    }

    public void iniciar() {
        int opcion;
        do {
            lock.lock();
            mostrarMenuPrincipal();
            opcion = ip.leerEntero("Seleccione una opción: ");
            lock.unlock();
            switch (opcion) {
                case 1 -> gestionarUsuarios();
                case 2 -> gestionarRecursos();
                case 3 -> gestionarPrestamos();
                case 4 -> gestionarReservas();
                case 5 -> gestionarReportes();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
        gestorBiblioteca.terminar();
    }

    private  void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Sistema de usuarios");
        System.out.println("2. Sistema de recursos");
        System.out.println("3. Sistema de prestamos");
        System.out.println("4. Sistema de reservas");
        System.out.println("5. Sistema de reportes");
        System.out.println("0. Salir");

    }

    private  void mostrarMenuRecursos() {
        System.out.println("\n--- MENÚ RECURSOS ---");
        System.out.println("1. Crear Recurso");
        System.out.println("2. Buscar Recurso");
        System.out.println("3. Ordenar Lista de Recursos");
        System.out.println("4. Listar recursos");
        System.out.println("5. Cambiar recurso a disponible");
        System.out.println("0. Salir");

    }

    private void gestionarRecursos() {
        int opcion;
        do {
            lock.lock();
            mostrarMenuRecursos();
            opcion = ip.leerEntero("Seleccione una opción: ");
            lock.unlock();
            try {
                switch (opcion) {
                    case 0 -> System.out.println("Saliendo...");
                    case 1 -> gestorBiblioteca.crear("Recurso");
                    case 2 -> gestorBiblioteca.buscar("Recurso");
                    case 3 -> gestorBiblioteca.ordenar("Recurso");
                    case 4 -> gestorBiblioteca.listar("Recurso");
                    case 5 -> gestorBiblioteca.cambiarEstado();
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (RecursoNoDisponibleException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }


    private void mostrarMenuUsuarios() {
        System.out.println("\n--- MENÚ USUARIOS ---");
        System.out.println("1. Crear Usuario");
        System.out.println("2. Buscar Usuario");
        System.out.println("3. Ordenar Lista de Usuarios");
        System.out.println("4. Listar Usuarios");
        System.out.println("0. Salir");

    }

    private void gestionarUsuarios() {
        int opcion;
        do {
            lock.lock();
            mostrarMenuUsuarios();
            opcion = ip.leerEntero("Seleccione una opcion: ");
            lock.unlock();
            try{
                switch (opcion) {
                    case 0 -> System.out.println("Saliendo...");
                    case 1 -> gestorBiblioteca.crear("Usuario");
                    case 2 -> gestorBiblioteca.buscar("Usuario");
                    case 3 -> gestorBiblioteca.ordenar("Usuario");
                    case 4 -> gestorBiblioteca.listar("Usuario");
                    default -> System.out.println("Opcion invalida. Intente nuevamente.");
                }
            }catch (UsuarioNoEncontradoException e){
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        }while (opcion != 0);
    }

    private synchronized void gestionarPrestamos() {
        int opcion;
        do {
            lock.lock();
            mostrarMenuPrestamos();
            opcion = ip.leerEntero("Seleccione una opción: ");
            lock.unlock();
            try {
                switch (opcion) {
                    case 0 -> System.out.println("Saliendo...");
                    case 1 -> gestorBiblioteca.crear("Prestamo");
                    case 2 -> gestorBiblioteca.buscar("Prestamo");
                    case 3 -> gestorBiblioteca.ordenar("Prestamo");
                    case 4 -> gestorBiblioteca.listar("Prestamo");
                    case 5 -> gestorBiblioteca.devolverPrestamo();
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (RecursoNoDisponibleException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private  void mostrarMenuPrestamos() {
        synchronized (System.out) {
            System.out.println("\n--- MENÚ PRESTAMOS ---");
            System.out.println("1. Crear Prestamo");
            System.out.println("2. Buscar Prestamo");
            System.out.println("3. Ordenar Lista de Prestamos");
            System.out.println("4. Listar Prestamos");
            System.out.println("5. Devolver Prestamo");
            System.out.println("0. Salir");
        }
    }

    private void gestionarReservas() {
        int opcion;
        do {
            lock.lock();
            mostrarMenuReservas();
            opcion = ip.leerEntero("Seleccione una opción: ");
            lock.unlock();
            try {
                switch (opcion) {
                    case 0 -> System.out.println("Saliendo...");
                    case 1 -> gestorBiblioteca.crear("Reserva");
                    case 2 -> gestorBiblioteca.buscar("Reserva");
                    case 3 -> gestorBiblioteca.ordenar("Reserva");
                    case 4 -> gestorBiblioteca.listar("Reserva");
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (RecursoNoDisponibleException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private synchronized void mostrarMenuReservas() {
        System.out.println("\n--- MENÚ RESERVAS ---");
        System.out.println("1. Crear Reserva");
        System.out.println("2. Buscar Reserva");
        System.out.println("3. Ordenar Lista de Reservas");
        System.out.println("4. Listar Reservas");
        System.out.println("0. Salir");
    }

    private void gestionarReportes(){
        int opcion;
        do {
            lock.lock();
            mostrarMenuReportes();
            opcion = ip.leerEntero("Seleccione una opcion: ");
            lock.unlock();
            switch (opcion) {
                case 0 -> System.out.println("Saliendo...");
                case 1 -> reporteGenerator.generarReporteRecursosMasPrestados(gestorBiblioteca.conseguirRecursosPrestamos());
                case 2 -> reporteGenerator.generarReporteRecursosMasReservados(gestorBiblioteca.conseguirRecursosReservas());
                case 3 -> reporteGenerator.generarReporteUsuariosMasActivos(gestorBiblioteca.conseguirUsuarios());
                case 4 -> reporteGenerator.generarEstadisticasUsoPorCategoria(gestorBiblioteca.conseguirRecursos());
                case 5 -> gestorBiblioteca.configurarPreferencias();
                case 6 -> gestorBiblioteca.mostrarHistorialReportes();
            }
        }while (opcion != 0);
    }

    private synchronized void mostrarMenuReportes(){
        System.out.println("\n--- MENÚ REPORTES ---");
        System.out.println("1. Ver recursos más prestados");
        System.out.println("2. Ver recursos más reservados");
        System.out.println("3. Ver usuarios más activos");
        System.out.println("4. Ver uso por categorías");
        System.out.println("5. Modificar preferencias");
        System.out.println("6. Ver historial de reportes");
        System.out.println("0. Salir");
    }
}
