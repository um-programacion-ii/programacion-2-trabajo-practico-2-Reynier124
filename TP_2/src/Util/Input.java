package Util;

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
}
