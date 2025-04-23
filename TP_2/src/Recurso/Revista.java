package Recurso;

import Enum.Periodicidad;
import Enum.EstadoRecurso;
import Enum.Categoria;
import Interface.RecursoVisitor;

public class Revista extends RecursoBase {
    private Periodicidad periodicidad;

    public Revista(EstadoRecurso estado, String titulo, Categoria categoria, Periodicidad periodicidad) {
        super(estado, titulo,categoria);
        this.periodicidad = periodicidad;
    }

    public Periodicidad getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Periodicidad periodicidad) {
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
