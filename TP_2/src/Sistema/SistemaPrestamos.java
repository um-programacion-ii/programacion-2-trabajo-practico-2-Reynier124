package Sistema;

import Excepciones.RecursoNoDisponibleException;
import Observer.PrestamoObserver;
import Interface.Prestable;
import Prestamo.Prestamo;
import Usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SistemaPrestamos {
    private final BlockingQueue<Prestamo> colaSolicitudes;
    private final ExecutorService procesadorPrestamos;
    private final List<PrestamoObserver> observadores = new ArrayList<>();
    private final SistemaNotificaciones sistemaNotificaciones;

    public SistemaPrestamos(SistemaNotificaciones sistemaNotificaciones, int hilos) {
        this.colaSolicitudes = new LinkedBlockingQueue<>();
        this.procesadorPrestamos = Executors.newFixedThreadPool(hilos);
        this.sistemaNotificaciones = sistemaNotificaciones;
        iniciarProcesamiento();
    }

    private void iniciarProcesamiento() {
        for (int i = 0; i < 5; i++) {
            procesadorPrestamos.submit(() -> {
                while (true) {
                    try {
                        Prestamo prestamo = colaSolicitudes.take();
                        procesarPrestamo(prestamo);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
        }
    }

    public void solicitarPrestamo(Usuario usuario, Prestable recurso) throws RecursoNoDisponibleException {
        if (recurso.estaDisponible()){
            Prestamo prestamo = new Prestamo(usuario, recurso, recurso.getFechaDevolucion());
            colaSolicitudes.add(prestamo);
            sistemaNotificaciones.notificarDisponibilidadPrestamo(prestamo);
        }else {
            throw new RecursoNoDisponibleException("El recurso no está disponible para préstamo.");
        }
    }

    private void procesarPrestamo(Prestamo prestamo) {
        Prestable recurso = prestamo.getRecurso();
        Usuario usuario = prestamo.getUsuario();
        usuario.reporte();
        recurso.prestar(usuario);
        sistemaNotificaciones.notificarProcesamientoPrestamo(prestamo);
        notificarObservadores(prestamo);
    }

    public void agregarObservador(PrestamoObserver o) {
        synchronized (observadores) {
            observadores.add(o);
        }
    }

    public void eliminarObservador(PrestamoObserver o) {
        synchronized (observadores) {
            observadores.add(o);
        }
    }

    private void notificarObservadores(Prestamo prestamo) {
        synchronized (observadores) {
            for (PrestamoObserver o : observadores) {
                o.actualizar(prestamo);
            }
        }
    }

    public void cerrar() {
        try {
            procesadorPrestamos.shutdown();
            if (!procesadorPrestamos.awaitTermination(1, TimeUnit.SECONDS)) {
                procesadorPrestamos.shutdownNow();

                if (!procesadorPrestamos.awaitTermination(1, TimeUnit.SECONDS)) {
                    System.err.println("Algunas tareas no se terminaron correctamente.");
                }
            }
        } catch (InterruptedException e) {
            procesadorPrestamos.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
