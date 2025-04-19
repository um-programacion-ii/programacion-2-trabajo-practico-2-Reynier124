package Interface;

import Enum.Categoria;
import Enum.EstadoRecurso;

public interface RecursoDigital {
    int getId();
    EstadoRecurso getEstado();
    void setEstado(EstadoRecurso estado);
    Categoria getCategoria();
    void setCategoria(Categoria categoria);
    String getTitulo();
    void setTitulo(String titulo);

    void accept(RecursoVisitor visitor);
}
