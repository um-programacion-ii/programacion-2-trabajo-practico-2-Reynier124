package Gestor;

import Excepciones.RecursoNoDisponibleException;
import Excepciones.UsuarioNoEncontradoException;
import Interface.ServicioNotificaciones;
import Recordatorio.PreferenciasNotificacion;
import Sistema.SistemaNotificaciones;
import Sistema.SistemaDisponibilidad;
import Observer.RecursoObserver;
import Prestamo.Prestamo;
import Recurso.RecursoBase;
import Reserva.Reservas;
import Usuario.Usuario;
import Util.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GestorBiblioteca {
    private final GestorUsuarios gUsuario;
    private final GestorRecursos gRecurso;
    private final GestorReservas gReserva;
    private final SistemaNotificaciones gNotificacion;
    private final GestorPrestamos gPrestamo;
    private SistemaDisponibilidad gDisponibilidad;
    private final Input input;

    public GestorBiblioteca(Scanner scanner, ServicioNotificaciones servicioNotificacion,int hilos) {
        List<RecursoObserver> observers = new ArrayList<>();
        this.input = new Input(scanner);
        this.gNotificacion = new SistemaNotificaciones(servicioNotificacion, pedirPrefrencias(),hilos);
        observers.add(gNotificacion);
        this.gUsuario = new GestorUsuarios(scanner, servicioNotificacion, gNotificacion);
        this.gRecurso = new GestorRecursos(scanner, servicioNotificacion, observers);
        this.gReserva = new GestorReservas(scanner, servicioNotificacion, gUsuario, gRecurso, gNotificacion, hilos);
        this.gPrestamo = new GestorPrestamos(scanner, servicioNotificacion, gUsuario, gRecurso, gNotificacion, hilos);

        this.gDisponibilidad = new SistemaDisponibilidad(gNotificacion, gPrestamo);
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

    public List<RecursoBase> conseguirRecursosPrestamos(){
       List<Prestamo> prestamos = gPrestamo.getPrestamos();
       List<RecursoBase> recursos = new ArrayList<>();
       for (Prestamo prestamo : prestamos) {
           recursos.add((RecursoBase) prestamo.getRecurso());
       }
       return recursos;
    }

    public List<RecursoBase> conseguirRecursosReservas(){
        List<Reservas> reservas = gReserva.getReservas();
        List<RecursoBase> recursos = new ArrayList<>();
        for (Reservas reserva : reservas) {
            recursos.add((RecursoBase) reserva.getRecurso());
        }
        return recursos;
    }

    public List<Usuario> conseguirUsuarios(){
        Map<String, Usuario> usuarios = gUsuario.getUsuarios();
        return new ArrayList<>(usuarios.values());
    }
    /*
    public List<Usuario> conseguirUsuarios(){
        Map<String, Usuario> usuarios = gUsuario.getUsuarios();
        List<Usuario> usuarioList = new ArrayList<>();
        usuarioList.addAll(usuarios.values());
        return usuarioList;
    }
    */
    //Comprobar funcionamiento de esta función
    public List<RecursoBase> conseguirRecursos(){
        List<RecursoBase> lista1 = conseguirRecursosPrestamos();
        List<RecursoBase> lista2 = conseguirRecursosReservas();
        List<RecursoBase> listaRecursos = new ArrayList<>(lista1);
        listaRecursos.addAll(lista2);
        return listaRecursos;
    }

    public void terminar(){
        gPrestamo.apagarSistema();
        gReserva.apagarSistema();
        gNotificacion.cerrar();
    }

    public PreferenciasNotificacion pedirPrefrencias(){
        PreferenciasNotificacion preferencias = new PreferenciasNotificacion();
        System.out.println("Configura tus preferencias de notificación:");
        String decision = input.leerTexto("¿Deseas recibir notificaciones de tipo INFO? (s/n): ").toLowerCase();
        comprobarPrefrencia(decision, "info" , preferencias);
        decision = input.leerTexto("¿Deseas recibir notificaciones de tipo WARNING? (s/n): ").toLowerCase();
        comprobarPrefrencia(decision, "warning" , preferencias);
        decision = input.leerTexto("¿Deseas recibir notificaciones de tipo ERROR? (s/n): ").toLowerCase();
        comprobarPrefrencia(decision, "error" , preferencias);
        return preferencias;

    }

    public void comprobarPrefrencia(String decision , String tipo, PreferenciasNotificacion preferencias){
        switch(decision){
            case "s" -> configurarRecordatorio(tipo, preferencias);
            case "n" -> System.out.println("No se enviaran recordatorios de tipo INFO");
        }

    }

    public void configurarRecordatorio(String tipo, PreferenciasNotificacion preferencias){
        switch(tipo){
            case "info" -> {
                preferencias.setNotificarInfo(true);
            }
            case "warning" -> {preferencias.setNotificarWarning(true);}
            case "error" -> {preferencias.setNotificarError(true);}
        }
    }

    public void mostrarHistorialReportes(){
        System.out.println("\n--- HISTORIAL REPORTES ---");
        gNotificacion.mostrarHistorial();
    }

    public void configurarPreferencias(){
        PreferenciasNotificacion preferencias = pedirPrefrencias();
        gNotificacion.configurarPreferencias(preferencias);
    }
}
