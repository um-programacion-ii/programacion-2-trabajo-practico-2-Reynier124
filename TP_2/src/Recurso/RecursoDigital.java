package Recurso;

import Util.IdGenerator;

public abstract class RecursoDigital {
    private String titulo;
    private final int id;
    private Estado estado;

    public RecursoDigital(Estado estado, String titulo) {
        this.id = IdGenerator.generateUniqueId(RecursoDigital.class);
        this.estado = estado;
        this.titulo = titulo;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", titulo=" + titulo +
                ", estado=" + estado;
    }
}

