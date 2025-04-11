package Class;

public abstract class RecursoDigital {
    private String titulo;
    private final int id;
    private String estado;

    public RecursoDigital(String estado, String titulo) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", titulo='" + titulo +
                ", estado='" + estado;
    }
}

