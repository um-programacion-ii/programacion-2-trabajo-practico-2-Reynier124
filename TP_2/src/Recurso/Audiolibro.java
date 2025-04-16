package Recurso;

import Enum.EstadoRecurso;
import Enum.Categoria;

public class Audiolibro extends RecursoBase {
    private String duracion;

    public Audiolibro(EstadoRecurso estado, String titulo, Categoria categoria, String duracion) {
        super(estado, titulo, categoria);
        this.duracion = duracion;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return super.toString() + ", duracion=" + duracion;
    }
}
