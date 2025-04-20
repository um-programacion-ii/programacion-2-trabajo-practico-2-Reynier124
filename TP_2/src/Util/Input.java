package Util;

import Enum.Categoria;
import Enum.EstadoRecurso;
import Usuario.Usuario;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Input {
    private Scanner scanner;

    public Input(Scanner sc) {
        this.scanner = sc;
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, introduzca un número entero.");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    public Integer leerEnteroOpcional(String mensaje) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Por favor, introduzca un número entero válido o deje en blanco.");
            return leerEnteroOpcional(mensaje);
        }
    }

    public EstadoRecurso leerEnumEstado() {
        EstadoRecurso estado = null;
        System.out.println("Estados disponibles:");
        for (EstadoRecurso e : EstadoRecurso.values()) {
            System.out.println("- " + e);
        }
        while (estado == null) {
            String estadoTexto = leerTexto("Ingrese el estado: ").toUpperCase();
            try {
                estado = EstadoRecurso.valueOf(estadoTexto);
            } catch (IllegalArgumentException e) {
                System.out.println("EstadoRecurso inválido. Intente nuevamente.");
            }
        }
        return estado;
    }

    ;

    public EstadoRecurso leerEnumEstadoOpcional(String mensaje) {
        System.out.print(mensaje);
        String estadoTexto = scanner.nextLine().trim().toUpperCase();
        if (estadoTexto.isEmpty()) {
            return null;
        }
        try {
            return EstadoRecurso.valueOf(estadoTexto);
        } catch (IllegalArgumentException e) {
            System.out.println("EstadoRecurso inválido. Intente nuevamente.");
            return leerEnumEstadoOpcional(mensaje);
        }
    }

    public Categoria leerEnumCategoria() {
        Categoria categoria = null;
        System.out.println("Categorías disponibles:");
        for (Categoria c : Categoria.values()) {
            System.out.println("- " + c);
        }
        while (categoria == null) {
            String categoriaTexto = leerTexto("Ingrese la categoría: ").toUpperCase();
            try {
                categoria = Categoria.valueOf(categoriaTexto);
            } catch (IllegalArgumentException e) {
                System.out.println("Categoría inválida. Intente nuevamente.");
            }
        }
        return categoria;
    }

    public LocalDate leerFechaOpcional(String mensaje) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return null;
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            System.out.println("Formato incorrecto. Use: yyyy-MM-dd");
            return leerFechaOpcional(mensaje);
        }
    }

}

