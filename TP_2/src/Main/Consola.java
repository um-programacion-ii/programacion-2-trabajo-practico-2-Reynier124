package Main;

import Interface.ServicioNotificaciones;
import Recurso.RecursoDigital;
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
                case 1 -> gu.crearUsuario();
                case 2 -> gr.agregarRecurso();
                case 3 -> listarRecursos();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Crear Usuario");
        System.out.println("2. Agregar Recurso Digital");
        System.out.println("3. Listar Recursos Digitales");
        System.out.println("0. Salir");
    }

    private void listarRecursos() {
        System.out.println("\n--- LISTA DE RECURSOS ---");
        for (RecursoDigital recurso : gr.getRecursos()) {
            System.out.println(recurso);
        }
    }

}
