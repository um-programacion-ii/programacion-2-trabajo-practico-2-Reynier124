package Util;

import Enum.Categoria;
import Enum.EstadoRecurso;
import Interface.RecursoDigital;
import Interface.ServicioNotificaciones;
import Recurso.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GestorRecursos extends Gestor {
    private List<RecursoDigital> recursos;

    public GestorRecursos(Scanner sc, ServicioNotificaciones notificaciones) {
        super(sc, notificaciones);
        recursos = new ArrayList<>();
    }

    public List<RecursoDigital> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<RecursoDigital> recursos) {
        this.recursos = recursos;
    }

    @Override
    public void crear() {
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
            EstadoRecurso estado = ip.leerEnumEstado();
            Categoria categoria = ip.leerEnumCategoria();
            switch (tipo){
                case 1 -> {
                    int cant_paginas = ip.leerEntero("Cantidad de paginas: ");
                    recursos.add(new Libro(estado, titulo,categoria, cant_paginas));
                    notificaciones.notificar("El libro ha sido creado con éxito");
                }
                case 2 -> {
                    String periocidad = ip.leerTexto("Periocidad: ");
                    recursos.add(new Revista(estado, titulo,categoria, periocidad));
                    notificaciones.notificar("La revista ha sido creada con éxito");
                }
                case 3 -> {
                    String duracion = ip.leerTexto("Duracion: ");
                    recursos.add(new Audiolibro(estado, titulo, categoria, duracion));
                    notificaciones.notificar("El audiolibro ha sido creado con éxito");
                }
            }

        }

    }

    public List<RecursoDigital> busquedaTitulo(String titulo){
        return recursos.stream()
                .filter(entry -> entry.getTitulo().equalsIgnoreCase(titulo))
                .collect(Collectors.toList());
    };

    public List<RecursoDigital> busquedaCategoria(String categoria){
        return recursos.stream()
                .filter(entry -> entry.getCategoria().toString().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    };

}
