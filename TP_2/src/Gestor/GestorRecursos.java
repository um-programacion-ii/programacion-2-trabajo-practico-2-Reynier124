package Gestor;

import Enum.Categoria;
import Enum.EstadoRecurso;
import Enum.Periodicidad;
import Excepciones.RecursoNoDisponibleException;
import Interface.RecursoDigital;
import Interface.ServicioNotificaciones;
import Observer.RecursoObserver;
import Recurso.*;
import Comparadores.ComparadorRecursoTitulo;
import Sistema.SistemaNotificaciones;
import Util.Input;
import Util.RecursoSearchVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GestorRecursos {
    private List<RecursoDigital> recursos;
    private List<RecursoObserver> observadores;
    private Input ip;
    private Scanner sc;
    private SistemaNotificaciones sistemaNotificaciones;

    public GestorRecursos(SistemaNotificaciones sistemaNotificaciones,List<RecursoObserver> observadores) {
        this.sc = new Scanner(System.in);
        this.ip = new Input(new Scanner(System.in));
        recursos = new ArrayList<>();
        this.sistemaNotificaciones = sistemaNotificaciones;
        this.observadores = observadores;
    }

    public List<RecursoDigital> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<RecursoDigital> recursos) {
        this.recursos = recursos;
    }

    public List<RecursoObserver> getObservadores() {
        return observadores;
    }

    public void crear(){
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
                try{
                    String titulo = ip.leerTexto("Título del libro: ");
                    if (recursos.stream().anyMatch(r -> r.getTitulo().equalsIgnoreCase(titulo))) {
                        throw new RecursoNoDisponibleException("El recurso con el título '" + titulo + "' ya existe.");
                    }
                    crearRecurso(tipo, titulo);
                }catch (RecursoNoDisponibleException e) {
                    System.out.println("Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Ocurrió un error inesperado: " + e.getMessage());
                }

            }else {
                System.out.println("No es ninguna de las opciones. Intente de nuevo.");
            }
        }while (tipo != 0);
    }

    public void crearRecurso(int tipo, String titulo) {
        switch (tipo){
            case 1 -> {
                Categoria categoria = Categoria.Libro;
                int cant_paginas = ip.leerEntero("Cantidad de paginas: ");
                RecursoDigital recurso = new Libro(EstadoRecurso.DISPONIBLE, titulo,categoria, cant_paginas);
                for (RecursoObserver observador : observadores) {
                    recurso.agregarObservador(observador);
                }
                recursos.add(recurso);
                this.sistemaNotificaciones.notificarInfo("El libro ha sido creado con éxito");
            }
            case 2 -> {
                Categoria categoria = Categoria.Revista;
                Periodicidad periocidad = ip.leerEnumPerioricidad();
                RecursoDigital recurso = new Revista(EstadoRecurso.DISPONIBLE, titulo,categoria, periocidad);
                for (RecursoObserver observador : observadores) {
                    recurso.agregarObservador(observador);
                }
                recursos.add(recurso);
                sistemaNotificaciones.notificarInfo("La revista ha sido creada con éxito");
            }
            case 3 -> {
                Categoria categoria = Categoria.Audiolibro;
                String duracion = ip.leerDuracion("Duracion (hh:mm:ss): ");
                RecursoDigital recurso = new Audiolibro(EstadoRecurso.DISPONIBLE, titulo,categoria, duracion);
                for (RecursoObserver observador : observadores) {
                    recurso.agregarObservador(observador);
                }
                recursos.add(recurso);
                sistemaNotificaciones.notificarInfo("El audiolibro ha sido creado con éxito");
            }
        }
    };


    public void buscar(){
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
        try{
            List<RecursoDigital> resultados = buscarRecursos(titulo, categoria, estado, cantPaginas, periodicidad, duracion);
            listarRecursos(resultados);
        } catch (RecursoNoDisponibleException e) {
            System.out.println("Error: " + e.getMessage());
        }catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }

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

    public void listarRecursos(List<RecursoDigital> recursos) {
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

    public RecursoDigital conseguirRecurso() {
        System.out.println("Ingrese el título o id del recurso: ");
        String input = sc.nextLine().trim();

        try {
            int id = Integer.parseInt(input);
            for (RecursoDigital recurso : recursos) {
                if (recurso.getId() == id) {
                    return recurso;
                }
            }
        } catch (NumberFormatException e) {
            for (RecursoDigital recurso : recursos) {
                if (recurso.getTitulo().equalsIgnoreCase(input)) {
                    return recurso;
                }
            }
        }

        System.out.println("Recurso no encontrado.");
        return null;
    }

    public void cambiarEstado(){
        RecursoDigital recurso = conseguirRecurso();
        recurso.setEstado(EstadoRecurso.DISPONIBLE);
    }

}
