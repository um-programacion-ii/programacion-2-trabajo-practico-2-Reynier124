package Recordatorio;

import java.time.LocalDateTime;
import Enum.NivelUrgencia;

public class Recordatorio {
    private String mensaje;
    private NivelUrgencia nivelUrgencia;
    private LocalDateTime fechaHora;

    public Recordatorio(String mensaje, NivelUrgencia nivelUrgencia, LocalDateTime fechaHora) {
        this.mensaje = mensaje;
        this.nivelUrgencia = nivelUrgencia;
        this.fechaHora = fechaHora;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public NivelUrgencia getNivelUrgencia() {
        return nivelUrgencia;
    }

    public void setNivelUrgencia(NivelUrgencia nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "[" + fechaHora + "] [" + nivelUrgencia + "] " + mensaje;
    }
}
