package Gestor;

import Excepciones.RecursoNoDisponibleException;
import Excepciones.UsuarioNoEncontradoException;
import Interface.ServicioNotificaciones;
import Recordatorio.PreferenciasNotificacion;
import Sistema.SistemaNotificaciones;
import Sistema.SistemaRecordatorios;
import Util.Input;

import java.util.Scanner;

public class GestorBiblioteca {
    private GestorUsuarios gUsuario;
    private GestorRecursos gRecurso;
    private GestorReservas gReserva;
    private SistemaNotificaciones gNotificacion;
    private GestorPrestamos gPrestamo;
    private Input input;

    public GestorBiblioteca(Scanner scanner, ServicioNotificaciones servicioNotificacion,int hilos) {
        this.gNotificacion = new SistemaNotificaciones(servicioNotificacion, hilos);
        this.gUsuario = new GestorUsuarios(scanner, servicioNotificacion, gNotificacion);
        this.gRecurso = new GestorRecursos(scanner, servicioNotificacion, gNotificacion);
        this.gReserva = new GestorReservas(scanner, servicioNotificacion, gUsuario, gRecurso, gNotificacion, hilos);
        this.gPrestamo = new GestorPrestamos(scanner, servicioNotificacion, gUsuario, gRecurso, gNotificacion, hilos);
        this.input = new Input(scanner);
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

    public PreferenciasNotificacion pedirPrefrencias(){
        PreferenciasNotificacion preferencias = new PreferenciasNotificacion();
        System.out.println("Configura tus preferencias de notificación:");
        String decision = input.leerTexto("¿Deseas recibir notificaciones de tipo INFO? (s/n): ").toLowerCase();
        comprobarPrefrencia(decision, "info" , preferencias);
        decision = input.leerTexto("\"¿Deseas recibir notificaciones de tipo WARNING? (s/n): ").toLowerCase();
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
}
