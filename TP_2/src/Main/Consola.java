package Main;

import Excepciones.RecursoNoDisponibleException;
import Excepciones.UsuarioNoEncontradoException;
import Interface.ServicioNotificaciones;
import Interface.RecursoDigital;
import Util.GestorRecursos;
import Util.GestorUsuarios;
import Util.Input;

import java.util.Scanner;

public class Consola {
    private Scanner scanner;
    private final ServicioNotificaciones notificaciones;
    private Input ip;
    private final GestorRecursos gr;
    private final GestorUsuarios gu;

    public Consola(ServicioNotificaciones servicio) {
        scanner = new Scanner(System.in);
        this.notificaciones = servicio;
        ip = new Input(scanner);
        gr = new GestorRecursos(scanner, notificaciones);
        gu = new GestorUsuarios(scanner, notificaciones);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = ip.leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> gestionarUsuarios();
                case 2 -> gestionarRecursos();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Sistema de usuarios");
        System.out.println("2. Sistema de recursos");
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
                    case 1 -> gr.crear();
                    case 2 -> gr.buscar();
                    case 3 -> gr.ordenar();
                    case 4 -> listarRecursos();
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
                    case 1 -> gu.crear();
                    case 2 -> gu.buscar();
                    case 3 -> gu.ordenar();
                    case 4 -> gu.listarUsuarios(gu.getUsuarios());
                    default -> System.out.println("Opcion invalida. Intente nuevamente.");
                }
            }catch (UsuarioNoEncontradoException e){
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        }while (opcion != 0);
    }

    private void listarRecursos() {
        System.out.println("\n--- LISTA DE RECURSOS ---");
        for (RecursoDigital recurso : gr.getRecursos()) {
            System.out.println(recurso);
        }
    }

}
