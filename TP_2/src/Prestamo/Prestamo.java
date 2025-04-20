package Prestamo;

import Interface.Prestable;
import Usuario.Usuario;

import java.time.LocalDateTime;

public class Prestamo {
    private final Usuario usuario;
    private final Prestable recurso;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaDevolucion;

    public Prestamo(Usuario usuario, Prestable recurso, LocalDateTime fechaDevolucion) {
        this.usuario = usuario;
        this.recurso = recurso;
        this.fechaEntrega = LocalDateTime.now();
        this.fechaDevolucion = fechaDevolucion;
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

    @Override
    public String toString() {
        return "Prestamo{" +
                "usuario=" + usuario +
                ", recurso=" + recurso +
                ", fechaEntrega=" + fechaEntrega +
                ", fechaDevolucion=" + fechaDevolucion +
                '}';
    }
}