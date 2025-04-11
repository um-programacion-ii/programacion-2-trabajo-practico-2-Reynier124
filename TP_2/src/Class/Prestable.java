package Class;

import java.time.LocalDateTime;
import Class.Usuario;

public interface Prestable {
    boolean estaDisponible();
    LocalDateTime getFechaDevolucion();
    void prestar(Usuario usuario);
}
