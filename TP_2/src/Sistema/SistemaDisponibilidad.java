package Sistema;

import Enum.EstadoRecurso;
import Gestor.GestorPrestamos;
import Interface.RecursoDigital;
import Observer.RecursoObserver;
import Util.Input;

import java.util.Scanner;

public class SistemaDisponibilidad implements RecursoObserver {
    private final SistemaNotificaciones notificaciones;
    private final GestorPrestamos gestorPrestamos;
    private final Input input;

    public SistemaDisponibilidad(SistemaNotificaciones notificaciones, GestorPrestamos gestorPrestamos) {
        this.notificaciones = notificaciones;
        this.gestorPrestamos = gestorPrestamos;
        this.input = new Input(new Scanner(System.in));
    }

    public void alertaDisponibilidad(RecursoDigital recurso) {
        System.out.println("La reserva del recurso '" + recurso.getTitulo() + "' ahora esta disponible.");
    }

    @Override
    public void actualizar(RecursoDigital recurso) {
        if (recurso.getEstado()== EstadoRecurso.RESERVADO){
            alertaDisponibilidad(recurso);
            String decision;
            do {
                decision = input.leerTexto("¿Le gustaría realizar un prestamo a este recurso? (S/N)").toLowerCase();
                switch (decision) {
                    case "s" -> gestorPrestamos.solicitarPrestamo(recurso);
                    case "n" -> System.out.println();
                    default -> System.out.println("Esa opcion no existe. Por favor intente de nuevo.");
                }
            }while(!decision.equals("s") && !decision.equals("n"));
        }

    }
}
