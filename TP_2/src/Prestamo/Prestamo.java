package Prestamo;

import Interface.Prestable;
import Usuario.Usuario;

import java.time.LocalDateTime;

public class Prestamo {
    private final Usuario usuario;
    private final Prestable recurso;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaDevolucion;
    private boolean devuelto;

    public Prestamo(Usuario usuario, Prestable recurso, LocalDateTime fechaDevolucion) {
        this.usuario = usuario;
        this.recurso = recurso;
        this.fechaEntrega = LocalDateTime.now();
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = false;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Prestable getRecurso() {
        return recurso;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "usuario=" + usuario +
                ", recurso=" + recurso +
                ", fechaEntrega=" + fechaEntrega +
                ", fechaDevolucion=" + fechaDevolucion +
                ", devuelto=" + devuelto +
                '}';
    }
}