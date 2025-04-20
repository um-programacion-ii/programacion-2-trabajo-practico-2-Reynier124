package Interface;

import java.time.LocalDateTime;
import Usuario.Usuario;

public interface Prestable {
    boolean estaDisponible();
    LocalDateTime getFechaDevolucion();
    void prestar(Usuario usuario);
    void reservar();
}
