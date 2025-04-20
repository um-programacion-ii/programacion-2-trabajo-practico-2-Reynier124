package Interface;

import Enum.Categoria;
import Enum.EstadoRecurso;
import Observer.RecursoObserver;

public interface RecursoDigital {
    int getId();
    EstadoRecurso getEstado();
    void setEstado(EstadoRecurso estado);
    Categoria getCategoria();
    void setCategoria(Categoria categoria);
    String getTitulo();
    void setTitulo(String titulo);

    void accept(RecursoVisitor visitor);

    void agregarObservador(RecursoObserver observador);
    void eliminarObservador(RecursoObserver observador);
    void notificarObservadores();
}
