package Excepciones;

public class UsuarioNoEncontradoException extends Exception {
    public UsuarioNoEncontradoException() {
    }

    public UsuarioNoEncontradoException(String message) {
        super(message);
    }
}
