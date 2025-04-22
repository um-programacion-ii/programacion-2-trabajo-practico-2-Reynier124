package Main;

import Excepciones.RecursoNoDisponibleException;
import Excepciones.UsuarioNoEncontradoException;
import Gestor.GestorBiblioteca;
import Interface.ServicioNotificaciones;
import Interface.RecursoDigital;
import Gestor.GestorRecursos;
import Gestor.GestorUsuarios;
import Util.Input;

import java.util.Scanner;

public class Consola {
    private Scanner scanner;
    private final ServicioNotificaciones notificaciones;
    private Input ip;
    private final GestorBiblioteca gestorBiblioteca;

    public Consola(ServicioNotificaciones servicio, int hilos) {
        scanner = new Scanner(System.in);
        this.notificaciones = servicio;
        ip = new Input(scanner);
        gestorBiblioteca = new GestorBiblioteca(scanner, notificaciones, hilos);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = ip.leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> gestionarUsuarios();
                case 2 -> gestionarRecursos();
                case 3 -> gestionarPrestamos();
                case 4 -> gestionarReservas();
                case 5 ->
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
        gestorBiblioteca.terminar();
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Sistema de usuarios");
        System.out.println("2. Sistema de recursos");
        System.out.println("3. Sistema de prestamos");
        System.out.println("4. Sistema de reservas");
        System.out.println("5. Configurar recordatorios");
        System.out.println("0. Salir");
    }

    private void mostrarMenuRecursos() {
        System.out.println("\n--- MENÚ RECURSOS ---");
        System.out.println("1. Crear Recurso");
        System.out.println("2. Buscar Recurso");
        System.out.println("3. Ordenar Lista de Recursos");
        System.out.println("4. Listar recursos");
        System.out.println("0. Salir");
    }

    private void gestionarRecursos() {
        int opcion;
        do {
            mostrarMenuRecursos();
            opcion = ip.leerEntero("Seleccione una opción: ");
            try {
                switch (opcion) {
                    case 0 -> System.out.println("Saliendo...");
                    case 1 -> gestorBiblioteca.crear("Recurso");
                    case 2 -> gestorBiblioteca.buscar("Recurso");
                    case 3 -> gestorBiblioteca.ordenar("Recurso");
                    case 4 -> gestorBiblioteca.listar("Recurso");
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
            mostrarMenuUsuarios();
            opcion = ip.leerEntero("Seleccione una opcion: ");
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

    private void gestionarPrestamos() {
        int opcion;
        do {
            mostrarMenuPrestamos();
            opcion = ip.leerEntero("Seleccione una opción: ");
            try {
                switch (opcion) {
                    case 0 -> System.out.println("Saliendo...");
                    case 1 -> gestorBiblioteca.crear("Prestamo");
                    case 2 -> gestorBiblioteca.buscar("Prestamo");
                    case 3 -> gestorBiblioteca.ordenar("Prestamo");
                    case 4 -> gestorBiblioteca.listar("Prestamo");
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (RecursoNoDisponibleException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void mostrarMenuPrestamos() {
        System.out.println("\n--- MENÚ PRESTAMOS ---");
        System.out.println("1. Crear Prestamo");
        System.out.println("2. Buscar Prestamo");
        System.out.println("3. Ordenar Lista de Prestamos");
        System.out.println("4. Listar Prestamos");
        System.out.println("0. Salir");
    }

    private void gestionarReservas() {
        int opcion;
        do {
            mostrarMenuReservas();
            opcion = ip.leerEntero("Seleccione una opción: ");
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

    private void mostrarMenuReservas() {
        System.out.println("\n--- MENÚ RESERVAS ---");
        System.out.println("1. Crear Reserva");
        System.out.println("2. Buscar Reserva");
        System.out.println("3. Ordenar Lista de Reservas");
        System.out.println("4. Listar Reservas");
        System.out.println("0. Salir");
    }
    
}
