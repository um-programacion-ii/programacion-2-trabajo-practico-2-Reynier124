package Gestor;

import Excepciones.RecursoNoDisponibleException;
import Excepciones.UsuarioNoEncontradoException;
import Interface.ServicioNotificaciones;
import Observer.RecursoObserver;
import Sistema.SistemaNotificaciones;
import Util.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorBiblioteca {
    private GestorUsuarios gUsuario;
    private GestorRecursos gRecurso;
    private GestorReservas gReserva;
    private SistemaNotificaciones gNotificacion;
    private GestorPrestamos gPrestamo;

    public GestorBiblioteca(Scanner scanner, ServicioNotificaciones servicioNotificacion,int hilos) {
        List<RecursoObserver> observers = new ArrayList<>();
        this.gNotificacion = new SistemaNotificaciones(servicioNotificacion, 3);
        observers.add(gNotificacion);
        this.gUsuario = new GestorUsuarios(scanner, servicioNotificacion, gNotificacion);
        this.gRecurso = new GestorRecursos(scanner, servicioNotificacion, observers);
        this.gReserva = new GestorReservas(scanner, servicioNotificacion, gUsuario, gRecurso, gNotificacion, hilos);
        this.gPrestamo = new GestorPrestamos(scanner, servicioNotificacion, gUsuario, gRecurso, gNotificacion, hilos);
        gRecurso.getObservadores().add(gPrestamo);
    }

    public void crear(String opcion) throws RecursoNoDisponibleException {
        switch(opcion){
            case "Usuario" -> {gUsuario.crear();}
            case "Recurso" -> {gRecurso.crear();}
            case "Reserva" -> {gReserva.crear();}
            case "Prestamo" -> {gPrestamo.crear();}
        }
    }

    public void buscar(String opcion) throws RecursoNoDisponibleException, UsuarioNoEncontradoException {
        switch(opcion){
            case "Usuario" -> {gUsuario.buscar();}
            case "Recurso" -> {gRecurso.buscar();}
            case "Reserva" -> {gReserva.buscar();}
            case "Prestamo" -> {gPrestamo.buscar();}
        }
    }

    public void ordenar(String opcion){
        switch(opcion){
            case "Usuario" -> {gUsuario.ordenar();}
            case "Recurso" -> {gRecurso.ordenar();}
            case "Reserva" -> {gReserva.ordenar();}
            case "Prestamo" -> {gPrestamo.ordenar();}
        }
    }

    public void listar(String opcion){
        switch (opcion){
            case "Usuario" -> {gUsuario.listarUsuarios(gUsuario.getUsuarios());}
            case "Recurso" -> {gRecurso.listarRecursos(gRecurso.getRecursos());}
            case "Reserva" -> {gReserva.listarReservas(gReserva.getReservas());}
            case "Prestamo" -> {gPrestamo.listarPrestamos(gPrestamo.getPrestamos());}
        }
    }

    public void terminar(){
        gPrestamo.apagarSistema();
        gReserva.apagarSistema();
        gNotificacion.cerrar();
    }
}
