package Recurso;

import Enum.EstadoRecurso;
import Enum.Categoria;
import Interface.RecursoVisitor;

public class Revista extends RecursoBase {
    private String periodicidad;

    public Revista(EstadoRecurso estado, String titulo, Categoria categoria, String periodicidad) {
        super(estado, titulo,categoria);
        this.periodicidad = periodicidad;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    @Override
    public void accept(RecursoVisitor visitor) {
        visitor.visit(this);
    }



    @Override
    public String toString() {
        return super.toString() + ", periocidad=" + periodicidad;
    }
}
