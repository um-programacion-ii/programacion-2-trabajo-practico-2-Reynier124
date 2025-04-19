package Prestamo;

import Interface.RecursoDigital;
import Usuario.Usuario;
import Enum.EstadoRecurso;

import java.util.Date;


public class Prestamo {
    private Usuario usuario;
    private RecursoDigital recurso;
    private Date fechaPrestamo;
    private Date fechaDevolucion;

    public Prestamo(Usuario usuario, RecursoDigital recurso) {
        this.usuario = usuario;
        this.recurso = recurso;
        this.fechaPrestamo = new Date();
        this.fechaDevolucion = null;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public RecursoDigital getRecurso() {
        return recurso;
    }

    public void setRecurso(RecursoDigital recurso) {
        this.recurso = recurso;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void realizarPrestamo() {
        if (recurso.getEstado() == EstadoRecurso.DISPONIBLE) {
            recurso.setEstado(EstadoRecurso.PRESTADO);
            System.out.println("Préstamo realizado con éxito.");
        } else {
            System.out.println("El recurso no está disponible para préstamo.");
        }
    }

    public void realizarDevolucion() {
        if (recurso.getEstado() == EstadoRecurso.PRESTADO) {
            recurso.setEstado(EstadoRecurso.DISPONIBLE);
            this.fechaDevolucion = new Date();
            System.out.println("Devolución realizada con éxito.");
        } else {
            System.out.println("El recurso no está prestado.");
        }
    }
}
