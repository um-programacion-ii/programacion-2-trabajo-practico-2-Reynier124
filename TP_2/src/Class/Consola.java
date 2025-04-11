package Class;

import Class.Usuario;
import Class.RecursoDigital;
import Class.Libro;
import Class.Revista;
import Class.Audiolibro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Consola {
    private Scanner scanner;
    private List<Usuario> usuarios;
    private List<RecursoDigital> recursos;

    public Consola() {
        scanner = new Scanner(System.in);
        usuarios = new ArrayList<>();
        recursos = new ArrayList<>();
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> crearUsuario();
                case 2 -> agregarRecurso();
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

    private void crearUsuario() {
        System.out.println("\n--- CREAR USUARIO ---");
        String nombre = leerTexto("Nombre: ");
        String email = leerTexto("Email: ");
        String password = leerTexto("Password: ");
        Usuario nuevo = new Usuario(nombre, email, password);
        usuarios.add(nuevo);
        System.out.println("Usuario creado: " + nuevo);
    }

    private void agregarRecurso() {
        System.out.println("\n--- AGREGAR RECURSO ---");
        System.out.println("Tipos disponibles:");
        System.out.println("1. Libro");
        System.out.println("2. Revistas");
        System.out.println("3. Audiolibro");
        System.out.println("0. Volver");
        int tipo = leerEntero("Seleccione tipo de recurso: ");
        if (tipo != 1 || tipo != 2 || tipo != 3) {
            System.out.println("Tipo invalido. Intentelo de nuevo");
        } else if (tipo == 0) {
            iniciar();
        }else {
            String titulo = leerTexto("Título del libro: ");
            String estado = leerTexto("Estado (Disponible/Prestado): ");
            switch (tipo){
                case 1 -> {
                    int cant_paginas = leerEntero("Cantidad de paginas: ");
                    recursos.add(new Libro(estado, titulo, cant_paginas));
                }
                case 2 -> {
                    String periocidad = leerTexto("Periocidad: ");
                    recursos.add(new Revista(estado, titulo, periocidad));
                }
                case 3 -> {
                    String duracion = leerTexto("Duracion: ");
                    recursos.add(new Audiolibro(estado, titulo, duracion));
                }
            }

        }

    }

    private void listarRecursos() {
        System.out.println("\n--- LISTA DE RECURSOS ---");
        for (RecursoDigital recurso : recursos) {
            System.out.println(recurso);
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, introduzca un número entero.");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}
