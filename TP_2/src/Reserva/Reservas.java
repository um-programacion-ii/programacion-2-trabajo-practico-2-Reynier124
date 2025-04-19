package Reserva;

import Interface.RecursoDigital;
import Usuario.Usuario;

import java.util.Date;

public class Reservas implements Comparable<Reservas> {
    private Usuario usuario;
    private RecursoDigital recurso;
    private Date fechaReserva;
    private int prioridad;

    public Reservas(Usuario usuario, RecursoDigital recurso, int prioridad) {
        this.usuario = usuario;
        this.recurso = recurso;
        this.fechaReserva = new Date();
        this.prioridad = prioridad;
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

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public int compareTo(Reservas otraReserva) {
        return Integer.compare(this.prioridad, otraReserva.prioridad);
    }

    @Override
    public String toString() {
        return "Reservas{" +
                "usuario=" + usuario +
                ", recurso=" + recurso +
                ", fechaReserva=" + fechaReserva +
                ", prioridad=" + prioridad +
                '}';
    }
}
