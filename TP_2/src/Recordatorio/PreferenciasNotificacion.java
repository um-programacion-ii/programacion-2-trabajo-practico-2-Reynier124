package Recordatorio;

public class PreferenciasNotificacion {
    private boolean notificarInfo;
    private boolean notificarWarning;
    private boolean notificarError;

    public PreferenciasNotificacion() {
        this.notificarInfo = false;
        this.notificarWarning = false;
        this.notificarError = false;
    }

    public void setNotificarInfo(boolean notificarInfo) {
        this.notificarInfo = notificarInfo;
    }

    public void setNotificarWarning(boolean notificarWarning) {
        this.notificarWarning = notificarWarning;
    }

    public void setNotificarError(boolean notificarError) {
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
