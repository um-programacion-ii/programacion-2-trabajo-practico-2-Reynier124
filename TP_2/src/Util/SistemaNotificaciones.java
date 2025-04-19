package Util;

import Interface.RecursoDigital;
import Interface.ServicioNotificaciones;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SistemaNotificaciones {
    private ExecutorService executorService;
    private ServicioNotificaciones servicioNotificaciones;

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

    public void cerrar() {
        executorService.shutdown();
    }
}
