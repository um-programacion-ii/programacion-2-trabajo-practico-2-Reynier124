package Util;

import Enum.Categoria;
import Enum.EstadoRecurso;
import Excepciones.RecursoNoDisponibleException;
import Interface.RecursoDigital;
import Interface.ServicioNotificaciones;
import Recurso.*;

import java.util.ArrayList;
import java.util.Collections;
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

    public void crear() throws RecursoNoDisponibleException {
        int tipo;
        do {
            System.out.println("\n--- AGREGAR RECURSO ---");
            System.out.println("Tipos disponibles:");
            System.out.println("1. Libro");
            System.out.println("2. Revistas");
            System.out.println("3. Audiolibro");
            System.out.println("0. Volver");
            tipo = ip.leerEntero("Seleccione tipo de recurso: ");
            if (0 == tipo) {
                System.out.println("Volviendo...");
            }else if(tipo > 0 && tipo < 4) {
                String titulo = ip.leerTexto("Título del libro: ");
                if (recursos.stream().anyMatch(r -> r.getTitulo().equalsIgnoreCase(titulo))) {
                    throw new RecursoNoDisponibleException("El recurso con el título '" + titulo + "' ya existe.");
                }
                crearRecurso(tipo, titulo);
            }else {
                System.out.println("No es ninguna de las opciones. Intente de nuevo.");
            }
        }while (tipo != 0);
    }

    public void crearRecurso(int tipo, String titulo) {
        EstadoRecurso estado = ip.leerEnumEstado();
        switch (tipo){
            case 1 -> {
                Categoria categoria = Categoria.Libro;
                int cant_paginas = ip.leerEntero("Cantidad de paginas: ");
                recursos.add(new Libro(estado, titulo,categoria, cant_paginas));
                notificaciones.notificar("El libro ha sido creado con éxito");
            }
            case 2 -> {
                Categoria categoria = Categoria.Revista;
                String periocidad = ip.leerTexto("Periocidad: ");
                recursos.add(new Revista(estado, titulo,categoria, periocidad));
                notificaciones.notificar("La revista ha sido creada con éxito");
            }
            case 3 -> {
                Categoria categoria = Categoria.Audiolibro;
                String duracion = ip.leerTexto("Duracion: ");
                recursos.add(new Audiolibro(estado, titulo, categoria, duracion));
                notificaciones.notificar("El audiolibro ha sido creado con éxito");
            }
        }
    };

    public void buscar() throws RecursoNoDisponibleException {
        String titulo = null;
        String categoria = null;
        EstadoRecurso estado = null;
        Integer cantPaginas = null;
        String periodicidad = null;
        String duracion = null;

        System.out.println("\n--- BUSCAR RECURSO ---");
        System.out.println("Introduzca los criterios de búsqueda (deje en blanco si no desea filtrar por ese criterio):");

        titulo = ip.leerTexto("Título: ");
        categoria = ip.leerTexto("Categoría: ");
        estado = ip.leerEnumEstadoOpcional("Estado: ");
        cantPaginas = ip.leerEnteroOpcional("Cantidad de páginas (solo para libros): ");
        periodicidad = ip.leerTexto("Periodicidad (solo para revistas): ");
        duracion = ip.leerTexto("Duración (solo para audiolibros): ");

        List<RecursoDigital> resultados = buscarRecursos(titulo, categoria, estado, cantPaginas, periodicidad, duracion);
        listarRecursos(resultados);
    }

    public List<RecursoDigital> buscarRecursos(String titulo, String categoria,EstadoRecurso estado, Integer cantPaginas, String periodicidad, String duracion) throws RecursoNoDisponibleException {
        RecursoSearchVisitor visitor = new RecursoSearchVisitor(titulo, categoria, estado, cantPaginas, periodicidad, duracion);
        List<RecursoDigital> resultados = recursos.stream()
                .filter(recurso -> {
                    recurso.accept(visitor);
                    return visitor.isMatch();
                })
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new RecursoNoDisponibleException("No se encontró ningún recurso con el título: " + titulo);
        }
        return resultados;
    };

    private void listarRecursos(List<RecursoDigital> recursos) {
        System.out.println("\n--- LISTA DE RECURSOS ---");
        for (RecursoDigital recurso : recursos) {
            System.out.println(recurso.toString());
        }
    }

    public void ordenar(){
        int opcion;
        do {
            System.out.println("\n--- ORDENAR DE RECURSOS ---");
            System.out.println("1. Ordenar de forma ascendente");
            System.out.println("2. Ordenar de forma descendente");
            System.out.println("0. Salir");
            opcion = ip.leerEntero("Opcion: ");
            switch(opcion){
                case 0 -> System.out.println("Volviendo...");
                case 1 -> ordenarPorTituloAscendente();
                case 2 -> ordenarPorTituloDescendente();
                default -> System.out.println("Esa opción no existe. Intente de nuevo");
            }
        }while (opcion != 0);
    }

    public void ordenarPorTituloAscendente() {
        Collections.sort(recursos, new ComparadorRecursoTitulo());
        System.out.println("Se ordeno de forma ascendente de manera exitosa");
    }

    public void ordenarPorTituloDescendente() {
        Collections.sort(recursos, Collections.reverseOrder(new ComparadorRecursoTitulo()));
        System.out.println("Se ordeno de forma descendente de manera exitosa");
    }

}
