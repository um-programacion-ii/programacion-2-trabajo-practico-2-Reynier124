package Interface;

import Recurso.Libro;
import Recurso.Revista;
import Recurso.Audiolibro;

public interface RecursoVisitor {
    void visit(Libro libro);
    void visit(Revista revista);
    void visit(Audiolibro audiolibro);
}
