package Recurso;

import Enum.EstadoRecurso;
import Enum.Categoria;
import Interface.*;
import Observer.RecursoObserver;
import Usuario.Usuario;
import Util.IdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class RecursoBase implements RecursoDigital, Prestable, Renovable{
    private String titulo;
    private final int id;
    private EstadoRecurso estado;
    private Categoria categoria;
    private final List<RecursoObserver> observadores = new ArrayList<>();


    public RecursoBase(EstadoRecurso estado, String titulo, Categoria categoria) {
        this.id = IdGenerator.generateUniqueId(RecursoBase.class);
        this.titulo = titulo;
        this.estado = estado;
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public EstadoRecurso getEstado() {
        return estado;
    }

    public void setEstado(EstadoRecurso estado) {
        this.estado = estado;
        if (estado == EstadoRecurso.DISPONIBLE) {
            notificarObservadores();
        }
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void agregarObservador(RecursoObserver observador) {
        synchronized (observadores) {
            observadores.add(observador);
        }
    }

    public void eliminarObservador(RecursoObserver observador) {
        synchronized (observadores) {
            observadores.remove(observador);
        }
    }

    public void notificarObservadores() {
        synchronized (observadores) {
            for (RecursoObserver observador : observadores) {
                observador.actualizar(this);
            }
        }
    }

    @Override
    public abstract void accept(RecursoVisitor visitor);



    @Override
    public boolean estaDisponible() {
        return estado == EstadoRecurso.DISPONIBLE;
    }

    @Override
    public LocalDateTime getFechaDevolucion() {
        return LocalDateTime.now().plusDays(14);
    }

    @Override
    public void prestar(Usuario usuario) {
        setEstado(EstadoRecurso.PRESTADO);
    }

    @Override
    public void reservar(){
        setEstado(EstadoRecurso.RESERVADO);
    }

    @Override
    public void renovar(){
        notificarObservadores();
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", titulo=" + titulo +
                ", estado=" + estado;
    }
}
