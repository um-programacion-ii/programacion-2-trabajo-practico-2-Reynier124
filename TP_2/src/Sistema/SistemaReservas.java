package Sistema;

import Excepciones.RecursoNoDisponibleException;
import Interface.Prestable;
import Interface.RecursoDigital;
import Observer.ReservaObserver;
import Reserva.Reservas;
import Usuario.Usuario;
import Enum.EstadoRecurso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class SistemaReservas {
    private PriorityBlockingQueue<Reservas> colaReservas;
    private final ExecutorService procesadorReservas;
    private final List<ReservaObserver> observadores = new ArrayList<>();
    private final SistemaNotificaciones sistemaNotificaciones;

    public SistemaReservas(SistemaNotificaciones sistemaNotificaciones,int hilos) {
        this.colaReservas = new PriorityBlockingQueue<>();
        this.procesadorReservas = Executors.newFixedThreadPool(hilos);
        this.sistemaNotificaciones = sistemaNotificaciones;
        iniciarProcesamiento();
    }

    private void iniciarProcesamiento() {
        for (int i = 0; i < 5; i++) {
            procesadorReservas.submit(() -> {
                while (true) {
                    try {
                        Reservas reserva = colaReservas.take();
                        procesarReserva(reserva);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
        }
    }


    public void agregarReserva(Usuario usuario, Prestable recurso, int prioridad) throws RecursoNoDisponibleException {
        if (recurso.estaDisponible()){
            Reservas reserva = new Reservas(usuario, recurso, prioridad);
            colaReservas.put(reserva);
            sistemaNotificaciones.notificarDisponibilidadReserva(reserva);
        }else {
            throw new RecursoNoDisponibleException("El recurso no está disponible para reservar.");
        }

    }

    private void procesarReserva(Reservas reserva) {
        Prestable recurso = reserva.getRecurso();
        recurso.reservar();
        sistemaNotificaciones.notificarProcesamientoReserva(reserva);
        notificarObservadores(reserva);
    }

    public void agregarObservador(ReservaObserver o) {
        observadores.add(o);
    }

    public void eliminarObservador(ReservaObserver o) {
        observadores.remove(o);
    }

    private void notificarObservadores(Reservas reserva) {
        for (ReservaObserver o : observadores) {
            o.actualizar(reserva);
        }
    }

    public void cerrar() {
        procesadorReservas.shutdown();
    }
}
