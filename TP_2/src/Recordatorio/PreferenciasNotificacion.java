package Recordatorio;

public class PreferenciasNotificacion {
    private boolean notificarInfo;
    private boolean notificarWarning;
    private boolean notificarError;

    public PreferenciasNotificacion(boolean notificarInfo, boolean notificarWarning, boolean notificarError) {
        this.notificarInfo = notificarInfo;
        this.notificarWarning = notificarWarning;
        this.notificarError = notificarError;
    }

    public boolean isNotificarInfo() {
        return notificarInfo;
    }

    public boolean isNotificarWarning() {
        return notificarWarning;
    }

    public boolean isNotificarError() {
        return notificarError;
    }
}
