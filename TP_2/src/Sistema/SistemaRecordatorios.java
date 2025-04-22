package Sistema;

import Enum.NivelUrgencia;
import Recordatorio.Recordatorio;
import Recordatorio.PreferenciasNotificacion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SistemaRecordatorios {
    private final List<Recordatorio> historialRecordatorios = new ArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private PreferenciasNotificacion preferencias;

    public SistemaRecordatorios(PreferenciasNotificacion preferencias) {
        this.preferencias = preferencias;
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
                return preferencias.isNotificarInfo();
            case WARNING:
                return preferencias.isNotificarWarning();
            case ERROR:
                return preferencias.isNotificarError();
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
        this.preferencias = preferencias;
    }
}
