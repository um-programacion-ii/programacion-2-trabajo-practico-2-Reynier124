package Recurso;

import Enum.EstadoRecurso;
import Enum.Categoria;
import Interface.RecursoDigital;
import Interface.RecursoVisitor;
import Util.IdGenerator;

public abstract class RecursoBase implements RecursoDigital {
    private String titulo;
    private final int id;
    private EstadoRecurso estado;
    private Categoria categoria;


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
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public abstract void accept(RecursoVisitor visitor);

    @Override
    public String toString() {
        return  "id=" + id +
                ", titulo=" + titulo +
                ", estado=" + estado;
    }
}
