package Sistema;

import Enum.EstadoRecurso;
import Enum.NivelUrgencia;
import Interface.Prestable;
import Interface.RecursoDigital;
import Interface.ServicioNotificaciones;
import Observer.RecursoObserver;
import Prestamo.Prestamo;
import Recordatorio.PreferenciasNotificacion;
import Recordatorio.Recordatorio;
import Reserva.Reservas;
import Usuario.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SistemaNotificaciones implements RecursoObserver {
    private final ExecutorService executorService;
    private final ServicioNotificaciones servicioNotificaciones;
    private  PreferenciasNotificacion preferencia;
    private final List<Recordatorio> historialRecordatorios = new ArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public SistemaNotificaciones(ServicioNotificaciones servicioNotificaciones, PreferenciasNotificacion preferencia, int hilos) {
        this.executorService = Executors.newFixedThreadPool(hilos);
        this.servicioNotificaciones = servicioNotificaciones;
        this.preferencia = preferencia;
    }

    public void notificarInfo(String mensaje){
        if (preferencia.isNotificarInfo()){
            servicioNotificaciones.notificar(mensaje);
            guardarRecordatorio(mensaje, NivelUrgencia.INFO);
        }
    };

    public void notificarWarning(String mensaje){
        if (preferencia.isNotificarWarning()){
            servicioNotificaciones.notificar(mensaje);
            guardarRecordatorio(mensaje, NivelUrgencia.WARNING);
        }
    };

    public void notificarDisponibilidad(RecursoDigital recurso) {
        executorService.submit(() -> {
            String mensaje = "El recurso '" + recurso.getTitulo() + "' está ahora disponible.";
            notificarInfo(mensaje);
        });
    }



    public void notificarDisponibilidadPrestamo(Prestamo prestamo) {
        executorService.submit(() -> {
            RecursoDigital recurso = convertirARecursoDigital(prestamo.getRecurso());
            String mensaje = "El préstamo del recurso '" + recurso.getTitulo() + "' ha sido creado.";
            notificarInfo(mensaje);

        });
    }

    public void notificarProcesamientoPrestamo(Prestamo prestamo) {
        executorService.submit(() -> {
            RecursoDigital recurso = convertirARecursoDigital(prestamo.getRecurso());
            String mensaje = "El préstamo del recurso '" + recurso.getTitulo() + "' ha sido procesado.";
            notificarInfo(mensaje);

        });
    }

    public void notificarDisponibilidadReserva(Reservas reserva) {
        executorService.submit(() -> {
            RecursoDigital recurso = convertirARecursoDigital(reserva.getRecurso());
            String mensaje = "La reserva del recurso '" + recurso.getTitulo() + "' ha sido creada.";
            notificarInfo(mensaje);

        });
    }

    public void notificarProcesamientoReserva(Reservas reserva) {
        executorService.submit(() -> {
            RecursoDigital recurso = convertirARecursoDigital(reserva.getRecurso());
            String mensaje = "La reserva del recurso '" + recurso.getTitulo() + "' ha sido procesada.";
            notificarInfo(mensaje);
        });
    }


    public void notificarCreacionUsuario(Usuario usuario) {
        executorService.submit(() -> {
            String mensaje = "Usuario creado: " + usuario.getNombre();
            notificarInfo(mensaje);
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

    public void programarRecordatorio(String mensaje, NivelUrgencia nivelUrgencia, LocalDateTime fechaHora) {
        Recordatorio recordatorio = new Recordatorio(mensaje, nivelUrgencia, fechaHora);
        long delay = calcularDelay(fechaHora);
        scheduler.schedule(() -> notificarRecordatorio(recordatorio), delay, TimeUnit.MILLISECONDS);
    }

    private long calcularDelay(LocalDateTime fechaHora) {
        LocalDateTime ahora = LocalDateTime.now();
        return java.time.Duration.between(ahora, fechaHora).toMillis();
    }

    private void notificarRecordatorio(Recordatorio recordatorio) {
        if (debeNotificar(recordatorio.getNivelUrgencia())) {
            System.out.println(recordatorio);
            historialRecordatorios.add(recordatorio);
        }
    }

    private boolean debeNotificar(NivelUrgencia nivelUrgencia) {
        switch (nivelUrgencia) {
            case INFO:
                return preferencia.isNotificarInfo();
            case WARNING:
                return preferencia.isNotificarWarning();
            case ERROR:
                return preferencia.isNotificarError();
            default:
                return false;
        }
    }

    public void mostrarHistorial() {
        for (Recordatorio recordatorio : historialRecordatorios) {
            System.out.println(recordatorio);
        }
    }

    public void configurarPreferencias(PreferenciasNotificacion preferencias) {
        this.preferencia = preferencias;
    }

    public PreferenciasNotificacion getPreferencia() {
        return preferencia;
    }

    public void guardarRecordatorio(String mensaje, NivelUrgencia nivelUrgencia) {
        Recordatorio recordatorio = new Recordatorio(mensaje, nivelUrgencia, LocalDateTime.now());
        historialRecordatorios.add(recordatorio);
    }

    @Override
    public void actualizar(RecursoDigital recurso) {
        if (recurso.getEstado() == EstadoRecurso.DISPONIBLE){
            notificarInfo("El recurso " + recurso.getTitulo() + " ahora esta disponible.");
        } else if (recurso.getEstado() == EstadoRecurso.PRESTADO) {
            notificarWarning("El recurso " + recurso.getTitulo() + " ha sido procesado.");
        }
    }


}
