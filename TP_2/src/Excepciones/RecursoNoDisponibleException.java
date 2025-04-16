package Excepciones;

public class RecursoNoDisponibleException extends Exception{
    public RecursoNoDisponibleException() {
    }

    public RecursoNoDisponibleException(String message) {
        super(message);
    }
}
