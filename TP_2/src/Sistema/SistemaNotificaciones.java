package Sistema;

import Enum.EstadoRecurso;
import Interface.Prestable;
import Interface.RecursoDigital;
import Interface.ServicioNotificaciones;
import Observer.RecursoObserver;
import Prestamo.Prestamo;
import Recurso.RecursoBase;
import Reserva.Reservas;
import Usuario.Usuario;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SistemaNotificaciones implements RecursoObserver {
    private final ExecutorService executorService;
    private final ServicioNotificaciones servicioNotificaciones;

    public SistemaNotificaciones(ServicioNotificaciones servicioNotificaciones, int hilos) {
        this.executorService = Executors.newFixedThreadPool(hilos);;
        this.servicioNotificaciones = servicioNotificaciones;
    }

    public void notificarDisponibilidad(RecursoDigital recurso) {
        executorService.submit(() -> {
            String mensaje = "El recurso '" + recurso.getTitulo() + "' está ahora disponible.";
            servicioNotificaciones.notificar(mensaje);
        });
    }

    public void notificarDisponibilidadPrestamo(Prestamo prestamo) {
        executorService.submit(() -> {
            RecursoDigital recurso = convertirARecursoDigital(prestamo.getRecurso());
            String mensaje = "El préstamo del recurso '" + recurso.getTitulo() + "' ha sido creado.";
            servicioNotificaciones.notificar(mensaje);
        });
    }

    public void notificarProcesamientoPrestamo(Prestamo prestamo) {
        executorService.submit(() -> {
            RecursoDigital recurso = convertirARecursoDigital(prestamo.getRecurso());
            String mensaje = "El préstamo del recurso '" + recurso.getTitulo() + "' ha sido procesado.";
            servicioNotificaciones.notificar(mensaje);
        });
    }

    public void notificarDisponibilidadReserva(Reservas reserva) {
        executorService.submit(() -> {
            RecursoDigital recurso = convertirARecursoDigital(reserva.getRecurso());
            String mensaje = "La reserva del recurso '" + recurso.getTitulo() + "' ha sido creada.";
            servicioNotificaciones.notificar(mensaje);
        });
    }

    public void notificarProcesamientoReserva(Reservas reserva) {
        executorService.submit(() -> {
            RecursoDigital recurso = convertirARecursoDigital(reserva.getRecurso());
            String mensaje = "La reserva del recurso '" + recurso.getTitulo() + "' ha sido procesada.";
            servicioNotificaciones.notificar(mensaje);
        });
    }

    public void notificarCreacionUsuario(Usuario usuario) {
        executorService.submit(() -> {
            String mensaje = "Usuario creado: " + usuario.getNombre();
            servicioNotificaciones.notificar(mensaje);
        });
    }

    public RecursoDigital convertirARecursoDigital(Prestable recurso) {
        if (recurso instanceof RecursoDigital) {
            return (RecursoDigital) recurso;
        }
        throw new IllegalArgumentException("El recurso no es un RecursoDigital");
    }

    public void cerrar() {
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                    System.err.println("Algunas tareas no se terminaron correctamente.");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void actualizar(RecursoDigital recurso) {
        if (recurso.getEstado() == EstadoRecurso.DISPONIBLE){
            servicioNotificaciones.notificar("El recurso " + recurso.getTitulo() + " ahora esta disponible.");
        } else if (recurso.getEstado() == EstadoRecurso.PRESTADO) {
            servicioNotificaciones.notificar("El recurso " + recurso.getTitulo() + " ha sido procesado.");
        }
    }
}
