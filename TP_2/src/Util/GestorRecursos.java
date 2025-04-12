package Util;

import Interface.ServicioNotificaciones;
import Recurso.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorRecursos {
    private List<RecursoDigital> recursos;
    private Scanner sc;
    private Input ip;
    private final ServicioNotificaciones notificaciones;

    public GestorRecursos(Scanner sc, ServicioNotificaciones notificaciones) {
        recursos = new ArrayList<>();
        this.sc = sc;
        this.ip = new Input(sc);
        this.notificaciones = notificaciones;
    }

    public List<RecursoDigital> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<RecursoDigital> recursos) {
        this.recursos = recursos;
    }

    public void agregarRecurso() {
        System.out.println("\n--- AGREGAR RECURSO ---");
        System.out.println("Tipos disponibles:");
        System.out.println("1. Libro");
        System.out.println("2. Revistas");
        System.out.println("3. Audiolibro");
        System.out.println("0. Volver");
        int tipo = ip.leerEntero("Seleccione tipo de recurso: ");
        if (0 == tipo) {
            System.out.println("Volviendo...");
        }else {
            String titulo = ip.leerTexto("Título del libro: ");
            Estado estado = leerEnum();
            switch (tipo){
                case 1 -> {
                    int cant_paginas = ip.leerEntero("Cantidad de paginas: ");
                    recursos.add(new Libro(estado, titulo, cant_paginas));
                    notificaciones.notificar("El libro ha sido creado con éxito");
                }
                case 2 -> {
                    String periocidad = ip.leerTexto("Periocidad: ");
                    recursos.add(new Revista(estado, titulo, periocidad));
                    notificaciones.notificar("La revista ha sido creada con éxito");
                }
                case 3 -> {
                    String duracion = ip.leerTexto("Duracion: ");
                    recursos.add(new Audiolibro(estado, titulo, duracion));
                    notificaciones.notificar("El audiolibro ha sido creado con éxito");
                }
            }

        }

    }

    public Estado leerEnum(){
        Estado estado = null;
        System.out.println("Estados disponibles:");
        for (Estado e : Estado.values()) {
            System.out.println("- " + e);
        }
        while (estado == null) {
            String estadoTexto = ip.leerTexto("Ingrese el estado: ").toUpperCase();
            try {
                estado = Estado.valueOf(estadoTexto);
            } catch (IllegalArgumentException e) {
                System.out.println("Estado inválido. Intente nuevamente.");
            }
        }
        return estado;
    };
}
